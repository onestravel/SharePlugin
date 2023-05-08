package cn.onestravel.share.share_plugin

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.text.TextUtils
import androidx.core.content.FileProvider
import androidx.core.content.FileProvider.getUriForFile
import java.io.File

/**
 * @author onestravel
 * @date 2023/5/4
 */
object ShareUtils {
    const val REQUEST_CODE = 0x1015

    /**
     * 分享 Text
     *
     * @param context
     * @param text
     * @param title
     */
    @JvmStatic
    fun shareText(context: Context?, text: String?, title: String?, senderIntent: Intent): Boolean {
        if (context == null || TextUtils.isEmpty(text)) {
            return false
        }
        val pi = PendingIntent.getBroadcast(context, REQUEST_CODE, senderIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        val intent = Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Intent.EXTRA_TEXT, text)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            type = "text/plain"
        }
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                context.startActivity(Intent.createChooser(intent, title, pi.intentSender).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            } else {
                context.startActivity(Intent.createChooser(intent, title).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
                senderIntent?.let {
                    context.sendBroadcast(it)
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 分享 Image
     *
     * @param context
     * @param text
     * @param title
     */
    @JvmStatic
    fun shareImage(context: Context?, files: List<String>?, subject: String?, extraText: String?, title: String?, senderIntent: Intent): Boolean {
        if (context == null || files == null || files.isEmpty()) {
            return false
        }
        val pi = PendingIntent.getBroadcast(context, REQUEST_CODE, senderIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        val intent = Intent().apply {


            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            putExtra(Intent.EXTRA_SUBJECT, subject ?: "")
            putExtra(Intent.EXTRA_TEXT, extraText ?: "")
            type = "image/*"
            val inten = this
            if (files.size == 1) {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, getUriForFile(context, inten, files[0]))
            } else {
                action = Intent.ACTION_SEND_MULTIPLE
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList<Parcelable?>().apply { addAll(files.mapNotNull { getUriForFile(context, inten, it) }) })
            }
        }
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                context.startActivity(Intent.createChooser(intent, title, pi.intentSender).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            } else {
                context.startActivity(Intent.createChooser(intent, title).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
                senderIntent?.let {
                    context.sendBroadcast(it)
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }


    /**
     * 分享 Image
     *
     * @param context
     * @param text
     * @param title
     */
    @JvmStatic
    fun shareFile(context: Context?, files: List<String>?, subject: String?, extraText: String?, title: String?, senderIntent: Intent): Boolean {
        if (context == null || files == null || files.isEmpty()) {
            return false
        }
        val isPdf = files.all { it.contains("pdf") }
        val pi = PendingIntent.getBroadcast(context, REQUEST_CODE, senderIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        val intent = Intent().apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            putExtra(Intent.EXTRA_SUBJECT, subject ?: "")
            putExtra(Intent.EXTRA_TEXT, extraText ?: "")
            type = if (isPdf) {
                "application/pdf"
            } else {
                "application/*"
            }
            val inten = this
            if (files.size == 1) {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, getUriForFile(context, inten, files[0]))
            } else {
                action = Intent.ACTION_SEND_MULTIPLE
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList<Parcelable?>().apply { addAll(files.mapNotNull { getUriForFile(context, inten, it) }) })
            }


        }
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                context.startActivity(Intent.createChooser(intent, title, pi.intentSender).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            } else {
                context.startActivity(Intent.createChooser(intent, title).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
                senderIntent?.let {
                    context.sendBroadcast(it)
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getUriForFile(context: Context, intent: Intent, path: String): Uri? {
        val uri: Uri
        val file = File(path)
        uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //android 7.0以上
            val providerUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            grantPermissions(context, intent, providerUri, true)
            providerUri
        } else {
            Uri.fromFile(file)
        }
        return uri
    }

    private fun grantPermissions(context: Context, intent: Intent, uri: Uri?, writeAble: Boolean) {
        var flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        if (writeAble) {
            flag = flag or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        }
        intent.addFlags(flag)
        val resInfoList = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            context.grantUriPermission(packageName, uri, flag)
        }
    }
}