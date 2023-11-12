package me.yihtseu.jishi.model.jishi

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.yihtseu.jishi.MainActivity
import me.yihtseu.jishi.utils.apdu.bytesToHexString

object NfcState {
    private lateinit var activity: MainActivity
    private lateinit var adapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent

    fun initialize(context: Context) {
        activity = context as MainActivity
        adapter = NfcAdapter.getDefaultAdapter(context)
        pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE
        )
    }

    fun enable() {
        adapter.enableForegroundDispatch(activity, pendingIntent, null, null)
    }

    fun disable() {
        adapter.disableForegroundDispatch(activity)
    }

    fun write(tag: Tag) = runBlocking {
        val mfc = MifareClassic.get(tag)!!
        mfc.connect()
        if (mfc.authenticateSectorWithKeyA(0, MifareClassic.KEY_DEFAULT)) {
            Log.d("nfc", "auth")
            val data = mfc.readBlock(mfc.sectorToBlock(0))
            DataStore.setString("nfc_card_id", bytesToHexString(tag.id)!!)
            DataStore.setString("nfc_card_data", data!!.toString())
            Log.d("nfc", bytesToHexString(data)!!)
        } else {
            throw Exception("read tag failed")
        }
    }

    fun read(): ByteArray = runBlocking {
        return@runBlocking DataStore.getString("nfc_card_data")!!.first()!!.toByteArray()
    }
}