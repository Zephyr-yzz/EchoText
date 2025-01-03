package com.example.speechmate.data.entity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "paw";
    private static final String COLUMN_PHONE = "pho";
    private static final String COLUMN_SIGN = "sign";

    private static final String COLUMN_AVATAR = "avatar"; // 新增头像列

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        SQLiteDatabase db = getWritableDatabase();
//        onCreate(db);
    }

    //创建用户信息表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_PHONE + " TEXT NOT NULL, " +
                COLUMN_SIGN + " TEXT,"+
                COLUMN_AVATAR + " TEXT)";
        db.execSQL(CREATE_USER_TABLE);
//        Log.d("Yes","ok");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 插入用户信息
    public boolean insertUser(String username, String password, String phone, String sign, String avatar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_SIGN, sign);
        values.put(COLUMN_AVATAR, avatar);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1; // 返回插入是否成功
    }

    // 验证用户登录
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        return cursor.getCount() > 0; // 如果找到匹配的用户，则返回 true
    }

    // 获取用户信息
    public Cursor getUser(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
    }

    //更新用户信息
    public boolean updateUser(String oldUsername, String newUsername, String password, String phone, String sign, String avatar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_SIGN, sign);
        values.put(COLUMN_AVATAR, avatar);

        // 更新用户信息
        int result = db.update(TABLE_NAME, values, COLUMN_USERNAME + "=?", new String[]{oldUsername});
        return result > 0; // 返回更新是否成功
    }

    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE "+TABLE_NAME);
    }

    @SuppressLint("Range")
    public void GetUser(Cursor cursor){
        cursor.moveToFirst();
        while(cursor.moveToNext()){

            Log.d(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)),cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
        }
    }
    public void fun(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("PRAGMA table_info("+TABLE_NAME+")");
    }
}