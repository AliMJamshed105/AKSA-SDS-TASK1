package com.example.task1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static smslistener mListener;
    boolean numbercheck=true;
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBRoadcastReceiver";
    String msg;
    String phoneNo = "";
    String mynum= "+923078126327";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(TAG, "Intent Recieved:" +intent.getAction());
        if(intent.getAction()==SMS_RECEIVED)
        {
            Bundle dataBundle = intent.getExtras();
            if (dataBundle!=null)
            {
                Object[] mypdu = (Object[])dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];

                for (int i = 0; i<mypdu.length;i++)
                {
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
                    {
                        String format = dataBundle.getString("format");
                        message[i]= SmsMessage.createFromPdu((byte[])mypdu[i], format);
                    }
                    else
                    {
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i]);
                    }
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getDisplayOriginatingAddress();
                }

                Toast.makeText(context, "message recieved here" + msg +"\nNumber: " +phoneNo, Toast.LENGTH_LONG).show();
                numbercheck=phoneNo.equals(mynum);
                if(numbercheck==true) {
                    mListener.messageReceived(msg);
                }
                else
                {
                }
            }
        }
    }
    public static void bindListener(smslistener listener) {
        mListener = listener;
    }
}
