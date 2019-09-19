package com.desired.offermachi.customer.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.hand;
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashboardCategortListAdapter extends RecyclerView.Adapter<DashboardCategortListAdapter.MyViewHolder> {
    private ArrayList<CategoryListModel> categoryListModelArrayList;
    private Context mContext;
    private String followstatus;
    String status;
    hand handobj;
    Activity act;

    public DashboardCategortListAdapter(Activity act, Context context, ArrayList<CategoryListModel> categoryListModelArrayList) {
        this.categoryListModelArrayList = categoryListModelArrayList;
        this.mContext = context;
        this.act = act;
    }

    @Override
    public DashboardCategortListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_activity, parent, false);
        DashboardCategortListAdapter.MyViewHolder myViewHolder = new DashboardCategortListAdapter.MyViewHolder(view);
        handobj = hand.getintance();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final DashboardCategortListAdapter.MyViewHolder holder, final int i) {
        final CategoryListModel categoryListModel= categoryListModelArrayList.get(i);


        holder.unfolltext.setVisibility(View.GONE);
        holder.productname.setText(categoryListModel.getCatname());
        if(categoryListModel.getCatimage().equals("")){
        }else{
            Picasso.get().load(categoryListModel.getCatimage()).placeholder(R.drawable.ic_broken).into(holder.productimg);
        }

        status=categoryListModel.getFollowstatus();
        if (status.equals("1")){
            holder.unfolltext.setText("Unfollow");
            holder.unfolltext.setBackground(ContextCompat.getDrawable(mContext,R.drawable.view_red_background));
        }else if (status.equals("0")){
            holder.unfolltext.setText("Follow");
            holder.unfolltext.setBackground(ContextCompat.getDrawable(mContext,R.drawable.view_background));
        }
        holder.categorylinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handobj.setCatid(categoryListModel.getCatid());
                handobj.setCatname(categoryListModel.getCatname());
                handobj.setCatimage(categoryListModel.getBannerimage());
                Intent myIntent = new Intent(mContext, DashBoardActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
                act.finish();
            }
        });
        holder.unfolltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=categoryListModel.getFollowstatus();
                if (status.equals("0")){
                    followstatus="1";
                    Intent intent=new Intent("Follow");
                    intent.putExtra("catid",categoryListModel.getCatid());
                    intent.putExtra("followstatus",followstatus);
                    intent.putExtra("pos",i);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }else if (status.equals("1")){
                    followstatus="0";
                    Intent intent=new Intent("Follow");
                    intent.putExtra("catid",categoryListModel.getCatid());
                    intent.putExtra("followstatus",followstatus);
                    intent.putExtra("pos",i);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryListModelArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout categorylinear;
        ImageView productimg;
        TextView productname,unfolltext;

        public MyViewHolder(View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.category_image);
            productname = itemView.findViewById(R.id.category_name);
            unfolltext= itemView.findViewById(R.id.unfollowtext);
            categorylinear= itemView.findViewById(R.id.linear_category_id);
        }
    }
}


