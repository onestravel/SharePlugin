part of share_plugin_plus;




/// An implementation of [_SharePluginPlatform] that uses method channels.
class _MethodChannelSharePlugin extends _SharePluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('cn.onestravel.share.share_plugin');

  @override
  Future<ShareResult> shareInner(List<String>? list, String type, {String? sharePanelTitle, String? subject, String? extraText}) async {
    assert(list != null && list.isNotEmpty);
    final Map<String, dynamic> params = <String, dynamic>{'list': list, 'type': type, 'sharePanelTitle': sharePanelTitle ?? "", 'subject': subject ?? "", 'extraText': extraText ?? ""};
    Map map = await methodChannel.invokeMethod('share', params);
    return ShareResult.fromMap(map);
  }
}
