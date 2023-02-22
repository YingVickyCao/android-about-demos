package com.hades.example.android.test.apk_upgrade;

public class AppVersionUpgrade {
    private static AppVersionUpgrade mInstance = null;

    private INetManager mNetManager = new OkHttpNetManager();

    private AppVersionUpgrade() {

    }

    public static AppVersionUpgrade getInstance() {
        AppVersionUpgrade local = mInstance;
        if (null == local) {
            synchronized (AppVersionUpgrade.class) {
                if (null == mInstance) {
                    mInstance = local = new AppVersionUpgrade();
                }
            }
        }
        return local;
    }

    public void setNetManager(INetManager netManager) {

    }

    public INetManager getNetManager() {
        return mNetManager;
    }
}
