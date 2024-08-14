package com.example.viewpager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Tôi trong Receiver", "Báo thức đã được kích hoạt!");
        String chuoi_string = intent.getExtras().getString("extra");
        Log.e("Truyen key: ",chuoi_string );
        Intent myIntent = new Intent(context,Music.class);
        myIntent.putExtra("extra",chuoi_string);
        context.startService(myIntent);

    }
}
