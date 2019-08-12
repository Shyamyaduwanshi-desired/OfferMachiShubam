package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.FollowerModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.presenter.FollowerPresenter;

import java.util.ArrayList;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<FollowerModel> followerModels;

    public MultiAdapter(Context context, ArrayList<FollowerModel> followerModels) {
        this.context = context;
        this.followerModels = followerModels;
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_follower, viewGroup, false);
        MultiViewHolder multiViewHolder = new MultiViewHolder(view);
        return multiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MultiViewHolder multiViewHolder, int position) {
        final FollowerModel followerModel = followerModels.get(position);
        multiViewHolder.imageView.setVisibility(followerModel.isChecked() ? View.VISIBLE : View.GONE);
        multiViewHolder.textView.setText(followerModel.getFollowername());
        multiViewHolder.textViewid.setText(followerModel.getId());

        multiViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followerModel.setChecked(!followerModel.isChecked());
                multiViewHolder.imageView.setVisibility(followerModel.isChecked() ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return followerModels.size();
    }

    public static class MultiViewHolder extends RecyclerView.ViewHolder {

        private TextView textView,textViewid;
        private ImageView imageView;

       public MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.followername);
            textViewid = itemView.findViewById(R.id.followerid);
            imageView = itemView.findViewById(R.id.imageView);
        }

    }

    public ArrayList<FollowerModel> getAll() {
        return followerModels;
    }

    public ArrayList<FollowerModel> getSelected() {
        ArrayList<FollowerModel> selected = new ArrayList<>();
        for (int i = 0; i < followerModels.size(); i++) {
            if (followerModels.get(i).isChecked()) {
                selected.add(followerModels.get(i));
            }
        }
        return selected;
    }
}