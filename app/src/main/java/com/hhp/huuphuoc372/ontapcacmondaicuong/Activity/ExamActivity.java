package com.hhp.huuphuoc372.ontapcacmondaicuong.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hhp.huuphuoc372.ontapcacmondaicuong.R;
import com.hhp.huuphuoc372.ontapcacmondaicuong.adapter.ItemGridViewAdapter;
import com.hhp.huuphuoc372.ontapcacmondaicuong.adapter.ResultGridviewApdater;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.DBHelper;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.ExamDAO;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.QuestionAnsweredDAO;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.QuestionDAO;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Exam;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Question;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.QuestionAnswered;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExamActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvcountDown, tvnameSubject, tvquestionContent, tvnumberQuestion, tvListQuestion, tvSumCorrect, tvResult;
    private Button btnSubmit, btnNext, btnPre, btnClose, btnCloseResult, btnReview;
    private RadioGroup groupAnswer;
    private RadioButton rdbtnAnswer1, rdbtnAnswer2, rdbtnAnswer3, rdbtnAnswer4;
    private GridView gvListQuestion, gvListQuestionResult;
    private List<Question> questionList;
    private Context context;
    private int numQuestion = 0;
    private List<Question> listQuestionFull;
    private List<QuestionAnswered> qaList = new ArrayList<>();
    private int num = 0;
    private int numQ;
    private String subject;
    private CountDownTimer cdt;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mapping();
        getCountDown();
        Intent intent = getIntent();
        subject = intent.getStringExtra("name");
        listQuestionFull = getQuestion(getIdSubject(subject));
        questionList = listQuestionFull.subList(0, 40);
        dbHelper = new DBHelper(getApplicationContext());
        Collections.shuffle(questionList);
        int i = 0;
        for (Question q : questionList) {
            QuestionAnswered qa = new QuestionAnswered(i, q.getId(), 0);
            i++;
            qaList.add(qa);
        }
        showQuestion();
        btnNext.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        tvListQuestion.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
        builder.setMessage("Bài làm sẽ không được lưu, bạn có chắc muốn thoát");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cdt.cancel();
                Intent intent1 = new Intent(ExamActivity.this, MainActivity.class);
                startActivity(intent1);
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
    public String getIdSubject(String subject){
        if (subject.equalsIgnoreCase("Chủ nghĩa Xã hội Khoa học")) {
            return "CNXHKH";
        }
        else if(subject.equalsIgnoreCase("Lịch sử Đảng Cộng Sản Việt Nam")){
            return  "LSDCSVN";
        }
        return null;
    }

    private void showQuestion() {
        groupAnswer.clearCheck();
        tvnameSubject.setText(subject);
        tvnumberQuestion.setText("Câu hỏi số 1:");
        tvquestionContent.setText(questionList.get(0).getQuestion());
        rdbtnAnswer1.setText(questionList.get(0).getA());
        rdbtnAnswer2.setText(questionList.get(0).getB());
        rdbtnAnswer3.setText(questionList.get(0).getC());
        rdbtnAnswer4.setText(questionList.get(0).getD());
    }

    private void nextQuestion(int number) {
        if (num == questionList.size() - 1) {
            Toast.makeText(getApplicationContext(), "Đây đã là câu cuối cùng!!",
                    Toast.LENGTH_SHORT).show();
        } else {
            num = number + 1;
            numQ = num + 1;
            groupAnswer.clearCheck();
            tvListQuestion.setText("Câu " + numQ);
            tvnumberQuestion.setText("Câu hỏi số " + numQ + ":");
            tvquestionContent.setText(questionList.get(num).getQuestion());
            rdbtnAnswer1.setText(questionList.get(num).getA());
            rdbtnAnswer2.setText(questionList.get(num).getB());
            rdbtnAnswer3.setText(questionList.get(num).getC());
            rdbtnAnswer4.setText(questionList.get(num).getD());
            setCheckedForRadiobutton(num);
        }
    }

    private void showQuestionByNumber(int number) {
        tvListQuestion.setText("Câu " + number);
        tvnumberQuestion.setText("Câu hỏi số " + number + ":");
        tvquestionContent.setText(questionList.get(number - 1).getQuestion());
        rdbtnAnswer1.setText(questionList.get(number - 1).getA());
        rdbtnAnswer2.setText(questionList.get(number - 1).getB());
        rdbtnAnswer3.setText(questionList.get(number - 1).getC());
        rdbtnAnswer4.setText(questionList.get(number - 1).getD());
        num = number - 1;
        setCheckedForRadiobutton(num);
    }

    public void setCheckedForRadiobutton(int num) {
        if (qaList.size() > 0) {
            for (QuestionAnswered qa : qaList) {
                if (qa.getNumberQuestion() == num) {
                    switch (qa.getAnswerOfQuestion()) {
                        case 1:
                            rdbtnAnswer1.setChecked(true);
                            break;
                        case 2:
                            rdbtnAnswer2.setChecked(true);
                            break;
                        case 3:
                            rdbtnAnswer3.setChecked(true);
                            break;
                        case 4:
                            rdbtnAnswer4.setChecked(true);
                            break;
                        case 0:
                            rdbtnAnswer1.setChecked(false);
                            rdbtnAnswer2.setChecked(false);
                            rdbtnAnswer3.setChecked(false);
                            rdbtnAnswer4.setChecked(false);
                            break;
                    }
                    break;
                } else {
                    rdbtnAnswer1.setChecked(false);
                    rdbtnAnswer2.setChecked(false);
                    rdbtnAnswer3.setChecked(false);
                    rdbtnAnswer4.setChecked(false);
                }
            }
        }
    }

    private List<Question> getQuestion(String sub) {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        List<Question> qlist = new QuestionDAO(dbHelper).findQuestionBySubject(sub);
        return qlist;
    }

    private void getCountDown() {
      cdt = new CountDownTimer(1 * 60 * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                @SuppressLint("DefaultLocale") String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
                tvcountDown.setText("" + timeLeftFormatted);
                if (millisUntilFinished < 600000) { // Kiểm tra nếu còn dưới 10 phút
                    tvcountDown.setTextColor(Color.rgb(255, 165, 0)); // Thiết lập màu sắc của TextView sang màu cam
                }
                if (millisUntilFinished < 60000) { // Kiểm tra nếu còn dưới 1 phút
                    tvcountDown.setTextColor(Color.RED); // Thiết lập màu sắc của TextView sang màu đỏ
                }
            }

            public void onFinish() {
                tvcountDown.setText("Hết giờ");
                showDialogFullTime();
            }
        }.start();
       
    }

    private void showDialogFullTime() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
        builder.setMessage("Thời gian đã hết. Bài làm sẽ được tự động nộp!!");
        builder.setPositiveButton("Đã hiểu!", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialogResult();
            }
        });
        builder.show();
    }

    public void mapping() {
        tvcountDown = (TextView) findViewById(R.id.count_down);
        tvnameSubject = (TextView) findViewById(R.id.nameOfSubject);
        tvquestionContent = (TextView) findViewById(R.id.questionContent);
        tvnumberQuestion = (TextView) findViewById(R.id.numberQuestion);
        tvListQuestion = (TextView) findViewById(R.id.tvListQuestion);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPre = (Button) findViewById(R.id.btnPre);
        groupAnswer = (RadioGroup) findViewById(R.id.groupAnswer);
        rdbtnAnswer1 = (RadioButton) findViewById(R.id.rdbtnAnswer1);
        rdbtnAnswer2 = (RadioButton) findViewById(R.id.rdbtnAnswer2);
        rdbtnAnswer3 = (RadioButton) findViewById(R.id.rdbtnAnswer3);
        rdbtnAnswer4 = (RadioButton) findViewById(R.id.rdbtnAnswer4);
    }

    public int getAnswerSelected() {
        if (rdbtnAnswer1.isChecked()) {
            return 1;
        } else if (rdbtnAnswer2.isChecked()) {
            return 2;
        } else if (rdbtnAnswer3.isChecked()) {
            return 3;
        } else if (rdbtnAnswer4.isChecked()) {
            return 4;
        } else return 0;
    }

    public void updateQuestionWithAnswer() {
        int numCheck = -1;
        QuestionAnswered qa = new QuestionAnswered(num, questionList.get(num).getId(), getAnswerSelected());
        for (int i = 0; i < qaList.size(); i++) {
            if (qa.getNumberQuestion() == qaList.get(i).getNumberQuestion()) {
                numCheck = i;
                break;
            }
        }
        qaList.set(numCheck, qa);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                if (getAnswerSelected() != 0) {
                    for (QuestionAnswered qa :
                            qaList) {
                        if (qa.getNumberQuestion() == num) {
                            updateQuestionWithAnswer();
                            nextQuestion(num);
                            break;
                        }
                    }
                    break;
                } else {
                    nextQuestion(num);
                    break;
                }
            case R.id.btnPre:
                if (num == 0) {
                    Toast.makeText(getApplicationContext(), "Đây đã là câu đầu tiên!!",
                            Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (getAnswerSelected() != 0) {
                        for (QuestionAnswered qa :
                                qaList) {
                            if (qa.getNumberQuestion() == num) {
                                updateQuestionWithAnswer();
                                showQuestionByNumber(num);
                                break;
                            }
                        }
                        break;
                    } else {
                        showQuestionByNumber(num);
                        break;
                    }
                }
            case R.id.tvListQuestion:
                showDialogListQuestion();
                break;
            case R.id.btnSubmit:
                if (checkFullAnswer()) {
                    cdt.cancel();
                    showDialogResult();
                    break;
                } else {
                    showConfirmDialog();
                    break;
                }
        }
    }

    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
        if (!checkFullAnswer()) {
            builder.setMessage("Bạn vẫn còn câu hỏi chưa hoàn thành, xác nhận nộp bài?");
        } else {
            builder.setMessage("Bạn có chắc muốn nộp bài?");
        }
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialogResult();
                cdt.cancel();
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

    private boolean checkFullAnswer() {
        for (QuestionAnswered qa :
                qaList) {
            if (qa.getAnswerOfQuestion() == 0) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDialogResult() {
        LocalDateTime localDate = java.time.LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        String dateNow = formatter.format(localDate);
        updateQuestionWithAnswer();
        Exam ex = new Exam(0, subject,qaList, dateNow);
        insertExamToDatabase(ex);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_result);
        tvSumCorrect = dialog.findViewById(R.id.tvSumCorrect);
        tvResult = dialog.findViewById(R.id.tvResult);
        btnCloseResult = dialog.findViewById(R.id.btnClose);
        btnReview = dialog.findViewById(R.id.btnReview);
        tvSumCorrect.setText(checkSumAnswerCorrect() + "/" + "30");
        ResultGridviewApdater resultGridviewApdater = new ResultGridviewApdater(qaList, this);
        gvListQuestionResult = dialog.findViewById(R.id.gvListQuestionResult);
        gvListQuestionResult.setAdapter(resultGridviewApdater);
        float result = (float) checkSumAnswerCorrect() * ((float) 10 / (float) 30);
        DecimalFormat f = new DecimalFormat("##.00");
        tvResult.setText(f.format(result) + " Điểm!!");
        btnCloseResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent1 = new Intent(ExamActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamActivity.this, ReviewExamActivity.class);
                intent.putExtra("list", (Serializable) qaList);
                intent.putExtra("name", subject);
                intent.putExtra("category", -1);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    private void insertExamToDatabase(Exam ex) {
        ExamDAO examDAO = new ExamDAO(dbHelper);
        QuestionAnsweredDAO questionAnsweredDAO = new QuestionAnsweredDAO(dbHelper);
        if(examDAO.insertExam(ex)){
            Exam exam = examDAO.getExamNew();
            for (QuestionAnswered qa:
                    ex.getQaList()) {
                if (!questionAnsweredDAO.insertQA(qa, exam.getIdExam())){
                    Toast.makeText(this, "Lỗi khi lưu bài kiểm tra!!", Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(this, "Bạn có thể xem lại bài kiểm tra của mình trong phần lịch sử", Toast.LENGTH_SHORT).show();
        }

    }

    private int checkSumAnswerCorrect() {
        int sumCorrect = 0;
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        for (QuestionAnswered qa :
                qaList) {
            Question q = new QuestionDAO(dbHelper).findSingleQuestionById(qa.getIdQuestion());
            if (q.getAnswer() - 1 == qa.getAnswerOfQuestion()) {
                sumCorrect++;
            }
        }
        return sumCorrect;
    }

    private void showDialogListQuestion() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_list_question);
        dialog.setTitle("Bảng câu hỏi");
        ItemGridViewAdapter itemGridViewAdapter = new ItemGridViewAdapter(qaList, this);
        gvListQuestion = dialog.findViewById(R.id.gvListQuestionDialog);
        btnClose = dialog.findViewById(R.id.btnClose);
        gvListQuestion.setAdapter(itemGridViewAdapter);
        gvListQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getAnswerSelected() != 0) {
                    updateQuestionWithAnswer();
                    showQuestionByNumber(position + 1);
                    dialog.dismiss();
                } else {
                    showQuestionByNumber(position + 1);
                    dialog.dismiss();
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}