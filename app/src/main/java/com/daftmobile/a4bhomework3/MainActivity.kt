package com.daftmobile.a4bhomework3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var EMAIL_RETRIEVER: EmailRetriever
        const val REQUEST_SELECT_CONTACT = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EMAIL_RETRIEVER = EmailRetriever.Impl(applicationContext)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode ==  REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            val contactUri: Uri = data!!.data
            val emailAddress = arrayOf(EMAIL_RETRIEVER.retrieve(contactUri))
           composeEmail(emailAddress)
        }
    }

    fun selectContact(v: View){

        val intent = Intent(Intent.ACTION_PICK).apply {
            type = ContactsContract.CommonDataKinds.Email.CONTENT_TYPE
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent,  REQUEST_SELECT_CONTACT)
        }
    }

    private fun composeEmail(addresses: Array<String?>, subject : String = "Wiadomość z pracy domowej") {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
                    }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }


}
