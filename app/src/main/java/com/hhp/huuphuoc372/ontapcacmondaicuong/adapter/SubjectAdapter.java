package com.hhp.huuphuoc372.ontapcacmondaicuong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hhp.huuphuoc372.ontapcacmondaicuong.R;
import com.hhp.huuphuoc372.ontapcacmondaicuong.model.Subject;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<Subject> subList;
    private SubjectItemListener subjectItemListener;

    public SubjectAdapter( List<Subject> subList) {
        this.subList = subList;
    }

    public void setSubjectItemListener(SubjectItemListener subjectItemListener) {
        this.subjectItemListener = subjectItemListener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject sub = subList.get(position);
        if (sub==null){
            return;
        }
        holder.imageView.setImageResource(sub.getImgSubject());
        holder.textView.setText(sub.getNameSubject());
        holder.tvSlogan.setText(sub.getSlogan());

    }

    @Override
    public int getItemCount() {
        if (subList!=null){
            return subList.size();
        }
        return 0;
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvSlogan;
        private ImageView imageView;
        private TextView textView;
        private CardView cardView;
        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_subject);
            textView = itemView.findViewById(R.id.name_subject);
            tvSlogan = itemView.findViewById(R.id.tvSlogan);
            cardView = itemView.findViewById(R.id.cview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(subjectItemListener != null){
                subjectItemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface SubjectItemListener{
        public void onItemClick(View view, int position);
    }

}
