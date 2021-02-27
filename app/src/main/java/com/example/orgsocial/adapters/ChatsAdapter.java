package com.example.orgsocial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orgsocial.models.Chats;
import com.example.orgsocial.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.Holder> {

    List<Chats> chatlist;
    Context context;
    FirebaseUser firebaseUser;
    final static int MSG_TYPE_LEFT = 0;
    final static int MSG_TYPE_RIGHT = 1;
    final static int MSG_TYPE_ANNOUNCE = 2;

    public ChatsAdapter(List<Chats> chatlist, Context context) {
        this.chatlist = chatlist;
        this.context = context;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
        View view2 = LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
        View view3 = LayoutInflater.from(context).inflate(R.layout.chat_item_announcement,parent,false);
        if(viewType == MSG_TYPE_LEFT){
        return  new Holder(view1);
    }else if(viewType == MSG_TYPE_RIGHT){
        return  new Holder(view2);
    }else{
            return new Holder(view3);
        }

}

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
                     holder.bind(chatlist.get(position));
    }

    @Override
    public int getItemViewType(int position) {
     if (chatlist.get(position).getType().equals("ANNOUNCEMENT")){
         return MSG_TYPE_ANNOUNCE;
     }else{
         if(chatlist.get(position).getSender().equals(firebaseUser.getEmail())){
             return MSG_TYPE_RIGHT;
         }else{
             return MSG_TYPE_LEFT;
         }
     }

    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.chat_last_message);
        }

        public void bind(Chats chats) {
            String message = chats.getTextMessage();
            view.setText(message);
        }
    }
}
