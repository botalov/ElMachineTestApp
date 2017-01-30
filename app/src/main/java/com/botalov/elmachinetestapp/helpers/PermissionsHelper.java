package com.botalov.elmachinetestapp.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionsHelper {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permissionINTERNET = ActivityCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);
        int permissionACCESS_NETWORK_STATE = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE);

        if (permissionACCESS_NETWORK_STATE != PackageManager.PERMISSION_GRANTED || permissionINTERNET != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
