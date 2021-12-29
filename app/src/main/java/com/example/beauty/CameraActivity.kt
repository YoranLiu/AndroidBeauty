package com.example.beauty

import android.Manifest.permission.*
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.beauty.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {
    private val TAG = CameraActivity::class.java.simpleName

    private var imgUri: Uri? = null

    private val RC_CAMERA = 100

    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCameraBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val cameraPermission = ActivityCompat.checkSelfPermission(this, CAMERA)
        val writeExternalPermission = ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
        if (cameraPermission == PackageManager.PERMISSION_DENIED ||
            writeExternalPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE),
                RC_CAMERA)
        }
        else {
            openCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == RC_CAMERA) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openCamera()
            else
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG)
                    .show()
        }
    }

    var resultLauncherCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                binding.imageView.setImageURI(imgUri)
            }
        }

    private fun openCamera() {
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "My picture")
            put(MediaStore.Images.Media.DESCRIPTION, "Testing")
        }
        imgUri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        camera.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
        resultLauncherCamera.launch(camera)
    }

}