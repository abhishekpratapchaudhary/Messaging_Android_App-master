package com.arya.flashchatnewfirebase;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;







public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayEmail=null;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ChatlistAdapter chatlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        mDisplayEmail = getIntent().getExtras().getString("email");

        // TODO: Set up the display name and get the Firebase reference
        mAuth = FirebaseAuth.getInstance();
        //setupDisplayname();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed
        mInputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });
        // TODO: Add an OnClickListener to the sendButton to send a message

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

   // TODO: Retrieve the display name from the Shared Preferences
   /*private void setupDisplayName(){

       SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);

       mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY, null);

       if (mDisplayName == null) mDisplayName = "Anonymous";
   }*/



    private void sendMessage() {

        // TODO: Grab the text the user typed in and push the message to Firebase
          String message=mInputText.getText().toString();
          if(!message.equals(""))
          {
              InstantMessage instantMessage=new InstantMessage(message,mDisplayEmail);
              databaseReference.child("messages").push().setValue(instantMessage);
              mInputText.setText("");
          }

    }

    @Override
    protected void onStart() {
        super.onStart();
        chatlistAdapter=new ChatlistAdapter(this,databaseReference,mDisplayEmail);
        mChatListView.setAdapter(chatlistAdapter);
    }
// TODO: Override the onStart() lifecycle method. Setup the adapter here.


  /*  @Override
    public void onStop() {
        try {
            super.onStop();
            chatlistAdapter.cleanup();
        }
        catch (Exception e)
        {
            Log.i("Shutdown:",e.getMessage());
        }
        // TODO: Remove the Firebase event listener on the adapter.

    }*/

}
