package io.realworld.android.hitsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.realworld.android.hitsapp.Models.Message;
import io.realworld.android.hitsapp.R;

public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<Message> messages;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
            return  new senderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.reciver_layout, parent, false);
            return  new receiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return  RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = messages.get(position);

        if (holder.getClass() == senderViewHolder.class) {
            ((senderViewHolder) holder).senderMsg.setText(message.getMessage());
        } else {
            ((receiverViewHolder) holder).receiverMsg.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class receiverViewHolder extends RecyclerView.ViewHolder {

               TextView receiverMsg;
               TextView receiverTime;

      public receiverViewHolder(@NonNull View itemView) {
          super(itemView);

          receiverMsg = itemView.findViewById(R.id.reciver_text);
          receiverTime = itemView.findViewById(R.id.reciver_time);
      }
  }

    class senderViewHolder extends RecyclerView.ViewHolder {

        TextView senderMsg;
        TextView senderTime;

        public senderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.sender_text);
            senderTime = itemView.findViewById(R.id.sender_time);
        }
    }

}
