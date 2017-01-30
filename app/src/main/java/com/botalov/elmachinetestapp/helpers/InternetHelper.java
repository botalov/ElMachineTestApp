package com.botalov.elmachinetestapp.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetHelper {

    /**
     * Проверяет доступ к сети
     * @param context Текущий контекст
     * @return true, если доступ к сети есть
     */
    public static boolean IsNetworkAvailable(Context context) {

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        catch (Exception exc){
            return false;
        }
    }

}
