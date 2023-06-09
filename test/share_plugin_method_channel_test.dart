import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:share_plugin/share_plugin_method_channel.dart';

void main() {
  MethodChannelSharePlugin platform = MethodChannelSharePlugin();
  const MethodChannel channel = MethodChannel('share_plugin');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.shareInner(["text"], "text"), '42');
  });
}
