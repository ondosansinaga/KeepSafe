package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailHelpActivity extends AppCompatActivity {

    private TextView  txtDetailKej, txtDate, txtLoc;
    Button btnDetailSending;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_help);

        txtDetailKej = findViewById(R.id.detailKejadian);
        txtDate = findViewById(R.id.detailTime);
        txtLoc= findViewById(R.id.detailLoc);
        btnDetailSending= findViewById(R.id.btnSendDetail);

        Intent intent = getIntent();
        String detailKejadianInten = intent.getStringExtra("key");
        String detailDate = intent.getStringExtra("key2");
        String detailLoc = intent.getStringExtra("key3");
        txtDetailKej.setText(detailKejadianInten);
        txtDate.setText(detailDate);
        txtLoc.setText(detailLoc);

        databaseReference = FirebaseDatabase.getInstance("https://keepsafe-de2ef-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Reporter");
        btnDetailSending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertReportData();
                Intent intent = new Intent(DetailHelpActivity.this, HelpFragment.class);
                startActivity(intent);
            }
        });


    }

    private void insertReportData() {
        String kejadian = txtDetailKej.getText().toString();
        String date = txtDate.getText().toString();
        String loc = txtLoc.getText().toString();

        Reporter reporter = new Reporter(kejadian,date,loc);
        databaseReference.push().setValue(reporter);
        Toast.makeText(this, "You data has been sent, Thank you for helping others", Toast.LENGTH_SHORT).show();
    }
}