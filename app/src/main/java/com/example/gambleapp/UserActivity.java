package com.example.gambleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "UserActivity";
    @BindView(R.id.first)
    EditText number1;
    @BindView(R.id.second)
    EditText number2;
    @BindView(R.id.third)
    EditText number3;
    @BindView(R.id.fourth)
    EditText number4;
    @BindView(R.id.fifth)
    EditText number5;
    @BindView(R.id.sixth)
    EditText number6;
    @BindView(R.id.textViewInfo)
    TextView tvInfo;
    DatabaseReference myRef;
    Query lastQuery;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Numbers");
        lastQuery = myRef.orderByKey().limitToLast(1);
    }

    @OnClick(R.id.logout)
    void Logout() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(UserActivity.this, LoginActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.btnPlay)
    void Play() {

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final LuckyNumbers[] numbers = {null};
                dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                    numbers[0] = dataSnapshot1.getValue(LuckyNumbers.class);
                });
                int[] arr = new int[]{0, 0, 0, 0, 0, 0};
                int[] arr2 = new int[]{0, 0, 0, 0, 0, 0};

                if(!(number1.getText().toString().isEmpty()||number2.getText().toString().isEmpty()|| number3.getText().toString().isEmpty()||
                        number4.getText().toString().isEmpty()|| number5.getText().toString().isEmpty()|| number6.getText().toString().isEmpty())) {
                    arr2[0] = Integer.parseInt(number1.getText().toString());
                    arr2[1] = Integer.parseInt(number2.getText().toString());
                    arr2[2] = Integer.parseInt(number3.getText().toString());
                    arr2[3] = Integer.parseInt(number4.getText().toString());
                    arr2[4] = Integer.parseInt(number5.getText().toString());
                    arr2[5] = Integer.parseInt(number6.getText().toString());

                    for (int i = 0; i < 6; i++) {
                        arr[i] = numbers[0].getArr().get(i);
                    }
                    boolean allDifferent = true;
                    boolean allFit = true;
                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < i; j++) {
                            if (arr2[i] == arr2[j]) {
                                allDifferent = false;
                                Toast.makeText(UserActivity.this, "Aynı Sayı girmeden tekrar deneyiniz.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (arr2[i] > 25 || arr2[i] < 1) {
                            allFit = false;
                            Toast.makeText(UserActivity.this, "Sayılar 1 ile 25 aralığında olsun.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (allDifferent && allFit) {
                        Intent i = new Intent(UserActivity.this, ResultActivity.class);

                        i.putExtra("numbers1", arr);
                        i.putExtra("numbers2", arr2);
                        startActivity(i);
                    } else {
                        number1.setText("");
                        number2.setText("");
                        number3.setText("");
                        number4.setText("");
                        number5.setText("");
                        number6.setText("");

                    }
                }else{
                    Toast.makeText(UserActivity.this,"6 tane sayı girin",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }
        };
        lastQuery.addValueEventListener(postListener);

    }

    @OnClick(R.id.imageButton)
    void Info() {
        if (tvInfo.getVisibility() == View.VISIBLE)
            tvInfo.setVisibility(View.INVISIBLE);
        else {
            tvInfo.setVisibility(View.VISIBLE);
        }
    }
}
