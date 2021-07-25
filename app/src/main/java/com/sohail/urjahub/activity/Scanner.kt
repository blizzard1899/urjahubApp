package com.sohail.urjahub.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.sohail.urjahub.R
import com.sohail.urjahub.interfaces.QRCodeFoundListener
import com.sohail.urjahub.utils.QRCodeImageAnalyzer


class Scanner : AppCompatActivity() {
    lateinit var previewView: PreviewView
    private lateinit var cameraProviderFeature: ListenableFuture<ProcessCameraProvider>
    lateinit var btnQrCodeScanner: Button
    private lateinit var qrCode: String
    lateinit var etQrCode: EditText
    lateinit var lLQrCodeFound: LinearLayout

    companion object {
        const val PERMISSION_REQUEST_CAMERA = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        btnQrCodeScanner = findViewById(R.id.btnQrCodeFound)
        etQrCode = findViewById(R.id.etQrCodeInfo)
        lLQrCodeFound = findViewById(R.id.LlQrCode)
        lLQrCodeFound.visibility = View.INVISIBLE
        btnQrCodeScanner.setOnClickListener {
            val intentRide = Intent(this@Scanner, Trip::class.java)
            startActivity(intentRide)
            finish()
        }

        previewView = findViewById(R.id.activity_scanner_previewView)
        cameraProviderFeature =
            androidx.camera.lifecycle.ProcessCameraProvider.getInstance(this@Scanner)
        requestCamera()
    }

    private fun requestCamera() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                ActivityCompat.requestPermissions(
                    this@Scanner,
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_CAMERA
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@Scanner,
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_CAMERA
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun startCamera() {
        cameraProviderFeature.addListener(
            Runnable {
                val cameraProvider = cameraProviderFeature.get()
                bindCameraPreview(cameraProvider)
            }, ContextCompat.getMainExecutor(this)
        )
    }

    private fun bindCameraPreview(cameraProvider: ProcessCameraProvider) {
        var preview: Preview = Preview.Builder().build()

        var cameraSelector: CameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview.setSurfaceProvider(previewView.surfaceProvider)
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            QRCodeImageAnalyzer(object : QRCodeFoundListener {
                override fun onQrCodeFound(QRCode: String) {
                    qrCode = QRCode
                    etQrCode.setText(qrCode)
                    lLQrCodeFound.visibility = View.VISIBLE
                }

                override fun QrCodeNotFound() {
                    lLQrCodeFound.visibility = View.INVISIBLE
                }
            }
            )
        )


        val camera: Camera = cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            imageAnalysis,
            preview
        )


    }

}

