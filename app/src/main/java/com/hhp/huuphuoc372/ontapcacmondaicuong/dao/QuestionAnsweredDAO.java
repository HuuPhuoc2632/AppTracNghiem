package com.hhp.huuphuoc372.ontapcacmondaicuong.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Question;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.QuestionAnswered;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnsweredDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public QuestionAnsweredDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        db = dbHelper.getWritableDatabase();
    }
    public boolean insertQA(QuestionAnswered qa, int id){
        ContentValues values = new ContentValues();
        values.put("idHistory", id);
        values.put("idQuestion", qa.getIdQuestion());
        values.put("numQuestion", qa.getNumberQuestion());
        values.put("answer", qa.getAnswerOfQuestion());
        long kq = db.insert("history_detail", null, values);
        return kq>0;
    }
    public List<QuestionAnswered> getListQA(int id){
        List<QuestionAnswered> qaList = new ArrayList<>();
        String query = "SELECT * FROM history_detail WHERE idHistory = "+id;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            do {
                QuestionAnswered qa = new QuestionAnswered(
                    cursor.getInt(2),
                    cursor.getInt(1),
                    cursor.getInt(3)
                );
                qaList.add(qa);
            } while (cursor.moveToNext());
        }
        return qaList;
    }
public boolean deleteQA(int id){
        int kq = db.delete("history_detail", "idHistory=?", new String[]{id+""});
        return kq>0;
    }

}
