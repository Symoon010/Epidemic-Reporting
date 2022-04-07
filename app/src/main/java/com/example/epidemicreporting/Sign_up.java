package com.example.epidemicreporting;

import androidx.annotation.NonNull;
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
import android.service.autofill.SaveInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Sign_up extends AppCompatActivity {

    private Window window;
    DatabaseReference mref;
    AutoCompleteTextView division,district,upazila;
    ActionBar actionbar;
    String name1,email1,nid1,division1,district1,upazila1,password1;
    Button signup;
    EditText name,email,nid,password;
    String gender="";
    RadioButton male,female;
    String userID;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign Up");

        if (Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }
       actionbar=getSupportActionBar();
       actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));

        name=findViewById(R.id.nameid);
        email=findViewById(R.id.emailid);
        nid=findViewById(R.id.nidid);
        password=findViewById(R.id.passwordid);
        division=(AutoCompleteTextView)findViewById(R.id.divisionid);
        district=(AutoCompleteTextView)findViewById(R.id.districtid);
        upazila=(AutoCompleteTextView)findViewById(R.id.upazilaid);
        signup=findViewById(R.id.signupid);
        male=findViewById(R.id.maleradiobuttonid);
        female=findViewById(R.id.femaleradiobuttonid);

        fAuth=FirebaseAuth.getInstance();
       String []AllDivision={"Dhaka","Rajshahi","Chittagong","Barishal","Sylhet","Rangpur","Khulna","Mymensingh"};

       String []AllDistrict={"Barguna","Barisal","Bhola","Jhalokati","Patuakhali", "Pirojpur", "Bandarban","Brahmanbaria","Chandpur", "Chittagong", "Comilla","Cox's Bazar","Feni","Khagrachhari","Lakshmipur", "Noakhali", "Rangamati",
        "Dhaka","Faridpur","Gazipur",  "Gopalganj",  "Kishoreganj","Madaripur",  "Manikganj","Munshiganj",  "Narayanganj","Narsingdi","Rajbari","Shariatpur","Tangail",
        "Bagerhat", "Chuadanga","Jessore",  "Jhenaidah",  "Khulna",     "Kushtia",    "Magura",   "Meherpur",    "Narail",     "Satkhira",
        "Jamalpur", "Mymensingh",  "Netrakona","Sherpur", "Bogra","Chapainawabganj","Joypurhat","Naogaon",    "Natore",     "Pabna",      "Rajshahi", "Sirajganj",
        "Dinajpur", "Gaibandha","Kurigram", "Lalmonirhat","Nilphamari", "Panchagarh", "Rangpur",  "Thakurgaon",
        "Habiganj", "Moulvibazar","Sunamganj","Sylhet"};

        String []AllUpazila={"Patnitala","Porsha","Manda","Mohadevpur"};


       ArrayAdapter<String>adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,AllDivision);
       division.setThreshold(1);
       division.setAdapter(adapter);

       ArrayAdapter<String>adapter1=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,AllDistrict);
       district.setThreshold(1);
       district.setAdapter(adapter1);

       ArrayAdapter<String>adapter2=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,AllUpazila);
       upazila.setThreshold(1);
       upazila.setAdapter(adapter2);


/*
        mref= FirebaseDatabase.getInstance().getReference("Demo");


        ValueEventListener event =new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                divisionsearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

       mref.addListenerForSingleValueEvent(event);*/

