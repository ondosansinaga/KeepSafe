package com.example.keepsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    Button btnVerifyNow;
    TextView txtVerifyNow;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnVerifyNow = findViewById(R.id.btnVerify);
        txtVerifyNow = findViewById(R.id.txtVerify);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseAuth.getCurrentUser() != null) {

            if(!firebaseUser.isEmailVerified()) {

                btnVerifyNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("tag","onFailure : Email not sent" + e.getMessage());

                            }
                        });

                    }
                });
            } else {
                btnVerifyNow.setVisibility(View.GONE);
                txtVerifyNow.setVisibility(View.GONE);
            }





        } else {

            startActivity(new Intent(this, MainActivity.class));
            finish();

        }


    }

    //Logout This App
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        this.finish();

        /*startActivity(new Intent(this, MainActivity.class));
        finish();
*/
    }


}