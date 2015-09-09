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


## 遗留的疑问

### startActivity是否可能触发这个异常

### ipc中的ibinder会触发这个问题，那么lpc和rpc都会出现这个问题么？