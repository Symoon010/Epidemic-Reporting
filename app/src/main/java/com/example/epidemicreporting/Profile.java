package com.example.epidemicreporting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    Window window;
    ActionBar actionbar;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser user;
    String userId,NAME,EMAIL,DIVISION,DISTRICT,UPAZILA,NID,GENDER,PASSWORD;
    Button updatebutton;
    private EditText name,div,dis,upz,password,gender;
    TextView email,nid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.setTitle("Profile");

        if (Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }
        actionbar=getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));


        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        name=findViewById(R.id.editnameid);
        email=findViewById(R.id.editemailid);
        nid=findViewById(R.id.editnidid);
        div=findViewById(R.id.editdivisionid);
        dis=findViewById(R.id.editdistrictid);
        upz=findViewById(R.id.editupazilaid);
        password=findViewById(R.id.editpasswordid);
        gender=findViewById(R.id.editgenderid);
        updatebutton=findViewById(R.id.updateprofileid);
        user=fAuth.getCurrentUser();


        userId=fAuth.getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                name.setText( snapshot.child("name").getValue().toString());
                email.setText( snapshot.child("email").getValue().toString());
                nid.setText( snapshot.child("nid").getValue().toString());
                div.setText( snapshot.child("division").getValue().toString());
                dis.setText( snapshot.child("district").getValue().toString());
                upz.setText( snapshot.child("upazila").getValue().toString());
                gender.setText( snapshot.child("gender").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkConnection()==true)
                {
                    Snackbar.make(v, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                    return;
                }
                if(name.getText().toString().isEmpty())
                {
                    Snackbar.make(v, "Name is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(div.getText().toString().isEmpty())
                {
                    Snackbar.make(v, "Division is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(dis.getText().toString().isEmpty())
                {
                    Snackbar.make(v, "District is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(upz.getText().toString().isEmpty())
                {
                    Snackbar.make(v, "Upazila is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(gender.getText().toString().isEmpty())
                {
                    Snackbar.make(v, "Gender is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }

                if(password.getText().toString().isEmpty())
                {
                    Snackbar.make(v, "Password is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }



                  NAME=name.getText().toString();
                  EMAIL=email.getText().toString();
                  NID=nid.getText().toString();
                  DIVISION=div.getText().toString();
                  DISTRICT=dis.getText().toString();
                  UPAZILA=upz.getText().toString();
                  GENDER=gender.getText().toString();
                  UPAZILA=upz.getText().toString();
                  PASSWORD=password.getText().toString();




                // user.updateEmail(updateemail);
                reference=FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                HashMap hashMap=new HashMap();
                hashMap.put("name",NAME);
                hashMap.put("nid",NID);
                hashMap.put("division",DIVISION);
                hashMap.put("district",DISTRICT);
                hashMap.put("upazila",UPAZILA);
                hashMap.put("gender",GENDER);
                hashMap.put("password",PASSWORD);
                reference.updateChildren(hashMap);
                user.updatePassword(PASSWORD);
                Toast.makeText(Profile.this,"Profile Update Sucessfully",Toast.LENGTH_LONG).show();
                Profile.this.startActivity(new Intent(Profile.this, Home.class));

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