package ch.jacem.for_keycloak.email_otp_authenticator;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class EmailOTPValidationRequireActionFactory implements RequiredActionFactory {

    private static final EmailOTPValidationRequireAction SINGLETON = new EmailOTPValidationRequireAction();

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return SINGLETON;
    }


    @Override
    public String getId() {
        return EmailOTPValidationRequireAction.PROVIDER_ID;
    }

    @Override
    public String getDisplayText() {
        return "Email OTP Validation";
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }
}
