package com.hhp.huuphuoc372.ontapcacmondaicuong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhp.huuphuoc372.ontapcacmondaicuong.R;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.DBHelper;
import com.hhp.huuphuoc372.ontapcacmondaicuong.dao.QuestionDAO;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Question;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.QuestionAnswered;

import java.util.List;

public class ResultGridviewApdater extends BaseAdapter {
    private final DBHelper dbHelper;
    List<QuestionAnswered> qaList;
    LayoutInflater layoutInflater;

    public ResultGridviewApdater(List<QuestionAnswered> qaList, Context context) {
        dbHelper = new DBHelper(context);
        this.qaList = qaList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return qaList.size();
    }

    @Override
    public Object getItem(int position) {
        return qaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuestionAnswered qa = (QuestionAnswered) getItem(position);
        ItemGridViewAdapter.ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ItemGridViewAdapter.ViewHolder();
            convertView= layoutInflater.inflate(R.layout.item_gridview, null);
            viewHolder.itemQuestion = convertView.findViewById(R.id.tvNumberQuestion);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ItemGridViewAdapter.ViewHolder) convertView.getTag();
        }
        int i = position+1;
        viewHolder.itemQuestion.setText("Câu "+i);
        Question q = new QuestionDAO(dbHelper).findSingleQuestionById(qa.getIdQuestion());
        System.err.println("day ne "+ q);
        if(qa.getAnswerOfQuestion()==q.getAnswer()-1){
            viewHolder.itemQuestion.setBackgroundResource(R.drawable.bg_item_gridview_true);
        }
        else {
            viewHolder.itemQuestion.setBackgroundResource(R.drawable.bg_item_gridview_false);
        }
        return convertView;

    }
    public static class ViewHolder{
        TextView itemQuestion;
    }
}
