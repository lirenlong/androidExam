#Exam3#

##不同size的布局适配

###xlarge,large,normal,small###

对不同分辨率布局适配，可以使用Qualifiers。如layout-xlarge，layout-large,系统会根据设备实际的分辨率和dpi，来计算实际加载哪个布局文件。对于关系如下

|Size Qualifiers|least size(dp)|
|:----:|:----:|
xlarge | 960dp x 720dp
large | 640dp x 480dp
normal | 470dp x 320dp
small | 426dp x 320dp

> The sizes that you specify using these qualifiers are not the actual screen sizes. Rather, the sizes are for the width or height in dp units that are available to your activity's window.

###sw600dp###
如果使用文件夹layout-sw600dp，则表示实际设备的最小可能宽度不小于600dp时，则使用这套布局。（与纵向和横向无关）

* layout-sw600dp-port 纵向最小
* layout-sw600dp-land 横向最小
* layout-w600dp 与横纵有关，即用当前设备的宽度与600比较

##系统匹配顺序##

* 3.2(api=13)之前不支持特殊宽度/高度限定符，因此只匹配xlarge,large,normal,small
* 3.2之后，优先匹配swXXXdp,wXXXdp,shXXXdp,hXXXdp,然后匹配四个默认限定符