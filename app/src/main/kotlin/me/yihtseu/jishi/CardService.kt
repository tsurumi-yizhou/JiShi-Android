package me.yihtseu.jishi

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log
import me.yihtseu.jishi.model.jishi.NfcState
import me.yihtseu.jishi.utils.apdu.ApduResponse

class CardService : HostApduService() {
    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        try {
            val data = commandApdu!!
            Log.d("nfc_card_command_apdu", data.contentToString())
            val card = NfcState.read()
            return ApduResponse(61, card.size.toByte(), card).serialize()
        } catch (e: Exception) {
            return ApduResponse(62, 83).serialize()
        }
    }

    override fun onDeactivated(reason: Int) {
        Log.d("nfc_card_reason", reason.toString())
    }
}