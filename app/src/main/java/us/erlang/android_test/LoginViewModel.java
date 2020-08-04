package us.erlang.android_test;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResult> loginResult;

    public MutableLiveData<LoginResult> getLoginResult() {
        if (loginResult == null) {
            loginResult = new MutableLiveData<LoginResult>();
        }
        return loginResult;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
