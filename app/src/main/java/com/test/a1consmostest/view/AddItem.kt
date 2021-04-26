package com.test.a1consmostest.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.test.a1consmostest.BuildConfig
import com.test.a1consmostest.CommanViewModel
import com.test.a1consmostest.R
import com.test.a1consmostest.model.Data
import com.test.a1consmostest.utility.Util
import kotlinx.android.synthetic.main.add_item.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddItem: AppCompatActivity() {


    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2
    lateinit var currentPhotoPath: String

    private lateinit var noteViewModel: CommanViewModel


    fun displayToast(msg:String)
    {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item)
        noteViewModel = ViewModelProvider(this).get(CommanViewModel(application)::class.java)

        add?.setOnClickListener {



            if(!this::currentPhotoPath.isInitialized)
            {
                currentPhotoPath=""
            }
            else if(enter_first_name.text.toString().isEmpty())
            {
                displayToast("Please enter first name")
            }
            else if(enter_last_name.text.toString().isEmpty())
            {
                displayToast("Please enter last name")
            }
            else if(!enter_email_id.text.toString().isEmpty() && !Util.isValidEmailId(enter_email_id.text.toString()))
            {
                displayToast("Please enter valid email")
            }
            else {

                val count=noteViewModel.getNumFiles()
                val data = Data(count+1, enter_email_id.text.toString(), enter_first_name.text.toString(), enter_last_name.text.toString(), currentPhotoPath)
                noteViewModel.insert(data)


            }

        }

        capture_photo?.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Take a Avator image")
            builder.setMessage("Take photo from Camera or fetch from Gallery")


            builder.setPositiveButton("Camera") { dialog, which ->
                openCamera()
            }

            builder.setNegativeButton("Gallery") { dialog, which ->
                openGallery()
            }

            builder.setNeutralButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            builder.show()
        }
    }


    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_PERMISSION)
        }
    }

    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }
        }
    }



    @Throws(IOException::class)
    private fun createCapturedPhoto(): File {
        val timestamp: String = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PHOTO_${timestamp}",".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createCapturedPhoto()
                } catch (ex: IOException) {
                    // If there is error while creating the File, it will be null
                    null
                }
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                            this,
                            "${BuildConfig.APPLICATION_ID}.fileprovider",
                            it
                    )
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val uri = Uri.parse(currentPhotoPath)
                display_photo.setImageURI(uri)
            }
            else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.getData()
                currentPhotoPath=uri?.path!!
                display_photo.setImageURI(uri)
            }
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==REQUEST_PERMISSION && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {

        }
        else
            checkCameraPermission()
    }


}