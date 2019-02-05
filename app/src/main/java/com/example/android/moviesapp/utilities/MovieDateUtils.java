package com.example.android.moviesapp.utilities;

public final class MovieDateUtils {
    private static String[] shortMonthArray = {
            "Placeholder",
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sept",
            "Oct",
            "Nov",
            "Dec"
    };

    public static String makePrettyDate(String date){
        //Cut date in parts while separator is -
        String[] dateParts = date.split("-");

        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];

        //Make moth an int and get short month name from array
        int montInt = Integer.valueOf(month);
        String monthName = shortMonthArray[montInt];

        //Make String for new format
        String newDate = monthName + " " + day + ", " + year;

        return newDate;
    }
}
