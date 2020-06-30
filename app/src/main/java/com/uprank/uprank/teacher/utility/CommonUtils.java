package com.uprank.uprank.teacher.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.mrntlu.toastie.Toastie;

public class CommonUtils {

    public static void successToast(Context context, String msg) {

        Toastie.success(context,msg, Toast.LENGTH_SHORT).show();
    }

    public static void errorToast(Context context, String msg) {

        Toastie.error(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void warningToast(Context context) {

        Toastie.warning(context,"Please check Internet Connection !", Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context,String msg) {


        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }


    public static boolean isNetworkAvailable(Activity context) {
        ConnectivityManager objConnectivityManager;
        try {
            objConnectivityManager = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
            final boolean IsWifiAvailable = objConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
            //final boolean IsInternetAvailable = objConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
            boolean IsInternetAvailable = false;
            if (objConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null)
                IsInternetAvailable = objConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
            return IsWifiAvailable || IsInternetAvailable;
        } catch (Exception e) {
            return false;
        }
    }
}