/*
       final String totaldivision[]={"Dhaka","Rajshahi","Chittagong","Khulna","Sylhet","Barisal","Rangpur","Mymensingh"};
       final String Dhaka[]={"Dhaka","Gazipur"};
       final String Rajshahi[]={"Rajshahi","Naogaon"};
       final String Dhaka1[]={"Keraniganj","Nawabganj","Dohar","Dhamrai","Savar"};
       final String Gazipur[]={"Gazipur Sadar","Kaliakair","Kapasia","Kaliganj","Sreepur"};
       final String Naogaon[]={"Naogaon Sadar","Patnitala","Dhamoirhat","Porsha","Sapahar","Niamatpur","Mahadebpur","Badalgachhi","Manda","Atrai","Raninagar"};
       final String Rajshahi1[]={"Boalia","Matihar","Rajpara","Shah Makhdum","Godagari","Charghat","Tanore","Durgapur","Paba","Puthia","Baghmara","Bagha","Mohanpur"};

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,totaldivision);
        division.setAdapter(adapter);

        division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String divisionitem = totaldivision[position];
                if(position==0)
                {
                    ArrayAdapter<String> adapter1= new ArrayAdapter<String>(Sign_up.this,android.R.layout.simple_spinner_dropdown_item,Dhaka);
                    district.setAdapter(adapter1);

                    district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                             String upazilaitem=Dhaka[position];
                             if(position==0)
                             {
                                 ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Sign_up.this, android.R.layout.simple_spinner_dropdown_item, Dhaka1);
                                 upazila.setAdapter(adapter3);
                             }
                            if(position==1)
                            {
                                ArrayAdapter<String> adapter4= new ArrayAdapter<String>(Sign_up.this, android.R.layout.simple_spinner_dropdown_item, Gazipur);
                                upazila.setAdapter(adapter4);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    
                }
               if(position==1)
                {
                    ArrayAdapter<String> adapter2= new ArrayAdapter<String>(Sign_up.this,android.R.layout.simple_spinner_dropdown_item,Rajshahi);
                    district.setAdapter(adapter2);

                    district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String upazilaitem=Rajshahi[position];
                            if(position==0)
                            {
                                ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(Sign_up.this, android.R.layout.simple_spinner_dropdown_item, Rajshahi1);
                                upazila.setAdapter(adapter5);
                            }
                            if(position==1)
                            {
                                ArrayAdapter<String> adapter6= new ArrayAdapter<String>(Sign_up.this, android.R.layout.simple_spinner_dropdown_item, Naogaon);
                                upazila.setAdapter(adapter6);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



*/
     /*    if(fAuth.getCurrentUser()!=null)
         {
             startActivity(new Intent(getApplicationContext(),Home.class));
             finish();
         }




*/


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email1=email.getText().toString().trim();
                name1=name.getText().toString().trim();
                nid1=nid.getText().toString().trim();
                division1=division.getText().toString().trim();
                district1=district.getText().toString().trim();
                upazila1=upazila.getText().toString().trim();
                password1=password.getText().toString().trim();


                if(checkConnection()==true)
                {
                    Snackbar.make(v, "No Internet Connection", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }

                if(TextUtils.isEmpty(name1))
                {
                    Snackbar.make(v, "Name is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(TextUtils.isEmpty(email1))
                {
                    Snackbar.make(v, "Email is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(TextUtils.isEmpty(nid1))
                {
                    Snackbar.make(v, "Nid is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }

                if(nid1.length()!=10)
                {
                    Snackbar.make(v, "Nid Must be 10 Digit", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }

                if(TextUtils.isEmpty(division1))
                {
                    Snackbar.make(v, "Division is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(TextUtils.isEmpty(district1))
                {
                    Snackbar.make(v, "District is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(TextUtils.isEmpty(upazila1))
                {
                    Snackbar.make(v, "Upazila is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(!female.isChecked()&&!male.isChecked())
                {
                    Snackbar.make(v, "Gender is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(male.isChecked())
                {
                    gender="Male";
                }
                if(female.isChecked())
                {
                    gender="Female";
                }
                if(TextUtils.isEmpty(password1))
                {
                    Snackbar.make(v, "Password is Required", Snackbar.LENGTH_LONG).setAction("", null).show();
                    return;
                }
                if(password1.length()<6)
                {
                    Snackbar.make(v, "Password must be >= 6 Character", Snackbar.LENGTH_LONG);
                    return;
                }


                fAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser User=FirebaseAuth.getInstance().getCurrentUser();
                            User.sendEmailVerification();
                            userID=fAuth.getUid();
                            StoreData  info = new  StoreData(name1,email1,nid1,division1,district1,upazila1,gender);
                            FirebaseDatabase.getInstance().getReference("Users").child(userID).setValue(info);
                            Toast.makeText(Sign_up.this,"Check Your Email To Verify Your Account",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Sign_in.class));
                        }
                        else
                        {
                            Toast.makeText(Sign_up.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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