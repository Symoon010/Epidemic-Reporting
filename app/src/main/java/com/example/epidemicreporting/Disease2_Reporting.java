package com.example.epidemicreporting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Disease2_Reporting extends AppCompatActivity {
    Window window;
    ActionBar actionbar;
    Button infected,recover,death;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease2__reporting);
        this.setTitle("REPORTING");

        if (Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }
        actionbar=getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));

        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        infected=findViewById(R.id.disease2infectedid);
        recover=findViewById(R.id.disease2recoverdid);
        death=findViewById(R.id.disease2deathid);
        infected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Disease2_Reporting.this.startActivity(new Intent(Disease2_Reporting.this, Disease2_Infected.class));
            }
        });

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Disease2_Reporting.this.startActivity(new Intent(Disease2_Reporting.this, Disease2_Recover.class));
            }
        });

        death.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Disease2_Reporting.this.startActivity(new Intent(Disease2_Reporting.this, Disease2_Death.class));
            }
        });

    }
}