package com.example.buzzbuddy

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.provider.Telephony.TextBasedSmsColumns.MESSAGE_TYPE_INBOX
import android.telephony.SmsManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buzzbuddy.adapter.MessageAdapter
import com.example.buzzbuddy.data.MessageDto
import com.example.buzzbuddy.db.BuzzBudyDatabase


class ConversationActivity : AppCompatActivity() {
    lateinit var db: BuzzBudyDatabase
    private var firstName = String()
    private var lastName = String()
    private var phone = String()
    private lateinit var MessageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<MessageDto>
    private lateinit var smsManager: SmsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)
        db = BuzzBudyDatabase(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        val color = ColorDrawable(db.getHeaderColor())
        supportActionBar!!.setBackgroundDrawable(color)

        messageBox = findViewById(R.id.messageBox)
        MessageRecyclerView = findViewById(R.id.charRecyclerView)
        sendButton = findViewById(R.id.sendButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        smsManager = getSmsManager()

        firstName = intent.getStringExtra("first_name").toString()
        lastName = intent.getStringExtra("last_name").toString()
        phone = intent.getStringExtra("phone").toString()

        supportActionBar!!.title = phone

        MessageRecyclerView.layoutManager = LinearLayoutManager(this)
        MessageRecyclerView.adapter = messageAdapter
        SmsReceiver.subscribe(phone, ::onNewMessages)
        initMessages()
        sendButton.setOnClickListener { onSendBtnClick() }
    }

    private fun onNewMessages(receivedMessages : List<MessageDto>) {
        messageList.addAll(receivedMessages)
        MessageRecyclerView.adapter?.notifyItemRangeInserted(
            messageList.size - receivedMessages.size, // Start position
            receivedMessages.size
        )
        MessageRecyclerView.scrollToPosition(messageList.size - 1)
    }

    override fun onDestroy() {
        SmsReceiver.unSubscribe(phone)
        super.onDestroy()
    }

    private fun getSmsManager() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            applicationContext.getSystemService(SmsManager::class.java)
        } else {
            SmsManager.getDefault()
        }

    private fun initMessages() {
        Toast.makeText(this, "$phone VS ${db.getOwnerPhone()}", Toast.LENGTH_LONG).show()
        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            "${Telephony.Sms.ADDRESS} = ?",
            arrayOf(phone),
            Telephony.Sms.DATE
        )
        cursor?.use {
            while(cursor.moveToNext()) {
                val text = it.getString(it.getColumnIndexOrThrow(Telephony.Sms.BODY))
                val typeRaw = it.getInt(it.getColumnIndexOrThrow(Telephony.Sms.TYPE))
                if (typeRaw == Telephony.Sms.MESSAGE_TYPE_INBOX)
                {
                    messageList.add(MessageDto(text, false, phone))
                }
                else
                {
                    messageList.add(MessageDto(text, true, db.getOwnerPhone()))
                    if (phone == db.getOwnerPhone())
                        messageList.add(MessageDto(text, false, phone))
                }
            }
        }
        MessageRecyclerView.scrollToPosition(messageList.size - 1)
    }

    private fun onSendBtnClick() {
        val message = messageBox.text.toString()
        val messageObject = MessageDto(message, true, db.getOwnerPhone())
        if(message.isNotBlank()) {
            smsManager.sendTextMessage(phone, null, message, null, null)
            messageBox.setText("")
            messageList.add(messageObject)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.conversation_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.contact_edit -> {
                Intent(this, EditContactActivity::class.java).also {
                    it.putExtra("first_name", firstName)
                    it.putExtra("last_name", lastName)
                    it.putExtra("phone", phone)
                    startActivity(it)
                }
            }
            R.id.contact_delete -> {
                val ret = db.deleteUser(phone)
                Intent(this, HomeActivity::class.java).also {
                    startActivity(it)
                }
            }
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed();
                return true;
            }
        }
        return true
    }

}