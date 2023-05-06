package cn.onestravel.share.share_plugin

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** SharePlugin */
class SharePlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    private val ACTION_SHARE = " cn.onestravel.share.share_action"
//    private val shareResultReceiver = ShareReceiver()

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "cn.onestravel.share.share_plugin")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
//        val filter = IntentFilter()
//        filter.addAction(ACTION_SHARE)
//        context?.registerReceiver(shareResultReceiver, filter)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "share") {
            val fileList = call.argument<List<String>>("list")
            if (fileList == null || fileList.isEmpty()) {
                return
            }
            val shaderIntent = Intent(context, ShareReceiver::class.java)
            shaderIntent.action = ACTION_SHARE
            val type = call.argument<String>("type")
            val subject = call.argument<String>("subject")
            val sharePanelTitle = call.argument<String>("sharePanelTitle")
            val extraText = call.argument<String>("extraText")
            val success = when (type) {
                "text" -> {
                    ShareReceiver.setResult(result)
                    ShareUtils.shareText(context, fileList[0], sharePanelTitle, shaderIntent)
                }
                "image" -> {
                    ShareReceiver.setResult(result)
                    ShareUtils.shareImage(context, fileList, subject, extraText, sharePanelTitle, shaderIntent)
                }
                "file" -> {
                    ShareReceiver.setResult(result)
                    ShareUtils.shareFile(context, fileList, subject, extraText, sharePanelTitle, shaderIntent)
                }
                else -> {
                    false
                }
            }
            if (!success) {
                result.success(mapOf("status" to 0))
            }

        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
//        try {
//            context?.unregisterReceiver(shareResultReceiver)
//        } catch (e: Exception) {
//        }
    }
}
