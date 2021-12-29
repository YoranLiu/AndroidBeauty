package com.example.beauty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.beauty.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bTakePhoto.setOnClickListener {
            Intent(this, CameraActivity::class.java)
                .apply {
                    startActivity(this)
                }
        }

        binding.bBodyMode.setOnClickListener {
            Intent(this, BodyActivity::class.java)
                .apply {
                    startActivity(this)
                }
        }
    }
}