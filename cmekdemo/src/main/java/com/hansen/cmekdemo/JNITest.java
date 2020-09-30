package com.hansen.cmekdemo;

/**
 * @project customview
 * @description:
 * @version: 2.1.67
 */
public class JNITest {
    static {
        System.loadLibrary("JniLib");
    }
    public native String getString();
}
