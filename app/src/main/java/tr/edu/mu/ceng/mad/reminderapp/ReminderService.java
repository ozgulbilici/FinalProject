package tr.edu.mu.ceng.mad.reminderapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Provider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.core.app.NotificationCompat;

public class ReminderService extends Service {

    private final static String TAG = "countdown";

    public static final String COUNTDOWN_REMINDER = "com.edumucengmad.reminderapp";
    Intent bi = new Intent(COUNTDOWN_REMINDER);
    CountDownTimer cdt = null;

    long timeRemaining;

    String CHANNEL_ID;

    int NOTIFICATION_ID = 235;
    NotificationManager notificationManager;

    DatabaseHelper databaseHelper;
    List<Plan> list;
    List<TimeRemaining> timeRemainingList;

    String sdate,stime, sname, snote;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        databaseHelper = new DatabaseHelper(getApplicationContext());
        list = new ArrayList<>();
        timeRemainingList = new ArrayList<>();
    }
    @Override
    public void onDestroy() {

        //cdt.cancel();
        Log.i(TAG, "Timer cancelled");
        startService(new Intent(getApplicationContext(), ReminderService.class));

        super.onDestroy();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Cursor cursor = databaseHelper.VeriGetir();

        if (cursor.getCount() == 0){



        } else {

            cursor.moveToLast();

            int id = cursor.getColumnIndex("id");
            int sid = cursor.getInt(id);

            int date = cursor.getColumnIndex("date");
            sdate = cursor.getString(date);

            int time = cursor.getColumnIndex("time");
            stime = cursor.getString(time);

            int name = cursor.getColumnIndex("remindername");
            sname = cursor.getString(name);

            int note = cursor.getColumnIndex("remindernote");
            snote = cursor.getString(note);


            // GEÇMİŞ Mİ YOKSA HALA VAR MI DİYE ZAMANLARI KIYASLA
            String simdikiDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            String simdikiTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

            String finishDate = sdate;
            String finishZaman = stime;

            String toyBornTime = sdate + " " + stime;

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm");

            String simdikizamans = simdikiDate + " " + simdikiTime;


            try {

                Date oldDate = dateFormat.parse(toyBornTime);
                Date simdiki = dateFormat.parse(simdikizamans);

                System.out.println(oldDate);

                Date currentDate = new Date();


                long diff = oldDate.getTime() - simdiki.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;
                if (diff > 0) {

                    // list.add(new Plan(sid,sdate,stime,svucudunilgilikismi,segzersiz));
                    timeRemainingList.add(new TimeRemaining(String.valueOf(diff), String.valueOf(sid)));
                    Log.d("yazdir", toyBornTime);
                }

                // Log.e("toyBornTime", "" + toyBornTime);

            } catch (ParseException e) {

                e.printStackTrace();
            }

            while (cursor.moveToPrevious()) {

                id = cursor.getColumnIndex("id");
                sid = cursor.getInt(id);

                date = cursor.getColumnIndex("date");
                sdate = cursor.getString(date);

                time = cursor.getColumnIndex("time");
                stime = cursor.getString(time);

                name = cursor.getColumnIndex("remindername");
                sname = cursor.getString(name);

                note = cursor.getColumnIndex("remindernote");
                snote = cursor.getString(note);

                // GEÇMİŞ Mİ YOKSA HALA VAR MI DİYE ZAMANLARI KIYASLA
                // HALA VARSA LİSTEYE EKLE

                // GEÇMİŞ Mİ YOKSA HALA VAR MI DİYE ZAMANLARI KIYASLA
                simdikiDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                simdikiTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                finishDate = sdate;
                finishZaman = stime;

                toyBornTime = sdate + " " + stime;

                dateFormat = new SimpleDateFormat(
                        "dd/MM/yyyy HH:mm");

                simdikizamans = simdikiDate + " " + simdikiTime;

                try {

                    Date oldDate = dateFormat.parse(toyBornTime);
                    Date simdiki = dateFormat.parse(simdikizamans);

                    System.out.println(oldDate);

                    Date currentDate = new Date();

                    long diff = oldDate.getTime() - simdiki.getTime();
                    long seconds = diff / 1000;
                    long minutes = seconds / 60;
                    long hours = minutes / 60;
                    long days = hours / 24;

                    if (diff > 0) {

                        Log.d("yazdir", toyBornTime);
                        timeRemainingList.add(new TimeRemaining(String.valueOf(diff), String.valueOf(sid)));

                    }

                    // Log.e("toyBornTime", "" + toyBornTime);

                } catch (ParseException e) {

                    e.printStackTrace();
                }


            }

            // LİSTEYİ EN YKAINDAN SONRAYA DOĞRU SIRALA

            Collections.sort(timeRemainingList, new CustomComprator());

            if (timeRemainingList.size()!=0){
                Log.d("kalankalan", timeRemainingList.get(0).getDiff());

                Cursor cursorx = databaseHelper.VeriGetirByID(timeRemainingList.get(0).getSatirid());

                if (cursorx.getCount() == 0) {

                } else {

                    cursorx.moveToLast();

                    id = cursorx.getColumnIndex("id");
                    sid = cursorx.getInt(id);

                    date = cursorx.getColumnIndex("date");
                    sdate = cursorx.getString(date);

                    time = cursorx.getColumnIndex("time");
                    stime = cursorx.getString(time);

                    name = cursorx.getColumnIndex("remindername");
                    sname = cursorx.getString(name);

                    note = cursorx.getColumnIndex("remindernote");
                    snote = cursorx.getString(note);


                    // EN YAKIN OLAN İÇİN GERİ SAYIM BAŞLAT
                    // GERİ SAYIM BİTİNCE BİLDİRİMİ VERİTABANINA KAYDET
                    // TÜM SERVİCEI EN BAŞTAN AÇ

                    //timeRemaining = Long.parseLong(intent.getExtras().getString("bisiler"));
                    timeRemaining = Long.parseLong(timeRemainingList.get(0).getDiff());

                    Log.d("timeRemaining",String.valueOf(timeRemaining));

                    Log.i(TAG, "Starting timer...");

                    cdt = new CountDownTimer(timeRemaining, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                            //     Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
                            bi.putExtra("countdown", millisUntilFinished);
                            sendBroadcast(bi);

                            Log.d("gercektimeRemaining", String.valueOf(millisUntilFinished));

                        }

                        @Override
                        public void onFinish() {
                            Log.i(TAG, "Timer finished");

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("Reminder")
                                    .setContentText("It's time for " + sname);

                            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                            stackBuilder.addParentStack(MainActivity.class);
                            stackBuilder.addNextIntent(resultIntent);
                            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(resultPendingIntent);

                            notificationManager.notify(NOTIFICATION_ID, builder.build());


                            stopService(new Intent(getApplicationContext(), ReminderService.class));


                        }
                    };
                    cdt.start();
                }

            } else {
                stopService(new Intent(getApplicationContext(), ReminderService.class));
            }

        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    private class CustomComprator implements Comparator<TimeRemaining> {

        @Override
        public int compare(TimeRemaining o1, TimeRemaining o2){
            return Long.valueOf(o1.getDiff()).compareTo(Long.valueOf(o2.getDiff()));

        }

    }

}