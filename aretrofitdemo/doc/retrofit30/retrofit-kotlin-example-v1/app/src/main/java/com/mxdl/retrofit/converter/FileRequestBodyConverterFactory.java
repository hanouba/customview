package com.mxdl.retrofit.converter;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Description: <FileRequestBodyConverterFactory><br>
 * Author:      mxdl<br>
 * Date:        2020/8/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class FileRequestBodyConverterFactory extends Converter.Factory {
    public static FileRequestBodyConverterFactory create(){
        return new FileRequestBodyConverterFactory();
    }
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if(type == File.class){
            return FileRequestBodyConverter.INSTANCE;
        }
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
