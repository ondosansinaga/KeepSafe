package com.example.keepsafe;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    TextView info1,info2,collab;
    ImageView ig,fb,wa,web,call;

    String strinfInfo1 = "This App will send your report to" + "<b>"
            + " KeepSafe Headquarters " + "</b>" + "and notify the devision " +
            "that will be in the field to help";

    String stringInfo2 = "All information that you send and your identity will " +
            "remain " + "<b>" + "private & secret to others " + "</b>" + "<i>" + "except you " +
            "share it." + "</i>" + " Your Identity " + "<b>" + "will not " + "</b>" + "use by all of us";

    String stringCollab = "PMI " + "<br>" + "SAR " + "<br>" + "PLN " + "<br>" + "POLRI " + "<br>" + "DAMKAR " + "<br>" +
            "PRAMUKA " + "<br>" + "KEMENKES " + "<br>" + "HOTLINE COVID-19 " + "<br>";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        info1 = view.findViewById(R.id.info);
        info2 = view.findViewById(R.id.info2);
        collab = view.findViewById(R.id.collab);
        ig = view.findViewById(R.id.instagram);
        fb = view.findViewById(R.id.facebook);
        wa = view.findViewById(R.id.whatsapp);
        web = view.findViewById(R.id.web);
        call = view.findViewById(R.id.call);

        info1.setText(Html.fromHtml(strinfInfo1));
        info2.setText(Html.fromHtml(stringInfo2));
        collab.setText(Html.fromHtml(stringCollab));
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("http://instagram.com/cokfotokandulu");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/cokfotokandulu")));
                }

            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/cokfotokandulu1/"));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/cokfotokandulu1/")));
                }
            }
        });

        wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+62 88216439586"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = getContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getActivity(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cokfotokandulu.blogspot.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = "+6281269087592";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);


            }
        });







        return view;

    }
}
