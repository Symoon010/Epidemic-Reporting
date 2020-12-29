package com.example.epidemicreporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {
    private Window window;
    private ActionBar actionbar;
    private CardView covid,chikungunya,dengue,diarrhea;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    public  int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Epidemic Reporting");
        actionbar=getSupportActionBar();
        //actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        nav=findViewById(R.id.ppid);

        drawerLayout=findViewById(R.id.drawerid);
        covid=findViewById(R.id.covidid);
        dengue=findViewById(R.id.dengueid);
        diarrhea=findViewById(R.id.diarrheaid);
        chikungunya=findViewById(R.id.chikungunyaid);

        toolbar=findViewById(R.id.toolbarid);







        covid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Home.this,Disease.class);
                intent.putExtra("covid19","Covid 19");
                startActivity(intent);
            }
        });
        dengue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Home.this,Demo.class);
                intent.putExtra("dengue","Dengue");
                startActivity(intent);
            }
        });
        chikungunya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Home.this,Demo.class);
                intent.putExtra("chikungunya","Chikungunya");
                startActivity(intent);
            }
        });
        diarrhea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Home.this,Demo.class);
                intent.putExtra("diarrhea","Diarrhea");
                startActivity(intent);
            }
        });




        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               // item.setCheckable(true);
                return true;
            }
        });
        //setSupportActionBar(toolbar);
        //drawerLayout.bringToFront();
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        //toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }



}