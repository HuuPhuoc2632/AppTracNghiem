package com.hhp.huuphuoc372.ontapcacmondaicuong.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.QuestionDAO;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Question;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.QuestionAnswered;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewExamActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvnameSubject, tvquestionContent, tvnumberQuestion, tvListQuestion;
    private Button btnNext, btnPre;
    private RadioGroup groupAnswer;
    private RadioButton rdbtnAnswer1, rdbtnAnswer2, rdbtnAnswer3, rdbtnAnswer4;
    private List<QuestionAnswered> qaList;
    private List<Question> questionList = new ArrayList<>();
    private int num = 0;
    private int numQ, category;
    private String subject = null;
    private GridView gvListQuestion;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_exam);
        Intent intent = getIntent();
        subject = intent.getStringExtra("name");
        category =  intent.getIntExtra("category", -1);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        qaList = (List<QuestionAnswered>)  intent.getSerializableExtra("list");
        for (QuestionAnswered qa:
             qaList) {
            Question q = new QuestionDAO(dbHelper).findSingleQuestionById(qa.getIdQuestion());
            if(q!=null){
                System.out.println("co them vo List: "+ q);
                questionList.add(q);
            }
        }
        mapping();
        getFuncShowQuestion();
        btnNext.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        tvListQuestion.setOnClickListener(this);

    }

    private void getFuncShowQuestion() {
        if(category==-1){
            showQuestion();
        }
        else{
            showQuestionByNumber(category+1);
        }
    }

    public String getIdSubject(String subject){
        if (subject.equalsIgnoreCase("Chủ nghĩa Xã hội Khoa học")) {
            return "CNXHKH";
        }
        return null;
    }

    public void mapping() {
        tvnameSubject = (TextView) findViewById(R.id.nameOfSubjectResult);
        tvquestionContent = (TextView) findViewById(R.id.questionContentResult);
        tvnumberQuestion = (TextView) findViewById(R.id.numberQuestionResult);
        tvListQuestion = (TextView) findViewById(R.id.tvListQuestionResult);
        btnNext = (Button) findViewById(R.id.btnNextResult);
        btnPre = (Button) findViewById(R.id.btnPreResult);
        groupAnswer = (RadioGroup) findViewById(R.id.groupAnswerResult);
        rdbtnAnswer1 = (RadioButton) findViewById(R.id.rdbtnAnswer1Result);
        rdbtnAnswer2 = (RadioButton) findViewById(R.id.rdbtnAnswer2Result);
        rdbtnAnswer3 = (RadioButton) findViewById(R.id.rdbtnAnswer3Result);
        rdbtnAnswer4 = (RadioButton) findViewById(R.id.rdbtnAnswer4Result);
    }
    private void showQuestion() {
        setDefaultBackground();
        tvnameSubject.setText(subject);
        tvnumberQuestion.setText("Câu hỏi số 1:");
        tvquestionContent.setText(questionList.get(0).getQuestion());
        rdbtnAnswer1.setText(questionList.get(0).getA());
        rdbtnAnswer2.setText(questionList.get(0).getB());
        rdbtnAnswer3.setText(questionList.get(0).getC());
        rdbtnAnswer4.setText(questionList.get(0).getD());
        setCheckedForRadiobutton(num);
        showAnswerCorrect();
    }

    private void setDefaultBackground() {
        rdbtnAnswer1.setBackgroundResource(R.drawable.bg_button_submit);
        rdbtnAnswer2.setBackgroundResource(R.drawable.bg_button_submit);
        rdbtnAnswer3.setBackgroundResource(R.drawable.bg_button_submit);
        rdbtnAnswer4.setBackgroundResource(R.drawable.bg_button_submit);
    }

    @SuppressLint("ResourceAsColor")
    private void showAnswerCorrect() {
        if(getButtonSelected()!=null) {
            if (getAnswerSelected() == questionList.get(num).getAnswer() - 1) {
                Objects.requireNonNull(getButtonSelected()).setBackgroundResource(R.drawable.bg_true_answer);
            } else {
                Objects.requireNonNull(getButtonSelected()).setBackgroundResource(R.drawable.bg_false_answer);
                Objects.requireNonNull(getTrueAnswer()).setBackgroundResource(R.drawable.bg_true_answer);
            }
        }
        else{
            rdbtnAnswer1.setBackgroundResource(R.drawable.bg_false_answer);
            rdbtnAnswer2.setBackgroundResource(R.drawable.bg_false_answer);
            rdbtnAnswer3.setBackgroundResource(R.drawable.bg_false_answer);
            rdbtnAnswer4.setBackgroundResource(R.drawable.bg_false_answer);
            Objects.requireNonNull(getTrueAnswer()).setBackgroundResource(R.drawable.bg_true_answer);
        }
    }

    private RadioButton getTrueAnswer() {
        switch (questionList.get(num).getAnswer()-1){
            case 1:
                return rdbtnAnswer1;
            case 2:
                return rdbtnAnswer2;
            case 3:
                return rdbtnAnswer3;
            case 4:
                return rdbtnAnswer4;
        }
        return null;
    }

    private RadioButton getButtonSelected() {
        switch (getAnswerSelected()){
            case 1:
                return rdbtnAnswer1;
            case 2:
                return rdbtnAnswer2;
            case 3:
                return rdbtnAnswer3;
            case 4:
                return rdbtnAnswer4;
            case 0:
                return null;
        }
        return null;
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



    private void showQuestionByNumber(int number) {
        setDefaultBackground();
        tvListQuestion.setText("Câu " + number);
        tvnumberQuestion.setText("Câu hỏi số " + number + ":");
        tvquestionContent.setText(questionList.get(number - 1).getQuestion());
        rdbtnAnswer1.setText(questionList.get(number - 1).getA());
        rdbtnAnswer2.setText(questionList.get(number - 1).getB());
        rdbtnAnswer3.setText(questionList.get(number - 1).getC());
        rdbtnAnswer4.setText(questionList.get(number - 1).getD());
        num = number - 1;
        setCheckedForRadiobutton(num);
        showAnswerCorrect();
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
            rdbtnAnswer1.setEnabled(false);
            rdbtnAnswer2.setEnabled(false);
            rdbtnAnswer3.setEnabled(false);
            rdbtnAnswer4.setEnabled(false);
        }
    }
    private void nextQuestion(int number) {
        if (num == questionList.size() - 1) {
            Toast.makeText(getApplicationContext(), "Đây đã là câu cuối cùng!!",
                    Toast.LENGTH_SHORT).show();
        } else {
            setDefaultBackground();
            num = number + 1;
            numQ = num + 1;
            tvListQuestion.setText("Câu " + numQ);
            tvnumberQuestion.setText("Câu hỏi số " + numQ + ":");
            tvquestionContent.setText(questionList.get(num).getQuestion());
            rdbtnAnswer1.setText(questionList.get(num).getA());
            rdbtnAnswer2.setText(questionList.get(num).getB());
            rdbtnAnswer3.setText(questionList.get(num).getC());
            rdbtnAnswer4.setText(questionList.get(num).getD());
            setCheckedForRadiobutton(num);
            showAnswerCorrect();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextResult:
                if (getAnswerSelected() != 0) {
                    for (QuestionAnswered qa :
                            qaList) {
                        if (qa.getNumberQuestion() == num) {
                            nextQuestion(num);
                            break;
                        }
                    }
                    break;
                } else {
                    nextQuestion(num);
                    break;
                }
            case R.id.btnPreResult:
                if (num == 0) {
                    Toast.makeText(getApplicationContext(), "Đây đã là câu đầu tiên!!",
                            Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (getAnswerSelected() != 0) {
                        for (QuestionAnswered qa :
                                qaList) {
                            if (qa.getNumberQuestion() == num) {
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
            case R.id.tvListQuestionResult:
                showDialogListQuestion();
                break;
//            case R.id.btnSubmit:
//                if (checkFullAnswer()) {
//                    cdt.cancel();
//                    showDialogResult();
//                    break;
//                } else {
//                    showConfirmDialog();
//                    break;
//                }
        }
    }

    private void showDialogListQuestion() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_list_question);
        dialog.setTitle("Danh sách");
        ResultGridviewApdater resultGridviewApdater = new ResultGridviewApdater(qaList, this);
        gvListQuestion = dialog.findViewById(R.id.gvListQuestionDialog);
        btnClose = dialog.findViewById(R.id.btnClose);
        gvListQuestion.setAdapter(resultGridviewApdater);
        gvListQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getAnswerSelected() != 0) {
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