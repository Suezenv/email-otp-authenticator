package ch.jacem.for_keycloak.email_otp_authenticator;

import org.jboss.logging.Logger;
import ch.jacem.for_keycloak.email_otp_authenticator.credentials.EmailOTPFormCredentialModel;
import ch.jacem.for_keycloak.email_otp_authenticator.helpers.ConfigHelper;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.authentication.CredentialRegistrator;
import org.keycloak.authentication.authenticators.browser.AbstractUsernameFormAuthenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.sessions.AuthenticationSessionModel;
import jakarta.ws.rs.core.Response;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.messages.Messages;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.events.Errors;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.email.EmailTemplateProvider;
import org.keycloak.services.messages.Messages;
import jakarta.ws.rs.core.MultivaluedMap;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class EmailOTPValidationRequireAction implements RequiredActionProvider, CredentialRegistrator {

    private static final Logger logger = Logger.getLogger(EmailOTPValidationRequireAction.class);
    public static final String PROVIDER_ID = "email_validation_config";

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        // TODO: get the alias from somewhere else or move config into realm or application scope
        AuthenticatorConfigModel config = context.getRealm().getAuthenticatorConfigByAlias("sms-2fa");
        if (config == null) {
            logger.error("Failed to check 2FA enforcement, no config alias sms-2fa found");
            return;
        }

        // REMOVE ALL ACTION ICI
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        try {
            this.generateOtp(context, false);
            context.challenge(this.challenge(context, null, null));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            context.failure();
        }
    }

    @Override
    public void processAction(RequiredActionContext context) {
        MultivaluedMap<String, String> inputData = context.getHttpRequest().getDecodedFormParameters();
        AuthenticationSessionModel authenticationSession = context.getAuthenticationSession();

        UserModel user = context.getUser();
        boolean userEnabled = this.enabledUser(context, user);
        // the brute force lock might be lifted/user enabled in the meantime -> we need to clear the auth session note
        if (userEnabled) {
            context.getAuthenticationSession().removeAuthNote(AbstractUsernameFormAuthenticator.SESSION_INVALID);
        }
        if ("true".equals(context.getAuthenticationSession().getAuthNote(AbstractUsernameFormAuthenticator.SESSION_INVALID))) {
            context.getEvent().user(context.getUser()).error(Errors.INVALID_AUTHENTICATION_SESSION);
            // challenge already set by calling enabledUser() above
            return;
        }
        if (!userEnabled) {
            // error in context is set in enabledUser/isDisabledByBruteForce
            context.getAuthenticationSession().setAuthNote(AbstractUsernameFormAuthenticator.SESSION_INVALID, "true");
            return;
        }

        if (inputData.containsKey(EmailOTPFormAuthenticator.OTP_FORM_RESEND_ACTION_NAME)) {
            logger.debug("Resending a new OTP");
            // Regenerate and resend a new OTP
            this.generateOtp(context, true);
            // Reshow the form
            context.challenge(
                this.challenge(context, null, "infoResendingEmailOtp")
            );

            return;
        }

        String otp = inputData.getFirst(EmailOTPFormAuthenticator.OTP_FORM_CODE_INPUT_NAME);

        if (null == otp) {
            context.challenge(
                this.challenge(context, null, null)
            );

            return;
        }

        if (otp.isEmpty() || !otp.equals(authenticationSession.getAuthNote(EmailOTPFormAuthenticator.AUTH_NOTE_OTP_KEY))) {
            context.getEvent().user(user).error(Errors.INVALID_USER_CREDENTIALS);
            context.challenge(
                this.challenge(context, "errorInvalidEmailOtp", null)
            );

            return;
        }

        // Check if the OTP is expired
        if (this.isOtpExpired(context)) {
            // In this case, we generate a new OTP
            this.generateOtp(context, true);
            context.getEvent().user(user).error(Errors.EXPIRED_CODE);
            context.challenge(
                this.challenge(context, "errorExpiredEmailOtp", null)
            );

            return;
        }

        // OTP is correct
        authenticationSession.removeAuthNote(EmailOTPFormAuthenticator.AUTH_NOTE_OTP_KEY);
        if (!authenticationSession.getAuthenticatedUser().isEmailVerified()) {
            authenticationSession.getAuthenticatedUser().setEmailVerified(true);
        }

        //AuthenticationSessionModel authSession = context.getAuthenticationSession();
        EmailOTPFormCredentialProvider smnp = (EmailOTPFormCredentialProvider) context.getSession().getProvider(CredentialProvider.class, EmailOTPFormCredentialProviderFactory.PROVIDER_ID);
        if (!smnp.isConfiguredFor(context.getRealm(), context.getUser(), EmailOTPFormCredentialModel.TYPE)) {
            smnp.createCredential(context.getRealm(), context.getUser(), EmailOTPFormCredentialModel.create());
        } else {
            smnp.updateCredential(
                context.getRealm(),
                context.getUser(),
                new UserCredentialModel("random_id", "mobile-number", "email")
            );
        }
        context.success();
    }

    @Override
    public void close() {
    }

    @Override
    public String getCredentialType(KeycloakSession keycloakSession, AuthenticationSessionModel authenticationSessionModel) {
        return EmailOTPFormCredentialModel.TYPE;
    }

    private String generateOtp(RequiredActionContext context, boolean forceRegenerate) {
        // TODO: get the alias from somewhere else or move config into realm or application scope
        AuthenticatorConfigModel config = context.getRealm().getAuthenticatorConfigByAlias("email-2fa");
        // If the OTP is already set in the auth session and we are not forcing a regeneration, return it
        String existingOtp = context.getAuthenticationSession().getAuthNote(EmailOTPFormAuthenticator.AUTH_NOTE_OTP_KEY);
        if (!forceRegenerate && existingOtp != null && !existingOtp.isEmpty() && !this.isOtpExpired(context)) {
            return existingOtp;
        }

        String alphabet = ConfigHelper.getOtpCodeAlphabet(config);
        int length = ConfigHelper.getOtpCodeLength(config);

        // Generate a random `length` character string based on the `alphabet`
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder otpBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otpBuilder.append(alphabet.charAt(secureRandom.nextInt(alphabet.length())));
        }
        String otp = otpBuilder.toString();

        context.getAuthenticationSession().setAuthNote(EmailOTPFormAuthenticator.AUTH_NOTE_OTP_CREATED_AT, String.valueOf(System.currentTimeMillis() / 1000));
        context.getAuthenticationSession().setAuthNote(EmailOTPFormAuthenticator.AUTH_NOTE_OTP_KEY, otp);

        this.sendGeneratedOtp(context);

        return otp;
    }

    private void sendGeneratedOtp(RequiredActionContext context) {
        // TODO: get the alias from somewhere else or move config into realm or application scope
        AuthenticatorConfigModel config = context.getRealm().getAuthenticatorConfigByAlias("email-2fa");

        // If the OTP is not set in the auth session, fail
        String otp = context.getAuthenticationSession().getAuthNote(EmailOTPFormAuthenticator.AUTH_NOTE_OTP_KEY);
        if (null == otp || otp.isEmpty()) {
            logger.error("OTP is not set in the auth session.");

            context.getEvent().user(context.getUser()).error(Errors.INVALID_USER_CREDENTIALS);
            context.challenge(
                this.challenge(context, Messages.INTERNAL_SERVER_ERROR, null)
            );

            return;
        }

        UserModel user = context.getUser();
        String email = user.getEmail();

        if (email == null || email.isEmpty()) {
            logger.error("User does not have an email address configured.");
            context.getEvent().user(user).error(Errors.INVALID_EMAIL);
            context.challenge(
                this.challenge(context, Messages.INVALID_EMAIL, null)
            );

            return;
        }

        try {
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("otp", otp);
            attributes.put("ttl", ConfigHelper.getOtpLifetime(config));

            context.getSession()
                .getProvider(EmailTemplateProvider.class)
                .setRealm(context.getRealm())
                .setUser(user)
                .send(
                    EmailOTPFormAuthenticator.OTP_EMAIL_SUBJECT_KEY,
                    EmailOTPFormAuthenticator.OTP_EMAIL_TEMPLATE_NAME,
                    attributes
                );

            logger.debug("OTP email sent to " + user.getUsername());
        } catch (Exception e) {
            logger.error("Failed to send OTP email", e);

            context.getEvent().user(user).error(Errors.EMAIL_SEND_FAILED);
            context.challenge(
                this.challenge(context, Messages.EMAIL_SENT_ERROR, null)
            );
        }
    }

    private boolean isOtpExpired(RequiredActionContext context) {
        // TODO: get the alias from somewhere else or move config into realm or application scope
        AuthenticatorConfigModel config = context.getRealm().getAuthenticatorConfigByAlias("email-2fa");
        int lifetime = ConfigHelper.getOtpLifetime(config);
        long createdAt = Long.parseLong(context.getAuthenticationSession().getAuthNote(EmailOTPFormAuthenticator.AUTH_NOTE_OTP_CREATED_AT));
        long now = System.currentTimeMillis() / 1000;

        return ((now - lifetime) > createdAt);
    }

    protected Response challenge(RequiredActionContext context, String error, String info) {
        LoginFormsProvider form = context.form();

        if (error != null) {
            form.setError(error);
        }

        if (info != null) {
            form.setInfo(info);
        }

        form.setUser(context.getUser());

        return form.setAttribute("realm", context.getRealm())
            .createForm(EmailOTPFormAuthenticator.OTP_FORM_TEMPLATE_NAME);
    }

    public boolean enabledUser(RequiredActionContext context, UserModel user) {
        if (!user.isEnabled()) {
            context.getEvent().user(user);
            context.getEvent().error(Errors.USER_DISABLED);
            Response challengeResponse = challenge(context, Messages.ACCOUNT_DISABLED, null);
            context.challenge(challengeResponse);
            return false;
        }
        return true;
    }
}
