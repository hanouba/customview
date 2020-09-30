编译制作so
https://blog.csdn.net/leilifengxingmw/article/details/71598687

1新建一个MyJni.java文件
2 然后点击一下 make project 会在app的build目录下面生成.class文件。
3在app/src/main文件夹下新建一个jni文件夹
  执行命令G:\gitWorkPlace\aboutCustomView\cmekdemo\src\main\jni>
  javah -jni -classpath G:\gitWorkPlace\aboutCustomView\cmekdemo\build\intermediates\javac\debug\classes
  com.hansen.cmekdemo.JNITest
4  在jni目录下新建一个c/c++source file ,取名test.c 实现上面.h文件中的方法
5 接着在jni文件夹下新建Android.mk和Application.mk文件。
