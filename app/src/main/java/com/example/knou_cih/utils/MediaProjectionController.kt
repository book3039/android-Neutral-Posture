package com.example.knou_cih.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.ImageReader.OnImageAvailableListener
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.github.sceneview.ar.ArSceneView
import io.reactivex.functions.Consumer
import java.text.SimpleDateFormat
import java.util.*

//스크린 캡쳐 및 녹화를 관리하는 컨트롤러
object MediaProjectionController {

    const val mediaScreenCapture = 100
    const val mediaScreenRecord = 101

    private var projectionManager: MediaProjectionManager? = null
    private var projectionCapture: MediaProjection? = null
    private var projectionRecord: MediaProjection? = null
    private var virtualDisplayCapture: VirtualDisplay? = null
    private var virtualDisplayRecord: VirtualDisplay? = null
    private var imageReader: ImageReader? = null
    private var mediaRecorder: MediaRecorder? = null

    private var width = 0
    private var height = 0
    private var videoHeight = 0

    private var prevIntentData: Intent? = null
    private var prevResultCode = 0

    private var captureCompletedAction: Consumer<Bitmap>? = null

    private var fileDescriptor: ParcelFileDescriptor? = null

    var isRecording = MutableLiveData(false)

    fun getScreenCapturePermission(activity: Activity) {
        captureScreen(activity) {}
    }

    fun captureScreen(activity: Activity, action: Consumer<Bitmap>?) {
        captureCompletedAction = action

        if (prevIntentData != null) {
            getMediaProjectionCapture(activity, prevResultCode, prevIntentData)
        } else {
            projectionManager =
                activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            activity.startActivityForResult(
                projectionManager?.createScreenCaptureIntent(),
                mediaScreenCapture
            )
        }
    }

    fun getMediaProjectionCapture(activity: Activity, resultCode: Int, intentData: Intent?) {
        projectionCapture = projectionManager?.getMediaProjection(resultCode, intentData!!)

        if (projectionCapture != null) {
            prevIntentData = intentData
            prevResultCode = resultCode
            createVirtualDisplayCapture(activity)
        }
    }

    @SuppressLint("WrongConstant")
    private fun createVirtualDisplayCapture(activity: Activity) {
        val metrics = activity.resources?.displayMetrics!!
        val density = metrics.densityDpi
        val flags =
            DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC

        width = metrics.widthPixels
        height = metrics.heightPixels

        imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2)
        imageReader?.setOnImageAvailableListener(ImageAvailableListener(), null)

        virtualDisplayCapture = projectionCapture?.createVirtualDisplay(
            "captureScreen", width, height, density, flags,
            imageReader?.surface, null, null
        )
    }

    private class ImageAvailableListener : OnImageAvailableListener {
        override fun onImageAvailable(reader: ImageReader) {
            var image: Image? = null
            try {
                image = reader.acquireLatestImage()
                if (image != null) {
                    val planes = image.planes
                    val buffer = planes[0].buffer
                    val pixelStride = planes[0].pixelStride
                    val rowStride = planes[0].rowStride
                    val rowPadding = rowStride - pixelStride * width

                    var bitmap = Bitmap.createBitmap(
                        width + rowPadding / pixelStride,
                        height,
                        Bitmap.Config.ARGB_8888
                    )
                    bitmap.copyPixelsFromBuffer(buffer)
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height)

                    projectionCapture?.stop()

                    captureCompletedAction?.accept(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                image?.close()
            }
        }
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    fun recordScreen(activity: Activity, view: ArSceneView) {

        val resourceId =
            activity.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        var deviceHeight = 0
        if (resourceId > 0) {
            deviceHeight = activity.resources.getDimensionPixelSize(resourceId)
        }

        videoHeight = view.height - deviceHeight

        if (prevIntentData != null) {
            getMediaProjectionRecord(activity, prevResultCode, prevIntentData)
        } else {
            projectionManager =
                activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            activity.startActivityForResult(
                projectionManager?.createScreenCaptureIntent(),
                mediaScreenRecord
            )
        }
    }

    fun getMediaProjectionRecord(activity: Activity, resultCode: Int, intentData: Intent?) {
        projectionRecord = projectionManager?.getMediaProjection(resultCode, intentData!!)

        if (projectionRecord != null) {
            prevIntentData = intentData
            prevResultCode = resultCode

            createVirtualDisplayRecord(activity)

            if (virtualDisplayRecord != null) {
                startRecording(activity)
            }
        }
    }

    private fun createVirtualDisplayRecord(activity: Activity) {
        val metrics = activity.resources?.displayMetrics!!
        val density = metrics.densityDpi
        val flags = DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR

        width = metrics.widthPixels
        height = metrics.heightPixels

        mediaRecorder = MediaRecorder()
        prepareRecording(activity)

        virtualDisplayRecord = projectionRecord?.createVirtualDisplay(
            "screenRecord", width, height, density, flags,
            mediaRecorder?.surface, null, null
        )
    }

    private fun createFile(activity: Activity): ParcelFileDescriptor? {

        val contentValues = ContentValues()
        val currentTime = Date(System.currentTimeMillis())
        val currentTimeStamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(currentTime)

        contentValues.put(
            MediaStore.MediaColumns.DISPLAY_NAME,
            "MediaProjectionEx$currentTimeStamp.mp4"
        )
        contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
        contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis())

        val contentResolver = activity.contentResolver
        val collectionUri =
            contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)

        return contentResolver.openFileDescriptor(collectionUri!!, "w")
    }

    private fun prepareRecording(activity: Activity) {

        fileDescriptor = createFile(activity)

        mediaRecorder?.apply {

            setOutputFile(fileDescriptor?.fileDescriptor)
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setVideoEncodingBitRate(5 * 1024 * 1000)
            setVideoFrameRate(30)
            setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT)
            setVideoSize(width, videoHeight)
            prepare()
        }
    }

    private fun startRecording(activity: Activity) {
        if (mediaRecorder != null) {
            try {
                mediaRecorder?.start()

                isRecording.value = true

                Toast.makeText(activity, "녹화가 시작되었습니다.", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun stopRecording(activity: Activity) {
        if (mediaRecorder != null) {
            try {
                mediaRecorder?.stop()
                mediaRecorder?.reset()
                virtualDisplayRecord?.release()
                projectionRecord?.stop()
                isRecording.value = false
                fileDescriptor?.close()

                Toast.makeText(activity, "녹화가 종료되었습니다. 파일 저장 완료!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}