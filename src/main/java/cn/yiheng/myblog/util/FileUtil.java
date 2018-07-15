package cn.yiheng.myblog.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public void toFile(String str,String path,String fileName){
		File file =new File(path);    
		//如果文件夹不存在则创建    
		if(!file.exists()&&!file.isDirectory())      {
		    file.mkdirs();    
		} 
	     FileWriter writer = null;
	     try {
	    	 writer = new FileWriter(file.getAbsolutePath()+"/"+fileName, true);
	         writer.append(str);
	         writer.append("\n");
	         writer.flush();
	     } catch (FileNotFoundException e) {
	         e.printStackTrace();
	     }catch (IOException e) {
	         e.printStackTrace();
	     }finally{
	         try {
	             writer.close();
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	     }
		}
	
	 public  boolean copyForChannel(File f1,File f2){
	        int length=2097152;
	        FileInputStream in=null;
	        FileOutputStream out=null;
	        FileChannel inC=null;
	        FileChannel outC=null;
	        try{
	        	in=new FileInputStream(f1);
	        	out=new FileOutputStream(f2);
	        	inC=in.getChannel();
	 	        outC=out.getChannel();
	        ByteBuffer b=null;
	        while(true){
	            if(inC.position()==inC.size()){
	            	inC.close();
	            	outC.close();
	            	break;
	            }
	            if((inC.size()-inC.position())<length){
	                length=(int)(inC.size()-inC.position());
	            }else
	                length=2097152;
	            b=ByteBuffer.allocateDirect(length);
	            inC.read(b);
	            b.flip();
	            outC.write(b);
	            outC.force(false);
	        }
	        return true;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
	        	if(inC!=null)
	        	inC.close();
	        	if(outC!=null)
		        outC.close();
	        	if(in!=null)
		        in.close();
	        	if(out!=null)
		        out.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
	        return false;
	    }
	    public List<String> readFileByLines(String fileName) {
	    	List<String> retList=new ArrayList<String>();
	        File file = new File(fileName);
	        BufferedReader reader = null;
	        try {
	            //System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	               // System.out.println("line " + line + ": " + tempString);
	                //str.append(tempString);
	                retList.add(tempString);
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	        return retList;
	    }
	    public List<String> readFileByLines(File file) {
	    	List<String> retList=new ArrayList<String>();
	        BufferedReader reader = null;
	        try {
	            //System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	               // System.out.println("line " + line + ": " + tempString);
	                retList.add(tempString);
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	        return retList;
	    }
	 
	    public void clearFiles1(String workspaceRootPath,String orgId){
    	    File file = new File(workspaceRootPath);
    	     if(file.exists() && file.isDirectory()){
    	    	 File cmdFile=null;
    	    	 File jpgFile=null;
    	    	 File rstFile=null;
    	    	  File[] files = file.listFiles();
    	    		  for(int i=0; i<files.length; i++){
    	    			  if((orgId+".cmd").equals(files[i].getName())){
    	    				  cmdFile=files[i];
    	    			  }else if((orgId+".jpg").equals(files[i].getName())){
    	    				  jpgFile= files[i];
    	    			  }else if((orgId+".cmd.rst").equals(files[i].getName())){
    	    				  rstFile= files[i];
    	    			  }
    	    		  }
    	    	if(cmdFile!=null){
    	    		cmdFile.delete();
    	    	}
    	    	if(jpgFile!=null){
    	    		jpgFile.delete();
    	    	}
    	    	if(rstFile!=null){
    	    		rstFile.delete();
    	    	}
    	    	
    	     }
    	 }
	    
	    
	    	public void clearFiles(String workspaceRootPath,String orgId){
	    		
	    	    File file = new File(workspaceRootPath);
	    	     if(file.exists()){
	    	          deleteFile(file,orgId);
	    	     }
	    	 }
	    
	    	 private void deleteFile(File file,String orgId){
	    	     if(file.isDirectory()){
	    	          File[] files = file.listFiles();
	    	          for(int i=0; i<files.length; i++){
	    	               deleteFile(files[i],orgId);
	              }
	    	    }
	    	     
	    	   if(file.isFile()&& file.getName().indexOf(orgId)!=-1)
	    	      file.delete();
	    }
	    	 
   public boolean getFileIsExit(String path,String fileName){
    	 File dir = new File(path);  
         File[] files = dir.listFiles();  
         if (files == null)  
             return false;  
         for (int i = 0; i < files.length; i++) {  
             if (!files[i].isDirectory()) {
            	 if(files[i].getName().equals(fileName))
            		 return true;
             }  
         }  
         return false;
    }
   
   
   public void deleteFiles(String workspaceRootPath,String fileName){
	   File file = new File(workspaceRootPath+"/"+fileName);
	    	if(file!=null){
	    		file.delete();
	    	}
	 }
   public void deleteFile(String filePath){
	   File file = new File(filePath);
	    	if(file!=null){
	    		file.delete();
	    	}
	 }
   public void deleteTrainFiles(String workspaceRootPath,String cmdfileName,String orgId){
	   File file = new File(workspaceRootPath+"/"+cmdfileName);
	    	if(file!=null){
	    		file.delete();
	    	}
	   //样本文件夹删除
	    	File orgfile = new File(workspaceRootPath+"/"+orgId);
	    	deleteAllFilesOfDir(orgfile);
	 }
	
   
   public  void deleteAllFilesOfDir(File path) {  
	    if (!path.exists())  
	        return;  
	    if (path.isFile()) {  
	        path.delete();  
	        return;  
	    }  
	    File[] files = path.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        deleteAllFilesOfDir(files[i]);  
	    }  
	    path.delete();
	}
   
   public  String download(String urlString, String filename,String savePath){  
	   OutputStream os =null;
	   InputStream is=null;
	   try{
       // 构造URL  
       URL url = new URL(urlString);  
       // 打开连接  
       URLConnection con = url.openConnection();  
       //设置请求超时为5s  
       con.setConnectTimeout(5*1000);  
       // 输入流  
        is = con.getInputStream();  
     
       // 1K的数据缓冲  
       byte[] bs = new byte[1024];  
       // 读取到的数据长度  
       int len;  
       // 输出的文件流  
      File sf=new File(savePath);  
      if(!sf.exists()){  
          sf.mkdirs();  
      }  
       os = new FileOutputStream(sf.getAbsolutePath()+"/"+filename);  
       // 开始读取  
       while ((len = is.read(bs)) != -1) {  
         os.write(bs, 0, len);  
       }  
       // 完毕，关闭所有链接  
       os.flush();
       os.close();  
       is.close(); 
       return sf.getAbsolutePath()+"/"+filename;
	   }catch(Exception e){
		   e.printStackTrace();
	   }finally{
		   if(os!=null){
			   try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		   if(is!=null){
			   try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	   }
       return null;
   }   
 
  
   public List<String> readFileByLines1(String fileName) {
   	List<String> retList=new ArrayList<String>();
       BufferedReader reader = null;
       try {

           //System.out.println("以行为单位读取文件内容，一次读一整行：");

           reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"GBK"));
           String tempString = null;
           int line = 1;
           // 一次读入一行，直到读入null为文件结束
           while ((tempString = reader.readLine()) != null) {
               // 显示行号
              // System.out.println("line " + line + ": " + tempString);
               retList.add(tempString);
               line++;
           }
           reader.close();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (reader != null) {
               try {
                   reader.close();
               } catch (IOException e1) {
               }
           }
       }
       return retList;
   }
   
   public String readFileByLines2String(File file){
	   StringBuffer str=new StringBuffer();
  
       BufferedReader reader = null;
       try {
           //System.out.println("以行为单位读取文件内容，一次读一整行：");
           reader = new BufferedReader(new FileReader(file));
           String tempString = null;
           int line = 1;
           // 一次读入一行，直到读入null为文件结束
           while ((tempString = reader.readLine()) != null) {
               // 显示行号
              // System.out.println("line " + line + ": " + tempString);
        	   str.append(tempString);
               line++;
           }
           reader.close();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (reader != null) {
               try {
                   reader.close();
               } catch (IOException e1) {
               }
           }
       }
       return str.toString();
   }
   
}
