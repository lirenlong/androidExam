## 1

        try {
            Class a = Class.forName("com.roger.abc");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
      
如果这么调用，会报：

```
W/System.err(14589): java.lang.ClassNotFoundException: com.roger.abc
W/System.err(14589): 	at java.lang.Class.classForName(Native Method)
W/System.err(14589): 	at java.lang.Class.forName(Class.java:251)
W/System.err(14589): 	at java.lang.Class.forName(Class.java:216)
W/System.err(14589): 	at org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.testClassNotFound(goToGetIt.java:30)
W/System.err(14589): 	at org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.onCreate(goToGetIt.java:25)
W/System.err(14589): 	at android.app.Activity.performCreate(Activity.java:5600)
W/System.err(14589): 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1093)
W/System.err(14589): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2504)
W/System.err(14589): 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2599)
W/System.err(14589): 	at android.app.ActivityThread.access$900(ActivityThread.java:174)
W/System.err(14589): 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1321)
W/System.err(14589): 	at android.os.Handler.dispatchMessage(Handler.java:102)
W/System.err(14589): 	at android.os.Looper.loop(Looper.java:146)
W/System.err(14589): 	at android.app.ActivityThread.main(ActivityThread.java:5748)
W/System.err(14589): 	at java.lang.reflect.Method.invokeNative(Native Method)
W/System.err(14589): 	at java.lang.reflect.Method.invoke(Method.java:515)
W/System.err(14589): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1291)
W/System.err(14589): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1107)
W/System.err(14589): 	at dalvik.system.NativeStart.main(Native Method)
W/System.err(14589): Caused by: java.lang.NoClassDefFoundError: com/roger/abc
W/System.err(14589): 	... 19 more
W/System.err(14589): Caused by: java.lang.ClassNotFoundException: Didn't find class "com.roger.abc" on path: DexPathList[[zip file "/data/app/org.roger.sample.androidexam-6.apk"],nativeLibraryDirectories=[/data/app-lib/org.roger.sample.androidexam-6, /vendor/lib, /system/lib]]
W/System.err(14589): 	at dalvik.system.BaseDexClassLoader.findClass(BaseDexClassLoader.java:56)
W/System.err(14589): 	at java.lang.ClassLoader.loadClass(ClassLoader.java:497)
W/System.err(14589): 	at java.lang.ClassLoader.loadClass(ClassLoader.java:457)
W/System.err(14589): 	... 19 more
D/Activity(14589): #1 setTransGradationModeColor false
```
异常被catch住，程序没有crash。

## 2
goToGetIt类中的onCreate方法中，使用了testClassNotFound，testClassNotFound中使用了BaseTestRunner，这个runner在junit-4.12.jar中，如果我不把这个jar打入dex，那么会报如下的错误，程序crash。

```
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gotogetit_activity);
        testClassNotFound();
    }
```

```
W/dalvikvm(13406): Unable to resolve superclass of Lorg/roger/sample/androidexam/Exam0_wasteland/goToGetIt$1; (2684)
W/dalvikvm(13406): Link of class 'Lorg/roger/sample/androidexam/Exam0_wasteland/goToGetIt$1;' failed
E/dalvikvm(13406): Could not find class 'org.roger.sample.androidexam.Exam0_wasteland.goToGetIt$1', referenced from method org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.testClassNotFound
W/dalvikvm(13406): VFY: unable to resolve new-instance 2693 (Lorg/roger/sample/androidexam/Exam0_wasteland/goToGetIt$1;) in Lorg/roger/sample/androidexam/Exam0_wasteland/goToGetIt;
D/dalvikvm(13406): VFY: replacing opcode 0x22 at 0x0000
W/dalvikvm(13406): Unable to resolve superclass of Lorg/roger/sample/androidexam/Exam0_wasteland/goToGetIt$1; (2684)
W/dalvikvm(13406): Link of class 'Lorg/roger/sample/androidexam/Exam0_wasteland/goToGetIt$1;' failed
D/dalvikvm(13406): DexOpt: unable to opt direct call 0x4e4f at 0x02 in Lorg/roger/sample/androidexam/Exam0_wasteland/goToGetIt;.testClassNotFound
D/AndroidRuntime(13406): Shutting down VM
W/dalvikvm(13406): threadid=1: thread exiting with uncaught exception (group=0x41932da0)
E/AndroidRuntime(13406): FATAL EXCEPTION: main
E/AndroidRuntime(13406): Process: org.roger.sample.androidexam, PID: 13406
E/AndroidRuntime(13406): java.lang.NoClassDefFoundError: org.roger.sample.androidexam.Exam0_wasteland.goToGetIt$1
E/AndroidRuntime(13406): 	at org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.testClassNotFound(goToGetIt.java:34)
E/AndroidRuntime(13406): 	at org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.onCreate(goToGetIt.java:25)
E/AndroidRuntime(13406): 	at android.app.Activity.performCreate(Activity.java:5600)
E/AndroidRuntime(13406): 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1093)
E/AndroidRuntime(13406): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2504)
E/AndroidRuntime(13406): 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2599)
E/AndroidRuntime(13406): 	at android.app.ActivityThread.access$900(ActivityThread.java:174)
E/AndroidRuntime(13406): 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1321)
E/AndroidRuntime(13406): 	at android.os.Handler.dispatchMessage(Handler.java:102)
E/AndroidRuntime(13406): 	at android.os.Looper.loop(Looper.java:146)
E/AndroidRuntime(13406): 	at android.app.ActivityThread.main(ActivityThread.java:5748)
E/AndroidRuntime(13406): 	at java.lang.reflect.Method.invokeNative(Native Method)
E/AndroidRuntime(13406): 	at java.lang.reflect.Method.invoke(Method.java:515)
E/AndroidRuntime(13406): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1291)
E/AndroidRuntime(13406): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1107)
E/AndroidRuntime(13406): 	at dalvik.system.NativeStart.main(Native Method)
```

编译之后是这样得：

```
private void testClassNotFound() {
        AnonymousClass1 anonymousClass1 = new BaseTestRunner() {
            protected void runFailed(String str) {
            }

            public void testEnded(String str) {
            }

            public void testFailed(int i, Test test, Throwable th) {
            }

            public void testStarted(String str) {
            }
        };
    }
    
```



## 3

如果把2的情况，移到onButtonClick中，

```
public void onBtnClick(View view) {
        testClassNotFound();
    }
```

则

```
D/AndroidRuntime(18374): Shutting down VM
W/dalvikvm(18374): threadid=1: thread exiting with uncaught exception (group=0x41932da0)
E/AndroidRuntime(18374): FATAL EXCEPTION: main
E/AndroidRuntime(18374): Process: org.roger.sample.androidexam, PID: 18374
E/AndroidRuntime(18374): java.lang.IllegalStateException: Could not execute method of the activity
E/AndroidRuntime(18374): 	at android.view.View$1.onClick(View.java:4000)
E/AndroidRuntime(18374): 	at android.view.View.performClick(View.java:4754)
E/AndroidRuntime(18374): 	at android.view.View$PerformClick.run(View.java:19605)
E/AndroidRuntime(18374): 	at android.os.Handler.handleCallback(Handler.java:733)
E/AndroidRuntime(18374): 	at android.os.Handler.dispatchMessage(Handler.java:95)
E/AndroidRuntime(18374): 	at android.os.Looper.loop(Looper.java:146)
E/AndroidRuntime(18374): 	at android.app.ActivityThread.main(ActivityThread.java:5748)
E/AndroidRuntime(18374): 	at java.lang.reflect.Method.invokeNative(Native Method)
E/AndroidRuntime(18374): 	at java.lang.reflect.Method.invoke(Method.java:515)
E/AndroidRuntime(18374): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1291)
E/AndroidRuntime(18374): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1107)
E/AndroidRuntime(18374): 	at dalvik.system.NativeStart.main(Native Method)
E/AndroidRuntime(18374): Caused by: java.lang.reflect.InvocationTargetException
E/AndroidRuntime(18374): 	at java.lang.reflect.Method.invokeNative(Native Method)
E/AndroidRuntime(18374): 	at java.lang.reflect.Method.invoke(Method.java:515)
E/AndroidRuntime(18374): 	at android.view.View$1.onClick(View.java:3995)
E/AndroidRuntime(18374): 	... 11 more
E/AndroidRuntime(18374): Caused by: java.lang.NoClassDefFoundError: org.roger.sample.androidexam.Exam0_wasteland.goToGetIt$1
E/AndroidRuntime(18374): 	at org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.testClassNotFound(goToGetIt.java:39)
E/AndroidRuntime(18374): 	at org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.onBtnClick(goToGetIt.java:144)
E/AndroidRuntime(18374): 	... 14 more
```

## 4
如果是用Version

```
D/AndroidRuntime(21370): Shutting down VM
W/dalvikvm(21370): threadid=1: thread exiting with uncaught exception (group=0x41932da0)
E/AndroidRuntime(21370): FATAL EXCEPTION: main
E/AndroidRuntime(21370): Process: org.roger.sample.androidexam, PID: 21370
E/AndroidRuntime(21370): java.lang.IllegalStateException: Could not execute method of the activity
E/AndroidRuntime(21370): 	at android.view.View$1.onClick(View.java:4000)
E/AndroidRuntime(21370): 	at android.view.View.performClick(View.java:4754)
E/AndroidRuntime(21370): 	at android.view.View$PerformClick.run(View.java:19605)
E/AndroidRuntime(21370): 	at android.os.Handler.handleCallback(Handler.java:733)
E/AndroidRuntime(21370): 	at android.os.Handler.dispatchMessage(Handler.java:95)
E/AndroidRuntime(21370): 	at android.os.Looper.loop(Looper.java:146)
E/AndroidRuntime(21370): 	at android.app.ActivityThread.main(ActivityThread.java:5748)
E/AndroidRuntime(21370): 	at java.lang.reflect.Method.invokeNative(Native Method)
E/AndroidRuntime(21370): 	at java.lang.reflect.Method.invoke(Method.java:515)
E/AndroidRuntime(21370): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1291)
E/AndroidRuntime(21370): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1107)
E/AndroidRuntime(21370): 	at dalvik.system.NativeStart.main(Native Method)
E/AndroidRuntime(21370): Caused by: java.lang.reflect.InvocationTargetException
E/AndroidRuntime(21370): 	at java.lang.reflect.Method.invokeNative(Native Method)
E/AndroidRuntime(21370): 	at java.lang.reflect.Method.invoke(Method.java:515)
E/AndroidRuntime(21370): 	at android.view.View$1.onClick(View.java:3995)
E/AndroidRuntime(21370): 	... 11 more
E/AndroidRuntime(21370): Caused by: java.lang.NoClassDefFoundError: junit.runner.Version
E/AndroidRuntime(21370): 	at org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.testClassNotFound(goToGetIt.java:63)
E/AndroidRuntime(21370): 	at org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.onBtnClick(goToGetIt.java:148)
E/AndroidRuntime(21370): 	... 14 more
W/ActivityManager( 1025):   Force finishing activity org.roger.sample.androidexam/.Exam0_wasteland.goToGetIt
```


## 5

如果

```
try {
            Version.id();
        } catch (Throwable e) {
            Log.i("roger","catched");
            e.printStackTrace();
        }
```

那么

```
I/roger   (22154): catched
W/System.err(22154): java.lang.NoClassDefFoundError: junit.runner.Version
W/System.err(22154): 	at org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.testClassNotFound(goToGetIt.java:64)
W/System.err(22154): 	at org.roger.sample.androidexam.Exam0_wasteland.goToGetIt.onBtnClick(goToGetIt.java:153)
W/System.err(22154): 	at java.lang.reflect.Method.invokeNative(Native Method)
W/System.err(22154): 	at java.lang.reflect.Method.invoke(Method.java:515)
W/System.err(22154): 	at android.view.View$1.onClick(View.java:3995)
W/System.err(22154): 	at android.view.View.performClick(View.java:4754)
W/System.err(22154): 	at android.view.View$PerformClick.run(View.java:19605)
W/System.err(22154): 	at android.os.Handler.handleCallback(Handler.java:733)
W/System.err(22154): 	at android.os.Handler.dispatchMessage(Handler.java:95)
W/System.err(22154): 	at android.os.Looper.loop(Looper.java:146)
W/System.err(22154): 	at android.app.ActivityThread.main(ActivityThread.java:5748)
W/System.err(22154): 	at java.lang.reflect.Method.invokeNative(Native Method)
W/System.err(22154): 	at java.lang.reflect.Method.invoke(Method.java:515)
W/System.err(22154): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1291)
W/System.err(22154): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1107)
W/System.err(22154): 	at dalvik.system.NativeStart.main(Native Method)
```