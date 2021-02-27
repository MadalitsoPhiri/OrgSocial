package com.example.orgsocial.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.orgsocial.DialogActivity;
import com.example.orgsocial.R;
import com.example.orgsocial.models.ChatList;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class chatListAdapter extends RecyclerView.Adapter<chatListAdapter.Holder> {
    //data for the recycler view
    private List<ChatList> list;
    private Context context;

    public chatListAdapter(List<ChatList> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public chatListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(  R.layout.chat_list_item,parent,false);
        return new Holder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final chatListAdapter.Holder holder, int position) {
        final ChatList chatlist = list.get(position);
        String Date;
        String LastSeen;
        final Date mDate = chatlist.getDate();
        Date mDate1 = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
        String DateToday = formatter.format(mDate1);
        String DateTime = formatter.format(mDate);
        String [] UserDateTime = DateTime.split(",");
        String [] CurrentDateTime = DateToday.split(",");
        String UserDate = UserDateTime[0];
        String UserTime = UserDateTime[1];
        String DateNow = CurrentDateTime[0];
       // String TimeNow = CurrentDateTime[1];
        if(UserDate.equals(DateNow)){
            Date = UserTime;
             LastSeen = "Last seen today at "+ UserTime;
        }else{
            Date = UserDate;
            LastSeen = "Last seen on "+ UserDate;
        }
        final String Lastseen = LastSeen;
        final String mName = chatlist.getUserName();
        String mMessage = chatlist.getDescription();
        holder.date.setText(Date);
        holder.name.setText(mName);
        holder.last_message.setText(mMessage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //start Chat Activity
                context.startActivity(new Intent(context, ChatsActivity.class).putExtra("userId",chatlist.getUserID()).putExtra("userName",chatlist.getUserName()).putExtra("userPhotoUrl",chatlist.getUrlProfile()).putExtra("userEmail",chatlist.getEmail()).putExtra("date",Lastseen).putExtra("chatType","chat").putExtra("joined",chatlist.getDateJoined()).putExtra("phone",chatlist.getUserPhone()).putExtra("about",chatlist.getAbout()));
            }
        });


        if(chatlist.getUrlProfile().equals("")){
            holder.profile_image.setImageDrawable(context.getDrawable(R.drawable.ic_group_13));
            holder.progress.setVisibility(View.GONE);
            holder.profile_image.setClickable(true);
            holder.profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(new Intent(context, DialogActivity.class));
                    i.putExtra("photoUrl",chatlist.getUrlProfile());
                    i.putExtra("username",mName);
                    i.putExtra("chatType","chat");
                    i.putExtra("joined",chatlist.getDateJoined());
                    i.putExtra("phone",chatlist.getUserPhone());
                    i.putExtra("about",chatlist.getAbout());
                    i.putExtra("userId",chatlist.getUserID()).putExtra("userName",chatlist.getUserName()).putExtra("userPhotoUrl",chatlist.getUrlProfile()).putExtra("userEmail",chatlist.getEmail()).putExtra("date",Lastseen);


                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation((Activity) context, v, "profImg");
                    context.startActivity(i, options.toBundle());
                }
            });
        }else{
            //using glide for image load
            Glide.with(context).load(chatlist.getUrlProfile()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progress.setVisibility(View.GONE);
                    holder.profile_image.setClickable(true);

                    return false;
                }
            }).into(holder.profile_image);
            holder.profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(new Intent(context, DialogActivity.class));
                    i.putExtra("photoUrl",chatlist.getUrlProfile());
                    i.putExtra("username",mName);
                    i.putExtra("chatType","chat");
                    i.putExtra("joined",chatlist.getDateJoined());
                    i.putExtra("phone",chatlist.getUserPhone());
                    i.putExtra("about",chatlist.getAbout());
                    i.putExtra("userId",chatlist.getUserID()).putExtra("userName",chatlist.getUserName()).putExtra("userPhotoUrl",chatlist.getUrlProfile()).putExtra("userEmail",chatlist.getEmail()).putExtra("date",Lastseen);


                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation((Activity) context, v, "profImg");
                    context.startActivity(i, options.toBundle());
                }
            });

        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    //View holder
    public class Holder extends RecyclerView.ViewHolder {
        CircularImageView profile_image;
        ProgressBar progress;
        TextView name,date,last_message;
        View linearLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.profile_image = itemView.findViewById(R.id.chat_profile);
            this.name = itemView.findViewById(R.id.chat_name);
            this.date = itemView.findViewById(R.id.chat_last_message_date);
            this.last_message = itemView.findViewById(R.id.chat_last_message);
            this.linearLayout = itemView.findViewById(R.id.chat_details);
            this.progress = itemView.findViewById(R.id.chatItemProgress);
        }
    }
}