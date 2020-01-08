package com.example.eomtaeyoon.eightchat.adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eomtaeyoon.eightchat.R;
import com.example.eomtaeyoon.eightchat.customviews.RoundedImageView;
import com.example.eomtaeyoon.eightchat.models.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eomtaeyoon on 2017. 11. 14..
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendHolder> {

    public static final int UNSELECTION_MODE = 1;
    public static final int SELECTION_MODE = 2;

    //private int selectionMode = UNSELECTION_MODE;

    private ArrayList<User> friendList;

    public FriendListAdapter() {
        friendList = new ArrayList<>();
    }

    public void addItem(User friend) {
        friendList.add(friend);
        notifyDataSetChanged();
    }

    /*public void setSelectionMode(int selectionMode) {
        this.selectionMode = selectionMode;
        notifyDataSetChanged();
    }
    public int getSelectionMode() {
        return this.selectionMode;
    }
    public int getSelectionUsersCount() {
        int selectedCount = 0;
        for ( User user : friendList ) {
            if(user.isSelection()) {
                selectedCount++;
            }
        }
        return selectedCount;
    }
    public String[] getSelectedUids() {
        String[] selectedUids = new String[getSelectionUsersCount()];
        int i = 0;
        for ( User user : friendList ) {
            if(user.isSelection()) {
                selectedUids[i++] = user.getUid();
            }
        }
        return selectedUids;
    }*/

    public User getItem(int position) {
        return this.friendList.get(position);
    }

    @Override
    public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_friend_item, parent, false);
        FriendHolder friendHolder = new FriendHolder(view);
        return friendHolder;
    }

    @Override
    public void onBindViewHolder(FriendHolder holder, int position) {
        User friend = getItem(position);
        if(friend.getProfileUrl() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(friend.getProfileUrl())
                    .into(holder.mProfileView);
        }
        holder.mEmailView.setText(friend.getEmail());
        holder.mNameView.setText(friend.getName());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public static class FriendHolder extends RecyclerView.ViewHolder {

        /*@BindView(R.id.checkbox)
        CheckBox friendSelectedView;

        @BindView(R.id.thumb)
        RoundedImageView mProfileView;*/

        ImageView mProfileView;

        TextView mNameView;

        TextView mEmailView;

        CheckBox friendSelectedView;

        private FriendHolder(View v) {
            super(v);
            mProfileView = (ImageView) v.findViewById(R.id.thumb);
            mNameView = (TextView) v.findViewById(R.id.name);
            mEmailView = (TextView) v.findViewById(R.id.email);
            friendSelectedView = (CheckBox) v.findViewById(R.id.checkbox);
            //ButterKnife.bind(this, v);
        }
    }
}
