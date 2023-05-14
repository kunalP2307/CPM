package com.example.cpm.utils;
import android.util.Log;

import com.example.cpm.model.Activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ActivityUtils {

    public static int getDelay(Activity activity){
        String delay = "";

        String startDate = activity.getStartDate();
        String finishDate = activity.getFinishDate();
        String dayDuration = activity.getDuration();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        long dayDiff = 0;
        try {
            Date startD = simpleDateFormat.parse(startDate);
            Date finishD = simpleDateFormat.parse(finishDate);
            long timeDiff = finishD.getTime() - startD.getTime();
            dayDiff = TimeUnit.MILLISECONDS.toDays(timeDiff) % 365;
            dayDiff += 1;

        }catch(ParseException e) {

        }

        long delayInt = Integer.parseInt(dayDuration) - dayDiff;
        //System.out.println(delayInt);

        return (int) delayInt;
    }


    public static void main(String args[]){
        ActivityUtils activityUtils = new ActivityUtils();
        activityUtils.getDelay(new Activity(
                "id",
                "PDN Design,Estimate,DTP and Tender",
                "15",
                "07-09-22",
                "27-09-22"
        ));
    }
}
