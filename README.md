### Flutter路由、图片、字体路径等资源生成插件(AS、IDEA)
<img src="https://github.com/Xie-Yin/FlutterPlugin/blob/main/imgs/plugin.png" width="750" >

### 使用
- [下载插件安装](https://github.com/Xie-Yin/FlutterPlugin/releases)

- 在`pubspec.yaml`可以配置`generatePrefix`字段用来设定生成的文件名和类名，如果没有配置则会使用`pubspec.yaml`中的`name`字段

```yaml
# 配置生成的名称
generatePrefix: hsh
```
- 生成的文件位于`/lib/generated/`目录下