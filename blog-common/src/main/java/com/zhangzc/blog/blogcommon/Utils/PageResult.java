package com.zhangzc.blog.blogcommon.Utils;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.netty.util.internal.MathUtil;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Setter
public class PageResult implements Serializable {
    //成功响应
    public Boolean success;
    //总记录数
    public Long total = 0L;
    //每一页的记录数
    public Long size = 10L;
    //当前的页码数
    public Long current;
    //总页数
    public Long pages;
    public String status;
    public String meg;
    public Object data;

    public static <T> PageResult Success(Object data,Long total,Long size,Long current) {
        PageResult response = new PageResult();
        response.setStatus("200");
        response.setMeg("查询成功");
        response.setPages((total + size - 1) / size);
        response.setData(data);
        response.setTotal(total);
        response.setSize(size);
        response.setCurrent(current);
        response.setSuccess(true);
        return response;
    }

    public static <T> PageResult Success(IPage page) {
        PageResult response = new PageResult();
        response.setStatus("200");
        response.setCurrent(Objects.isNull(page) ? 1L : page.getCurrent());
        response.setSize(Objects.isNull(page) ? 10L : page.getSize());
        response.setPages(Objects.isNull(page) ? 0L : page.getPages());
        response.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        response.setMeg("查询成功");
        response.setData(null);
        response.setSuccess(true);
        return response;
    }

    public static <T> PageResult Success(IPage page, List<T> data) {
        PageResult response = new PageResult();
        response.setStatus("200");
        response.setCurrent(Objects.isNull(page) ? 1L : page.getCurrent());
        response.setSize(Objects.isNull(page) ? 10L : page.getSize());
        response.setPages(Objects.isNull(page) ? 0L : page.getPages());
        response.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        response.setMeg("查询成功");
        response.setData(data);
        response.setSuccess(true);
        return response;
    }


    public static <T> PageResult Success(IPage page, List<T> data,Long current,Long size) {
        PageResult response = new PageResult();
        response.setStatus("200");
        response.setCurrent(current);
        response.setSize(size);
        response.setPages(Objects.isNull(page) ? 0L : page.getPages());
        response.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        response.setMeg("查询成功");
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static <T> PageResult Success(IPage page, Object data) {
        PageResult response = new PageResult();
        response.setStatus("200");
        response.setCurrent(Objects.isNull(page) ? 1L : page.getCurrent());
        response.setSize(Objects.isNull(page) ? 10L : page.getSize());
        response.setPages(Objects.isNull(page) ? 0L : page.getPages());
        response.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        response.setMeg("查询成功");
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

}
