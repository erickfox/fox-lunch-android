package com.example.jordyg.foxlunches

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.github.kittinunf.fuel.httpPost
import kotlinx.android.synthetic.main.activity_user_detail.*


class UserDetail : AppCompatActivity() {

    var currentUser: String = ""
    var currenUserID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        progressBar.visibility = View.GONE
        this.currentUser = intent.getStringExtra("nombre_usr")
        this.currenUserID = intent.getStringExtra("id_usr").toInt()
        lblName.setText(this.currentUser)
        btnAcredit.setOnClickListener {
            this.acredit()
        }
    }

    fun acredit() {
        val userID = this.currenUserID.let { it } ?: kotlin.run {
            Toast.makeText(this, "Información del colaborador incorrecta", Toast.LENGTH_SHORT).show()
            return
        }

        if (txtSaldo.text.isEmpty()) {
            Toast.makeText(this, "Ingrese tickets", Toast.LENGTH_SHORT).show()
            return
        }

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Se agregarán " + txtSaldo.text + " tickets a: \n" +  this.currentUser)
        builder.setPositiveButton("ACEPTAR"){dialog, which ->
            progressBar.visibility = View.VISIBLE
            this.requestToApi(userID, txtSaldo.text.toString().toInt()){
                setResult(it)
                finish()
                progressBar.visibility = View.GONE
            }
        }

        builder.setNegativeButton("CANCELAR"){dialog,which ->

        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun requestToApi(id: Int, saldo: Int, callback: (Int) -> Unit) {
        val body = "{\"user_id\": " + id.toString() + ", \"quantity\": " + saldo.toString() + "}"
        val request = "http://34.215.153.5:8080/purchase/credits".httpPost().body(body)
        request.timeoutInMillisecond = 10000 //10s
        request.headers["Content-Type"] = "application/json"
        request.response { request, response, result ->
            callback(response.statusCode)
        }
    }
}
