package ch.jacem.for_keycloak.email_otp_authenticator;

import ch.jacem.for_keycloak.email_otp_authenticator.credentials.EmailOTPFormCredentialModel;
import org.keycloak.common.util.Time;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialTypeMetadata;
import org.keycloak.credential.CredentialTypeMetadataContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;

import java.util.*;
import java.util.stream.Stream;

public class EmailOTPFormCredentialProvider implements CredentialProvider<EmailOTPFormCredentialModel>, CredentialInputValidator, CredentialInputUpdater {

    protected final KeycloakSession session;

    public EmailOTPFormCredentialProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
        if (!(input instanceof UserCredentialModel)) {
            return false;
        }
        if (!input.getType().equals(getType())) {
            return false;
        }
        String challengeResponse = input.getChallengeResponse();
        if (challengeResponse == null) {
            return false;
        }

        CredentialModel credentialModel = user.credentialManager().getStoredCredentialById(input.getCredentialId());
        EmailOTPFormCredentialModel model = getCredentialFromModel(credentialModel);

        return true;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return getType().equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        if (!supportsCredentialType(credentialType)) return false;
        return user.credentialManager().getStoredCredentialsByTypeStream(credentialType).findAny().isPresent();
    }

    @Override
    public CredentialModel createCredential(RealmModel realm, UserModel user, EmailOTPFormCredentialModel credentialModel) {
        credentialModel.setCreatedDate(Time.currentTimeMillis());
        return user.credentialManager().createStoredCredential(credentialModel);
    }

    @Override
    public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
        Optional<CredentialModel> model = user.credentialManager().getStoredCredentialsByTypeStream(input.getType()).findFirst();
        if (model.isPresent()) {
            CredentialModel credentialModel = model.get();
            deleteCredential(realm, user, credentialModel.getId());
            createCredential(realm, user, EmailOTPFormCredentialModel.create());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteCredential(RealmModel realm, UserModel user, String credentialId) {
        return user.credentialManager().removeStoredCredentialById(credentialId);
    }

    @Override
    public EmailOTPFormCredentialModel getCredentialFromModel(CredentialModel model) {
        return EmailOTPFormCredentialModel.createFromModel(model);
    }

    @Override
    public CredentialTypeMetadata getCredentialTypeMetadata(CredentialTypeMetadataContext metadataContext) {
        return CredentialTypeMetadata.builder()
            .type(getType())
            .category(CredentialTypeMetadata.Category.TWO_FACTOR)
            .displayName("emailAuthenticator")
            .helpText("emailUpdate")
            .createAction(EmailOTPValidationRequireAction.PROVIDER_ID)
            .removeable(true)
            .build(session);
    }

    @Override
    public String getType() {
        return EmailOTPFormCredentialModel.TYPE;
    }

    @Override
    public Stream<String> getDisableableCredentialTypesStream(RealmModel realm, UserModel user) {
        return Stream.empty();
    }

    @Override
    public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
    }
}
