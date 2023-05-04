package com.hhp.huuphuoc372.ontapcacmondaicuong.model;

import android.content.Context;

import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.DBHelper;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.QuestionDAO;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;

public class Exam {
    private int idExam;
    private String idSubject;
    private List<QuestionAnswered> qaList;
    private String date;

    public int getIdExam() {
        return idExam;
    }

    public void setIdExam(int idExam) {
        this.idExam = idExam;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public List<QuestionAnswered> getQaList() {
        return qaList;
    }

    public void setQaList(List<QuestionAnswered> qaList) {
        this.qaList = qaList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Exam(int idExam, String idSubject, List<QuestionAnswered> qaList, String date) {
        this.idExam = idExam;
        this.idSubject = idSubject;
        this.qaList = qaList;
        this.date = date;
    }

    public Exam() {
    }
    public double pointOfExam(Context context){
        double result = (double) checkSumAnswerCorrect(context) * ((float) 10 / (float) 30);
        DecimalFormat f = new DecimalFormat("##.00");
        return Double.parseDouble(f.format(result));
    }
    private int checkSumAnswerCorrect(Context context) {
        int sumCorrect = 0;
        DBHelper dbHelper = new DBHelper(context);
        for (QuestionAnswered qa :
                qaList) {
            Question q = new QuestionDAO(dbHelper).findSingleQuestionById(qa.getIdQuestion());
            if (q.getAnswer() - 1 == qa.getAnswerOfQuestion()) {
                sumCorrect++;
            }
        }
        return sumCorrect;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "idExam=" + idExam +
                ", idSubject='" + idSubject + '\'' +
                ", qaList=" + qaList +
                ", date=" + date +
                '}';
    }
}
