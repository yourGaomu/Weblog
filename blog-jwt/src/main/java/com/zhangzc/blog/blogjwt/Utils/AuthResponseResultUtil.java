package com.zhangzc.blog.blogjwt.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangzc.blog.blogcommon.Utils.R;
import io.jsonwebtoken.io.IOException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AuthResponseResultUtil {
    /**
     * 成功响参
     * @param response
     * @param result
     * @throws IOException
     */
    public static void ok(HttpServletResponse response, R result) throws IOException, java.io.IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    /**
     * 失败响参
     * @param response
     * @param result
     * @throws IOException
     */
    public static void fail(HttpServletResponse response, R result) throws IOException, java.io.IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    /**
     * 失败响参
     * @param response
     * @param status 可指定响应码，如 401 等
     * @param result
     * @throws IOException
     */
    public static void fail(HttpServletResponse response, int status, R result) throws IOException, java.io.IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
