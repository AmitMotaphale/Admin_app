package com.example.adminapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int a = R.id.addNotice ;
    MaterialCardView uploadNotice;
    MaterialCardView deleteNotice;
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        uploadNotice = (MaterialCardView) super.findViewById(R.id.addNotice);
        uploadNotice.setOnClickListener(this);

        deleteNotice = (MaterialCardView) super.findViewById(R.id.deleteNotice);
        deleteNotice.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
       if(view.getId()==R.id.addNotice){
           Intent intent = new Intent(MainActivity.this,UploadNotice.class);
           startActivity(intent);
       }
       if(view.getId()==R.id.deleteNotice){
           Intent intent = new Intent(MainActivity.this,DeleteNotice.class);
           startActivity(intent);
       }
    }
}