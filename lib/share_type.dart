part of share_plugin;

enum ShareType {
  TEXT("text"),
  IMAGE("image"),
  FILE("file");

  final String typeText;

  const ShareType(this.typeText);
}
