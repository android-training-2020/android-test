package us.erlang.android_test;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface UserRepository {
    @Query("select * from users where name=:name")
    Maybe<User> findByName(String name);

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    Completable save(User user);
}
