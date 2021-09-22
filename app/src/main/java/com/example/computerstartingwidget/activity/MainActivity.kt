package com.example.computerstartingwidget.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.computerstartingwidget.R
import com.example.computerstartingwidget.databinding.ActivityMainBinding
import com.example.computerstartingwidget.room.MacAddress
import com.example.computerstartingwidget.room.MacAddressDatabase
import com.example.computerstartingwidget.workers.StartWorkerTasks.startWorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startWorkManager(this)
        }

        binding.saveButton.setOnClickListener {
            saveMacAddressToDatabase()
        }
    }

    private fun saveMacAddressToDatabase() {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {
                val stringInTextField = binding.macAddressEditText.text.toString()
                val database = App.instance!!.getDatabase()!!
                val databaseDao = database.macAddressDao()
                val macAddress = MacAddress(0, stringInTextField)
                databaseDao!!.insert(macAddress)
        }

    }



    /*
    private fun startComputer() {

        val intent = Intent(Intent.ACTION_QUICK_VIEW, Uri.parse("http://152.70.190.52:8081/wol/add?key=dshjfnsndvcoksjzdkjfPOWDSJFOISAJDGFDFJNBVKJVB&mac=70:85:c2:46:7e:dc"))
        startActivity(intent)
    }

     */
}