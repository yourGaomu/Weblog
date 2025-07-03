package com.zhangzc.blog.blogcommon.exception.exceptionClasses;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileUpDownFailException  extends RuntimeException{
    private String fileName;
    private String time;

    public FileUpDownFailException(String fileName, String time){
        this.fileName = fileName;
        this.time = time;
    }
}
