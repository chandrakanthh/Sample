package com.example.mysample.view.ui.payments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mysample.R
import com.phonepe.intent.sdk.api.PhonePe
import com.phonepe.intent.sdk.api.PhonePeInitException


class PaymentActivity : AppCompatActivity() {
    private val TAG = "PaymentActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        PhonePe.init(this)
        try {
            PhonePe.isUPIAccountRegistered { shouldShow ->
                Log.d(TAG, "$shouldShow")
                Toast.makeText(this,"$shouldShow",Toast.LENGTH_SHORT).show()
            }
        } catch (e: PhonePeInitException) {
            Log.d(TAG, "make sure you call PhonePe init before calling this")
        }
    }
}