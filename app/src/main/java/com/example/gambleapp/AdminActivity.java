package com.example.gambleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminActivity extends Activity {

    DatabaseReference myRef;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
    }


    @OnClick(R.id.logout)
    void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.btnOynat)
    void btnOynat() {
        Boolean notThisOne;
        List<Integer> arr = new ArrayList<Integer>();
        int rand1, rand2, counter = 1;
        Random random = new Random();
        rand1 = 1 + random.nextInt(25);
        arr.add(rand1);
        try {
            do {
                notThisOne = false;
                rand2 = 1 + random.nextInt(25);
                for (int j = 0; j < counter; j++) {
                    if (rand2 == arr.get(j)) {
                        notThisOne = true;
                    }
                }
                if (!notThisOne) {
                    arr.add(rand2);
                    counter++;
                }
            } while (counter < 6);
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        Collections.sort(arr);
        myRef = database.getReference("Numbers");
        String id = myRef.push().getKey();
        LuckyNumbers i = new LuckyNumbers(id, arr);
        myRef.child(id).setValue(i);
        String text = "";
        for (int var : i.getArr()) {
            text = text + " " + var;
        }
        tvNumber.setText("Oynatılacak Sayılar" + text);
        Toast.makeText(AdminActivity.this, "Oyun oynatıldı", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
