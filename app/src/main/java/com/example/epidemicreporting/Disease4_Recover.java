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

public class Disease4_Recover extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener mDateSetListenar;
    EditText male1,male2,male3,female1,female2,female3;
    ActionBar actionbar;
    Window window;
    private Button submit,button;
    String userId,division,district,upazila,date="",DiseaseName="",name="",email="",Year="",Month="",key="";
    DatabaseReference reference;
    FirebaseUser user;
    String  MaleRecover1,MaleRecover2,MaleRecover3,FemaleRecover1,FemaleRecover2,FemaleRecover3,RecoverMaleTotal,RecoverFemaleTotal,RecoverTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease4__recover);
        this.setTitle("RECOVER REPORTING");

        if (Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }
        actionbar=getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));

        male1=findViewById(R.id.disease4recovermale1);
        male2=findViewById(R.id.disease4recovermale2);
        male3=findViewById(R.id.disease4recovermale3);
        female1=findViewById(R.id.disease4recoverfemale1);
        female2=findViewById(R.id.disease4recoverfemale2);
        female3=findViewById(R.id.disease4recoverfemale3);
        button=findViewById(R.id.disease4recoverdate);
        submit=findViewById(R.id.disease4recoversubmitid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(Disease4_Recover.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListenar,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenar=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month+=1;
                Month=""+month;
                Year=""+year;
                date=day+ " - " +month+ " - " +year;
                button.setText(date);
            }
        };



        reference= FirebaseDatabase.getInstance().getReference().child("Current Disease");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DiseaseName=snapshot.child("4").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference().child("Users");
        userId=user.getUid();
        reference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                division=snapshot.child("division").getValue().toString();
                district=snapshot.child("district").getValue().toString();
                upazila=snapshot.child("upazila").getValue().toString();
                name=snapshot.child("name").getValue().toString();
                email=snapshot.child("email").getValue().toString();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        submit.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                MaleRecover1= male1.getText().toString().trim();
                MaleRecover2= male2.getText().toString().trim();
                MaleRecover3= male3.getText().toString().trim();
                FemaleRecover1= female1.getText().toString().trim();
                FemaleRecover2= female2.getText().toString().trim();
                FemaleRecover3= female3.getText().toString().trim();

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
                if(TextUtils.isEmpty(MaleRecover1)||TextUtils.isEmpty(MaleRecover2)||TextUtils.isEmpty(MaleRecover3))
                {
                    Snackbar.make(v, "Male Input Field is Empty", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(TextUtils.isEmpty(FemaleRecover1)||TextUtils.isEmpty(FemaleRecover2)||TextUtils.isEmpty(FemaleRecover3))
                {
                    Snackbar.make(v, "Female Input Field is Empty", Snackbar.LENGTH_LONG).setAction("", null).show();;
                    return;
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                int a=Integer.parseInt(MaleRecover1);
                int b=Integer.parseInt(MaleRecover2);
                int c=Integer.parseInt(MaleRecover3);
                int TotalRecoverMale=a+b+c;
                a=Integer.parseInt(FemaleRecover1);
                b=Integer.parseInt(FemaleRecover2);
                c=Integer.parseInt(FemaleRecover3);
                int TotalRecoverFemale=a+b+c;
                int TotalRecover=TotalRecoverMale+TotalRecoverFemale;


                RecoverMaleTotal= String.valueOf(TotalRecoverMale);
                RecoverFemaleTotal= String.valueOf(TotalRecoverFemale);
                RecoverTotal= String.valueOf(TotalRecover);

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


                myRef.child("Recover_Male_Age0-25").setValue(MaleRecover1);
                myRef.child("Recover_Male_Age26-50").setValue(MaleRecover2);
                myRef.child("Recover_Male_Age51-Up").setValue(MaleRecover3);
                myRef.child("Recover_Male_Total").setValue(RecoverMaleTotal);

                myRef.child("Recover_Female_Age0-25").setValue(FemaleRecover1);
                myRef.child("Recover_Female_Age26-50").setValue(FemaleRecover2);
                myRef.child("Recover_Female_Age51-Up").setValue(FemaleRecover3);
                myRef.child("Recover_Female_Total").setValue(RecoverFemaleTotal);
                myRef.child("Recover_Total").setValue(RecoverTotal);


                Toast.makeText(getApplicationContext()," Recover Information Added Successfully",Toast.LENGTH_LONG).show();
                Disease4_Recover.this.startActivity(new Intent(Disease4_Recover.this, Disease4_Reporting.class));


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
