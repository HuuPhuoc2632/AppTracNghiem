package com.hhp.huuphuoc372.ontapcacmondaicuong.model;

public class QuestionAnswered {
    private int numberQuestion;
    private int idQuestion;
    private int answerOfQuestion;

    public int getNumberQuestion() {
        return numberQuestion;
    }

    public void setNumberQuestion(int numberQuestion) {
        this.numberQuestion = numberQuestion;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getAnswerOfQuestion() {
        return answerOfQuestion;
    }

    public void setAnswerOfQuestion(int answerOfQuestion) {
        this.answerOfQuestion = answerOfQuestion;
    }

    public QuestionAnswered(int numberQuestion, int idQuestion, int answerOfQuestion) {
        this.numberQuestion = numberQuestion;
        this.idQuestion = idQuestion;
        this.answerOfQuestion = answerOfQuestion;
    }

    public QuestionAnswered() {
    }

    @Override
    public String toString() {
        return "QuestionAnswered{" +
                "numberQuestion=" + numberQuestion +
                ", idQuestion=" + idQuestion +
                ", answerOfQuestion=" + answerOfQuestion +
                '}';
    }
}
