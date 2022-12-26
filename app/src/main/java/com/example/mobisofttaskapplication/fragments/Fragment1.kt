package com.example.mobisofttaskapplication.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.mobisofttaskapplication.R
import com.example.mobisofttaskapplication.databinding.ActivityMainBinding
import com.example.mobisofttaskapplication.databinding.Fragment1Binding
import java.io.File


class Fragment1 : Fragment() {

    private lateinit var binding: Fragment1Binding
     lateinit var imageViewUri: Uri
    private val TAG = "PermissionDemo"
    private val Camera_REQUEST_CODE = 101
    val getImageCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture(),
        {

//            binding.imageView1.setImageURI(imageViewUri)
            val bundle=Bundle()
            bundle.putString("URI",imageViewUri.toString())
            findNavController().navigate(R.id.action_fragment1_to_fragment2,bundle)
        })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = Fragment1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPermissions()
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            {
                if (it == null) {
                    Toast.makeText(
                        activity,
                        "Please Select an ProfilePic ..",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@registerForActivityResult
                }
                if (it!=null){
                    imageViewUri = it
                    val bundle=Bundle()
                    bundle.putString("URI",imageViewUri.toString())
                    findNavController().navigate(R.id.action_fragment1_to_fragment2,bundle)
                }
            })




        imageViewUri = createImageUri()!!
        binding.cameraBtn.setOnClickListener {
            getImageCamera.launch(imageViewUri)
        }

        binding.galleryBtn.setOnClickListener {
            getImage.launch("image/*")
        }
    }

    fun createImageUri(): Uri? {
        val image = File(requireContext().applicationContext.filesDir,"camera_photo.png")
        return FileProvider.getUriForFile(requireActivity().applicationContext,
            "com.example.mobisofttaskapplication.fileProvider",
        image)

    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(requireActivity(),
            Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            Camera_REQUEST_CODE)
    }




    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Camera_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }

    var activity: Activity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is Activity) context else null
    }
}