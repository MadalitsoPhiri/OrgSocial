package com.example.orgsocial.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orgsocial.R;
import com.example.orgsocial.models.CallList;
import com.example.orgsocial.models.ChatList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class callListAdapter extends RecyclerView.Adapter<callListAdapter.Holder> {
    public callListAdapter(List<CallList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    List<CallList> list = new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_list_item,parent,false);
        callListAdapter.Holder holder = new callListAdapter.Holder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CallList callList = list.get(position);
        String callType = callList.getCallType();
        holder.chatName.setText(callList.getUserName());
        holder.dateTime.setText(callList.getCallDate());
        if(callType == "Recieved"){
            holder.callActionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_arrow_downward_24));
        }else if(callType ==
                "Sent"){
            holder.callActionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_arrow_upward_24));
        }
        if(callList.getUserProfileUrl() == null){
            holder.ChatProfile.setImageDrawable(context.getDrawable(R.drawable.ic_user_placeholder));
        }else{
            //using glide for image load
            Glide.with(context).load(callList.getUserProfileUrl()).into(holder.ChatProfile);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder  extends RecyclerView.ViewHolder{
        ImageView callActionIcon,ChatProfile;
        TextView dateTime,chatName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            callActionIcon = itemView.findViewById(R.id.call_action_icon);
            ChatProfile = itemView.findViewById(R.id.chat_profile);
            dateTime = itemView.findViewById(R.id.call_date_time);
            chatName = itemView.findViewById(R.id.chat_name);
        }
    }
}