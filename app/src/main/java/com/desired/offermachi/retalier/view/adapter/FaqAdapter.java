package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.FAQ;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {
    Context context;
    ArrayList<FAQ> faqArrayList;
    int count=0;


    public FaqAdapter(Context favouriteListActivity, ArrayList<FAQ> faqArrayList) {
        this.context = favouriteListActivity;
        this.faqArrayList = faqArrayList;
    }

    @NonNull
    @Override
    public FaqAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.faqlist,viewGroup,false);
        FaqAdapter.ViewHolder viewHolder = new FaqAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FaqAdapter.ViewHolder viewHolder, int i) {
        final FAQ faq = faqArrayList.get(i);
        viewHolder.question.setText(faq.getQuestion());
        viewHolder.ans.setText(faq.getAns());
        viewHolder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count==0){
                    viewHolder.linearLayout.setVisibility(View.VISIBLE);
                    count=1;
                }else{
                    viewHolder.linearLayout.setVisibility(View.GONE);
                    count=0;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return faqArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView question, ans,arrow;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.ques);
            ans = itemView.findViewById(R.id.ans);
            arrow=itemView.findViewById(R.id.arrow);
            linearLayout=itemView.findViewById(R.id.ly);


        }
    }


}
