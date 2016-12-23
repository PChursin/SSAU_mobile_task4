package ru.ssau.mobile.ssau_mobile_task4.location;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Pavel on 22.12.2016.
 */

public final class PermissionUtils {

    private PermissionUtils() {
    }

    public static boolean checkPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!checkPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isDeviceInfoGranted(Context context) {
        return checkPermission(context, Manifest.permission.READ_PHONE_STATE);
    }

    public static void requestPermissions(Object o, int permissionId, String... permissions) {
        if (o instanceof Fragment) {
            FragmentCompat.requestPermissions((Fragment) o, permissions, permissionId);
        } else if (o instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) o, permissions, permissionId);
        }
    }
}