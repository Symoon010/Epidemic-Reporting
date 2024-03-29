package com.example.epidemicreporting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;

public class Disease1_Death extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener mDateSetListenar;
    EditText male1, male2, male3, female1, female2, female3;
    ActionBar actionbar;
    Window window;
    Button submit, button;
    String userId, division, district, upazila, date = "", DiseaseName,Year="",name="",email="",Month="",key="";
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease1__death);


        this.setTitle("DEATH REPORTING");

        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }
        actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));


        male1 = findViewById(R.id.disease1maledeathid1);
        male2 = findViewById(R.id.disease1maledeathid2);
        male3 = findViewById(R.id.disease1maledeathid3);
        female1 = findViewById(R.id.disease1deathfemale1);
        female2 = findViewById(R.id.disease1deathfemale2);
        female3 = findViewById(R.id.disease1deathfemale3);
        button = findViewById(R.id.disease1deathdateid);
        submit = findViewById(R.id.disease1deathmalesubmitid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Disease1_Death.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListenar, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenar = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month += 1;
                Month=""+month;
                Year=""+year;
                date = day + " - " + month + " - " + year;
                button.setText(date);
            }
        };



        reference=FirebaseDatabase.getInstance().getReference().child("Current Disease");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DiseaseName=snapshot.child("1").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        user=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference().child("Users");
        userId=user.getUid();
        reference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                division=snapshot.child("division").getValue().toString();
                district=snapshot.child("district").getValue().toString();
                upazila=snapshot.child("upazila").getValue().toString();
                name=snapshot.child("name").getValue().toString();
                email =snapshot.child("email").getValue().toString();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                String MaleDeath1 = male1.getText().toString().trim();
                String MaleDeath2 = male2.getText().toString().trim();
                String MaleDeath3 = male3.getText().toString().trim();
                String FemaleDeath1 = female1.getText().toString().trim();
                String FemaleDeath2 = female2.getText().toString().trim();
                String FemaleDeath3 = female3.getText().toString().trim();

                if(checkConnection()==true)
                {
                    Snackbar.make(v, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                    return;
                }


                if (date.isEmpty()) {
                    Snackbar.make(v, "Date is Not Selected", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if (TextUtils.isEmpty(MaleDeath1) || TextUtils.isEmpty(MaleDeath2) || TextUtils.isEmpty(MaleDeath3)) {
                    Snackbar.make(v, "Male Input Field is Empty", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if (TextUtils.isEmpty(FemaleDeath1) || TextUtils.isEmpty(FemaleDeath2) || TextUtils.isEmpty(FemaleDeath3)) {
                    Snackbar.make(v, "Female Input Field is Empty", Snackbar.LENGTH_LONG).setAction("", null).show();
                    ;
                    return;
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                int a = Integer.parseInt(MaleDeath1);
                int b = Integer.parseInt(MaleDeath2);
                int c = Integer.parseInt(MaleDeath3);
                int TotalDeathMale = a + b + c;
                a = Integer.parseInt(FemaleDeath1);
                b = Integer.parseInt(FemaleDeath2);
                c = Integer.parseInt(FemaleDeath3);


                int TotalDeathFemale = a + b + c;
                int TotalDeath = TotalDeathMale + TotalDeathFemale;


                String DeathMaleTotal = String.valueOf(TotalDeathMale);
                String DeathFemaleTotal = String.valueOf(TotalDeathFemale);
                String DeathTotal = String.valueOf(TotalDeath);

                key=date+" - "+upazila;
                DatabaseReference myRef = database.getReference(DiseaseName).child(key);


                // SaveTable();
                myRef.child("District").setValue(district);
                myRef.child("Disease_Name").setValue(DiseaseName);
                myRef.child("Division").setValue(division);
                myRef.child("Upazila").setValue(upazila);
                myRef.child("Date").setValue(date);
                myRef.child("Reporter_Name").setValue(name);
                myRef.child("Reporter_Email").setValue(email);
                myRef.child("Year").setValue(Year);
                myRef.child("Month").setValue(Month);

                myRef.child("Death_Male_Age0-25").setValue(MaleDeath1);
                myRef.child("Death_Male_Age26-50").setValue(MaleDeath2);
                myRef.child("Death_Male_Age51-Up").setValue(MaleDeath3);
                myRef.child("Death_Male_Total").setValue(DeathMaleTotal);

                myRef.child("Death_Female_Age0-25").setValue(FemaleDeath1);
                myRef.child("Death_Female_Age26-50").setValue(FemaleDeath2);
                myRef.child("Death_Female_Age51-Up").setValue(FemaleDeath3);
                myRef.child("Death_Female_Total").setValue(DeathFemaleTotal);
                myRef.child("Death_Total").setValue(DeathTotal);



                Toast.makeText(getApplicationContext(), "Death Information Added Successfully", Toast.LENGTH_LONG).show();
                Disease1_Death.this.startActivity(new Intent(Disease1_Death.this, Disease1_Reporting.class));

            }
        });


    }

    public  boolean checkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null)
        {

            return true;
        }
        else
            return false;
    }


}
