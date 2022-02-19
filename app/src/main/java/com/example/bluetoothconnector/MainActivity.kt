package com.example.bluetoothconnector

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_ENABLE_BT:Int=1
    private val REQUEST_CODE_DISCOVERABLE_BT:Int=2
    lateinit var badapter:BluetoothAdapter
    lateinit var Ivbluetooth: ImageView
    lateinit var Btnon: Button
    lateinit var Btnoff:Button
    lateinit var Btndisc:Button
    lateinit var Btngpd:Button
    lateinit var pairedTv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Ivbluetooth = findViewById(R.id.bluetoothIv)
        badapter=BluetoothAdapter.getDefaultAdapter()
        Btnon=findViewById(R.id.onBtn)
        Btnoff=findViewById(R.id.offBtn)
        Btndisc=findViewById(R.id.discBtn)
        Btngpd=findViewById(R.id.gpdBtn)
        pairedTv=findViewById(R.id.pairedTv)
        if(badapter==null){
            Toast.makeText(this, "Bluetooth is not available!", Toast.LENGTH_SHORT).show()
            finish()
        }
        if(badapter.isEnabled){
            Ivbluetooth.setImageResource(R.drawable.ic_blue_en)
        }
        else{
            Ivbluetooth.setImageResource(R.drawable.ic_blue_dis)
        }
        Btnon.setOnClickListener {
            if(badapter.isEnabled){
                Toast.makeText(this,"already on", Toast.LENGTH_SHORT).show()
            }
            else{
                var intent= Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent,REQUEST_CODE_ENABLE_BT)

            }
        }
        Btnoff.setOnClickListener {
            if(!badapter.isEnabled){
                Toast.makeText(this,"already off", Toast.LENGTH_SHORT).show()
            }
            else{
                pairedTv.text=""
                badapter.disable()
                Ivbluetooth.setImageResource(R.drawable.ic_blue_dis)
                Toast.makeText(this, "Bluetooth turned off", Toast.LENGTH_SHORT).show()
            }
        }
        Btndisc.setOnClickListener {
            if(!badapter.isDiscovering){
                pairedTv.text=""
                Toast.makeText(this,"Making Bluetooth Discoverable", Toast.LENGTH_SHORT).show()
                var intent:Intent=Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent,REQUEST_CODE_DISCOVERABLE_BT)
            }
        }
        Btngpd.setOnClickListener {
            if(badapter.isEnabled){
                pairedTv.text="PAIRED DEVICES:"
                val devices=badapter.bondedDevices
                for(device in devices){
                    val dName=device.name
                    pairedTv.append("\nDevice: $dName")
                }
            }
            else{
                Toast.makeText(this, "please turn on Bluetooth", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_CODE_ENABLE_BT->
                if(resultCode== Activity.RESULT_OK){
                    Ivbluetooth.setImageResource(R.drawable.ic_blue_en)
                    Toast.makeText(this, "bluetooth turned on", Toast.LENGTH_SHORT).show()
                }
                else{
                    Ivbluetooth.setImageResource(R.drawable.ic_blue_dis)
                    Toast.makeText(this, "Bluetooth Permission denied", Toast.LENGTH_SHORT).show()
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}