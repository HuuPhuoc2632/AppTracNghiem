package com.hhp.huuphuoc372.ontapcacmondaicuong.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hhp.huuphuoc372.ontapcacmondaicuong.R;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.DBHelper;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.Database;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.QuestionDAO;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Question;

import java.util.Collections;
import java.util.List;

public class ContestActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvSubject, tvQuestion, tvNumQuestion, tvAnswer1,
            tvAnswer2,tvAnswer3,tvAnswer4;
    private Button btnNext, btnCancel;

    private Database db;
    private Context context;
    private boolean check = true;
    private int numQuestion = 0;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);
        Intent intent = getIntent();
        String subject = intent.getStringExtra("name");
        if(subject.equalsIgnoreCase("Chủ nghĩa Xã hội Khoa học")){
            subject = "CNXHKH";
        }
        questionList = getQuestion(subject);
        Collections.shuffle(questionList);
        
        //anh xạ
        tvSubject = (TextView) findViewById(R.id.nameOfSubject);
        tvQuestion = (TextView) findViewById(R.id.questionContent);
        tvNumQuestion = (TextView) findViewById(R.id.numberQuestion);
        tvAnswer1 = (TextView) findViewById(R.id.answer1);
        tvAnswer2 = (TextView) findViewById(R.id.answer2);
        tvAnswer3 = (TextView) findViewById(R.id.answer3);
        tvAnswer4 = (TextView) findViewById(R.id.answer4);
        btnNext = (Button) findViewById(R.id.buttonNext);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check){
                    Toast.makeText(getApplicationContext(), "Bạn phải chọn một đáp án để sang câu tiếp theo!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                nextQuestion();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContestActivity.this);
                builder.setMessage("Bạn có chắc muốn dừng luyện tập ở đây?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Không làm gì cả
                    }
                });
                builder.show();
            }
        });

        tvSubject.setText(intent.getStringExtra("name"));
        tvAnswer1.setOnClickListener(this);
        tvAnswer2.setOnClickListener(this);
        tvAnswer3.setOnClickListener(this);
        tvAnswer4.setOnClickListener(this);
        nextQuestion();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ContestActivity.this);
        builder.setMessage("Bạn có chắc muốn dừng luyện tập ở đây?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Không làm gì cả
            }
        });
        builder.show();

    }


    @SuppressLint("SetTextI18n")
    public void nextQuestion(){
        setDefautOption();
        int num = numQuestion+1;
        tvNumQuestion.setText("Câu hỏi số "+num+":");
        tvQuestion.setText(questionList.get(numQuestion).getQuestion());
        tvAnswer1.setText(questionList.get(numQuestion).getA());
        tvAnswer2.setText(questionList.get(numQuestion).getB());
        tvAnswer3.setText(questionList.get(numQuestion).getC());
        tvAnswer4.setText(questionList.get(numQuestion).getD());
        numQuestion++;
    }


    public List<Question> getQuestion(String sub){
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        List<Question> qlist =  new QuestionDAO(dbHelper).findQuestionBySubject(sub);
        return qlist;
    }
    public void checkAnswer(TextView tv, int select, int answer){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(select==answer){
                    tv.setBackgroundResource(R.drawable.bg_true_answer);
                    check = true;
                }
                else{
                    tv.setBackgroundResource(R.drawable.bg_false_answer);
                    showAnswerCorrect(answer);
                    check = true;
                }
            }


        }, 400);
    }
    public void showAnswerCorrect(int ans){
        switch (ans){
            case 1:
                tvAnswer1.setBackgroundResource(R.drawable.bg_true_answer);
                break;
            case 2:
                tvAnswer2.setBackgroundResource(R.drawable.bg_true_answer);
                break;
            case 3:
                tvAnswer3.setBackgroundResource(R.drawable.bg_true_answer);
                break;
            case 4:
                tvAnswer4.setBackgroundResource(R.drawable.bg_true_answer);
                break;
        }
        check=true;
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        int answer = 0;
        if(!check) {
            switch (v.getId()) {
                case R.id.answer1:
                    tvAnswer1.setBackgroundResource(R.drawable.bg_selected_answer);
                    answer = questionList.get(numQuestion-1).getAnswer();
                    checkAnswer(tvAnswer1, 1, answer-1);
                    break;
                case R.id.answer2:
                    tvAnswer2.setBackgroundResource(R.drawable.bg_selected_answer);
                    answer = questionList.get(numQuestion-1).getAnswer();
                    checkAnswer(tvAnswer2, 2, answer-1);
                    break;
                case R.id.answer3:
                    tvAnswer3.setBackgroundResource(R.drawable.bg_selected_answer);
                    answer = questionList.get(numQuestion-1).getAnswer();
                    checkAnswer(tvAnswer3, 3, answer-1);
                    break;
                case R.id.answer4:
                    tvAnswer4.setBackgroundResource(R.drawable.bg_selected_answer);
                    answer = questionList.get(numQuestion-1).getAnswer();
                    checkAnswer(tvAnswer4, 4, answer-1);
                    break;

            }
        }
    }
    public void setDefautOption(){
        tvAnswer1.setBackgroundResource(R.drawable.bg_normal_answer);
        tvAnswer2.setBackgroundResource(R.drawable.bg_normal_answer);
        tvAnswer3.setBackgroundResource(R.drawable.bg_normal_answer);
        tvAnswer4.setBackgroundResource(R.drawable.bg_normal_answer);
        check = false;
    }


}