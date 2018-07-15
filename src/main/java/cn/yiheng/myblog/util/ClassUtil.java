package cn.yiheng.myblog.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtil {
	/**
	 * @param obj
	 * @return 取得类的所有FIELD
	 */
	public static Field[] getField(Object obj) {
		Field[] fields = new Field[getFieldLength(obj)];
		copyField(fields, obj.getClass(), 0);
		return fields;
	}
	

	/**
	 * @param fields
	 * @param obj
	 * @param dsize
	 */
	private static void copyField(Field[] fields, Class<?> obj, int dsize) {
		if (obj.getSuperclass() != null) {
			System.arraycopy(obj.getDeclaredFields(), 0, fields, dsize, obj
					.getDeclaredFields().length);
			dsize = dsize + obj.getDeclaredFields().length;
			copyField(fields, obj.getSuperclass(), dsize);
		} else {
			System.arraycopy(obj.getDeclaredFields(), 0, fields, dsize, obj
					.getDeclaredFields().length);
			dsize = dsize + obj.getDeclaredFields().length;
		}
	}

	/**
	 * @param obj
	 * @return
	 */
	private static int getFieldLength(Object obj) {
		return obj.getClass().getDeclaredFields().length
				+ getClassFieldLength(obj.getClass());
	}

	/**
	 * @param obj
	 * @return
	 */
	private static int getClassFieldLength(Class<?> obj) {
		int length = 0;
		if (obj.getSuperclass() != null) {
			length = length + obj.getSuperclass().getDeclaredFields().length;
			getClassFieldLength(obj.getSuperclass());
		}
		return length;
	}

	/**
	 * @param obj
	 * @param filedName
	 * @param filedValue
	 *            改变一个filedName 的 filedValue
	 */
	public static void setFiledValue(Object obj, String filedName,
			Object filedValue) {
		Field f = null;
		try {
			f = obj.getClass().getDeclaredField(filedName);
		} catch (Exception e) {
			try {
				if (obj.getClass().getSuperclass() != null) {
					f = obj.getClass().getSuperclass().getDeclaredField(
							filedName);
				}
			} catch (Exception e1) {
				e.printStackTrace();
				System.out.println(" not find filed ");
			}
		}
		if (f != null) {
			f.setAccessible(true);
			try {
				f.set(obj, filedValue);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	 public static Object invokeGet(Object o, String fieldName) {  
		        Method method = getGetMethod(o.getClass(), fieldName);  
		         try {  
		             return method.invoke(o, new Object[0]);  
		         } catch (Exception e) {  
		             e.printStackTrace();  
		         }  
		       return null;  
		     }  

	
	public static void invokeSet(Object o, String fieldName, Object value) {  
		     Method method = getSetMethod(o.getClass(), fieldName);  
		        try {  
		           method.invoke(o, new Object[] { value });  
		       } catch (Exception e) {  
		           e.printStackTrace();  
		       }  
		    }  

	/**
     * java反射bean的set方法
     * 
     * @param objectClass
     * @param fieldName
     * @return
     */
    public static Method getSetMethod(Class<?> objectClass, String fieldName) {
        try {
            Class<?>[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * java反射bean的get方法
     * 
     * @param objectClass
     * @param fieldName
     * @return
     */
    public static Method getGetMethod(Class<?> objectClass, String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (Exception e) {
        }
        return null;
    }


	/**
	 * @param obj
	 * @param filedName
	 * @return 取得属性的值的值
	 */
	public static Object getFiledValue(Object obj, String filedName) {
		if(filedName.indexOf(".")!=-1){
			String[] filedNames=filedName.split("\\.");
			Object val=obj;
			for(int i=0;i<filedNames.length;i++){
				val=getFiledValue(val,filedNames[i]);
				if(val==null || "".equals(val)){
					val="";
					break;
				}
			}
			
			return val;
		}else{
		try {
				Class<?> objClass = obj.getClass();
				Method method = objClass.getMethod("get"
						+ filedName.substring(0, 1).toUpperCase()
						+ filedName.substring(1));
				obj = method.invoke(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (obj == null) {
				return "";
			}
		}
		return obj;
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */

	public static Class<?> getSuperClassGenricType(final Class<?> clazz,
			final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return  (Class<?>) params[index];
	}


}
