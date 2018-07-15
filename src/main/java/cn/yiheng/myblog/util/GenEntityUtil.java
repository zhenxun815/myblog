package cn.yiheng.myblog.util;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenEntityUtil {
	//mysql 配置
	private final String driverName = "com.mysql.jdbc.Driver";
    private final String user = "root";
    private final String password = "tqhy817@2017";
    
    private final String url = "jdbc:mysql://192.168.1.180:3306/aidr?characterEncoding=UTF8";
    
    private final String workpath="D:\\workspace\\ai-dr";
    
    //数据类型
    private final String type_char = "char";
    private final String type_date = "date";
    private final String type_timestamp = "timestamp";
    private final String type_int = "int";
    private final String type_bigint = "bigint";
    private final String type_double="double";
    private final String type_text = "text";
    private final String type_bit = "bit";
    private final String type_decimal = "decimal";
    private final String type_blob = "blob";
    
    String idName="";
    String tableName="";
    
    private Connection conn = null;
    
    private void init() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, user, password);
    }
    
    /**
     *  获取所有的表
     *
     * @return
     * @throws SQLException 
     */
    private List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<String>();
        PreparedStatement pstate = conn.prepareStatement("show tables");
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString(1);
            //          if ( tableName.toLowerCase().startsWith("yy_") ) {
            tables.add(tableName);
            //          }
        }
        return tables;
    }
    /**
     *  获取所有的数据库表注释
     *
     * @return
     * @throws SQLException 
     */
    private Map<String, String> getTableComment() throws SQLException {
        Map<String, String> maps = new HashMap<String, String>();
        PreparedStatement pstate = conn.prepareStatement("show table status");
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString("NAME");
            String comment = results.getString("COMMENT");
            maps.put(tableName, comment);
        }
        return maps;
    }
    
    private String processField(String field ) {
        StringBuffer sb = new StringBuffer(field.length());
        //field = field.toLowerCase();
        String[] fields = field.split("_");
        String temp = null;
        sb.append(fields[0]);
        for ( int i = 1 ; i < fields.length ; i++ ) {
            temp = fields[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString();
    }
    
    String beanName="";
    String mapperName="";
    String filterName="";
    String serviceName="";
    String srcBasePath="\\src\\main\\java\\com\\tqhy\\";
    String xml_path="\\src\\main\\resources\\mapper\\";
    String bean_path="";
    String service_path="";
    String mapper_path="";
    String filter_path="";
    String bean_package="";
    String mapper_package="";
    String filter_package="";
    String service_package="";
    private void processTable(String table) {
        StringBuffer sb = new StringBuffer(table.length());
        String tableNew = table.toLowerCase();
        String[] tables = tableNew.split("_");
        String temp = null;
        for ( int i = 0 ; i < tables.length ; i++ ) {
            temp = tables[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        beanName = sb.toString();
        System.out.println("beanName:"+beanName);
        mapperName = beanName + "Mapper";
        System.out.println("mapperName:"+mapperName);
        filterName=beanName+"Filter";
        System.out.println("filterName:"+filterName);
        serviceName=beanName+"Service";
        System.out.println("serviceName:"+serviceName);
        bean_path =workpath+srcBasePath+"model\\"+tables[0];
        System.out.println("bean_path:"+bean_path);
        service_path =workpath+srcBasePath+"service\\"+tables[0];
        System.out.println("service_path:"+service_path);
        mapper_path=workpath+srcBasePath+"mapper\\"+tables[0];
        System.out.println("mapper_path:"+mapper_path);
        xml_path=workpath+xml_path+tables[0];
        System.out.println("xml_path:"+xml_path);
        filter_path=workpath+srcBasePath+"filter\\"+tables[0];
        System.out.println("filter_path:"+filter_path);
        bean_package="com.tqhy.model."+tables[0];
        mapper_package="com.tqhy.mapper."+tables[0];
        System.out.println("bean_package:"+bean_package);
        filter_package="com.tqhy.filter."+tables[0];
        System.out.println("filter_package:"+filter_package);
        service_package="com.tqhy.service."+tables[0];
        System.out.println("service_package:"+service_package);
    }
    
    private String processType( String type ) {
        if ( type.indexOf(type_char) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_bigint) > -1 ) {
            return "Long";
        } else if ( type.indexOf(type_int) > -1 ) {
            return "Integer";
        } else if ( type.indexOf(type_date) > -1 ) {
            return "java.util.Date";
        } else if ( type.indexOf(type_text) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_timestamp) > -1 ) {
            return "java.util.Date";
        } else if ( type.indexOf(type_bit) > -1 ) {
            return "Boolean";
        } else if ( type.indexOf(type_decimal) > -1 ) {
            return "java.math.BigDecimal";
        } else if ( type.indexOf(type_blob) > -1 ) {
            return "byte[]";
        }else if(type.indexOf(type_double)>-1){
        	return "Double";
        }
        return null;
    }
    
    private boolean beanDefalt(String column){
    	if(column.equals("id") || column.equals("delFlag") ||  column.equals("createTime") || column.equals("updateTime"))
    		return true;
    	else
    		return false;
    	
    }
    
    /**
     *  生成实体类
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException 
     */
    private void buildEntityBean( List<String> columns, List<String> types, List<String> comments, String tableComment )
        throws IOException {
    	System.out.println("bean_path:"+bean_path);
        File folder = new File(bean_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File beanFile = new File(bean_path, beanName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        bw.write("package " + bean_package + ";");
        bw.newLine();
        bw.write("import com.tqhy.model.BaseEntity;");
        //bw.write("import lombok.Data;");
        //      bw.write("import javax.persistence.Entity;");
        bw.newLine();
        //bw.write("@Entity");
        //bw.write("@Data");
        //bw.newLine();
        bw.newLine();
        bw.newLine();
        bw.write("public class " + beanName + " extends BaseEntity {");
        bw.newLine();
        bw.newLine();
        int size = columns.size();
        for ( int i = 0 ; i < size ; i++ ) {
        	if(beanDefalt(processField(columns.get(i))))
        		continue;
            bw.newLine();
            bw.write("\tprivate " + processType(types.get(i)) + " " + processField(columns.get(i)) + ";");
            bw.newLine();
        }
        bw.newLine();
        // 生成get 和 set方法
        String tempField = null;
        String _tempField = null;
        String tempType = null;
        for ( int i = 0 ; i < size ; i++ ) {
        	if(beanDefalt(processField(columns.get(i))))
        		continue;
            tempType = processType(types.get(i));
            _tempField = processField(columns.get(i));
            tempField = _tempField.substring(0, 1).toUpperCase() + _tempField.substring(1);
            bw.newLine();
            //          bw.write("\tpublic void set" + tempField + "(" + tempType + " _" + _tempField + "){");
            bw.write("\tpublic void set" + tempField + "(" + tempType + " " + _tempField + "){");
            bw.newLine();
            //          bw.write("\t\tthis." + _tempField + "=_" + _tempField + ";");
            bw.write("\t\tthis." + _tempField + " = " + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();
            bw.write("\tpublic " + tempType + " get" + tempField + "(){");
            bw.newLine();
            bw.write("\t\treturn this." + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
        }
        bw.newLine();
    /*    bw.write("\tpublic String toString(){");
        bw.newLine();
        bw.write("\t\treturn \""+beanName+" [");
        for(int i=0;i<size;i++){
        	  _tempField = processField(columns.get(i));
        	bw.write(" "+_tempField+"=\"+" + _tempField+"+\"");
        	if(i<size-1){
        		bw.write(",");
        	}
        }
        bw.write("]\";");
        bw.newLine();
        bw.write("\t}");*/
        bw.newLine();
        
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }
    
    
    /**
     *  构建实体类映射XML文件
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException 
     */
    private void buildMapperXml() throws IOException {
    	System.out.println("xmlFilePath:"+xml_path);
        File folder = new File(xml_path);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
        File mapperXmlFile = new File(xml_path, mapperName + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
        bw.newLine();
        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.write("<mapper namespace=\"" + mapper_package+"."+mapperName + "\" >");
        bw.newLine();
        bw.write("</mapper>");
        bw.flush();
        bw.close();
    }
    
    
    /**
     *  构建MAPPER接口
     *
     */
    private void buildMapperInterface() throws IOException {
    	System.out.println("buildMapperInterface:"+mapper_path);
        File folder = new File(mapper_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File beanFile = new File(mapper_path, mapperName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        bw.write("package " + mapper_package + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import com.tqhy.util.MyMapper;");
        bw.newLine();
        bw.write("import "+bean_package+"."+beanName+";");
        bw.newLine();
        bw.newLine();
        bw.newLine();
        bw.write("public interface " + mapperName + " extends MyMapper<"+beanName+"> {");
        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }
 
    /**
     *  构建MAPPER接口
     *
     */
    private void buildFilter() throws IOException {
    	System.out.println("filter_path:"+filter_path);
        File folder = new File(filter_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File beanFile = new File(filter_path, filterName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        bw.write("package " + filter_package + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import com.tqhy.filter.BaseFilter;");
        bw.newLine();
        bw.newLine();
        bw.write("public class " + filterName + " extends BaseFilter {");
        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }
    
    
    private void buildService() throws IOException {
    	System.out.println("service_path:"+service_path);
        File folder = new File(service_path);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
        File mapperFile = new File(service_path, serviceName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        bw.write("package " + service_package + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import " + bean_package + "." + beanName + ";");
        bw.newLine();
        bw.write("import " + filter_package + "." + filterName + ";");
        bw.newLine();
        bw.write("import com.tqhy.service.common.BaseService ;");
        bw.newLine();
        bw.newLine();
        //      bw.write("public interface " + mapperName + " extends " + mapper_extends + "<" + beanName + "> {");
        bw.write("public interface " + serviceName + " extends BaseService<"+beanName+","+filterName+"> {");
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }
    
    public void buildServiceImpl() throws IOException {
    	
    	String serviceImpPath=service_path+"\\impl";
    	String serviceImpName=serviceName+"Impl";
    	String serviceImp_package=service_package+".impl";
    	System.out.println("serviceImpPath:"+serviceImpPath);
        File folder = new File(serviceImpPath);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
        File mapperFile = new File(serviceImpPath, serviceImpName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        bw.write("package " + serviceImp_package + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import org.springframework.stereotype.Service;");
        bw.newLine();
        bw.write("import " + bean_package + "." + beanName + ";");
        bw.newLine();
        bw.write("import " + filter_package + "." + filterName + ";");
        bw.newLine();
        bw.write("import com.tqhy.service.common.BaseServiceImpl ;");
        bw.newLine();
        bw.write("import "+service_package+"."+serviceName+";");
        bw.newLine();
        bw.newLine();
        //      bw.write("public interface " + mapperName + " extends " + mapper_extends + "<" + beanName + "> {");
        bw.write("@Service");
        bw.newLine();
        bw.write("public class " + serviceImpName + " extends BaseServiceImpl<"+beanName+","+filterName+"> implements "+serviceName+" {");
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }


    public void generate() throws ClassNotFoundException, SQLException, IOException {
        init();
        String prefix = "show full fields from ";
        List<String> columns = null;
        List<String> types = null;
        List<String> comments = null;
        PreparedStatement pstate = null;
        List<String> tables = getTables();
        Map<String, String> tableComments = getTableComment();
        for ( String table : tables ) {
            columns = new ArrayList<String>();
            types = new ArrayList<String>();
            comments = new ArrayList<String>();
            pstate = conn.prepareStatement(prefix + "`"+table+"`");
            ResultSet results = pstate.executeQuery();
            while ( results.next() ) {
                columns.add(results.getString("FIELD"));
                types.add(results.getString("TYPE"));
                comments.add(results.getString("COMMENT"));
            }
            if(columns!=null && columns.size()>0)
            idName= processField(columns.get(0));
            tableName = table;
            if("ris".equals(tableName)){
	           	System.out.println(tableName);
	             processTable(table);
	            String tableComment = tableComments.get(tableName);
	            buildEntityBean(columns, types, comments, tableComment);
	            buildMapperXml();
	            buildMapperInterface();
	            buildFilter();
	            buildService();
	            buildServiceImpl();
	            break;
            }
        }
        conn.close();
    }
    
    public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException{
    	GenEntityUtil util=new GenEntityUtil();
    	util.generate();
    	
    	
    	
    }
    
 
}
