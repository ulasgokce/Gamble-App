package com.example.gambleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultActivity extends AppCompatActivity {
    @BindView(R.id.tvResult)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            int[] arr = extras.getIntArray("numbers1");
            int[] arr2 = extras.getIntArray("numbers2");
            Arrays.sort(arr);
            Arrays.sort(arr2);
            int achieve = 0;

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (arr2[i] == arr[j]) {
                        achieve++;
                    }
                }
            }
            String text = "Sayı bilerek kazandınız";
            if (achieve == 0) {
                text = "Başaramadınız bidahaki sefere";
                tvResult.setText(text);
            } else {
                tvResult.setText(achieve + " " + text);
            }
        }
    }

    @OnClick(R.id.btnPlayAgain)
    void PlayAgain() {
        Intent i = new Intent(ResultActivity.this, UserActivity.class);
        startActivity(i);
    }

}
