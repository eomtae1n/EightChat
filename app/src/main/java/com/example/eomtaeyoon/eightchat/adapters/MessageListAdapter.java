package com.example.eomtaeyoon.eightchat.adapters;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eomtaeyoon.eightchat.R;
import com.example.eomtaeyoon.eightchat.models.EmoticonMessage;
import com.example.eomtaeyoon.eightchat.models.Message;
import com.example.eomtaeyoon.eightchat.models.PhotoMessage;
import com.example.eomtaeyoon.eightchat.models.TextMessage;
import com.example.eomtaeyoon.eightchat.views.ChatActivity;
import com.example.eomtaeyoon.eightchat.views.MakeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by eomtaeyoon on 2017. 11. 19..
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>{


    private ArrayList<Message> mMessageList;

    private SimpleDateFormat messageDateFormat = new SimpleDateFormat("MM/dd\n hh:mm");

    private String userId;
    private ChatActivity chatActivity = new ChatActivity();

    public MessageListAdapter() {
        mMessageList = new ArrayList<>();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void addItem(Message item) {
        mMessageList.add(item);
        notifyDataSetChanged();
    }
    public void updateItem(Message item) {
        int position = getItemPosition(item.getMessageId());
        if(position < 0) {
            return;
        }
        mMessageList.set(position, item);
        notifyItemChanged(position);
    }

    public void clearItem() {
        mMessageList.clear();
    }

    private int getItemPosition(String messageId) {
        int position = 0;

        for(Message message : mMessageList) {
            if(message.getMessageId().equals(messageId)) {
                return position;
            }
            position++;
        }
        return -1;
    }

    public Message getItem(int position) {
        return mMessageList.get(position);
    }
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_message_item, parent, false);
        // viewHolder를 이용한 뷰홀더 리턴
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        // 전달받은 뷰 홀더를 이용한 뷰 구현
        Message item = getItem(position);

        TextMessage textMessage = null;
        PhotoMessage photoMessage = null;
        EmoticonMessage emoticonMessage = null;

        if(item instanceof TextMessage) {
            textMessage = (TextMessage) item;
        }
        else if(item instanceof PhotoMessage) {
            photoMessage = (PhotoMessage) item;
        }
        else if(item instanceof EmoticonMessage) {
            emoticonMessage = (EmoticonMessage) item;
        }

        // 내가 보낸 메세지인지 받은 메세지인지 판별
        if(userId.equals(item.getMessageUser().getUid())) {
            // 내가보낸 메세지 구현
            // 텍스트 메세지인지 포토 메세지인지 구별
            if(item.getMessageType() == Message.MessageType.TEXT) {
                holder.sendTxt.setText(textMessage.getMessageText());
                holder.sendTxt.setVisibility(View.VISIBLE);
                holder.sendImage.setVisibility(View.GONE);
                holder.sendEmoticon.setVisibility(View.GONE);
            }
            else if(item.getMessageType() == Message.MessageType.PHOTO) {

                Glide.with(holder.sendArea.getContext())
                        .load(photoMessage.getPhotoUrl())
                        .override(200, 200)
                        .into(holder.sendImage);

                holder.sendTxt.setVisibility(View.GONE);
                holder.sendImage.setVisibility(View.VISIBLE);
                holder.sendEmoticon.setVisibility(View.GONE);
            }
            else if(item.getMessageType() == Message.MessageType.EMOTICON) {
                Glide.with(holder.sendArea.getContext())
                        .load(emoticonMessage.getEmoticonUrl())
                        .override(400, 450)
                        .into(holder.sendEmoticon);

                holder.sendTxt.setVisibility(View.GONE);
                holder.sendImage.setVisibility(View.GONE);
                holder.sendEmoticon.setVisibility(View.VISIBLE);
            }
            holder.sendDate.setText(messageDateFormat.format(item.getMessageDate()));

            holder.yourArea.setVisibility(View.GONE);
            holder.sendArea.setVisibility(View.VISIBLE);
        } else {
            //상대방이 보낸 경우
            if(item.getMessageType() == Message.MessageType.TEXT) {

                holder.rcvTextView.setText(textMessage.getMessageText());
                holder.rcvTextView.setVisibility(View.VISIBLE);
                holder.rcvImage.setVisibility(View.GONE);
                holder.rcvEmoticon.setVisibility(View.GONE);

            }
            else if(item.getMessageType() == Message.MessageType.PHOTO) {
                Glide.with(holder.yourArea.getContext())
                        .load(photoMessage.getPhotoUrl())
                        .override(200, 200)
                        .into(holder.rcvImage);

                holder.rcvTextView.setVisibility(View.GONE);
                holder.rcvImage.setVisibility(View.VISIBLE);
                holder.rcvEmoticon.setVisibility(View.GONE);
            }
            else if(item.getMessageType() == Message.MessageType.EXIT) {
                holder.exitTextView.setText(String.format("%s님이 방에서 나가셨습니다.", item.getMessageUser().getName()));
                holder.exitArea.setVisibility(View.VISIBLE);
            }
            else if(item.getMessageType() == Message.MessageType.EMOTICON) {
                Glide.with(holder.yourArea.getContext())
                        .load(emoticonMessage.getEmoticonUrl())
                        .override(400,450)
                        .into(holder.rcvEmoticon);

                holder.rcvTextView.setVisibility(View.GONE);
                holder.rcvImage.setVisibility(View.GONE);
                holder.rcvEmoticon.setVisibility(View.VISIBLE);
            }

            if(item.getMessageType() == Message.MessageType.EXIT) {
                holder.yourArea.setVisibility(View.GONE);
                holder.sendArea.setVisibility(View.GONE);
                holder.exitArea.setVisibility(View.VISIBLE);
            } else {
                holder.rcvDate.setText(messageDateFormat.format(item.getMessageDate()));
                holder.yourArea.setVisibility(View.VISIBLE);
                holder.sendArea.setVisibility(View.GONE);
            }
            // 텍스트 메세지인지 포토 메세지인지 구별

        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        LinearLayout yourArea;
        LinearLayout sendArea;
        LinearLayout exitArea;
        ImageView rcvProfileView;
        TextView exitTextView;
        TextView rcvTextView;
        ImageView rcvImage;
        ImageView rcvEmoticon;
        TextView rcvDate;
        TextView sendDate;
        TextView sendTxt;
        ImageView sendImage;
        ImageView sendEmoticon;

        public MessageViewHolder(View v) {
            super(v);
            yourArea = (LinearLayout) v.findViewById(R.id.yourChatArea);
            sendArea = (LinearLayout) v.findViewById(R.id.myChatArea);
            exitArea = (LinearLayout) v.findViewById(R.id.exitArea);
            rcvProfileView = (ImageView) v.findViewById(R.id.rcvProfile);
            exitTextView = (TextView) v.findViewById(R.id.exitTxt);
            rcvTextView = (TextView) v.findViewById(R.id.rcvTxt);
            rcvImage = (ImageView) v.findViewById(R.id.rcvImage);
            rcvEmoticon = (ImageView) v.findViewById(R.id.rcvEmoticon);
            rcvDate = (TextView) v.findViewById(R.id.rcvDate);
            sendDate = (TextView) v.findViewById(R.id.sendDate);
            sendTxt = (TextView) v.findViewById(R.id.sendTxt);
            sendImage = (ImageView) v.findViewById(R.id.sendImage);
            sendEmoticon = (ImageView) v.findViewById(R.id.sendEmoticon);
        }
    }
}
