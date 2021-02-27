package com.example.orgsocial.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.orgsocial.models.GroupUser;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class GroupParticipantsAdapter extends RecyclerView.Adapter<GroupParticipantsAdapter.Holder>{
    private List<GroupUser> list;
    private Context context;
    private String userId;

    public GroupParticipantsAdapter(List<GroupUser> list, Context context,String userID) {
        this.list = list;
        this.context = context;
        this.userId = userID;
    }

    @NonNull
    @Override
    public GroupParticipantsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.participants_list_item,parent,false);

        return new Holder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final GroupParticipantsAdapter.Holder holder, int position) {
                   GroupUser currentUser = list.get(position);
                   Boolean isAdmin = currentUser.getAdmin();
                   Boolean isCreator = currentUser.getCreator();
                   if(currentUser.getDescription().isEmpty()){
                       holder.description.setText("Hey there iam using Orgsocial");
                   }else{
                       holder.description.setText(currentUser.getDescription());
                   }

                   if(currentUser.getUserId().equals(userId)){
                       holder.userName.setText("You");
                   }else{
                       holder.userName.setText(currentUser.getUserName());
                   }

                   if(currentUser.getUserPhotoUrl().equals("")){
                       holder.profile.setImageDrawable(context.getDrawable(R.drawable.ic_group_13));
                       holder.profileProgress.setVisibility(View.GONE);
                   }else{
                       Glide.with(context).load(currentUser.getUserPhotoUrl()).listener(new RequestListener<Drawable>() {
                           @Override
                           public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                               return false;
                           }

                           @Override
                           public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                               holder.profileProgress.setVisibility(View.GONE);
                               return false;
                           }
                       }).into(holder.profile);
                   }

                   if(isAdmin){
                       holder.adminBadge.setVisibility(View.VISIBLE);
                   }else{
                       holder.adminBadge.setVisibility(View.GONE);
                   }
                   if(isCreator){
                       holder.creatorBadge.setVisibility(View.VISIBLE);
                   }else{
                       holder.creatorBadge.setVisibility(View.GONE);
                   }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView userName,description;
        ImageView adminBadge,creatorBadge;
        CircularImageView profile;
        ProgressBar profileProgress;

        public Holder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profileImage);
            profileProgress = itemView.findViewById(R.id.profileImgProgress);
            adminBadge = itemView.findViewById(R.id.adminBadge);
            creatorBadge = itemView.findViewById(R.id.creatorBadge);
            userName = itemView.findViewById(R.id.username);
            description = itemView.findViewById(R.id.description);
        }
    }
}
