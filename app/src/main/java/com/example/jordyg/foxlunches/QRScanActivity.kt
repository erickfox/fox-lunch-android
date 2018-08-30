package com.example.jordyg.foxlunches

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.widget.Toast

class QRScanActivity : AppCompatActivity(), Fallback.retryListener {

    private lateinit var fallback: Fallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscan)
        fallback = supportFragmentManager.findFragmentById(R.id.fallback) as Fallback
        fallback.listener = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            when (resultCode){
                0 -> {
                    //usuario hizo back
                }
                200 -> {
                    Toast.makeText(this, "Operación exitosa!", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, "¡Ocurrió un error! Vuelve a intentarlo.", Toast.LENGTH_LONG).show()
                }
            }
        }else{
            data?.getStringExtra("SCAN_RESULT")?.let {
                val user = it.split(",")
                if (user.count() == 3) {
                    val intent = Intent(this, UserDetail::class.java)
                    intent.putExtra("id_usr", user.get(0))
                    intent.putExtra("fake_usr", user.get(1))
                    intent.putExtra("nombre_usr", user.get(2))
                    //startActivity(intent)
                    startActivityForResult(intent, 1)
                }else{
                    Toast.makeText(this,"Código QR no compatible. Comunícate con RRHH.",Toast.LENGTH_LONG)
                }
            }?: kotlin.run {
                //error
            }
        }
    }

    private fun retryScan() {
        try {
            val intent = Intent("com.google.zxing.client.android.SCAN")
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE") // "PRODUCT_MODE for bar codes
            startActivityForResult(intent, 0)
        } catch (e: Exception) {
            val marketUri = Uri.parse("market://details?id=com.google.zxing.client.android")
            val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
            startActivity(marketIntent)
        }
    }

    override fun btnRetry() {
        this.retryScan()
    }


}
