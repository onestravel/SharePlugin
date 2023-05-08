library share_plugin_plus;

import 'share_plugin_platform_interface.dart';

export 'share_plugin_method_channel.dart' hide MethodChannelSharePlugin;
export 'share_plugin_platform_interface.dart' hide SharePluginPlatform;

part 'share_type.dart';

part 'share_result.dart';

class SharePlugin {
  static Future<ShareResult> shareMultiple(List<String>? list, ShareType type, {String sharePanelTitle = "", String? subject = "", String? extraText = ""}) {
    assert(list != null && list.isNotEmpty);
    return _shareInner(list, type, subject: subject, sharePanelTitle: sharePanelTitle, extraText: extraText);
  }

  static Future<ShareResult> share(String? text, ShareType type, {String sharePanelTitle = "", String subject = "", String extraText = ""}) {
    assert(text?.isNotEmpty ?? true);
    List<String> list = [text ?? ""];
    return _shareInner(
      list,
      type,
      sharePanelTitle: sharePanelTitle,
      subject: subject,
      extraText: extraText,
    );
  }

  static Future<ShareResult> _shareInner(List<String>? list, ShareType type, {String? sharePanelTitle, String? subject, String? extraText}) async {
    assert(list != null && list.isNotEmpty);
    return SharePluginPlatform.instance.shareInner(list, type.typeText, sharePanelTitle: sharePanelTitle, subject: subject, extraText: extraText);
  }
}
