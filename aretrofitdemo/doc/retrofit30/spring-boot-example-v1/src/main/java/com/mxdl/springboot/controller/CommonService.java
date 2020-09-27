package com.mxdl.springboot.controller;

import com.mxdl.springboot.bean.BaseResponse;
import com.mxdl.springboot.util.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/common")
public class CommonService {
    @PostMapping("/upload")
    public BaseResponse uploadFile(@RequestPart("file") MultipartFile file){
        if(file != null) {
            try {
                InputStream inputStream = file.getInputStream();
                if (inputStream != null) {
//                    try {
//                        Thread.sleep(1000 * 6);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    FileUtils.saveFile(inputStream);
                    return new BaseResponse("上传成功",0);
                }
                return new BaseResponse("上传失败",-1);
            } catch (IOException e) {
                e.printStackTrace();
                return new BaseResponse("上传失败",-1);
            }
        }
        return new BaseResponse("上传失败",-1);
    }
    @PostMapping("/uploads")
    public BaseResponse uploadFiles(@RequestPart("files") MultipartFile[] files){
        if(files != null && files.length >0) {

            try {
                for(MultipartFile file :files){
                    InputStream inputStream = file.getInputStream();
                    if (inputStream != null) {
                        FileUtils.saveFile(inputStream);
                    }
                }
                return new BaseResponse("上传成功",0);
            } catch (IOException e) {
                e.printStackTrace();
                return new BaseResponse("上传失败",-1);
            }
        }
        return new BaseResponse("上传失败",-1);
    }

}
