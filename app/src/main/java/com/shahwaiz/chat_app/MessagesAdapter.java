package com.shahwaiz.chat_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private List<MessageList> msgList;
    private final Context context;

    public MessagesAdapter(List<MessageList> msgList, Context context) {
        this.msgList = msgList;
        this.context = context;
    }

    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MyViewHolder holder, int position) {
        MessageList conv=msgList.get(position);
        if(!conv.getDp().isEmpty())
        {
            Picasso.get().load(conv.getDp()).into(holder.dp);

        }
        holder.username.setText(conv.getName());
        holder.lastMsg.setText(conv.getLastMsg());

        if(conv.getUnreadMessages()==0)
        {
            holder.unread.setVisibility(View.GONE);
            holder.lastMsg.setTextColor(context.getResources().getColor(R.color.black));
        }
        else
        {
            holder.unread.setVisibility(View.VISIBLE);
            holder.lastMsg.setTextColor(context.getResources().getColor(R.color.teal_700));
        }

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Chat.class);
                intent.putExtra("mobile",conv.getMobile());
                intent.putExtra("name",conv.getName());
                intent.putExtra("profile_pic",conv.getDp());
                intent.putExtra("chat_key",conv.getChatKey());
                context.startActivity(intent);
            }
        });
    }


    public void updateData(List<MessageList> msgList)
    {
        this.msgList=msgList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return msgList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView dp;
        private TextView lastMsg,username,unread;
        private LinearLayout rootLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dp=itemView.findViewById(R.id.dp);
            lastMsg=itemView.findViewById(R.id.lastMsg);
            username=itemView.findViewById(R.id.username);
            unread=itemView.findViewById(R.id.unseen);
            rootLayout=itemView.findViewById(R.id.root);

        }


    }


}
