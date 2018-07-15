package cn.yiheng.myblog.util.cache;

public class RedisKeyConf {
	
	/**   
	 * @Fields permissions : TODO(权限前缀)   
	 */  
	public static String permissions="permissions:";
	
	/**   
	 * @Fields TAGTYPE_ID : TODO(标注类型ID单个对象前缀)   
	 */  
	public static String TAGTYPE_ID="tagtype_id:";
	/**   
	 * @Fields permissions_time : TODO(用户权限的过期时长)   
	 */  
	public static Long permissions_time=30*60L;
	public static String API_CUSTOMER="api_customer:";
	public static String API_USERKEY="api_userkey:";
	public static String IMG_CENTER="img_center:";
	
	public static int API_LOGIN_TIME=30;
	
	
	public static String RIS_ID="RIS:";
	
	public static String AI_PATIENT_ID="ai_patient_id:";
	
}
