package com.example.beauty

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.beauty.databinding.ActivityBodyBinding

class BodyActivity : AppCompatActivity() {
    private val TAG = BodyActivity::class.java.simpleName
    private val RC_EXTERNAL = 500
    private lateinit var binding: ActivityBodyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBodyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val writeExternalPermission = ActivityCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        writeExternalPermissionCheck(writeExternalPermission)

        binding.bImgChoose.setOnClickListener {
            writeExternalPermissionCheck(writeExternalPermission)
        }
    }

    private fun writeExternalPermissionCheck(writeExternalPermission: Int) {
        if (writeExternalPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), RC_EXTERNAL
            )
        } else
            openAlbum()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_EXTERNAL) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openAlbum()
            else
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG)
                    .show()
        }
    }

    val resultLauncherAlbum =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                binding.imgBody.setImageURI(result.data?.data)
             }

        }
    private fun openAlbum() {
        val album = Intent(Intent.ACTION_GET_CONTENT)
        album.type = "image/*"
        resultLauncherAlbum.launch(album)
    }
}