package me.yihtseu.jishi

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.HmsMessaging
import com.huawei.hms.push.RemoteMessage

class PushService : HmsMessageService() {

    override fun onMessageReceived(message: RemoteMessage?) {
        message ?: return
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle(message.notification.title)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentText(message.notification.body)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }.build()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(System.currentTimeMillis().toInt(), notification)
    }

    override fun onCreate() {
        super.onCreate()
        HmsMessaging.getInstance(this).apply {
            turnOffPush()
            isAutoInitEnabled = true
        }
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID = "bc309c75-0e32-4b73-9630-a9e09f6371bf"
        private const val CHANNEL_NAME = "推送通知通道"
    }
}