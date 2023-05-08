part of share_plugin_plus;

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
    return _SharePluginPlatform.instance.shareInner(list, type.typeText, sharePanelTitle: sharePanelTitle, subject: subject, extraText: extraText);
  }
}
