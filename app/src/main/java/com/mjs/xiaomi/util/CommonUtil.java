package com.mjs.xiaomi.util;

import android.hardware.Camera;

/**
 * Created by dafei on 2017/9/8.
 */
public class CommonUtil {
    /***
     *
     * @return
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            if (mCamera != null)
                mCamera.release();
            mCamera = null;
        }
        return canUse;
    }
}
