例子参考[here](http://www.cnblogs.com/newcj/archive/2011/05/30/2061370.html)

## start方式的service

* 无论start多少次service，都只会执行一次onCreate，多次onStartCommand
* 注意stop它，多次start调用，只需一次stop
* LocalActivity中的onDestroy中，如果不stopservice，则：离开activity后，仍然存活；横竖屏切换时，只有onStartCommand被调（否则onDestroy,onCreate,onStartCommand都会被调用）



## bind方式的service
bindService需要一个ServiceConnection。通过bind，可以将activity绑定到service上；通过ServiceConnection可以获取IBinder，进而对service操作。

* 注意若只有一个activity绑定到service上，则activity销毁，则service销毁，无论service是否执行完。这其中包括，横竖屏切换的情况：切一下，servcie就停止了。
* 多次unbindService会抛出异常
* 记得重载Service.onBind并且不要返回null（否则运行了Service但是没有绑定）

# 关于transactionTooLargeException问题

## 使用系统service注意
在context文件中，getSystemService的注释中，如下：

	Note:  System services obtained via this API may be closely associated with
     * the Context in which they are obtained from.  In general, do not share the
     * service objects between various different contexts (Activities, Applications,
     * Services, Providers, etc.)
     
所以通过某context获取的系统service，不应该在其他context（activity,application,service,privider）中使用。

### 使用notification

notification也是基于IBinder做的。从`ServiceManager`获取IBinder，在通过[INotificationManager](http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/5.0.0_r1/android/app/INotificationManager.java#INotificationManager.Stub)的存根方法，类型转换成，INotificationManager。

若TelephonyManager,就返回ITelecomService。
若NotificationManager，就返回INotificationService。


## 自定义的service注意
* 及时释放
* 不传大数据

## 关于intent
intent不建议传递serializal和parcelable，因为积累多了，就可能transactionTooLarge.

写了一个在intent放入大数据的例子：

* 写了一个传递1.2M图片的例子
有时不crash：
	09-09 15:26:42.992 E/JavaBinder(29246): !!! FAILED BINDER TRANSACTION !!!
图片太大，binder失败。
	
有时crash：

	09-09 11:38:38.772 E/AndroidRuntime(25018): FATAL EXCEPTION: main
	09-09 11:38:38.772 E/AndroidRuntime(25018): Process: org.roger.sample.androidexam, PID: 25018
	09-09 11:38:38.772 E/AndroidRuntime(25018): java.lang.IllegalStateException: Could not execute method of the activity
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.view.View$1.onClick(View.java:3829)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.view.View.performClick(View.java:4444)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.view.View$PerformClick.run(View.java:18457)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.os.Handler.handleCallback(Handler.java:733)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.os.Handler.dispatchMessage(Handler.java:95)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.os.Looper.loop(Looper.java:136)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.app.ActivityThread.main(ActivityThread.java:5049)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at java.lang.reflect.Method.invokeNative(Native Method)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at java.lang.reflect.Method.invoke(Method.java:515)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:793)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:609)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at dalvik.system.NativeStart.main(Native Method)
	09-09 11:38:38.772 E/AndroidRuntime(25018): Caused by: java.lang.reflect.InvocationTargetException
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at java.lang.reflect.Method.invokeNative(Native Method)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at java.lang.reflect.Method.invoke(Method.java:515)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.view.View$1.onClick(View.java:3824)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	... 11 more
	09-09 11:38:38.772 E/AndroidRuntime(25018): Caused by: java.lang.OutOfMemoryError
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.graphics.BitmapFactory.nativeDecodeStream(Native Method)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.graphics.BitmapFactory.decodeStreamInternal(BitmapFactory.java:627)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.graphics.BitmapFactory.decodeStream(BitmapFactory.java:603)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at android.graphics.BitmapFactory.decodeStream(BitmapFactory.java:641)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at org.roger.sample.androidexam.Exam7_Service.LocalActivity.getLocalBitmap(LocalActivity.java:181)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at org.roger.sample.androidexam.Exam7_Service.LocalActivity.doIntentBig(LocalActivity.java:133)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	at org.roger.sample.androidexam.Exam7_Service.LocalActivity.onBtnClick(LocalActivity.java:110)
	09-09 11:38:38.772 E/AndroidRuntime(25018): 	... 14 more
	09-09 11:38:38.782 W/ActivityManager( 1211):   Force finishing activity org.roger.sample.androidexam/.Exam7_Service.LocalActivity
	09-09 11:38:39.292 W/ActivityManager( 1211): Activity pause timeout for ActivityRecord{42d554e0 u0 org.roger.sample.androidexam/.Exam7_Service.LocalActivity t28 f}
	09-09 11:38:40.212 I/ActivityManager( 1211): Process org.roger.sample.androidexam (pid 25018) has died.
	
有的时候crash，可能是因为我在主线程做decode。

所以：一个是tmintent,一个是taobaointentservice中的notifcation,一个是service之间的broadcast

## 遗留的疑问

### startActivity是否可能触发这个异常

### ipc中的ibinder会触发这个问题，那么lpc和rpc都会出现这个问题么？