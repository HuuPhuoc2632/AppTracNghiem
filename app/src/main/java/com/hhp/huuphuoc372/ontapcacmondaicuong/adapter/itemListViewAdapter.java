package com.hhp.huuphuoc372.ontapcacmondaicuong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhp.huuphuoc372.ontapcacmondaicuong.R;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Exam;

import java.util.List;
import java.util.zip.Inflater;

public class itemListViewAdapter extends BaseAdapter {
    private List<Exam> exList;
    private Context context;
    private LayoutInflater layoutInflater;

    public itemListViewAdapter(List<Exam> exList, Context context) {
        this.exList = exList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return exList.size();
    }

    @Override
    public Object getItem(int position) {
        return exList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Exam ex = exList.get(position);
        ViewHolder viewHolder;

        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.line_history_exam, null);
            viewHolder.tvScore = convertView.findViewById(R.id.tvScore);
            viewHolder.tvNameOfSubject = convertView.findViewById(R.id.tvNameSubject);
            viewHolder.tvDateOfExam = convertView.findViewById(R.id.tvDateOfExam);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (itemListViewAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.tvScore.setText(ex.pointOfExam(context)+"");
        viewHolder.tvNameOfSubject.setText(ex.getIdSubject());
        viewHolder.tvDateOfExam.setText(ex.getDate()+"");
        return convertView;
    }
    public static class ViewHolder{
        TextView tvScore, tvNameOfSubject, tvDateOfExam;

    }

}
