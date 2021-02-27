package com.example.orgsocial.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.example.orgsocial.models.GroupUser;
import com.example.orgsocial.models.User;
import com.example.orgsocial.utils.Commons;
import com.example.orgsocial.utils.groupParticipantsClickListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.Holder> {

    List<User> list;
    List<User> list1;
    Context context;
    participantsAddedAdapter mAdapter;
    Holder currentHolder;
    List<String> participantsId;

    public ParticipantsAdapter(List<User> list, Context context,List<User>finalList,participantsAddedAdapter adapter) {
        this.list = list;
        this.context = context;
        this.list1 = finalList;
        this.mAdapter = adapter;
    }

    public ParticipantsAdapter(List<User> list, List<User> list1, Context context, participantsAddedAdapter mAdapter, List<String> participantsId) {
        this.list = list;
        this.list1 = list1;
        this.context = context;
        this.mAdapter = mAdapter;
        this.participantsId = participantsId;
    }

    @NonNull
    @Override
    public ParticipantsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            //super user use case
            View view = LayoutInflater.from(context).inflate(R.layout.contact_list_item1, parent, false);
            Holder holder = new Holder(view, viewType);

            return holder;
        } else if(viewType == 2){
            View view = LayoutInflater.from(context).inflate(R.layout.contact_list_item2,parent,false);
            Holder holder = new Holder(view, viewType);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
            Holder holder = new Holder(view, viewType);

            return holder;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        currentHolder = holder;
        final User current = list.get(position);
        String ImageUrl = current.getUserPhotoUrl();
        final String userName = current.getUserName();
        String description = current.getDescription();
        holder.username.setText(userName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.viewType == 2){

                }else{
                    if(holder.checked){
                        view.findViewById(R.id.checked).setVisibility(View.GONE);
                        holder.checked = false;
                        list1.remove(current);
                        mAdapter.notifyDataSetChanged();

                    }else{
                        view.findViewById(R.id.checked).setVisibility(View.VISIBLE);
                        holder.checked = true;
                        list1.add(current);
                        mAdapter.notifyDataSetChanged();
                    }

                }
                }

        });
        if(holder.viewType == 2){
            holder.description.setText("Already added to the group");
        }else{
            if(description.equals("")){
                holder.description.setText("Hey there iam using Orgsocial");
            }else {
                holder.description.setText(description);
            }
        }




        //Image Loading
        if(!ImageUrl.equals("")) {
            Glide.with(context).load(ImageUrl).listener(new RequestListener<Drawable>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progress.setVisibility(View.GONE);
                    Drawable dr = context.getDrawable(R.drawable.ic_group_13);
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
            Drawable dr = context.getDrawable(R.drawable.ic_group_13);
            holder.imageView.setImageDrawable(dr);
        }






    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        CircularImageView imageView;
        ProgressBar progress;
        TextView description, username;
        int viewType;
        public boolean checked = false;


        public Holder(@NonNull View itemView, int type) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profileImage);
            progress = itemView.findViewById(R.id.profileImgProgress);
            description = itemView.findViewById(R.id.description);
            username = itemView.findViewById(R.id.username);
            viewType = type;


        }
    }

    @Override
    public int getItemViewType(int position) {
        String currentId = list.get(position).getUserId();
            if(participantsId != null){
                if (participantsId.contains(currentId)) {
                    return 2;
                } else {
                    if (list.get(position).getUserEmail().equals("luyanganwawa@gmail.com") || list.get(position).getUserEmail().equals( "bsangunde@gmail.com")) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }else{
                if (list.get(position).getUserEmail().equals("luyanganwawa@gmail.com") || list.get(position).getUserEmail().equals( "bsangunde@gmail.com")) {
                    return 1;
                } else {
                    return 0;
                }
            }


    }


}