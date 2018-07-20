package cn.yiheng.myblog.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Yiheng
 * @create 2018/7/15
 * @since 1.0.0
 */
@Controller
public class IndexController extends BaseController{

    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = {"/", "/blog", "/blog/index.html"}, method = RequestMethod.GET)
    public String getIndex(){

        return "index";
    }

}
