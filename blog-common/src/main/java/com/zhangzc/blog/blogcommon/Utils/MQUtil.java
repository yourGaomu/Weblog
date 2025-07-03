package com.zhangzc.blog.blogcommon.Utils;

import com.zhangzc.blog.blogcommon.MQ.Listen.PointArtfic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MQUtil {

    private final PointArtfic pointArtfic;

    public R addPointByArtID(String artID) {
        R listen = pointArtfic.listen(artID);
        return listen;
    }
}
