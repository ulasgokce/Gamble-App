package com.example.gambleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    FirebaseAuth frAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        frAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.btnSignUp)
    void SignUp() {
        String _email = email.getText().toString();
        String _password = password.getText().toString();

        if (_email.isEmpty()) {
            email.setError("Please enter email");
            email.requestFocus();
        } else if (_password.isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
        } else if (_password.isEmpty() && _email.isEmpty()) {
            Toast.makeText(MainActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
        } else if (!(_password.isEmpty() && _email.isEmpty())) {
            frAuth.createUserWithEmailAndPassword(_email, _password).addOnCompleteListener(MainActivity.this, task -> {

                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "SignUp is unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tvSignIn)
    void SignIn() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
