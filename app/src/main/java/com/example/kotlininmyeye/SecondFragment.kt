package com.example.kotlininmyeye

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.fragment_second.*
import java.io.IOException


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        handleEvent()
    }

    private fun handleEvent() {
        val detector =
            BarcodeDetector.Builder(requireActivity()).setBarcodeFormats(Barcode.ALL_FORMATS).build()
        val cameraSource = CameraSource.Builder(requireActivity(), detector)
            .setRequestedPreviewSize(1000, 1500).build()
        cameraView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
            }

            override fun surfaceDestroyed(p0: SurfaceHolder?) {
                cameraSource.stop()
            }

            override fun surfaceCreated(p0: SurfaceHolder?) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                      //  cameraSource.start(cameraView.holder)
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.CAMERA),
                            201
                        )
                        return
                    }
                    cameraSource.start(cameraView.holder)

                } catch (e: IOException) {
                    Log.e("CAMERA SOURCE", e.message)
                }
            }

        })
//
//         fun requestPermission() {
//
//             val PERMISSION_REQUEST_CODE
//             ActivityCompat.requestPermissions(requireActivity(),
//                new String[]{Manifest.permission.CAMERA},
//                PERMISSION_REQUEST_CODE);
//        }



        detector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                //   TODO("Not yet implemented")
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                //    TODO("Not yet implemented")
                if (detections == null){
                    return
                }
                val barcodes: SparseArray<Barcode> = detections.detectedItems
                if (barcodes.size() != 0) {
                    Toast.makeText(requireActivity(),"$barcodes.valueAt(0) xxx", Toast.LENGTH_SHORT).show()
                    Log.d("xxx", "Gia tri1 ${barcodes.valueAt(0)}  " +
                            "Gias tri 2   ${barcodes.valueAt(1)}   ")
                }
            }

        })

    }
}