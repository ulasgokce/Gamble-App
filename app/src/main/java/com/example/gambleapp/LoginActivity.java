package com.example.gambleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    Boolean admin = false;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;

    FirebaseAuth frAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        frAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = frAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, UserActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };


    }

    @OnClick(R.id.btnSignIn)
    void SignIn() {
        String _email = email.getText().toString();
        String _password = password.getText().toString();
        if (_email.equalsIgnoreCase("ulasgokce98@gmail.com") && _password.equalsIgnoreCase("ulas123")) {
            admin = true;
        }
        if (_email.isEmpty()) {
            email.setError("Please enter email");
            email.requestFocus();
        } else if (_password.isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
        } else if (_password.isEmpty() && _email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
        } else if (!(_password.isEmpty() && _email.isEmpty())) {
            frAuth.signInWithEmailAndPassword(_email, _password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        password.setText("");
                        Toast.makeText(LoginActivity.this, "Login Error, Please Try Again", Toast.LENGTH_SHORT).show();
                    } else {
                        if (admin) {
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, UserActivity.class));
                        }
                    }
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        frAuth.addAuthStateListener(mAuthStateListener);
    }

    @OnClick(R.id.tvSignUp)
    void SignUp() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }
}
