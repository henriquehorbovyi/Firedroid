package com.app.henry.firedroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button signInButton;
    private TextView newuser_link;

    private ProgressDialog progressDialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth    = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, ProfileActivity.class ));
        }


        progressDialog  = new ProgressDialog(this);
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
                userLogin(view);
                break;
            case R.id.newuser_link:
                startActivity(new Intent(this,SignUpActivity.class));
                break;
        }
    }


    private void userLogin(View view){
        String email    = mEmailView    .getText().toString().trim();
        String pass     = mPasswordView .getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Snackbar.make(view,"Email is required!",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Snackbar.make(view,"Password is required!",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(!email.contains("@")){
            Snackbar.make(view,"Invalid email address!",Snackbar.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setTitle("Loading...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(task -> {
                    if(task.isComplete()){
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class ));
                        }else{
                            Snackbar.make(view,"Something went wrong! Try again...",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

}

