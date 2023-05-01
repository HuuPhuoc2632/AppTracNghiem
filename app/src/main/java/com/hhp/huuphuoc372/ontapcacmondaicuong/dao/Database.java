package com.hhp.huuphuoc372.ontapcacmondaicuong.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Question;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    //create database
    private static final String DATABASE_NAME = "Question.db";
    private static final int VERSION = 1;

    private SQLiteDatabase db;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
        // bảng question
        final String sql = "CREATE TABLE question(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "question TEXT, a TEXT, B TEXT, C TEXT, D TEXT, answer INTEGER, subject TEXT)";
        db.execSQL(sql);

    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS question");
    }

    public List<Question> getQuestionBySubject() {
        List<Question> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        @SuppressLint("Recycle") Cursor rs = sqLiteDatabase.query("question", null, null, null,
                null, null, null);

        while(rs!=null&&rs.moveToNext()){
            int id = rs.getInt(0);
            String question = rs.getString(1);
            String a = rs.getString(2);
            String b = rs.getString(3);
            String c = rs.getString(4);
            String d = rs.getString(5);
            int answer = rs.getInt(6);
            String sub = rs.getString(7);
            list.add(new Question(id, question,a ,b,c,d, answer,sub));
        }

        return list;
    }

}
