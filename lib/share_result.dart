part of share_plugin_plus;

class ShareResult {
  int status = 0;
  String platform = "";
  ShareResult(this.status,this.platform);
  ShareResult.fromMap(Map map) {
    status = map["status"] ?? 0;
    platform = map["platform"] ?? "unknown";
  }

  @override
  String toString() {
    return '{"status":$status,"platform":"$platform"}';
  }
}
