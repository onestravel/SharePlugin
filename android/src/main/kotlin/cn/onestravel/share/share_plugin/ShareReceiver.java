package cn.onestravel.share.share_plugin;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * @author onestravel
 * @date 2023/5/4
 */
public class ShareReceiver extends BroadcastReceiver {
    private static MethodChannel.Result result;

    public static void setResult(MethodChannel.Result result) {
        ShareReceiver.result = result;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String pkg = "unknown";
        if (intent != null) {
            ComponentName clickedComponent = intent.getParcelableExtra(Intent.EXTRA_CHOSEN_COMPONENT);
            if (clickedComponent != null && clickedComponent.getPackageName() != null) {
                pkg = clickedComponent.getPackageName();
            }else{
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1){
                    pkg = "unknown<5";
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("platform", pkg);
        resultMap.put("status", 1);
        try {
            if (result != null) {
                result.success(resultMap);
                result = null;
            }
//            context.unregisterReceiver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}