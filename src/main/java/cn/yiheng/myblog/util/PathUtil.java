package cn.yiheng.myblog.util;

import java.io.File;

public class PathUtil {

	public static String getRootPath() {
		PathUtil.class.getClassLoader().getResource("").getPath();
		
		String classPath = PathUtil.class.getClassLoader().getResource("/").getPath();
		String rootPath = "";
		//windows下
		if("\\".equals(File.separator)){
		rootPath = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
		rootPath = rootPath.replace("/", "\\");
		}
		//linux下
		if("/".equals(File.separator)){
			rootPath = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
		}
	
	  public static String getRootPath1(){    
	        //因为类名为"Application"，因此" Application.class"一定能找到    
	        String result = PathUtil.class.getResource("PathUtil.class").toString();
	        System.out.println(result);
	        int index = result.indexOf("WEB-INF");    
	        if(index == -1){    
	            index = result.indexOf("bin");    
	        }    
	        result = result.substring(0,index);    
	        if(result.startsWith("jar")){    
	            // 当class文件在jar文件中时，返回"jar:file:/F:/ ..."样的路径     
	            result = result.substring(10);    
	        }else if(result.startsWith("file")){    
	            // 当class文件在class文件中时，返回"file:/F:/ ..."样的路径     
	            result = result.substring(6);    
	        }    
	        if(result.endsWith("/"))result = result.substring(0,result.length()-1);//不包含最后的"/"    
	        return result;    
	    }  
	
	public static void main(String args[]){
		System.out.println(PathUtil.class.getClassLoader().getResource("").getPath());
		
		
	}
}
