package com.example.buzzbuddy

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.buzzbuddy.data.MessageDto
object SmsReceiver: BroadcastReceiver() {

    private val subscribers : MutableMap<String, (List<MessageDto>) -> Unit> = mutableMapOf()

    private fun convertInternationalToLocal(internationalNumber: String, countryCode: String): String {
        if (internationalNumber.startsWith("+")) {
            val prefix = "+$countryCode"
            if (internationalNumber.startsWith(prefix)) {
                return internationalNumber.replace(prefix, "0")
            } else {
                println("The number does not match the given country code.")
            }
        } else {
            println("Invalid international number format. Must start with '+'.")
        }
        return internationalNumber
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(p1)
            .map {
                MessageDto(
                    it.displayMessageBody,
                    false,
                    it.displayOriginatingAddress
                )
            }
        val phoneMessageMap = mutableMapOf<String, MutableList<MessageDto>>()

        messages.forEach { message ->
            phoneMessageMap.computeIfAbsent(convertInternationalToLocal(message.sender, "33")) { mutableListOf() } += message
        }
        phoneMessageMap.forEach {
                (address, msg) -> subscribers[address]?.invoke(msg.toList())
        }
        subscribers["ALL"]?.invoke(messages.toList())
    }

    fun subscribe(address : String, action : (List<MessageDto>) -> Unit) {
        subscribers[address] = action
    }

    fun unSubscribe(address : String) {
        subscribers.remove(address)
    }
}