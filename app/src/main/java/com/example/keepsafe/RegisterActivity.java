package com.example.keepsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editTextEmail = findViewById(R.id.editEmailRegister);
        EditText editTextNik = findViewById(R.id.editNik);
        EditText editTextPassword = findViewById(R.id.editPasswordRegister);
        EditText editTextRPassword = findViewById(R.id.editRePasswordRegister);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView btnTAndC = findViewById(R.id.btnTAndC);
        TextView btnToLogin = findViewById(R.id.btnToLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nik = editTextNik.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String RePassword = editTextRPassword.getText().toString().trim();

                if (TextUtils.isEmpty(nik)){
                    editTextNik.setError("NIK is Required");
                    return;

                }
                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password is Required");
                    return;
                }

                if ((password.length() < 6) && (RePassword.length() < 6)){
                    editTextPassword.setError("Password must be 6 characters or more");
                    editTextRPassword.setError("Re-Password must be 6 characters or more");
                    return;
                }

                if (TextUtils.isEmpty(RePassword)) {
                    editTextRPassword.setError("Retype Password is Required");
                    return;
                }
                if (!password.equals(RePassword)) {
                    editTextRPassword.setError("Password not matching");
                    editTextPassword.setError("Password not matching");
                    return;
                }




                //Here you Add Some Loading Bar
                //Registering User

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RegisterActivity.this, "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("tag","onFailure : Email not sent" + e.getMessage());

                                }
                            });


                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RegisterActivity.this, "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("tag","onFailure : Email not sent" + e.getMessage());

                                }
                            });


                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        } else {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            
                        }

                    }
                });

            }
        });

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });




    }
}