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
import com.example.orgsocial.ParticipantsActivity;
import com.example.orgsocial.R;
import com.example.orgsocial.models.User;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class participantsAddedAdapter extends RecyclerView.Adapter<participantsAddedAdapter.ViewHolder> {
     List<User> list;
     List<User> list1;
     Context context;
     public ParticipantsAdapter mAdapter;
     RecyclerView recycler;

    public participantsAddedAdapter(List<User> list, Context context, RecyclerView recyclerView,List<User> superuser) {
        this.list = list;
        this.list1 = superuser;
        this.context = context;
        recycler = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.participants_recycler_itemview1,parent,false);
            return new ViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.participants_recycler_itemview,parent,false);
            return new ViewHolder(view);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
              final User currentUser = list.get(position);
              holder.username.setText(currentUser.getUserName());
              holder.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      list.remove(position);
                      notifyDataSetChanged();
                      unCheck(currentUser);
                  }
              });
        if(!currentUser.getUserPhotoUrl().equals("")) {
            Glide.with(context).load(currentUser.getUserPhotoUrl()).listener(new RequestListener<Drawable>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progress.setVisibility(View.GONE);
                    Drawable dr = context.getDrawable(R.drawable.ic_group_13);
                    holder.profileImage.setImageDrawable(dr);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progress.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.profileImage);
        }else{
            holder.progress.setVisibility(View.GONE);
            Drawable dr = context.getDrawable(R.drawable.ic_group_13);
            holder.profileImage.setImageDrawable(dr);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
 CircularImageView profileImage;
 TextView username;
 ProgressBar progress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profileImage = itemView.findViewById(R.id.profileImage);
            progress = itemView.findViewById(R.id.profileImgProgress);
        }
    }
    public  void unCheck(User current){

        int position = list1.indexOf(current);
        ParticipantsAdapter.Holder currentHolder = (ParticipantsAdapter.Holder) recycler.findViewHolderForAdapterPosition(position);
        currentHolder.checked = false;
        currentHolder.itemView.findViewById(R.id.checked).setVisibility(View.GONE);

    }
}
