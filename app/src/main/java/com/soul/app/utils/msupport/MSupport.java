package com.soul.app.utils.msupport;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import com.soul.app.utils.msupport.MSupportConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle M support logic
 * Created by Rahul Gupta on 9/22/15.
 */
public class MSupport {

    public static boolean isMSupportDevice(Context ctx) {
        /**
         * @return true in case of M Device,
         * false in case of below M devices
         */
        return Build.VERSION.SDK_INT >= MSupportConstants.SDK_VERSION;
    }

    /**
     * MEthod to check permissions set
     *
     * @param mActivity     Calling activity context
     * @param fragment      Calling fragment instance
     * @param permissionSet Permission set to check
     * @param requestCode   request code
     * @return true in case of permission is granted or pre marshmallow
     * false in case of permission is not granted
     * in case of false we have to request that permission
     */
    @TargetApi(23)
    public static boolean checkPermission(Activity mActivity, Fragment fragment, String[] permissionSet,
                                          int requestCode) {

        if (MSupport.isMSupportDevice(mActivity)) {

            List<String> permissions = new ArrayList<>();
            for (String aPermissionSet : permissionSet) {
                int hasPermission = mActivity.checkSelfPermission(aPermissionSet);
                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(aPermissionSet);

                }
            }
            if (!permissions.isEmpty()) {
                if (fragment != null)
                    fragment.requestPermissions(permissions.toArray(new String[permissions.size()]), requestCode);
                else
                    mActivity.requestPermissions(permissions.toArray(new String[permissions.size()]), requestCode);
                return false;
            } else
                return true;

        } else
            return true;
    }

}

