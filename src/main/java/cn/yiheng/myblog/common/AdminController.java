package cn.yiheng.myblog.common;

import cn.yiheng.myblog.api.BaseController;
import cn.yiheng.myblog.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @RequestMapping(value = "/index", method = {RequestMethod.POST, RequestMethod.GET})
    public String index(ModelMap modelMap) {
        PropertiesUtil pu = new PropertiesUtil();
        System.out.println(pu.getPropertiesKeyValue("ip"));
        modelMap.put("ip", pu.getPropertiesKeyValue("ip"));
        modelMap.put("gateway", pu.getPropertiesKeyValue("gateway"));
        modelMap.put("subnet_mask", pu.getPropertiesKeyValue("subnet_mask"));
        modelMap.put("dcm4che_storescp", pu.getPropertiesKeyValue("dcm4che_storescp"));
        modelMap.put("dcm4che_port", pu.getPropertiesKeyValue("dcm4che_port"));
        return "/admin/index";
    }

    @RequestMapping(value = "/setip", produces = "application/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object setip(ModelMap modelMap, String ip, String gateway, String subnetMask, HttpServletRequest request) {
        //修改properes 文件
        //调用python 修改本机IP
        PropertiesUtil pu = new PropertiesUtil();
        pu.updatePro("ip", ip);
        pu.updatePro("gateway", gateway);
        pu.updatePro("subnet_mask", subnetMask);
        int re = callPythonSetIp(ip, gateway, subnetMask);
        Map<String, String> retMap = new HashMap<String, String>();
        if (re == 0)
            retMap.put("flag", "1");
        else
            retMap.put("flag", "0");
        return returnObject(request, retMap);
    }

    @RequestMapping(value = "/storescp", produces = "application/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object storescp(ModelMap modelMap, String name, String port, HttpServletRequest request) {
        //修改properes 文件
        //调用python 修改本机IP
        PropertiesUtil pu = new PropertiesUtil();
        pu.updatePro("dcm4che_storescp", name);
        pu.updatePro("dcm4che_port", port);
        Map<String, String> retMap = new HashMap<String, String>();
        retMap.put("flag", "1");
        return returnObject(request, retMap);
    }


    @RequestMapping(value = "/reboot", produces = "application/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
    public void reboot(ModelMap modelMap, HttpServletRequest request) {
        callPythonReboot();
    }


    private int callPythonSetIp(String ip, String gateway, String subnet_mask) {
        PropertiesUtil pu = new PropertiesUtil();
        String[] arguments = new String[]{"python", "/home/tqhy/tf/tq/systemControl.py", "changeNetwork", ip, gateway, subnet_mask, pu.getPropertiesKeyValue("device")};
        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                // System.out.println("ddd:"+line);
            }
            in.close();
            int re = process.waitFor();
            System.out.println("lll:" + re);
            return re;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void callPythonReboot() {
        String[] arguments = new String[]{"python", "/home/tqhy/tf/tq/systemControl.py", "reboot"};
        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                // System.out.println("ddd:"+line);
            }
            in.close();
            int re = process.waitFor();
            System.out.println("lll:" + re);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //处理文件上传
    @RequestMapping(value = "/uploadmodel", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadImg(@RequestParam("file") MultipartFile file,
                            HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();
        retMap.put("flag", "1");
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        try {
            saveFile(file.getInputStream(), "/home/tqhy/tf/tq/model", fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObject(request, retMap);
    }

    public String saveFile(InputStream file, String path, String fileName) {
        File newFile = new File(path);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        newFile = new File(path + "/" + fileName);
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(newFile);
            // if file doesn't exists, then create it
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            // get the content in bytes
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = file.read(buffer, 0, 8192)) != -1) {
                fop.write(buffer, 0, bytesRead);
            }
            //fop.write(contentInBytes);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null)
                    fop.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return fileName;
    }


}
