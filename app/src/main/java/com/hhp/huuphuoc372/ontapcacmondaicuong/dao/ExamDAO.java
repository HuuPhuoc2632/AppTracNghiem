package com.hhp.huuphuoc372.ontapcacmondaicuong.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Exam;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.QuestionAnswered;

import java.util.ArrayList;
import java.util.List;

public class ExamDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private QuestionAnsweredDAO qaDAO;
    private long kq;

    public ExamDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        db = dbHelper.getWritableDatabase();
    }
    public boolean insertExam(Exam ex){
        if(ex.getDate()!=null) {
            ContentValues values = new ContentValues();
            values.put("subject", ex.getIdSubject());
            values.put("date", ex.getDate());
            kq = db.insert("history", null, values);
        }
        return kq > 0;
    }
    public Exam getExamNew(){
        String query = "SELECT * FROM history";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToLast();
        Exam ex = new Exam(
                cursor.getInt(0),
                cursor.getString(1),
                null,
                cursor.getString(2)
        );
        return ex;
    }
    public List<Exam> getExam(){
        List<Exam> exList = new ArrayList<>();
        qaDAO = new QuestionAnsweredDAO(dbHelper);
        String query = "SELECT * FROM history";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            do {
                Exam ex = new Exam(
                        cursor.getInt(0),
                        cursor.getString(1),
                        qaDAO.getListQA(cursor.getInt(0)),
                        cursor.getString(2)
                );
                exList.add(ex);
            } while (cursor.moveToNext());
        }
        return exList;
    }
    public boolean deleteExam(int id){
        int kq = db.delete("history", "id=?", new String[]{id+""});
        return kq>0;
    }
}
