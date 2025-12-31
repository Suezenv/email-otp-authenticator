package ch.jacem.for_keycloak.email_otp_authenticator;

import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialProviderFactory;
import org.keycloak.models.KeycloakSession;

import ch.jacem.for_keycloak.email_otp_authenticator.credentials.EmailOTPFormCredentialModel;

public class EmailOTPFormCredentialProviderFactory implements CredentialProviderFactory<EmailOTPFormCredentialProvider> {

    public static final String PROVIDER_ID = "email-otp";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override

    public CredentialProvider<EmailOTPFormCredentialModel> create(KeycloakSession session) {
        return new EmailOTPFormCredentialProvider(session);
    }
}
