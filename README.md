# android-about-demos

project :

| Project     | 用途                                                                    |
| ----------- | ----------------------------------------------------------------------- |
| app         | 含有大部分的 android example 和 demo 代码                               |
| A           | 测试 Implicit/ Explicit Intent - 发送                                   |
| B1          | 测试 Implicit/ Explicit Intent - 接收 Implicit Intent 的 activity       |
| A1          | 测试 Deeplink(Open link url from Chrome App to open Other App Activity) |
| assist      | 为 app 测试 DictContentProvider 时提供数据源                            |
| B           | 为 app 测试 BoundedService 时提供监听状态的 Remote Bounded Service      |
| JavaLib     | java libary module                                                      |
| lib-android | android libary module                                                   |
| receive_app_links、other_app_having_app_links | 测试 app links（deep links、web link、Android App Links)


# Case
- [app内部版本升级](app/src/main/java/com/hades/example/android/_case/apk_upgrade)