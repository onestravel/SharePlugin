import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'share_plugin.dart';
import 'share_plugin_method_channel.dart';

abstract class SharePluginPlatform extends PlatformInterface {
  /// Constructs a SharePluginPlatform.
  SharePluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static SharePluginPlatform _instance = MethodChannelSharePlugin();

  /// The default instance of [SharePluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelSharePlugin].
  static SharePluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SharePluginPlatform] when
  /// they register themselves.
  static set instance(SharePluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }
  Future<ShareResult> shareInner(List<String>? list, String type, { String? sharePanelTitle, String? subject, String? extraText}){
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

}
