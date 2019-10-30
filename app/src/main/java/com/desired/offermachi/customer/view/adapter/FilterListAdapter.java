package com.desired.offermachi.customer.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.fragment.FeedsFragment;
import com.desired.offermachi.customer.view.fragment.HomeFragment;
import com.desired.offermachi.customer.view.fragment.SmartShoppingFragment;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilterListAdapter  extends RecyclerView.Adapter<FilterListAdapter.MyViewHolder> {
    private ArrayList<CategoryListModel> categoryListModelArrayList;
    private Context mContext;
    private Activity activity;
    private String followstatus;
    String status;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;

    public FilterListAdapter(Activity activity,Context context, ArrayList<CategoryListModel> categoryListModelArrayList) {
        this.categoryListModelArrayList = categoryListModelArrayList;
        this.mContext = context;
        this.activity=activity;
    }

    @Override
    public FilterListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_filter_activity, parent, false);
        FilterListAdapter.MyViewHolder myViewHolder = new FilterListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterListAdapter.MyViewHolder holder, final int i) {
        final CategoryListModel categoryListModel= categoryListModelArrayList.get(i);
        holder.productname.setText(categoryListModel.getCatname());
        holder.catcheck.setTag(new Integer(i));
        if(i == 0 && holder.catcheck.isChecked())
        {
            lastChecked = holder.catcheck;
            lastCheckedPos = 0;
        }
        if(categoryListModel.getCatimage().equals("")){
        }else{
            Picasso.get().load(categoryListModel.getCatimage()).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(holder.productimg);
        }
        holder.catcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);

                      //  fonts.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                    Intent intent=new Intent("Category");
                    intent.putExtra("catid",categoryListModelArrayList.get(i).getCatid());
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                    activity.finish();
                }
                else
                    lastChecked = null;

              //  fonts.get(clickedPos).setSelected(cb.isChecked);

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
        TextView productname;
        CheckBox catcheck;


        public MyViewHolder(View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.category_image);
            productname = itemView.findViewById(R.id.category_name);
            categorylinear= itemView.findViewById(R.id.linear_category_id);
            catcheck=itemView.findViewById(R.id.catcheck);
        }
    }
}


