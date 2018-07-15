package cn.yiheng.myblog.util.db;

import org.springframework.stereotype.Component;

@Component
public class DbUtil {/*
    private final Logger log = LoggerFactory.getLogger(this.getClass());

	private  static Connection  pascConn=null;
	private  static Connection  risConn=null;
	private static DbUtil dbUtil=null;
	
	private  DbUtil(){
		log.info("初始化连接池开始……");
		//System.out.println("---- 初始化连接池------------DB");
		initConn();
		log.info("初始化连接池结束……");
	}
	
    public static DbUtil getDbUtil() {
         if (dbUtil == null) { 
        	 dbUtil = new DbUtil();
         }  
        return dbUtil;
    }
	private  void initConn(){
		  try{
		   Properties pr = PropertiesUtil.getProperties();
		   //Class.forName(pr.getProperty("pascDriverName"));
		   //pascConn=DriverManager.getConnection(pr.getProperty("pascUrl"),pr.getProperty("pascUserName"),pr.getProperty("pascPassWord"));
		   Class.forName(pr.getProperty("risDriverName"));
		   risConn=DriverManager.getConnection(pr.getProperty("risUrl"),pr.getProperty("risUserName"),pr.getProperty("risPassWord"));
		   
		   // 初始化 检查类型和 部位
		  // this.setTypeList(this.query("select EXAM_TYPE_ENAME as name from  dbo.t_exam_type where IsUsed=1",this.getRisConn()));
		   //this.setPartList(this.query("select EXAM_PART_CNAME as name from t_exam_part where IsUsed=1 ",this.getRisConn()));
		   //System.out.println("初始化检查类型："+this.getTypeList().size());
		   //System.out.println("初始化部位类型："+this.getPartList().size());
		   
		  } catch(Exception e) {
		   e.printStackTrace();
		   System.out.print("数据库连接失败");
		  }   
	}
	
	public  ArrayList<Map<String,Object>> query(String sql,Connection conn){
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		PreparedStatement preStat = null;
		try {
			preStat = conn.prepareStatement(sql);
			ResultSet rs = preStat.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i = 0; i < rsMeta.getColumnCount(); ++i){
					String columnName = rsMeta.getColumnName(i+1);
					map.put(columnName,rs.getObject(columnName));
				}
				list.add(map);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preStat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(list);
		return list;
	}
	
	public  ArrayList<Map<String,Object>> query(String sql,Connection conn,int cpage,int ipage,String orderClom){
		
		StringBuffer sqlsb=new StringBuffer();
		long startPage=ipage*(cpage-1);
		long endPage=startPage+ipage;
		sql=sql.replaceAll("select ", "select top ("+endPage+")");
		sqlsb.append("select * from ( select row_number()over(order by "+orderClom+") temprownumber,* from( ");
		sqlsb.append(" "+sql+" ) t  ) tt where  temprownumber>"+startPage);
		//System.out.println(sqlsb);
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		PreparedStatement preStat = null;
		try {
			preStat = conn.prepareStatement(sqlsb.toString());
			ResultSet rs = preStat.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i = 0; i < rsMeta.getColumnCount(); ++i){
					String columnName = rsMeta.getColumnName(i+1);
					map.put(columnName,rs.getObject(columnName));
				}
				list.add(map);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preStat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(list);
		return list;
	}
	
	
	
	public  ResultSet queryRt(String sql,Connection conn){
		PreparedStatement preStat = null;
		try {
			preStat = conn.prepareStatement(sql);
			ResultSet rs=  preStat.executeQuery();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preStat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void close() {  
	        try {  
	        	if(pascConn!=null){
	        		this.pascConn.close();  
	        	}
	        	if(risConn!=null){
	        		this.risConn.close();
	        	}
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    } 
	
	public static Connection getPascConn() {
		return pascConn;
	}
	public static void setPascConn(Connection pascConn) {
		DbUtil.pascConn = pascConn;
	}
	public static Connection getRisConn() {
		try {
			if(risConn.isClosed()){
				   Properties pr = PropertiesUtil.getProperties();
				   Class.forName(pr.getProperty("risDriverName"));
				   risConn=DriverManager.getConnection(pr.getProperty("risUrl"),pr.getProperty("risUserName"),pr.getProperty("risPassWord"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risConn;
	}
	public static void setRisConn(Connection risConn) {
		DbUtil.risConn = risConn;
	}

*/}
