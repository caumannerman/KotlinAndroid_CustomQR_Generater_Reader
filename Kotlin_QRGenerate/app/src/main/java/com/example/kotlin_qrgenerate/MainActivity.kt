package com.example.kotlin_qrgenerate

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import com.example.kotlin_qrgenerate.databinding.ActivityMainBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.google.zxing.qrcode.QRCodeWriter

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    //
    lateinit var resultListener: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // call inside onCreate!!!
        val resultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

            if(result.resultCode == Activity.RESULT_OK) {
                val mString = result.data?.getStringExtra("key")
                binding.tvTest.text = mString
            }

        }

        // QR Code generate
        binding.btnGenerate.setOnClickListener{

            val data = binding.etData.text.toString().trim()

            if(data.isEmpty()){
                Toast.makeText(this, "enter some data", Toast.LENGTH_LONG).show()
            } else {

                val writer = QRCodeWriter()
                try{
                    val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                    for (x in 0 until width){
                        for(y in 0 until height){
                            bmp.setPixel(x, y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    binding.ivQr.setImageBitmap(bmp)
                } catch( e: WriterException){
                    e.printStackTrace()
                }
            }
        }

        binding.btnRead.setOnClickListener {
            val intent = Intent(this@MainActivity, CaptureQRActivity::class.java)
            //start Activity by result using launch  method
            resultListener.launch(intent)
        }

    }


}