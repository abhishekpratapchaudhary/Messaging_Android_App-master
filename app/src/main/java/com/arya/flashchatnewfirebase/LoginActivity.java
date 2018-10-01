package com.arya.flashchatnewfirebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;






public class LoginActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;
    public static String CHAT_PREFS="Reference location";
    public static String DISPLAY_EMAIL_KEY="Email_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // TODO: Grab an instance of FirebaseAuth
        mAuth=FirebaseAuth.getInstance();
    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {

        attemptLogin();
        // TODO: Call attemptLogin() here

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {

        String email=mEmailView.getText().toString();
        String password=mPasswordView.getText().toString();

        if(email.equals("") || (password.equals(""))) return;
        Toast.makeText(LoginActivity.this,"Login in Progress",Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String email=mEmailView.getText().toString();
                if(task.isSuccessful())
                {
                    Intent intent=new Intent(getApplicationContext(),MainChatActivity.class);
                    //Intent intent=new Intent(getApplicationContext(),FriendlistActivity.class);
                    intent.putExtra("email",email);
                    finish();
                    startActivity(intent);
                    //saveEmail();
                }

                else
                {
                    Log.i("Bharatchat","Problem in signing in"+task.getException());
                    error(task.getException().getMessage().toString());
                }

            }
        });



    }
    public void saveEmail()
    {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(LoginActivity.CHAT_PREFS,MODE_PRIVATE);
        sharedPreferences.edit().putString(DISPLAY_EMAIL_KEY,mEmailView.getText().toString());
    }

    // TODO: Show error on screen with an alert dialog
private void error(String message)
{
    new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Oops")
            .setMessage(message).setPositiveButton("Ok",null).show();

}


}