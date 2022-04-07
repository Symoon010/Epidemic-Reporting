package com.example.epidemicreporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_in extends AppCompatActivity {
    private Window window;
    private EditText email, password;
    ProgressDialog progressDialog;
    private Button signinbutton;
    private TextView forgetpassword, signup;
    FirebaseAuth mAuth;
    FirebaseUser User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        mAuth = FirebaseAuth.getInstance();
        User=FirebaseAuth.getInstance().getCurrentUser();


        email = findViewById(R.id.signinemailid);
        password = findViewById(R.id.signinpasswordid);
        signinbutton = findViewById(R.id.signinbuttonid);
        forgetpassword=findViewById(R.id.forgetpasswordid);
        signup = findViewById(R.id.signinsignupid);
        progressDialog= new ProgressDialog(this);




        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String email1 = email.getText().toString().trim();
                String password1 = password.getText().toString().trim();

                if(checkConnection()==true)
                {
                    Snackbar.make(v, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                    return;
                }

                if (TextUtils.isEmpty(email1)) {
                    Snackbar.make(v, "Email Required", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                    return;
                }
                if (TextUtils.isEmpty(password1)) {
                    Snackbar.make(v, "Password Required", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                    return;
                }
                if (password1.length() < 6) {
                    //password.setError("Password must be >= 6 Character");
                    Snackbar.make(v, "Password must be >=6 Character", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                    return;
                }
                
                progressDialog.setMessage("Sign-in");
                progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {

                                Toast.makeText(Sign_in.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                Sign_in.this.startActivity(new Intent(Sign_in.this, Home.class));
                                finish();
                            }
                            else {
                                Snackbar.make(v, "Email or Password Incorrect", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                        }

                    });

                }
        });


      signup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent= new Intent(Sign_in.this,Sign_up.class);
              startActivity(intent);
              //finish();
          }
      });

      forgetpassword.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent= new Intent(Sign_in.this,Forget_password.class);
              startActivity(intent);

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