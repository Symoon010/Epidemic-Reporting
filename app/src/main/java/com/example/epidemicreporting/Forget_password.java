package com.example.epidemicreporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_password extends AppCompatActivity {
  Window window;
  ActionBar actionbar;
  private EditText forgetemail;
  private Button forgetbutton;
  ProgressDialog progressDialog;
  FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        this.setTitle("Forget Password");

        if (Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }
        actionbar=getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));
        forgetbutton=findViewById(R.id.forgetbuttonid);
        forgetemail=findViewById(R.id.forgetemailid);
        progressDialog= new ProgressDialog(this);
        auth=FirebaseAuth.getInstance();

        forgetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {

        String email=forgetemail.getText().toString().trim();

        if(checkConnection()==true)
        {
            Toast.makeText(Forget_password.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            return;
        }
        if(email.isEmpty())
        {
            forgetemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            forgetemail.requestFocus();
            return;
        }
        progressDialog.setMessage("Forget Password");
        progressDialog.show();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();

                if(task.isSuccessful())
                {
                    Toast.makeText(Forget_password.this,"Check Your Email to Reset Your Password!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Forget_password.this,"Try Again! Something Wrong Happened!",Toast.LENGTH_LONG).show();
                }
                Forget_password.this.startActivity(new Intent(Forget_password.this,Sign_in.class));
                finish();
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