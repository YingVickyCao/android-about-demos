- web_open_app.html 放入 SD Card，用 mobile 浏览器打开。

- Open URL  
  ![android_app_links](https://yingvickycao.github.io/img/android/android_app_links.svg)

  源文件：https://www.processon.com/diagraming/61322bea079129550efd749b

- Deep Links VS Android App Links

  ![android_app_links2](https://yingvickycao.github.io/img/android/android_app_links2.jpg)

- Deep Links

- Android App Links

```json
// Save to https://hades.com/.well-known/assetlinks.json
[
  {
    "relation": ["delegate_permission/common.handle_all_urls"],
    "target": {
      "namespace": "android_app",
      "package_name": "com.hades.example.android.myapplication",
      "sha256_cert_fingerprints": [
        "64:FA:15:14:12:B3:C0:77:B2:78:14:B3:1D:4E:1D:C5:55:2F:00:34:49:EF:AC:90:4C:CE:6D:DE:72:A0:7C:24"
      ]
    }
  }
]
```

# Icon

- mipmap 图标文件  
   Android >=8.0(API 26) : mipmap-anydpi-v26, 应用图标设计分为 2 层：前景层和背景层。手机厂商在这两层之上盖上一个 Mask，切成相同规范的图标。
  ![NB_Icon_Mask_Shapes_Ext_01](https://developer.android.google.cn/guide/practices/ui_guidelines/images/NB_Icon_Mask_Shapes_Ext_01.gif?hl=zh-cn)

  ![NB_Icon_Mask_Shapes_Ext_02](https://developer.android.google.cn/guide/practices/ui_guidelines/images/NB_Icon_Mask_Shapes_Ext_02.gif?hl=zh-cn)

  Andrid <=7.1(API 25) : 系统应用图标适配的过渡版本.

- android:icon vs android:roundIcon  
  `android:icon="@mipmap/ic_launcher"` 普通图标。 => 用于切成各种形状.

  `android:roundIcon="@mipmap/ic_launcher_round"` 圆形图标 => 系统要的是圆形，所以不用切，直接拿来使用。

  https://developer.android.google.cn/guide/practices/ui_guidelines/icon_design_adaptive?hl=zh-cn

# Refs:

- https://developer.android.com/training/app-links
- https://developer.android.com/training/app-links/verify-site-associations
- https://developer.android.com/training/app-links/deep-linking
- https://developer.android.com/studio/write/app-link-indexing
- https://developer.android.com/guide/navigation/navigation-deep-link
- https://developer.android.com/guide/topics/manifest/data-element#path
