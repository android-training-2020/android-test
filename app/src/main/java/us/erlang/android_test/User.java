package us.erlang.android_test;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo
    public String name;
    @ColumnInfo
    public String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
