package com.example.orgsocial.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.orgsocial.R;
import com.example.orgsocial.models.User;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class participantsGridLayoutAdapter extends RecyclerView.Adapter<participantsGridLayoutAdapter.Holder> {
    List<com.example.orgsocial.models.User> list;
    Context context;

    public participantsGridLayoutAdapter(List<com.example.orgsocial.models.User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public participantsGridLayoutAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.participants_grid_item_view1,parent,false);
            return new participantsGridLayoutAdapter.Holder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.participants_grid_item_view,parent,false);
            return new participantsGridLayoutAdapter.Holder(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final participantsGridLayoutAdapter.Holder holder, int position) {
           User currentUser = list.get(position);
                   holder.username.setText(currentUser.getUserName());
        if(!currentUser.getUserPhotoUrl().equals("")) {
            Glide.with(context).load(currentUser.getUserPhotoUrl()).listener(new RequestListener<Drawable>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    Drawable dr = context.getDrawable(R.drawable.ic_group_13);
                    holder.profileImg.setImageDrawable(dr);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.profileImg);
        }else{
            holder.progressBar.setVisibility(View.GONE);
            Drawable dr = context.getDrawable(R.drawable.ic_group_13);
            holder.profileImg.setImageDrawable(dr);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getUserEmail().equals("luyanganwawa@gmail.com") || list.get(position).getUserEmail().equals( "bsangunde@gmail.com")) {
            return 1;
        } else {
            return 0;
        }

    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView username;
        CircularImageView profileImg;
        ProgressBar progressBar;
        public Holder(@NonNull View itemView) {
            super(itemView);
            profileImg = itemView.findViewById(R.id.profileImage);
            username = itemView.findViewById(R.id.username);
            progressBar = itemView.findViewById(R.id.profileImgProgress);
        }
    }
}
