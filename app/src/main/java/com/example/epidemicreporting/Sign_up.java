package com.example.epidemicreporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;


public class Sign_up extends AppCompatActivity {

    private Window window;

    ActionBar actionbar;

   Button signup;
   EditText name,email,nid,password;

    Spinner division,district,upazila;
    String gender;
    RadioButton male,female;

    String userID;

     FirebaseFirestore fStore;
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
        //gender=findViewById(R.id.radiogroupid);
      //  male=findViewById(R.id.maleradiobuttonid);
      //  female=findViewById(R.id.femaleradiobuttonid);
        division=findViewById(R.id.divisionid);
        district=findViewById(R.id.districtid);
        upazila=findViewById(R.id.upazilaid);
       signup=findViewById(R.id.signupid);
       male=findViewById(R.id.maleradiobuttonid);
       female=findViewById(R.id.femaleradiobuttonid);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();



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




     /*    if(fAuth.getCurrentUser()!=null)
         {
             startActivity(new Intent(getApplicationContext(),Home.class));
             finish();
         }
*/
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final   String email1=email.getText().toString().trim();
                final String name1=name.getText().toString().trim();
                final  String nid1=nid.getText().toString().trim();
                final String division1=division.getSelectedItem().toString();
                final String district1=district.getSelectedItem().toString();
                final String upazila1=upazila.getSelectedItem().toString();
                //String male1= male.getText().toString();
                // String female1= female.getText().toString();
                String password1=password.getText().toString().trim();

                if(male.isChecked())
                {
                    gender="Male";
                }
                if(female.isChecked())
                {
                    gender="Female";
                }
                if(TextUtils.isEmpty(email1))
                {
                    email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password1))
                {
                    password.setError("Password is Required");
                    return;
                }
                if(password1.length()<6)
                {
                    password.setError("Password must be >= 6 Character");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Sign_up.this,"User Created",Toast.LENGTH_SHORT).show();
                            userID=fAuth.getUid();


                            DocumentReference documentReference=fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Name", name1);
                            user.put("Email", email1);
                            user.put("Nid",nid1);
                            user.put("Division",division1);
                            user.put("District",district1);
                            user.put("Upazila",upazila1);
                            user.put("Gender",gender);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: user Profile is created for "+ userID);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(),Home.class));
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
}