package com.exclusive.original.impression_tracker.Helper;

public class Utils {

    public static boolean impressionChecker (double lastIdleTimeStamp, double currentIdleTimeStamp){
        double timeDifference = currentIdleTimeStamp - lastIdleTimeStamp;
        return  timeDifference >= Constant.MIN_TIME_TO_IMPRESSION_IN_MILLIS;
    }

}
