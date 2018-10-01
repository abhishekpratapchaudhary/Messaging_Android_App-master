package com.arya.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by arya on 5/3/18.
 */

public class ChatlistAdapter extends BaseAdapter {

    private Activity mactivity;
    private DatabaseReference databaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotList;

    public ChatlistAdapter(Activity activity, DatabaseReference databaseReference, String m1DisplayName) {
        mactivity = activity;
        databaseReference = databaseReference.child("messages");
        databaseReference.addChildEventListener(mlistener);
        mDisplayName= m1DisplayName;
        mSnapshotList = new ArrayList<>();
    }

    private ChildEventListener mlistener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    static class ViewHolder {
        TextView name;
        TextView body;
        LinearLayout.LayoutParams layoutParams;
    }


    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public InstantMessage getItem(int position) {
        DataSnapshot mSnapshot = mSnapshotList.get(position);
        return mSnapshot.getValue(InstantMessage.class);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_msg_row, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.author);
            viewHolder.body = (TextView) convertView.findViewById(R.id.message);
            viewHolder.layoutParams = (LinearLayout.LayoutParams) viewHolder.name.getLayoutParams();
            convertView.setTag(viewHolder);

        }
        final InstantMessage message = getItem(position);
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        String msg = message.getMessage();
        viewHolder.body.setText(msg);
        Log.i("Message:",msg);

        String email = message.getEmail();
        viewHolder.name.setText(email);
        try {
            Log.i("Email", mDisplayName);
        }
        catch (Exception e)
        {
            Log.i("Success",e.getMessage());
        }
        boolean isMe = message.getEmail().equals(mDisplayName);
        setChatRowAppearance(isMe, viewHolder);


        return convertView;
    }

    public void cleanup() {
        databaseReference.removeEventListener(mlistener);
    }

    private void setChatRowAppearance(boolean isItMe, ViewHolder holder) {

        if (isItMe) {


            holder.layoutParams.gravity = Gravity.END;
            holder.name.setTextColor(Color.GREEN);

            // If you want to use colours from colors.xml
            // int colourAsARGB = ContextCompat.getColor(mActivity.getApplicationContext(), R.color.yellow);
            // holder.authorName.setTextColor(colourAsARGB);

            holder.body.setBackgroundResource(R.drawable.bubble2);
        } else {
            holder.layoutParams.gravity = Gravity.START;
            holder.name.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        }

        holder.name.setLayoutParams(holder.layoutParams);
        holder.body.setLayoutParams(holder.layoutParams);


    }
}
