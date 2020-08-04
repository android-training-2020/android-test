package us.erlang.android_test;

public class LoginResult {
    public String getErrorMessage() {
        return errorMessage;
    }

    public LoginResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorMessage;
}
