import 'package:flutter_test/flutter_test.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:share_plugin_plus/share_plugin.dart';
import 'package:share_plugin_plus/share_plugin_platform_interface.dart';
import 'package:share_plugin_plus/share_plugin_method_channel.dart';

class MockSharePluginPlatform with MockPlatformInterfaceMixin implements SharePluginPlatform {
  @override
  Future<ShareResult> shareInner(List<String>? list, String type, {String? sharePanelTitle, String? subject, String? extraText}) {
    return Future.value(ShareResult(1, "test"));
  }
}

void main() {
  final SharePluginPlatform initialPlatform = SharePluginPlatform.instance;

  test('$MethodChannelSharePlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSharePlugin>());
  });

  test('getPlatformVersion', () async {
    MockSharePluginPlatform fakePlatform = MockSharePluginPlatform();
    SharePluginPlatform.instance = fakePlatform;

    expect(await SharePlugin.share("text", ShareType.TEXT), '42');
  });
}
