例子参考[here](http://www.cnblogs.com/newcj/archive/2011/05/30/2061370.html)

## start方式的servcie

* 无论start多少次service，都只会执行一次onCreate，多次onStartCommand
* 注意stop它，多次start调用，只需一次stop
* LocalActivity中的onDestroy中，如果不stopservice，则：离开activity后，仍然存活；横竖屏切换时，只有onStartCommand被调（否则onDestroy,onCreate,onStartCommand都会被调用）





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