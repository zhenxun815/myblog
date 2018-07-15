package cn.yiheng.myblog.util;

import java.io.*;
import java.util.Properties;

/**
 * 操作properties文件
 * @author cj
 *
 */
public class PropertiesUtil{
	
	/**
	 * @param propertyName
	 * @return 返回properties 文件
	 * 
	 */
	
	public  String   getPropertiesKeyValue(String key){
		Properties properties=null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/tqhy/tf/aidr/manager.properties"));
			properties = new Properties();
			properties.load(bufferedReader);
			return properties.getProperty(key);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	//参数为要修改的文件路径  以及要修改的属性名和属性值    
    public  Boolean updatePro(String key,String value){ 
		
        // 文件输出流     
    	Properties properties=null;
        try {    
        	BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/tqhy/tf/aidr/manager.properties"));
    		 properties = new Properties();
        	properties.load(bufferedReader);
            System.out.println("获取添加或修改前的属性值："+key+"=" + properties.getProperty(key));  
            properties.setProperty(key, value);     
            
            FileOutputStream fos = new FileOutputStream("/home/tqhy/tf/aidr/manager.properties");     
            // 将Properties集合保存到流中     
            properties.store(fos, "Copyright (c) Boxcode Studio");    
            fos.flush();
            fos.close();// 关闭流     
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
            return false;    
        } catch (IOException e) {    
            e.printStackTrace();    
            return false;    
        }    
        System.out.println("获取添加或修改后的属性值："+key+"=" + properties.getProperty(key));  
        return true;    
    }    
    
    public static void main(String args[]){
    	PropertiesUtil pu=new PropertiesUtil();
    	System.out.println(pu.updatePro("ip", "192.168.1.188"));
    	System.out.println(pu.getPropertiesKeyValue("ip"));
    	//System.out.println(PropertiesUtil.updatePro("sql","select * from tab"));
    }
    

}




