package com.veelit.plugins.ussdmanager;

import android.util.Log;

public class UssdManager {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
