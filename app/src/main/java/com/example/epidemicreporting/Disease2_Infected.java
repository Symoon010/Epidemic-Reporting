package com.example.epidemicreporting;

import androidx.annotation.NonNull;
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

import java.util.Calendar;

public class Disease2_Infected extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListenar;
    private EditText male1,male2,male3,female1,female2,female3;
    ActionBar actionbar;
    Window window;
    private Button submit,button;
    String userId,division,district,upazila,date="",Year="",name="",email="",key="",Month="";
    DatabaseReference reference;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String DiseaseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease2__infected);
        this.setTitle("INFECTED REPORTING");


        if (Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }
        actionbar=getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));



        male1=findViewById(R.id.disease2maleinfectedid1);
        male2=findViewById(R.id.disease2maleinfectedid2);
        male3=findViewById(R.id.disease2maleinfectedid3);
        female1=findViewById(R.id.disease2infectedfemale1);
        female2=findViewById(R.id.disease2infectedfemale2);
        female3=findViewById(R.id.disease2infectedfemale3);
        button=findViewById(R.id.disease2infecteddateid);
        submit=findViewById(R.id.disease2infectedmalesubmitid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(Disease2_Infected.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListenar,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenar=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month+=1;
                // Log.d("","OnDateSet: mm/dd/yy: "+day + " - "+day+ " - "+ year);
                Year=""+year;
                Month=""+month;
                date=day+ " - " +month+ " - " +year;
                button.setText(date);
            }
        };



        reference= FirebaseDatabase.getInstance().getReference().child("Current Disease");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DiseaseName=snapshot.child("2").getValue().toString();

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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                String MaleInfected1= male1.getText().toString().trim();
                String MaleInfected2= male2.getText().toString().trim();
                String MaleInfected3= male3.getText().toString().trim();
                String FemaleInfected1= female1.getText().toString().trim();
                String FemaleInfected2= female2.getText().toString().trim();
                String FemaleInfected3= female3.getText().toString().trim();
                if(checkConnection()==true)
                {
                    Snackbar.make(v, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                    return;
                }

                if(date.isEmpty())
                {
                    Snackbar.make(v, "Date is Not Selected", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(TextUtils.isEmpty(MaleInfected1)||TextUtils.isEmpty(MaleInfected2)||TextUtils.isEmpty(MaleInfected3))
                {
                    Snackbar.make(v, "Male Input Field is Empty", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(TextUtils.isEmpty(FemaleInfected1)||TextUtils.isEmpty(FemaleInfected2)||TextUtils.isEmpty(FemaleInfected3))
                {
                    Snackbar.make(v, "Female Input Field is Empty", Snackbar.LENGTH_LONG).setAction("", null).show();;
                    return;
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                int a=Integer.parseInt(MaleInfected1);
                int b=Integer.parseInt(MaleInfected2);
                int c=Integer.parseInt(MaleInfected3);
                int TotalInfectedMale=a+b+c;
                a=Integer.parseInt(FemaleInfected1);
                b=Integer.parseInt(FemaleInfected2);
                c=Integer.parseInt(FemaleInfected3);


                int TotalInfectedFemale=a+b+c;
                int TotalInfected=TotalInfectedMale+TotalInfectedFemale;


                String  InfectedMaleTotal= String.valueOf(TotalInfectedMale);
                String InfectedFemaleTotal= String.valueOf(TotalInfectedFemale);
                String   InfectedTotal= String.valueOf(TotalInfected);

                key=date+" - "+upazila;
                DatabaseReference myRef = database.getReference(DiseaseName).child(key);
                //save table
                myRef.child("District").setValue(district);
                myRef.child("Disease_Name").setValue(DiseaseName);
                myRef.child("Division").setValue(division);
                myRef.child("Upazila").setValue(upazila);
                myRef.child("Date").setValue(date);
                myRef.child("Reporter_Name").setValue(name);
                myRef.child("Reporter_Email").setValue(email);
                myRef.child("Year").setValue(Year);
                myRef.child("Month").setValue(Month);


                myRef.child("Infected_Male_Age0-25").setValue(MaleInfected1);
                myRef.child("Infected_Male_Age26-50").setValue(MaleInfected2);
                myRef.child("Infected_Male_Age51-Up").setValue(MaleInfected3);
                myRef.child("Infected_Male_Total").setValue(InfectedMaleTotal);

                myRef.child("Infected_Female_Age0-25").setValue(FemaleInfected1);
                myRef.child("Infected_Female_Age26-50").setValue(FemaleInfected2);
                myRef.child("Infected_Female_Age51-Up").setValue(FemaleInfected3);
                myRef.child("Infected_Female_Total").setValue(InfectedFemaleTotal);
                myRef.child("Infected_Total").setValue(InfectedTotal);



                Toast.makeText(getApplicationContext(),"Infected Information Added Successfully",Toast.LENGTH_LONG).show();
                Disease2_Infected.this.startActivity(new Intent(Disease2_Infected.this, Disease2_Reporting.class));

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