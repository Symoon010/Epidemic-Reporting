package com.example.epidemicreporting;

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
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {
    private Window window;
    private ActionBar actionbar;
    private CardView covid,chikungunya,dengue,diarrhea;
    TextView textView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    public  int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Epidemic Reporting");
        actionbar=getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }
        //navigationView=findViewById(R.id.navid);

        drawerLayout=findViewById(R.id.drawerid);
        covid=findViewById(R.id.covidid);
        dengue=findViewById(R.id.dengueid);
        diarrhea=findViewById(R.id.diarrheaid);
        chikungunya=findViewById(R.id.chikungunyaid);
        textView=findViewById(R.id.did);
        toolbar=findViewById(R.id.toolbarid);

       // setSupportActionBar(toolbar);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
       drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
       toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        covid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag=0;
                flag=1;
                Intent intent= new Intent(Home.this,Demo.class);
                intent.putExtra("covid19","Covid 19");
                startActivity(intent);
            }
        });
        dengue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag=0;
                flag=2;
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
    }

 /*   public void onBackpressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

*/
}