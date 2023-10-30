package me.yihtseu.jishi

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import me.yihtseu.jishi.utils.push.Pusher

class PushService: Pusher, Service() {
    override fun subscribe(topic: String) {
    }

    override fun onBind(intent: Intent?): IBinder {
        return Binder()
    }
}