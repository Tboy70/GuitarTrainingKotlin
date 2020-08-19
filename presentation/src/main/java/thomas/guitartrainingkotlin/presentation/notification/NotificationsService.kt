package thomas.guitartrainingkotlin.presentation.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.UserPanelActivity

private const val NOTIFICATION_ID = 7
private const val NOTIFICATION_TAG = "FIREBASEOC"
private const val NOTIFICATION_CHANNEL_NAME = "Message provenant de Firebase"

class NotificationsService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e("TEST", "TODO")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let { notification ->
            notification.body?.let {
                sendVisualNotification(it)
            }
        }
    }

    private fun sendVisualNotification(messageBody: String) {
        val intent = Intent(this, UserPanelActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.setBigContentTitle(getString(R.string.app_name))
        inboxStyle.addLine(messageBody)

        val channelId = getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_song)
            .setContentTitle(getString(R.string.app_name))
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            .setStyle(inboxStyle)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = NOTIFICATION_CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build())
    }
}