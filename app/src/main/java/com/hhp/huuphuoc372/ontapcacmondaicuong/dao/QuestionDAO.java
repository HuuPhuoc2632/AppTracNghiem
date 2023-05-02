package com.hhp.huuphuoc372.ontapcacmondaicuong.dao;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private DBHelper dbHelper;

    public QuestionDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    public List<Question> findQuestionBySubject(String subject) {
        List<Question> QList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM question WHERE subject = '" + subject + "'";
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            do {
                Question q = new Question(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getString(7)
                        );
                QList.add(q);
            } while (cursor.moveToNext());
        }
        return QList;
    }
    public Question findSingleQuestionById(int id) {
        Question question = new Question();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM question WHERE id = '" + id + "'";
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            do {
                question = new Question(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getString(7)
                );
            } while (cursor.moveToNext());
        }
        return question;
    }

}
