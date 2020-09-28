package com.hansen.aretrofitdemo.converter;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Description: <FileRequestBodyConverter><br>
 * Author:      mxdl<br>
 * Date:        2020/8/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class FileRequestBodyConverter implements Converter<File, RequestBody> {
    public static FileRequestBodyConverter INSTANCE = new FileRequestBodyConverter();
    private FileRequestBodyConverter(){}
    @Override
    public RequestBody convert(File value) throws IOException {
        return new MultipartBody.Builder().addFormDataPart("file","",
                RequestBody.create(MediaType.parse("multipart/form-data"),value))
                .build();
    }
}
