package ch.jacem.for_keycloak.email_otp_authenticator.credentials;

import org.keycloak.credential.CredentialModel;

public class EmailOTPFormCredentialModel extends CredentialModel {
    public static final String TYPE = "email-otp";


    public static EmailOTPFormCredentialModel create() {
        EmailOTPFormCredentialModel model = new EmailOTPFormCredentialModel();
        model.setType(TYPE);
        model.setCreatedDate(System.currentTimeMillis());
        return model;
    }

    public static EmailOTPFormCredentialModel createFromModel(CredentialModel credentialModel) {
        try {
            EmailOTPFormCredentialModel model = new EmailOTPFormCredentialModel();
            model.setCreatedDate(credentialModel.getCreatedDate());
            model.setType(TYPE);
            model.setId(credentialModel.getId());
            model.setCredentialData(credentialModel.getCredentialData());
            return model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
