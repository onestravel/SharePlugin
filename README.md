# share_plugin

A flutter plugin Share to Android System,

## Installing
  * Add ```share_plugin_plus: ^0.0.2``` to your pubspec.yaml

## Usage 
* share Text
```dart
  void shareTextImpl() async {
  var text = "This is a piece of text used to test the share function";
  var result = await SharePlugin.share(text, ShareType.TEXT);
  if (kDebugMode) {
    print('share result status is ${result.status},share platform is ${result.platform}');
  }
}
```
* share Image
```dart

  void shareImageImpl() async {
  var imagePath = "sdcard/image_1.jpg";
  var result = await SharePlugin.share(imagePath, ShareType.IMAGE);
  if (kDebugMode) {
    print('share result status is ${result.status},share platform is ${result.platform}');
  }
}
```

* share File
```dart

  void shareFileImpl() async {
  var filePath = "sdcard/aaa.pdf";
  var result = await SharePlugin.share(filePath, ShareType.FILE);
  if (kDebugMode) {
    print('share result status is ${result.status},share platform is ${result.platform}');
  }
}
```

