package com.example.keepsafe;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HelpFragment extends Fragment {

    EditText editTxtKejadian;
    TextView txtDate, txtGetLoc;
    DatePickerDialog.OnDateSetListener setListener;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button btnHelp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.help_fragment, container, false);


        editTxtKejadian = view.findViewById(R.id.editKejadian);
        txtDate = view.findViewById(R.id.txtDate);
        txtGetLoc = view.findViewById(R.id.btnGetLoc);
        btnHelp = view.findViewById(R.id.btnHelp);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                txtDate.setText(date);
            }
        };

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        txtDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        txtGetLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity()
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String kejadian = editTxtKejadian.getText().toString().trim();
                String date = txtDate.getText().toString().trim();
                String loc = txtGetLoc.getText().toString().trim();

                if (TextUtils.isEmpty(kejadian)){
                    editTxtKejadian.setError("Activity is Required");
                    return;

                }
                if (TextUtils.isEmpty(date)){
                    txtDate.setError("Date is Required");
                    return;

                }
                if (TextUtils.isEmpty(loc)){
                    txtGetLoc.setError("Location is Required");
                    return;

                }

                Intent intent = new Intent(getActivity(), DetailHelpActivity.class);
                intent.putExtra("key", editTxtKejadian.getText().toString());
                intent.putExtra("key2", txtDate.getText().toString());
                intent.putExtra("key3", txtGetLoc.getText().toString());
                startActivity(intent);

            }
        });


        return view;


    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        txtGetLoc.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Location : <b><b></font>"
                                        + addresses.get(0).getAddressLine(0)

                        ));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //Logout This App
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), MainActivity.class));
        requireActivity().finish();

    }

}
