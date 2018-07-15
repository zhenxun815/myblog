package cn.yiheng.myblog.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtil {

	public static String format(){
		return format(System.currentTimeMillis());
	}

	public static String format(long time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(time));
	}
	
	public static String format(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(time));
	}


	public static String translate(String time, String fromPattern, String toPattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fromPattern);
		Date date = sdf.parse(time);
		sdf.applyPattern(toPattern);
		return sdf.format(date);
	}
	public static String changeUsToChina(String filedValue)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		TimeZone tz = TimeZone.getTimeZone("US/Central");
		sdf.setTimeZone(tz);

		Date date = sdf.parse(filedValue.toString());

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm",
				Locale.CHINA);
		filedValue = format1.format(date);

		return filedValue;
	}

	public static long translateWithDate(String yyyymmddHHmm) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(yyyymmddHHmm);
			return d.getTime();
		}catch(Exception e){
			return 0;
		}

	}

	public static Date toDate(String yyyymmddHHmm) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(yyyymmddHHmm);
			return d;
		}catch(Exception e){
			return null;
		}

	}
	public static Date parse(String date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(false);
			return sdf.parse(date);
		}
		catch (Exception ex) {
		}
		return null;
	}

	/**
	 * @return
	 * 根据当前日期 返回 /年/月/日  文件目录
	 */
	public static String getNowDateFilePath(){
		 Calendar now = Calendar.getInstance();
		  int year=now.get(Calendar.YEAR);
		  int month=now.get(Calendar.MONTH)+1;
		  int day=now.get(Calendar.DAY_OF_MONTH);
		 return  "/"+year+"/"+month+"/"+day;

	}

	public static String getNowDateFilePath(Date date){
		 Calendar now = Calendar.getInstance();
		 now.setTime(date);
		  int year=now.get(Calendar.YEAR);
		  int month=now.get(Calendar.MONTH)+1;
		  int day=now.get(Calendar.DAY_OF_MONTH);
		 return  "/"+year+"/"+month+"/"+day;
	}

	/**
	 * @return
	 * 根据当前日期 返回 /年/月/日  文件目录
	 */
	public static String getNowMonthFilePath(){
		 Calendar now = Calendar.getInstance();
		  int year=now.get(Calendar.YEAR);
		  int month=now.get(Calendar.MONTH)+1;
		  int day=now.get(Calendar.DAY_OF_MONTH);
		 return  "/"+year+"/"+month;

	}

	/**
	 * 返回毫秒
	 *
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}


	/**日期相减 date-date1
	 * @param date
	 * @param date1
	 * @return 返回相减后的分钟
	 */
	public static int diffMinute(Date date, Date date1){
		return (int) ((getMillis(date) - getMillis(date1)) / (60*1000));
	}
	
	public static String format(Date date, String pattern) {
		if (date != null) {
			try {
				return new SimpleDateFormat(pattern).format(date);
			}
			catch (Exception ex) {
			}
		}
		return "";
	}
	
	
	/** 
     * @param args 
     * @throws ParseException  
     */  
    public static void main(String[] args) throws ParseException {  
        // TODO Auto-generated method stub  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date d1=sdf.parse("2012-09-08 10:10:10");  
        Date d2=sdf.parse("2012-09-18 00:00:00");  
        
        System.out.println(getDatePoor(d2,d1));
        System.out.println(daysBetween(d1,d2));  
        System.out.println(daysBetween("2012-09-08 10:10:10","2012-09-15 00:00:00"));  
    }  
      
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
      
/** 
*字符串的日期格式的计算 
*/  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  
    
    /** 获取两个时间的时间查 如1天2小时30分钟 */
    public static String getDatePoor(Date endDate, Date nowDate) {
     
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
       // long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" /*+ min + "分钟"*/;
    }
    

}
