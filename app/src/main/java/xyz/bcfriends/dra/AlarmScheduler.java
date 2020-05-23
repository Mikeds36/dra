package xyz.bcfriends.dra;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AlarmScheduler {
    final Context mContext;
    final SharedPreferences prefs;

    public AlarmScheduler(Context context, SharedPreferences prefs) {
        this.mContext = context;
        this.prefs = prefs;
    }

    public Calendar getNextNotifyTime() {
        Calendar cal = Calendar.getInstance();

        int hourOfDay = prefs.getInt("hourOfDay", -1);
        int minute = prefs.getInt("minute", -1);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);

        if (cal.before(Calendar.getInstance())) {
            cal.add(Calendar.DATE, 1);
        }

        return cal;
    }

    public void scheduleNotification() {
        PackageManager pm = mContext.getPackageManager();
        ComponentName receiver = new ComponentName(mContext, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Objects.requireNonNull(alarmManager);

        if (!prefs.contains("hourOfDay") || !prefs.contains("minute")) {
            Toast.makeText(mContext, "시간이 설정되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar cal = getNextNotifyTime();

        alarmManager.cancel(pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
//        }
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
//        }
//        else {
//            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
//        }

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

//        String nextNotifyDate = new SimpleDateFormat("yyyy년 MM월 dd일 a hh시 mm분", Locale.getDefault()).format(cal.getTime());
//        Toast.makeText(mContext, nextNotifyDate + "으로 알림이 설정되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public void cancelNotification() {
        Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Objects.requireNonNull(alarmManager);

        alarmManager.cancel(pendingIntent);
    }
}
