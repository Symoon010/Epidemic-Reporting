package com.example.epidemicreporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_in extends AppCompatActivity {
    private Window window;
    private EditText email, password;

    private Button signinbutton;
    private ProgressBar progressbar;
    private TextView forgetpassword, signup;
    FirebaseAuth mAuth;

    //TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }



      /*  t=findViewById(R.id.textView);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new  Intent(Sign_in.this,Sign_up.class);
                startActivity(intent);

            }
        });

       */

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.signinemailid);
        password = findViewById(R.id.signinpasswordid);
        signinbutton = findViewById(R.id.signinbuttonid);
        forgetpassword=findViewById(R.id.forgetpasswordid);
        //progressbar=findViewById(R.id.progressbarid);
        signup = findViewById(R.id.signinsignupid);
        // signinbutton.setOnClickListener(this);




        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString().trim();
                String password1 = password.getText().toString().trim();

                if (TextUtils.isEmpty(email1)) {
                    email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password1)) {
                    password.setError("Password is Required");
                    return;
                }
                if (password1.length() < 6) {
                    password.setError("Password must be >= 6 Character");
                    return;
                }

               else {
                    mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Sign_in.this.startActivity(new Intent(Sign_in.this, Home.class));
                                Sign_in.this.finish();

                                //Toast.makeText(Sign_in.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                // Intent intent = new Intent(Sign_in.this, Home.class);
                                //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //startActivity(intent);
                                // finish();
                                //  startActivity(new Intent(Sign_in.this, Home.class));

                            } else {
                                Toast.makeText(Sign_in.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            // finish();
                        }

                    });

                }

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
              final EditText resetmail=new EditText(v.getContext());
              AlertDialog.Builder passwordresetDialog=new AlertDialog.Builder(v.getContext());
              passwordresetDialog.setTitle("Reset Password ?");
              passwordresetDialog.setTitle("Enter Your Email To Received Reset Link . ");
              passwordresetDialog.setView(resetmail);

              passwordresetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                     String mail=resetmail.getText().toString();
                     mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {
                             Toast.makeText(Sign_in.this,"Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                           Toast.makeText(Sign_in.this,"Error ! Reset Link is Not Sent"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     });
                  }
              });


              passwordresetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      //close the dialog
                  }
              });
              passwordresetDialog.create().show();


          }
      });


}

      /*
    public  void  onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.signinbuttonid:
                userLogin();
                break;
        }
    }
    private  void userLogin()
    {
        String email1=email.getText().toString().trim();
        String password1=password.getText().toString().trim();


        mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Sign_in.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(Sign_in.this, Home.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intent);
                     finish();
                    //startActivity(new Intent(Sign_in.this,Home.class));

                } else {
                    Toast.makeText(Sign_in.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                // finish();
            }

        });

    }*/


}