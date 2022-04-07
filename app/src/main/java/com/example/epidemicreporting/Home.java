package com.example.epidemicreporting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Window window;
    ActionBar actionbar;
    CardView covid,chikungunya,dengue,diarrhea;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String division1,district1,upazila1,userId1;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //actionbar=getSupportActionBar();
        //actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));
        //getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        nav = findViewById(R.id.ppid);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        drawerLayout = findViewById(R.id.drawerid);
        progressDialog = new ProgressDialog(this);


        // covid=findViewById(R.id.covidid);
        //dengue=findViewById(R.id.dengueid);
        //diarrhea=findViewById(R.id.diarrheaid);
        // chikungunya=findViewById(R.id.chikungunyaid);


        toolbar = findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);


        //setSupportActionBar(toolbar);
        //drawerLayout.bringToFront();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentid, new fragment());
        fragmentTransaction.commit();
        nav.setNavigationItemSelectedListener(this);


      /*  userId1 = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId1);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                division1 = value.getString("Division");
                district1 = value.getString("District");
                upazila1 = value.getString("Upazila");
            }
        });

*/
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.outid)
        {
            progressDialog.setMessage("Sign-out");
            progressDialog.show();
            Toast.makeText(Home.this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent= new Intent(Home.this,Sign_in.class);
            startActivity(intent);
            finish();
            //progressDialog.dismiss();

            //finish();
          // startActivity(new Intent(Home.this,Sign_in.class));


        }
        else if(item.getItemId()==R.id.profileid)
        {

            Intent intent= new Intent(Home.this,Profile.class);
            startActivity(intent);

        }
        else if(item.getItemId()==R.id.instructionid)
        {

            Intent intent= new Intent(Home.this,Instruction.class);
            startActivity(intent);

        }
        else if(item.getItemId()==R.id.contactid)
        {

            Intent intent= new Intent(Home.this,Contact.class);
            startActivity(intent);

        }
        DrawerLayout drawerLayout=findViewById(R.id.drawerid);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}