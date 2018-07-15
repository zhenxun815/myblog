package cn.yiheng.myblog.common;

import cn.yiheng.myblog.api.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController extends BaseController {
    @RequestMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login() {
        return "/admin/login";
    }

    @RequestMapping(value = "/logon", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object logon(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        Map<String, Object> map = new HashMap<String, Object>();
        //只有注册的账户和特定的标注角色才可以登陆
        String code = request.getParameter("code");
        System.out.println(code);
        System.out.println(request.getSession().getAttribute("sessionSecCode"));
        if (null == code || "".equals(code) || !request.getSession().getAttribute("sessionSecCode").toString().toLowerCase().equals(code.toLowerCase())) {
            map.put("flag", 2);
            map.put("msg", "验证码错误");

        } else {

            if ((password.equals("admin@tqhy2017") && account.equals("admin"))
                    || (password.equals("ai@sq") && account.equals("user"))) {
                map.put("flag", 1);
                map.put("msg", "登录成功");
                request.getSession().setAttribute("user", account);
            } else {
                map.put("flag", 3);
                map.put("msg", "用户名密码错误");
            }
        }
        return returnObject(request, map);
    }

}
