package com.traveller.safe_travel_guide;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Process_Control extends ViewModel {
   String msource;
   String mDestination;
   String title;
   private static int dd,mm,yy;
   LocalDate today1 = LocalDate.now();

    String ImageLocationsafe = "@drawable/safe";
    String ImageLocationNotsafe = "@drawable/notsafe";

    public String getImageLocationsafe() {
        return ImageLocationsafe;
    }

    public String getImageLocationNotsafe() {
        return ImageLocationNotsafe;
    }

    public Process_Control(){

        }

    public void setMsource(String msource) {
        this.msource = msource;
    }

    public String getmDestination() {
        return mDestination;
    }

    public void setmDestination(String mDestination) {
        this.mDestination = mDestination;
    }

    public String getMsource() {
        return msource;
    }

    public String todaydate(){
        return ("Today:: " + today1.getDayOfWeek() + "/" + today1.getMonth() + "/" + today1.getYear());
    }

    public int getDd() {
        return dd;
    }

    public int getMm() {
        return mm;
    }

    public int getYy() {
        return yy;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int curentDay(){
        int day = today1.getDayOfMonth();
        return  day;
    }
    public int curentMonth(){
        int c_month = today1.getMonthValue();
        return  c_month;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int currentYear(){
        int year = today1.getYear();
        return  year;
    }

    public  void outrs(String result) {


        result = result.substring(0, 10);
        result = result.replaceAll("\\D+", "");

        String Day = result.substring(0, 2);
        dd = Integer.parseInt(Day);

        String mon = result.substring(2, 4);
        mm = Integer.parseInt(mon);

        String Year = result.substring(4, 8);
        yy = Integer.parseInt(Year);



    }
}
