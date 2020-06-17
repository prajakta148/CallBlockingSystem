package com.example.callblockingsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.android.internal.telephony.ITelephony;



public class IncomingCallReceiver extends BroadcastReceiver {
    public static int cntt=0;


    @Override
    public void onReceive(Context context, Intent intent) {

        ITelephony telephonyService;
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))
            {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    Method m = tm.getClass().getDeclaredMethod("getITelephony");

                    m.setAccessible(true);
                    telephonyService = (ITelephony) m.invoke(tm);

                    if ((number != null)) {
                        if(cntt>=3)
                        {

                        }
                        else
                        {
                            telephonyService.endCall();
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, null, "[DRIVE SAFE] I am Driving. I will contact you when possible.", null, null);

                        }
                        cntt++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Toast.makeText(context, "Ring " + number, Toast.LENGTH_SHORT).show();

            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                // Toast.makeText(context, "Answered " + number, Toast.LENGTH_SHORT).show();
            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){



                //Toast.makeText(context, "Idle "+ number, Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}