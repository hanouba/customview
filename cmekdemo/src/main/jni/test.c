//
// Created by Administrator on 2020/9/30.
//
#include "jni.h"
#include "com_hansen_cmekdemo_JNITest.h"
JNIEXPORT jstring JNICALL Java_com_hansen_cmekdemo_JNITest_getString
  (JNIEnv *env, jclass jz){

  return (*env)->NewStringUTF(env,"this is the first time for me to use jni");

  }