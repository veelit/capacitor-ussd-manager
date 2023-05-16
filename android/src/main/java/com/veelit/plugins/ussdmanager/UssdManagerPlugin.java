package com.veelit.plugins.ussdmanager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

@CapacitorPlugin(
    name = "UssdManager",
    permissions = { @Permission(strings = { Manifest.permission.CALL_PHONE }, alias = UssdManagerPlugin.USSD_CALL_PHONE) }
)
public class UssdManagerPlugin extends Plugin {

    static final String USSD_CALL_PHONE = "ussdCallPhone";
    private UssdManager implementation = new UssdManager();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void requestUssdPermission(PluginCall call) {
        boolean hasUssdPerms = getPermissionState(USSD_CALL_PHONE) == PermissionState.GRANTED;

        if (hasUssdPerms == false) {
            String[] aliases = new String[] { USSD_CALL_PHONE };
            requestPermissionForAliases(aliases, call, "ussdPermissionsCallback");
        } else {
            call.resolve();
        }
    }

    @PermissionCallback
    private void ussdPermissionsCallback(PluginCall call) {
        boolean hasUssdPerms = getPermissionState(USSD_CALL_PHONE) == PermissionState.GRANTED;
        if (hasUssdPerms) {
            call.resolve();
        } else {
            call.reject("USSD Permission not granted.");
        }
    }

    @PluginMethod
    public void callUssd(PluginCall call) {
        String ussdCode = call.getString("value");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);

            // Callback for USSD response
            TelephonyManager.UssdResponseCallback ussdResponseCallback = new TelephonyManager.UssdResponseCallback() {
                @Override
                public void onReceiveUssdResponse(TelephonyManager telephonyManager, String request, CharSequence response) {
                    super.onReceiveUssdResponse(telephonyManager, request, response);

                    JSObject ret = new JSObject();
                    ret.put("result", response);
                    ret.put("code", "" + ussdCode + "");
                    call.resolve(ret);
                }

                @Override
                public void onReceiveUssdResponseFailed(TelephonyManager telephonyManager, String request, int failureCode) {
                    super.onReceiveUssdResponseFailed(telephonyManager, request, failureCode);

                    JSObject ret = new JSObject();
                    ret.put("result", "USSD Request: " + request + " failed with code: " + failureCode);
                    ret.put("code", "ERROR_CODE: " + ussdCode + "");
                    call.reject(ret.toString());
                }
            };

            try {
                // Try to send USSD request. This might not work on all devices due to restrictions in Android's public API.
                telephonyManager.sendUssdRequest(ussdCode, ussdResponseCallback, new Handler(Looper.getMainLooper()));
            } catch (SecurityException e) {
                JSObject ret = new JSObject();
                ret.put("result", "Permission error: " + e.getMessage());
                ret.put("code", "ERROR_CODE: " + ussdCode + "");
                call.reject(ret.toString());
            } catch (Exception e) {
                JSObject ret = new JSObject();
                ret.put("result", "Error: " + e.getMessage());
                ret.put("code", "ERROR_CODE: " + ussdCode + "");
                call.reject(ret.toString());
            }
        }
    }
}
