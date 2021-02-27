package com.example.orgsocial.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.orgsocial.ChatsActivity;
import com.example.orgsocial.R;
import com.example.orgsocial.models.Group;
import com.example.orgsocial.models.User;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.Holder> {

    List<Group> list;
    Context context;

    public GroupsAdapter(List<Group> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public GroupsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            //super user use case
            View view = LayoutInflater.from(context).inflate(R.layout.group_list_item, parent, false);
            Holder holder = new Holder(view);

            return holder;




    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {

        final Group current = list.get(position);
        String ImageUrl = current.getGroupPhotoUrl();
        String subject = current.getGroupSubject();
        holder.groupSubject.setText(subject);

        //Image Loading
        if(!ImageUrl.equals("")) {
            Glide.with(context).load(ImageUrl).listener(new RequestListener<Drawable>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progress.setVisibility(View.GONE);
                    Drawable dr = context.getDrawable(R.drawable.ic_group_14);
                    holder.imageView.setImageDrawable(dr);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progress.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.imageView);
        }else{
            holder.progress.setVisibility(View.GONE);
            Drawable dr = context.getDrawable(R.drawable.ic_group_14);
            holder.imageView.setImageDrawable(dr);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"GroupChat Activity",Toast.LENGTH_SHORT).show();
            }
        });



    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CircularImageView imageView;
        ProgressBar progress;
        TextView groupSubject;


        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profileImage);
            progress = itemView.findViewById(R.id.profileImgProgress);
            groupSubject = itemView.findViewById(R.id.username);


        }
    }


}