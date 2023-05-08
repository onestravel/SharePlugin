part of share_plugin_plus;


abstract class _SharePluginPlatform extends PlatformInterface {
  /// Constructs a SharePluginPlatform.
  _SharePluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static _SharePluginPlatform _instance = _MethodChannelSharePlugin();

  /// The default instance of [_SharePluginPlatform] to use.
  ///
  /// Defaults to [_MethodChannelSharePlugin].
  static _SharePluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SharePluginPlatform] when
  /// they register themselves.
  static set instance(_SharePluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<ShareResult> shareInner(List<String>? list, String type, {String? sharePanelTitle, String? subject, String? extraText}) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
