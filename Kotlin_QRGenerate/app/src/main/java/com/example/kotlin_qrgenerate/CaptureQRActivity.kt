package com.example.kotlin_qrgenerate

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kotlin_qrgenerate.databinding.ActivityCaptureQractivityBinding
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult


class CaptureQRActivity : AppCompatActivity() {

    val binding by lazy { ActivityCaptureQractivityBinding .inflate(layoutInflater)}
    //resultActivity Listener for Scan Barcode ( QR )


    //일반 스캐너
    var options: ScanOptions = ScanOptions()
    //custom scanner
    var customOptions: ScanOptions = ScanOptions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //setting for scan
        setQROptions(options)
        //setting for custom scan options
        setCustomQROptions(customOptions)

        val barcodeLauncherOri = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->

            //QR code Contents(내용) ...
            if (result.contents == null) {
                Toast.makeText(this@CaptureQRActivity, "Cancelled", Toast.LENGTH_LONG).show()
                binding.tvTest.text = "null"
            } else {
                Toast.makeText(this@CaptureQRActivity, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                binding.tvTest.text = "${result.contents}"
            }
            //QR 코드를 찍은 순간의 사진
            if(result.barcodeImagePath != null) {
                val bitmap = BitmapFactory.decodeFile(result.barcodeImagePath)
                //image View에 찍어온 사진 저장
                binding.ivQrcode.setImageBitmap(bitmap)
            }

        }



        //Listener for ActivityResult
        val barcodeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

           if(result.data != null) {
               Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show()
           } else {
               Toast.makeText(this, "sdfdsfsed", Toast.LENGTH_LONG).show()
           }
           // if( result.GetContent() == null)
//            if(result !=null) {
//                if(result.data == null) {
//                    // qr코드에 주소가 없거나, 뒤로가기 클릭 시
//                    Toast.makeText(this.parent, "QR코드 인증이 취소되었습니다.",Toast.LENGTH_SHORT).show()
//                    Log.d("mymy", "${result.data}")
//                    finish()
//                } else {
//                    //qr코드에 주소가 있을때 -> 주소에 관한 Toast 띄우는 함수 호출
//                    Toast.makeText(this.parent, result.data.toString(),Toast.LENGTH_SHORT).show()
//                    Log.d("mymy", "${result.data}")
//                }
//            } else {
//                Toast.makeText(this.parent, "deprecate ",Toast.LENGTH_SHORT).show()
//                Log.d("mymy", "${result?.data}")
//            }


//            if(result.resultCode == Activity.RESULT_OK) {
//                Log.d("mymy", "success")
//                //val mString = result.data?.getStringExtra("key")
//                binding.tvTest.text = result.toString()
//                Log.d("mymycode", "${result.resultCode},    data: ${result.data}")
//            } else {
//                Log.d("mymy", "fail")
//            }

        }


        binding.btnSend.setOnClickListener {
            val mIntent = Intent(this@CaptureQRActivity, MainActivity::class.java).apply {
               putExtra("key", "good!!  ")
            }
            setResult(RESULT_OK, mIntent)
            if (!isFinishing) finish()
        }

        //일반 QR scanner를 호출하는 버튼 listener
        binding.btnScan.setOnClickListener {
           barcodeLauncherOri.launch(options)
           //barcodeLauncherOri.launch(ScanOptions())
        }
        //custom scanner를 호출하는 버튼 listener
        binding.btnCustom.setOnClickListener {

            barcodeLauncherOri.launch(customOptions)
        }



    }


    //settings for QR Scan option
    private fun setQROptions(options: ScanOptions){
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("QR Code Scan.... by concokorea")
        options.setCameraId(0)
        options.setBeepEnabled(true)
        //QR을 detect한 시점의 이미지 불러오기 가능
        options.setBarcodeImageEnabled(true)
    }

    //settings for QR Scan option
    private fun setCustomQROptions(options: ScanOptions){
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("QR Code Scan.... by concokorea")
        options.setCameraId(0)
        options.setBeepEnabled(true)
        //QR을 detect한 시점의 이미지 불러오기 가능
        options.setBarcodeImageEnabled(true)
        options.captureActivity = MyBarcodeReaderActivity::class.java
    }



}