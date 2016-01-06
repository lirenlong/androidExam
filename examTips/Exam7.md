例子参考[here](http://www.cnblogs.com/newcj/archive/2011/05/30/2061370.html)

## start方式的生命周期控制

* 无论start多少次service，都只会执行一次onCreate，多次onStartCommand
* 注意stop它，多次start调用，只需一次stop
* LocalActivity中的onDestroy中：
  - 若不stopservice，则离开activity后，仍然存活，再次start该service时，是同一个内存中的service。只掉onStartCommand。横竖屏切换时，同样。
  - 若调用stopservice，则servcie走生命周期onDestroy,onCreate,onStartCommand。

### crash情况分析

分析对于程序crash，service的生命。

* 若同一进程，则service直接被销毁，并没有onDestroy回调的机会
* 若不同进程，则service会保留下来。

注意：当我输出Thread.getName时，新的service进程输出，仍然是main线程。

> 为什么有的时候，crash会马上唤起一个新的servcie，有时不会。release包也会这样么。

答：对于crash，由于时机问题，有时会被ActivityManager进行Scheduling restart of crashed service，有时不会。

而对于killProcess system.exit,要看是在栈底的act调用，还是非栈底。不同于crash的是，Scheduling restart of crashed service一定会调用。而栈底与非栈底的区别在于：栈底，那么有一定概率是当下次点击launch时，才恢复。而非栈底，有act3执行，一定是到act2被onCreate，所以这个时候被杀掉的servcie，一定会被恢复的。


## bind方式的生命周期

bindService需要一个ServiceConnection。通过bind，可以将activity绑定到service上；通过ServiceConnection可以获取IBinder，进而对service操作。

* 注意若只有一个activity绑定到service上，则activity销毁，则service销毁，无论service是否执行完。这其中包括，横竖屏切换的情况：切一下，servcie就停止了，（2015-12-31后来发现，横竖屏切换，服务没有停止）
* 多次unbindService会抛出异常
* 记得重载Service.onBind并且不要返回null（否则运行了Service但是ServiceConnection没有建联的回调）

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

## 关于界面间intent
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

## 关于notification之间的intent
大数据时，通知点击没有效果。但是没有`FAILED BINDER TRANSACTION !!!`这样的log。

## 关于broadcastReceiver的intent
大数据时，send后，onReceive不响应。有`FAILED BINDER TRANSACTION !!!`

## 总结
走查代码中，关于：

* tmintent（这里使用了一些parcelable）
* notifcation（主要在taobaointentservice）
* service的数据传递（可能是broadcast，也可能是Bind方式的service，通过IBinder返回）

## 经验
* 不在intent中放大数据（包括界面间，broadcast，notification）
* 尽量少的在intent中使用serializable和parcelable
* 不在service和application间，一次性传大数据

尽量使用读i/o的方式，虽然较慢，但是不crash。

## 思考

是否可以把大数据放到c层，常驻呢？如果可以，即解决i/o慢的问题，又解决了稳定性的问题。



## 遗留的疑问

### startActivity是否可能触发这个异常

### ipc中的ibinder会触发这个问题，那么lpc和rpc都会出现这个问题么？



## 2015-09-10
测试broadcastreceiver在使用时，执行的线程是什么：

* 无论在什么线程里注册广播，onReceive执行的线程不收影响。关键因素在于调用注册方法的那个context，context一定是main thread。
* 那么是不是说所有的onReceive都在main thread执行，不是的。见[stackoverflow](http://stackoverflow.com/questions/5674518/does-broadcastreceiver-onreceive-always-run-in-the-ui-thread). 有两个方式：
  - 注册时指定了包含有非主线程looper的handler，那么就以这个looper所在线程去调用onReceive
  - 在onReceive中调用goAsync，这样会在新的线程中执行。（其实onReceive也是在主线程中调用的）
  
**注意：LocalBroadcastReceiver的register没有提供handler的方式，所以通过该管理器注册的所有接收者，都将被主线程调用**

另外：貌似调用sendBroadcastSync，会阻塞在所有Intent的接受者执行完毕，所以用这个api，应该会导致，都是在主线程执行。

**注意：一定要注意注册广播的位置，比如不小心写在了onStartCommand中，一旦这个service被start了多次，那么就会注册多次，那么当send的时候，就会执行多次，那么onReceive中的内容就惨了。所以要注意注册位置，send的位置，还要注意onReceive会不会有多次执行的bug**


## 2015-09-18
LocalBoradcastManager声明的receiver，可以通过在非主线程中调用`LocalBroadcastManager.getInstance(context).sendBroadcastSync(new Intent("com.roger.broadcastreceiver"));`，实现receive在同样的线程中被调用，而不是通常的在主线程被调用。

## 2015-09-21

1. http://stackoverflow.com/questions/11451393/what-to-do-on-transactiontoolargeexception/16895870#16895870  contentProvider的cursor没有close。

2. http://stackoverflow.com/questions/24253976/android-package-manager-has-died-with-transactiontoolargeexception getInstalledApplications