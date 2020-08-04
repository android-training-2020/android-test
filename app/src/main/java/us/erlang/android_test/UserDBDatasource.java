package us.erlang.android_test;

import android.content.Context;

import androidx.room.Room;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class UserDBDatasource {
    private UserRepository userRepository;

    public UserDBDatasource(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "db_users").build();
        userRepository = db.getUserRepository();
    }

    Maybe<User> findByName(String name){
        return this.userRepository.findByName(name);
    }

    Completable save(User user){
        user.password = caculMD5Hash(user.password);
        return this.userRepository.save(user);
    }

    public String caculMD5Hash(String text)  {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.reset();
        m.update(text.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        return bigInt.toString(16);
    }
}
