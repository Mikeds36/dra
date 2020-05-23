package xyz.bcfriends.dra;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import java.util.Objects;

public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
//            SharedPreferences prefs = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            AlarmScheduler alarmScheduler = new AlarmScheduler(context, prefs);
            alarmScheduler.scheduleNotification();
        }
    }
}
