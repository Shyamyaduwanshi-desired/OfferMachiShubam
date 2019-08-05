package com.desired.offermachi.retalier.retalieradapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.ui.RetalierProductActivity;
import com.desired.offermachi.retalier.ui.RetalierPushActivity;
import com.desired.offermachi.retalier.ui.RetalierViewOfferActivity;

import java.util.ArrayList;

public class PushOfferAdapter extends RecyclerView.Adapter<PushOfferAdapter.MyViewHolder> {
    private ArrayList<ViewOfferModel> viewcategorylistdataset;
    private final Context mContext;
    private Object ProductDetailsActivity;
    int count = 0;
    int countBACK = 0;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView categorylinear;
        ImageView productimg,likeimg;
        TextView productname,productdate;
        Button productbutton;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.categorylinear=(CardView)itemView.findViewById(R.id.card_linear_id);
            this.productimg = (ImageView) itemView.findViewById(R.id.product_image_id);
            this.productname = (TextView) itemView.findViewById(R.id.pushname_product_id);
            this.productdate = (TextView) itemView.findViewById(R.id.product_date_id);
            this.productbutton=(Button)itemView.findViewById(R.id.view_offer_button_id);

        }
    }
    public PushOfferAdapter(Context context,ArrayList<ViewOfferModel> data) {
        this.viewcategorylistdataset = data;
        this.mContext = context;
    }

    @Override
    public PushOfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.retalier_category_item, parent, false);

        PushOfferAdapter.MyViewHolder myViewHolder = new PushOfferAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final PushOfferAdapter.MyViewHolder holder, final int listPosition) {

        CardView categorylinear= (CardView) holder.categorylinear;
        ImageView productimg = holder.productimg;
        TextView productname = holder.productname;
        TextView productdate = holder.productdate;
        Button productbutton=holder.productbutton;
        productimg.setImageResource(viewcategorylistdataset.get(listPosition).getImg());
        productname.setText(viewcategorylistdataset.get(listPosition).getProductname());
        productdate.setText(viewcategorylistdataset.get(listPosition).getProductdate());
        productbutton.setText(viewcategorylistdataset.get(listPosition).getProductbutton());

        productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(mContext, RetalierProductActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);

            }
        });

        productbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final  Dialog dialog = new Dialog((Activity) v.getContext());
                dialog.setContentView(R.layout.select_follow_dialog);
                dialog.setTitle("Custom Dialog");
                LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.selected_spiner_id);
                final LinearLayout hidelinear=(LinearLayout) dialog.findViewById(R.id.name_hide_id);
                TextView selecttext=(TextView)dialog.findViewById(R.id.select_text_id);
                View view=(View)dialog.findViewById(R.id.view_id);
                ImageView spiner=(ImageView)dialog.findViewById(R.id.downarrow_id);
                TextView text1=(TextView)dialog.findViewById(R.id.name_first_id);
                TextView text2=(TextView)dialog.findViewById(R.id.name_second_id);
                TextView text3=(TextView)dialog.findViewById(R.id.name_third_id);
                TextView text4=(TextView)dialog.findViewById(R.id.name_forth_id);
                TextView text5=(TextView)dialog.findViewById(R.id.name_fifth_id);
                TextView text6=(TextView)dialog.findViewById(R.id.name_six_id);
                dialog.show();
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countBACK=1;
                        if (count==0){
                            hidelinear.setVisibility(View.VISIBLE);
                            count=1;
                        }else{
                            hidelinear.setVisibility(View.GONE);
                            count=0;
                        }
           }
        });

   }
        });
    }
    @Override
    public int getItemCount() {
        return viewcategorylistdataset.size();
    }
}



