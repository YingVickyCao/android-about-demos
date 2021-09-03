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

# Refs:

- https://developer.android.com/training/app-links
- https://developer.android.com/training/app-links/verify-site-associations
- https://developer.android.com/training/app-links/deep-linking
- https://developer.android.com/studio/write/app-link-indexing
- https://developer.android.com/guide/navigation/navigation-deep-link
- https://developer.android.com/guide/topics/manifest/data-element#path
