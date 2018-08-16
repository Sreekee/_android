package com.spanenterprises.spanenquirer.Utility;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class UtilsClass {

    public static boolean isValidString(String value) {

        if (value != null && value.trim().length() > 0 && !value.trim().equalsIgnoreCase("null")) {

            return true;

        } else {

            return false;
        }
    }

    public final static boolean isValidEmail(String email) {

        if (email == null)

            return false;
        else

            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static String validateString(String value) {

        if (isValidString(value)) {

            return value;

        } else {

            return "";
        }
    }

    // dd-mm-yyyyTHH;MM:ss:sss  UTC time format

    public static String getUTCDateTime(String date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String formattedDate = dateFormat.format(date);

        return formattedDate;
    }
}