import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import 'package:share_plugin/share_plugin.dart';
import 'package:share_plugin/share_plugin_platform_interface.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: TextButton(onPressed: _shareImpl,style: ButtonStyle(backgroundColor: MaterialStateColor.resolveWith((states) => Colors.amber)),child: const Text('Share Text'),),
        ),
      ),
    );
  }

  void _shareImpl() async {
    var text = "This is a piece of text used to test the share function";
    var result = await SharePlugin.share(text, ShareType.TEXT);
    if (kDebugMode) {
      print('share result status is ${result.status},share platform is ${result.platform}');
    }
  }
}
