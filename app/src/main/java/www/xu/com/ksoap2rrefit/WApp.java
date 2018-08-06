package www.xu.com.ksoap2rrefit;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public abstract class WApp extends Application {
    private static WApp instance;

    public static WApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public String getCurrentProcessName(Context context) {
        int pid = Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List infos = am.getRunningAppProcesses();
        if (infos == null) {
            return null;
        } else {
            Iterator i$ = infos.iterator();
            ActivityManager.RunningAppProcessInfo info;
            do {
                if (!i$.hasNext()) {
                    return null;
                }
                info = (ActivityManager.RunningAppProcessInfo) i$.next();
            } while (info.pid != pid);

            return info.processName;
        }
    }

    public abstract Map<String, String> getHttpHeaders();


    }

