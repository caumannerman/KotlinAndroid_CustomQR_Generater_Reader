package com.example.kotlin_qrgenerate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_qrgenerate.databinding.ActivityMyBarcodeReaderBinding
import com.journeyapps.barcodescanner.CaptureManager

class MyBarcodeReaderActivity : AppCompatActivity() {
    val binding by lazy {ActivityMyBarcodeReaderBinding.inflate(layoutInflater)}
    private lateinit var capture: CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        capture = CaptureManager(this, binding.barcodeScanner)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode()
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}