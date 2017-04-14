package com.app.henry.firedroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button signInButton;
    private TextView newuser_link;

    private FirebaseUser firebaseUser;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mLoginFormView  = findViewById(R.id.login_form);
        mProgressView   = findViewById(R.id.login_progress);
        mEmailView      = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView   = (EditText) findViewById(R.id.password);
        signInButton    = (Button) findViewById(R.id.signInButton);
        newuser_link    = (TextView) findViewById(R.id.newuser_link);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });

        signInButton.setOnClickListener(this);
        newuser_link.setOnClickListener(this);

    }

    @Override public void onClick(View view){
        switch (view.getId()){
            case R.id.signInButton:

                //TODO:

                break;
            case R.id.newuser_link:
                startActivity(new Intent(this,SignUpActivity.class));
                break;
        }
    }

}

