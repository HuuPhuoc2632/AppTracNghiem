package com.hhp.huuphuoc372.ontapcacmondaicuong.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.hhp.huuphuoc372.ontapcacmondaicuong.R;
import com.hhp.huuphuoc372.ontapcacmondaicuong.adapter.ResultGridviewApdater;
import com.hhp.huuphuoc372.ontapcacmondaicuong.adapter.itemListViewAdapter;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.DBHelper;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Exam;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.QuestionAnswered;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Subject;
import com.hhp.huuphuoc372.ontapcacmondaicuong.adapter.SubjectAdapter;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  SubjectAdapter.SubjectItemListener, View.OnClickListener{
    private RecyclerView recyclerView;
    private SubjectAdapter subjectAdapter;
    private RelativeLayout layoutPractice, layoutExam;
    private Dialog dialog;
    private CardView cview;
    Activity context;
    private String name = null;
    private Intent intent;
    private Intent intentEx;
    private TabHost mainTab;
    private Typeface mTypeface;
    private ListView lvHistory;
    private List<QuestionAnswered> qaList = new ArrayList<>();
    private List<Exam> examList = new ArrayList<>();
    private GridView gvListQuestionResult;
    private Button btnCloseResult;
    private Button btnReview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_main);
        recyclerView = (RecyclerView) findViewById(R.id.rview);
        subjectAdapter = new SubjectAdapter( getList());
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        layoutPractice = (RelativeLayout) view.findViewById(R.id.practice);
        layoutExam = (RelativeLayout) view.findViewById(R.id.exam);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(subjectAdapter);
        subjectAdapter.setSubjectItemListener(this);
        lvHistory = findViewById(R.id.lvHistoryExam);

        Exam ex = new Exam(1, "Chủ nghĩa Xã hội Khoa học", qaList, "30/04/2023");
        examList.add(ex);
        examList.add(ex);
        examList.add(ex);
        examList.add(ex);
        examList.add(ex);
        examList.add(ex);
        examList.add(ex);
        examList.add(ex);
        examList.add(ex);
        examList.add(ex);



        itemListViewAdapter itemListViewAdapter = new itemListViewAdapter(examList, this);
        lvHistory.setAdapter(itemListViewAdapter);
        System.out.println(lvHistory.getId());
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mappingTab();
        layoutPractice.setOnClickListener(this);
        layoutExam.setOnClickListener(this);

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void mappingTab() {

        mainTab = (TabHost) findViewById(R.id.mainTab);
        mainTab.setup();

        TabHost.TabSpec spec1, spec2;

        spec1 = mainTab.newTabSpec("tab1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Môn học  ");
        mainTab.addTab(spec1);

        spec2 = mainTab.newTabSpec("tab2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Lịch sử  ");
        mainTab.addTab(spec2);

        //set font for tabwidget
        TabWidget tabWidget = findViewById(android.R.id.tabs);
        mTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/sigmar.ttf");
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            TextView tabTextView = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tabTextView.setTypeface(Typeface.create(mTypeface, Typeface.BOLD_ITALIC));
            tabTextView.setTextSize(20);
//            tabTextView.setTextColor(getResources().getColor(R.color.purple_500));
        }


    }

    public void onClick(View view) {
        if (view.getId() == R.id.practice) {
            startActivity(intent);
            dialog.hide();
        }
        if (view.getId() == R.id.exam) {
            startActivity(intentEx);
            dialog.hide();
        }

    }

    private List<Subject> getList() {
        List<Subject> subList = new ArrayList<>();
        subList.add(new Subject(R.drawable.cnxhkh, "Chủ nghĩa Xã hội Khoa học", "Dân chủ là con đường hướng tới Chủ nghĩa Xã hội."));
        subList.add(new Subject(R.drawable.lsd, "Lịch sử Đảng Cộng Sản Việt Nam", "Đảng Cộng Sản Việt Nam quang vinh muôn năm."));
        subList.add(new Subject(R.drawable.thm_ln, "Triết học Mác Lê-nin", "Vật chất có trước, ý thức có sau, vật chất là nguồn gốc của ý thức, quyết định ý thức."));
        subList.add(new Subject(R.drawable.ktct, "Kinh tế Chính trị Mác Lê-nin", " Học thuyết giá trị thặng dư là biểu hiện mẫu mực nghiên cứu và vận dụng quan điểm duy vật lịch sử."));
        subList.add(new Subject(R.drawable.tthcm, "Tư tưởng Hồ Chí Minh","Học tập và làm theo tấm gương đạo đức Hồ Chí Minh"));
        subList.add(new Subject(R.drawable.pldc, "Pháp luật đại cương", "Pháp luật là đạo đức biểu hiện ra bên ngoài, đạo đức là pháp luật ẩn giấu bên trong."));
        return subList;
    }



    @Override
    public void onItemClick(View view, int position) {
          showDialog();
        intent = new Intent(MainActivity.this,
                ContestActivity.class);
        intentEx = new Intent(MainActivity.this,
                ExamActivity.class);
        String name = getList().get(position).getNameSubject();
        intent.putExtra("name",name);
        intentEx.putExtra("name", name);
    }


    private void showDialog() {
        if(this!=null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_layout);
            Window w = dialog.getWindow();
            w.setGravity(Gravity.CENTER);
            dialog.show();
        }
    }

    public Context getContext() {
        return this;
    }
    private void showDialogResult() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_review_exam);
        ResultGridviewApdater resultGridviewApdater = new ResultGridviewApdater(qaList, this);
        gvListQuestionResult = dialog.findViewById(R.id.gvListQuestionDialogHistory);
        btnCloseResult = dialog.findViewById(R.id.btnCloseDialogHistory);
        btnReview = dialog.findViewById(R.id.btnReviewExam);
        gvListQuestionResult.setAdapter(resultGridviewApdater);
        btnCloseResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReviewExamActivity.class);
                intent.putExtra("list", "");
                intent.putExtra("name", "");
                intent.putExtra("category", -1);
                startActivity(intent);
                dialog.dismiss();
                finish();

            }
        });

        dialog.show();
    }

}