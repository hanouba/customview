## 一些奇巧淫计

### 一 ProcessLifecycle
    维护 activity 栈来监听程序前后台切换的问题。其实单纯监听程序的前后台切换完全不需要维护 activity 栈，
    而现在比较主流的做法是使用 registerActivityLifecycleCallbacks。而今天我来介绍一下使用 ProcessLifecycleOwner 来实现这一功能
    作者：Flywith24
    链接：https://juejin.im/post/5efc2492e51d4534af686976
    来源：掘金
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

