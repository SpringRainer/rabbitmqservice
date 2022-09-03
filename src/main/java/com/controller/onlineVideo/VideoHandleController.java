package com.controller.onlineVideo;

import com.controller.PageConstants;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/8/3 21:59
 */
@Controller
@RequestMapping(value = "/local")
public class VideoHandleController {

    @RequestMapping(value = {"/index"})
    public String index() {
        return PageConstants.INDEX_PAGE;
    }

    @RequestMapping(value = "/webcam/pullVideo")
    public String pullVideo() {
        return PageConstants.VIDEO_NODES;
    }

    @RequestMapping(value = "/webcam/videoPullStream")
    public String videoPullStream() {
        return PageConstants.VIDEO_PULL_STREAM;
    }

    @RequestMapping(value = "/webcam/websocketPage")
    public String websocketPage() {
        SecurityUtils.getSubject().hasRole("user");
        return PageConstants.WEBSOCKET_PAGE;
    }

}
