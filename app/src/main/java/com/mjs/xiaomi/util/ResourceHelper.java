package com.mjs.xiaomi.util;

import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;

import com.mjs.xiaomi.AppProfile;

/**
 * Created by dafei on 2017/9/8.
 */
public class ResourceHelper {
    public static final String RES = "res";

    public static String getString(@StringRes int stringId) {
        return AppProfile.getContext().getResources().getString(stringId);
    }

    public static String[] getStringArray(@ArrayRes int arrayId) {
        return AppProfile.getContext().getResources().getStringArray(arrayId);
    }

    public static String stringFormat(@StringRes int stringId, Object... objects) {
        String formatString = getString(stringId);
        return String.format(formatString, objects);
    }

    public static int getColor(@ColorRes int colorId) {
        return AppProfile.getContext().getResources().getColor(colorId);
    }

    public static float getDimen(@DimenRes int dimenId) {
        return AppProfile.getContext().getResources().getDimension(dimenId);
    }
}
