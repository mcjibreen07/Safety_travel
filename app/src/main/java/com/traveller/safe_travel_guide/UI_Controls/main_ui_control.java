package com.traveller.safe_travel_guide.UI_Controls;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.traveller.safe_travel_guide.Process_Control;
import com.traveller.safe_travel_guide.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class main_ui_control extends Fragment {
    private ProgressBar progressBar;
    TextInputLayout ms, md;
    TextInputEditText msource, mDestination;
    private static int dd ,mm ,yy;
    private   Process_Control process_control;
    private MaterialButton safetyBTN,mapbtn_click;
    private ImageView imageView;
    private Uri url;
    private TextView safety_txt,today;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.main_ui_control,container,false);
        progressBar = v.findViewById(R.id.progress);
        process_control = new ViewModelProvider(this).get(Process_Control.class);
        msource = (TextInputEditText) v.findViewById(R.id.msource);
        mDestination = (TextInputEditText) v.findViewById(R.id.mdestination);
        safetyBTN  = v.findViewById(R.id.safetybtn);
        ms = (TextInputLayout) v.findViewById(R.id.msourceLayout);
        md = (TextInputLayout) v.findViewById(R.id.mDesLayout);
        mapbtn_click = (MaterialButton) v.findViewById(R.id.getMap);
        progressBar =v.findViewById(R.id.progress);
        imageView = v.findViewById(R.id.safety_img);
        safety_txt  = v.findViewById(R.id.safety_txt);
        today  = v.findViewById(R.id.today);
        mapbtn_click.setEnabled(false);
        today.setText(process_control.todaydate());
        safetyBTN.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {


                if (msource.getText().length() <= 1 && mDestination.getText().length() <= 1) {
                    ms.setError("Source is required");
                    md.setError("Source is required");
                } else {
                    ms.setError("");
                    md.setError("");
                    new ExampleAsyncTask(getActivity()).execute(mDestination.getText().toString());
                }


            }
        });
        mapbtn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if (msource.getText().length() <= 1 && mDestination.getText().length() <= 1) {
                        ms.setError("Source is required");
                        md.setError("Source is required");
                    } else {
                        ms.setError("");
                        md.setError("");
                        Toast.makeText(getActivity(),("Opening Map Navigation "), Toast.LENGTH_LONG).show();
                        MapActivity();

                    }
            }
        });
        return v;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private class ExampleAsyncTask extends AsyncTask<String, Integer, Boolean> {
        private WeakReference<Activity> activityWeakReference;
        private Process_Control process_control = new Process_Control();

        ExampleAsyncTask(Activity activity ) {
            activityWeakReference = new WeakReference<Activity>(activity);
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Activity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

             progressBar.setVisibility(View.VISIBLE);

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "finished";

            try {

                Document f_source = Jsoup.connect("https://www.tvcnews.tv/?s="+strings[0]).timeout(30*1000).get();
                Document s_source = Jsoup.connect("https://www.dw.com/search/?languageCode=ha&item="+strings[0]).timeout(30*1000).get();
                Element divisionBody = s_source.select("div.tw").first();
                Element datePosted = divisionBody.select("span.date").first();
                Element h2 = divisionBody.select("h2").first();
                Element s_div_body = f_source.select("h3:contains(attack),h3:contains(bomb),h3:contains(kidnap),h3:contains( )").first();
                String s_div_body_result = s_div_body.text();
                String date = datePosted.text();
                String header = h2.text();
                process_control.outrs(date);
                System.out.println(header);
                System.out.println(s_div_body_result);
                if(header.contains("bokoHaram")||header.contains("ISWAP")|| header.contains("Kisa")|| header.contains("Bomb")&&  s_div_body_result.length() > 1){
                    if(29 - process_control.curentDay()   <2 && process_control.curentMonth() == 6  && yy ==process_control.currentYear()){
                        return  true;
                    }
                }else {
                    return false;
                }

            } catch (IOException e) {
                System.out.println("Exception Please " +e);
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            Activity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
           progressBar.setProgress(View.VISIBLE);

        }


        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            Activity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            System.out.println(s);
            if(s == true) {
                setImage(process_control.getImageLocationNotsafe());
                safety_txt.setText("Its Not Safe To Travel");

            }
            else if(s == false) {
                setImage(process_control.getImageLocationsafe());
                safety_txt.setText("Its Safe To Travel");

                mapbtn_click.setEnabled(true);
            }
            progressBar.setProgress(0);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    public void setImage(String myImage) {

        int imageResource = getResources().getIdentifier(myImage, null, getActivity().getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imageView.setImageDrawable(res);
    }

    public void MapActivity() {
        try {

                url = Uri.parse("https://www.google.com/maps/dir/" + msource.getText() + "/" + mDestination.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
}
