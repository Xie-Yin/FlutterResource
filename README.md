### Flutter路由、图片、字体路径等资源生成插件(AS、IDEA)
<img src="https://github.com/Xie-Yin/FlutterPlugin/blob/main/imgs/plugin.png" width="750" >

### 使用
- [下载插件安装](https://github.com/Xie-Yin/FlutterPlugin/releases)

- 在`pubspec.yaml`可以配置`xie_yin`字段，如下：

```yaml
xie_yin:
  # 当前模块是否是子模块，对生成的图片资源路径有影响
  isModule: false
  # 配置生成的文件名和类名
  prefix: hsh
```
- 如果`isModule`没有配置，默认值为`true`
- 如果`prefix`没有配置，默认值为`pubspec.yaml`中的`name`字段
- 生成的文件位于`/lib/generated/`目录下