package com.example.epidemicreporting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Demo extends AppCompatActivity {
    private Window window;

    TextView textView,date,div,dis,upz;
    ActionBar actionbar;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        this.setTitle("Reporting Status");
       // getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }
        actionbar=getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A851D")));
        Bundle bundle=getIntent().getExtras();
        textView=findViewById(R.id.did);
        date=findViewById(R.id.dateid);
        div=findViewById(R.id.divid);
        dis=findViewById(R.id.disid);
        upz=findViewById(R.id.upzid);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();



        Home f = new Home();
        //String   desease1=  bundle.getString("dengue");
       // String  deseas2=  bundle.getString("chikungunya");
       // String desease3=  bundle.getString("diarrhea");
       // String desease =  bundle.getString("covid19");
        int ret=f.flag;
        if(ret==1)
           textView.setText("Covid 19");
       else  if(ret==2)
            textView.setText("Dengue");





            //textView.setText("Symoon");






        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE , dd-MMM-yyyy");
        String dateTime=simpleDateFormat.format(calendar.getTime());
        date.setText(dateTime);




        userId=fAuth.getCurrentUser().getUid();
         final DocumentReference documentReference=fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
               div.setText(value.getString("Division"));
               dis.setText(value.getString("District"));
               upz.setText(value.getString("Upazila"));
            }
        });





    }
}

      /* <ProgressBar
            android:id="@+id/progressbarid"
            style="?android:attr/progressBarStyleHorizontal"
            android:layout_width="200dp"
            android:layout_height="15dp"
            android:layout_marginStart="24dp"
            android:layout_marginLeft="24dp"
            android:layout_marginTop="24dp"
            android:layout_marginEnd="24dp"
            android:layout_marginRight="24dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/signinforgetpasswordid" />

*/