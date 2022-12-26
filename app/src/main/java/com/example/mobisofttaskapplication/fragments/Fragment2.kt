package com.example.mobisofttaskapplication.fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobisofttaskapplication.databinding.Fragment2Binding
import java.util.*


class Fragment2 : Fragment() {

    private lateinit var binding: Fragment2Binding
    private lateinit var imageViewUri: Uri

    var count=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            imageViewUri = Uri.parse(requireArguments().getString("URI").toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setImageURI(imageViewUri)


        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(2000)
                        activity!!.runOnUiThread {
                            // update TextView And Random number here!
                            count++
                            binding.textView.text = "Text Update at every 2 sec :- "+count.toString()

                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }


        thread.start()


    }

    var activity: Activity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is Activity) context else null
    }


}