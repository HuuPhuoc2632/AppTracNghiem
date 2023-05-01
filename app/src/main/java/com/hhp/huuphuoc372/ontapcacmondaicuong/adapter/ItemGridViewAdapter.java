package com.hhp.huuphuoc372.ontapcacmondaicuong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hhp.huuphuoc372.ontapcacmondaicuong.R;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.QuestionAnswered;

import java.util.List;

public class ItemGridViewAdapter extends BaseAdapter {

    List<QuestionAnswered> qaList;
    LayoutInflater layoutInflater;

    public ItemGridViewAdapter(List<QuestionAnswered> qaList, Context context) {
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
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView= layoutInflater.inflate(R.layout.item_gridview, null);
            viewHolder.itemQuestion = convertView.findViewById(R.id.tvNumberQuestion);
            viewHolder.btnClose = convertView.findViewById(R.id.btnClose);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int i = position+1;
        viewHolder.itemQuestion.setText("Câu "+i);
        if(qa.getAnswerOfQuestion()!=0){
            viewHolder.itemQuestion.setBackgroundResource(R.drawable.bg_item_gridview_checked);
        }
        return convertView;
    }

    public static class ViewHolder{
        TextView itemQuestion;
        Button btnClose;

    }

}
