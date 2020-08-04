package us.erlang.android_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private EditText userName;
    private EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userName = findViewById(R.id.user_name);
        userPassword = findViewById(R.id.user_password);

        Button createButton = findViewById(R.id.create_user);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initUser();
            }
        });

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(userName.getText().toString(), userPassword.getText().toString());
            }
        });
    }

    private void login(String name, final String password) {
        final UserDBDatasource dataSource = MyApplication.getInstance().getLocalDataSource();
        dataSource
                .findByName(userName.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableMaybeObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        if (dataSource.caculMD5Hash(password).equals(user.password)) {
                            showMessage("Login successfully");
                        } else {
                            showMessage("Password is invalid");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessage("Username does not exist");
                    }

                    @Override
                    public void onComplete() {
                        showMessage("Username does not exist");
                    }
                });
    }

    private void initUser() {
        User user = new User("android", "123456");
        MyApplication.getInstance().getLocalDataSource().save(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        showMessage("succeed to init user");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessage("failed to init user");
                    }
                });
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}