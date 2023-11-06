package me.yihtseu.jishi

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging
import me.yihtseu.jishi.utils.push.Pusher

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class PushService : Pusher, FirebaseMessagingService() {
    override fun subscribe(topic: String) {
        Firebase.messaging.subscribeToTopic(topic).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "subscribe to $topic", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle(message.notification?.title)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentText(message.notification?.body)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }.build()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

    override fun onCreate() {
        super.onCreate()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(CHANNEL_ID, "JiShi_Channel", NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
    }

    companion object {
        private const val NOTIFICATION_ID = 114514
        private const val CHANNEL_ID = "JiShiPushChannel"
    }
}