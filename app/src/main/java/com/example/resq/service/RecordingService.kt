package com.example.resq.service

import android.app.Service
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import com.example.resq.MainActivity.Companion.USER_DISPLAY_NAME
import com.example.resq.network.RetrofitInstance.apiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecordingService : Service() {

    private var mediaRecorder: MediaRecorder? = null
    private var recordingUri: Uri? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startRecording()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRecording()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun startRecording() {
        val contentResolver: ContentResolver = applicationContext.contentResolver
        val dateFormat = SimpleDateFormat("yyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "ResQ 녹음 ${USER_DISPLAY_NAME}_$dateFormat"
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Recordings/ResQ")
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4")
        }

        recordingUri = contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)

        try {
            @Suppress("DEPRECATION")
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(
                    contentResolver.openFileDescriptor(recordingUri!!, "w")?.fileDescriptor
                )
            }
            mediaRecorder?.prepare()
            mediaRecorder?.start()
        } catch (e: IOException) {
            Log.e("startRecording", e.message.toString())
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null

        recordingUri?.let { uri ->
            try {
                val inputStream = contentResolver.openInputStream(uri)
                val tempFile = File.createTempFile("upload_", ".mp4", cacheDir)
                inputStream?.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                val requestFile = tempFile.asRequestBody("audio/mp4".toMediaTypeOrNull())
                val audioPart =
                    MultipartBody.Part.createFormData("audio", tempFile.name, requestFile)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = apiService.uploadAudio(audioPart)
                    } catch (e: Exception) {
                        Log.d("uploadAudio", e.message.toString())
                    }
                }
            } catch (e: IOException) {
                Log.d("stopRecording", e.message.toString())
            }
        }
    }
}