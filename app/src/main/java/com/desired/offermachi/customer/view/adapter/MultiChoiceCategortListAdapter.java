package com.desired.offermachi.customer.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.hand;
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MultiChoiceCategortListAdapter extends RecyclerView.Adapter<MultiChoiceCategortListAdapter.MyViewHolder> {
    private ArrayList<CategoryListModel> categoryListModelArrayList;
    private Context mContext;
//    String status;
//    hand handobj;
    private AdapterClick itemClick;
    public MultiChoiceCategortListAdapter(Context context, ArrayList<CategoryListModel> categoryListModelArrayList, AdapterClick cateClick) {
        this.categoryListModelArrayList = categoryListModelArrayList;
        this.mContext = context;
        this.itemClick = cateClick;
    }

    @Override
    public MultiChoiceCategortListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_multi_choice_category, parent, false);
        MultiChoiceCategortListAdapter.MyViewHolder myViewHolder = new MultiChoiceCategortListAdapter.MyViewHolder(view);
//        handobj = hand.getintance();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MultiChoiceCategortListAdapter.MyViewHolder holder, final int position) {
        final CategoryListModel categoryListModel= categoryListModelArrayList.get(position);
        holder.productname.setText(categoryListModel.getCatname());
        if(categoryListModel.getCatimage().equals("")){
        }else{
            Picasso.get().load(categoryListModel.getCatimage()).placeholder(R.drawable.ic_broken).into(holder.productimg);//.placeholder(R.drawable.ic_broken)
            /*.networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)*/
        }
//        holder.catcheck.setEnabled(false);
        holder.catcheck.setOnCheckedChangeListener(null);
        if(categoryListModel.isCheckStatus())
        {
         holder.catcheck.setChecked(true);
        }
        else
        {
            holder.catcheck.setChecked(false);
        }

//        status=categoryListModel.getFollowstatus();
//        if (status.equals("1")){
//            holder.unfolltext.setText("Unfollow");
//            holder.unfolltext.setBackground(ContextCompat.getDrawable(mContext,R.drawable.view_red_background));
//           // holder.unfolltext.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.view_red_background) );
//           // holder.unfolltext.setBackground(ContextCompat.getDrawable(mContext, R.drawable.view_red_background));
//        }else if (status.equals("0")){
//            holder.unfolltext.setText("Follow");
//            holder.unfolltext.setBackground(ContextCompat.getDrawable(mContext,R.drawable.view_background));
//           // holder.unfolltext.setBackground(ContextCompat.getDrawable(mContext, R.drawable.view_background));
//        }
        holder.categorylinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onClick(position);

//                handobj.setCatid(categoryListModel.getCatid());
//                handobj.setCatname(categoryListModel.getCatname());
//                handobj.setCatimage(categoryListModel.getBannerimage());
//                Intent myIntent = new Intent(mContext, DashBoardActivity.class);
//              /*  myIntent.putExtra("catid",categoryListModel.getCatid());
//                myIntent.putExtra("catname",categoryListModel.getCatname());
//                myIntent.putExtra("catofferimage",categoryListModel.getBannerimage());*/
//                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(myIntent);
            }
        });
//        holder.catcheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemClick.onClick(position);
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return categoryListModelArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout categorylinear;
        ImageView productimg;
        TextView productname/*,unfolltext*/;
        CheckBox catcheck;
        public MyViewHolder(View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.category_image);
            productname = itemView.findViewById(R.id.category_name);
            catcheck=itemView.findViewById(R.id.catcheck);
            categorylinear= itemView.findViewById(R.id.linear_category_id);
        }
    }
    public interface AdapterClick{
        void onClick(int position);
    }
}


