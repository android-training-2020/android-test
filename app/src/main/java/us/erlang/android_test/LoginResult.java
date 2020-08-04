package us.erlang.android_test;

public class LoginResult {
    public String getMessage() {
        return message;
    }

    public LoginResult(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
