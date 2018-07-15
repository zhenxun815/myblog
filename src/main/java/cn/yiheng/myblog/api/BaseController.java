package cn.yiheng.myblog.api;

import cn.yiheng.myblog.util.cache.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName: BaseController
 * @Description:TODO(基类)
 * @author: Yiheng(GuoYiheng89@gmail.com)
 * @date: 2016年11月21日 下午3:21:39
 */
@Scope("prototype")
public class BaseController {
    @SuppressWarnings("unused")
    private RedisUtil redisUtil;

    public RedisUtil getRedisUtil() {
        return new RedisUtil();
    }

    public Object returnObject(HttpServletRequest request, Map<?, ?> map) {
        if (StringUtils.isNotEmpty(request.getParameter("callback"))) {
            String callback = request.getParameter("callback");
            return new JSONPObject(callback, map);
        } else {
            return map;
        }
    }

    public Object returnObject(HttpServletRequest request, JSONObject jsonObject) {
        if (StringUtils.isNotEmpty(request.getParameter("callback"))) {
            String callback = request.getParameter("callback");
            return new JSONPObject(callback, jsonObject);
        } else {
            return jsonObject;
        }
    }
}
