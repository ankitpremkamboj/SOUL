package com.soul.app.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.soul.app.R;
import com.soul.app.activity.BaseActivity;
import com.soul.app.activity.MatchActivity;
import com.soul.app.activity.SocketChatActivity;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.swiping.SwipingActivity;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;

import java.util.Random;

public class GcmIntentService extends IntentService {
    private static final String TAG = GcmIntentService.class.getSimpleName();
    public static int NOTIFICATION_ID = 100;
//    private String mPushMessage;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
            NOTIFICATION_ID = new Random(1000).nextInt();
            // The getMessageType() intent parameter must be the intent you received
            // in your BroadcastReceiver.
            String messageType = gcm.getMessageType(intent);

            if (PrefUtils.getSharedPrefBoolean(this, AppConstant.IS_LOGGED_IN)) {
                if (!extras.isEmpty()) { // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
			 * will be extended in the future with new message types, just ignore
			 * any message types you're not interested in, or that you don't
			 * recognize.
			 */
                    if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                        sendNotification("Send error: " + extras.toString(), extras.getString("type"), "", "", "");
                    } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                        sendNotification("Deleted messages on server: " + extras.toString(), extras.getString("type"), "", "", "");
                        // If it's a regular GCM message, do some work.
                    } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                        // This loop represents the service doing some work.
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ignored) {
                        }
                        Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                        // Post notification of received message.
                        String message = extras.getString("message");
                        String pushType = extras.getString("pushType");
                        if (pushType.equals("chat")) {
                            String chatActivityStatus = Utility.getStringSharedPreference(getApplicationContext(), Constants.CHAT_ACTIVITY_AVAILABLE);
                            if (chatActivityStatus.equals("inActive") || chatActivityStatus.equals("")) {
                                String userID = extras.getString("user_id");
                                sendNotification(message, pushType, userID, "", "");
                            } else {

                            }
                            // FOR MATCH
                        } else if (pushType.equals(Constants.Match)) {
                            if (BaseActivity.getContext() != null && ApplicationController.isActivityVisible()) {

                                String userID = extras.getString("user_id");
                                String usersName = extras.getString("usersname");
                                String profilePic = extras.getString("profilePic");

                                Intent intentMatch = new Intent(this, MatchActivity.class);
                                intentMatch.putExtra(Constants.EXTRA_OTHER_ID, userID);
                                intentMatch.putExtra(Constants.EXTRA_USER_PROFILE, profilePic);
                                String splitedName = null;
                                try {
                                    String nameArray[] = usersName.split(" ");
                                    splitedName = nameArray[0];
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                intentMatch.putExtra(Constants.EXTRA_NAME, splitedName);
                                intentMatch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentMatch);
                            } else {
                                String userID = extras.getString("user_id");
                                String usersName = extras.getString("usersname");
                                String profilePic = extras.getString("profilePic");
                                sendNotification(message, pushType, userID, profilePic, usersName);
                            }

                        } else {
                            sendNotification(message, "", "", "", "");
                        }
                        Log.i(TAG, "Received: " + extras.toString());
                    }
                }
                // Release the wake lock provided by the WakefulBroadcastReceiver.
                GcmBroadcastReceiver.completeWakefulIntent(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg, String pushType, String userId, String profilePic, String name) {
        NotificationManager mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = null;
        // for Chat
        if (pushType.equals(Constants.CHAT_TYPE)) {

            //intent = new Intent(this, ChatActivity.class);
            intent = new Intent(this, SocketChatActivity.class);
            intent.putExtra(PrefUtils.OTHER_ID, userId);
            intent.putExtra(Constants.EXTRA_PUSH_TYPE, Constants.CHAT_TYPE);

        } else if (pushType.equals(Constants.Match)) {   // For Match

            intent = new Intent(this, MatchActivity.class);
            intent.putExtra(Constants.EXTRA_OTHER_ID, userId);
            intent.putExtra(Constants.EXTRA_USER_PROFILE, profilePic);
            String splitedName = null;
            try {
                String nameArray[] = name.split(" ");
                splitedName = nameArray[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
            intent.putExtra(Constants.EXTRA_NAME, splitedName);

        } else {
            intent = new Intent(this, SwipingActivity.class);
        }

        PendingIntent contentIntent = getPendingIntentWithStackBuilder(intent, String.valueOf(NOTIFICATION_ID));

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_soul_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_soul_logo)).setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name)).setContentText(msg);

        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


    /**
     * this method is used when you need to open a activity when user press back from an activity opened through push notification
     *
     * @param intent
     * @param notificationId unique id to use as unique request code.
     * @return contentIntent PendingIntent
     */
    private PendingIntent getPendingIntentWithStackBuilder(Intent intent, String notificationId) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(SwipingActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(new Intent(this, SwipingActivity.class));
        stackBuilder.addNextIntent(intent);
        return stackBuilder.getPendingIntent(Integer.parseInt(notificationId), PendingIntent.FLAG_UPDATE_CURRENT);
    }


}