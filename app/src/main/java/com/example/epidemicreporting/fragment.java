package com.example.epidemicreporting;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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

import java.util.concurrent.Executor;


public class fragment extends Fragment {
    private CardView disease1,disease2,disease3,disease4;
    String division1,district1,upazila1,userId1;
    String disease1name,disease2name,disease3name,disease4name;
    DatabaseReference reference;
    TextView cardtext1,cardtext2,cardtext3,cardtext4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_fragment,container,false);
        disease1=v.findViewById(R.id.carddisease1id);
        disease2=v.findViewById(R.id.carddisease2id);
        disease3=v.findViewById(R.id.carddisease3id);
        disease4=v.findViewById(R.id.carddisease4id);
        cardtext1=v.findViewById(R.id.cardtext1id);
        cardtext2=v.findViewById(R.id.cardtext2id);
        cardtext3=v.findViewById(R.id.cardtext3id);
        cardtext4=v.findViewById(R.id.cardtext4id);

        reference=FirebaseDatabase.getInstance().getReference().child("Current Disease");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                disease1name=snapshot.child("1").getValue().toString();
                disease2name=snapshot.child("2").getValue().toString();
                disease3name=snapshot.child("3").getValue().toString();
                disease4name=snapshot.child("4").getValue().toString();
                cardtext1.setText(disease1name);
                cardtext2.setText(disease2name);
                cardtext3.setText(disease3name);
                cardtext4.setText(disease4name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











        disease1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(v.getContext(),Disease1_Reporting.class);
               // intent.putExtra("DiseaseName1",disease1name);
                //startActivity(intent);
                startActivity(new Intent(getActivity(),Disease1_Reporting.class));
                //finish();
            }
        });

        disease2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),Disease2_Reporting.class));
                //finish();
            }
        });
        disease3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),Disease3_Reporting.class));
                //finish();
            }
        });
        disease4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),Disease4_Reporting.class));
                //finish();
            }
        });

        return v;


    }
}