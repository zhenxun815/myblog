package cn.yiheng.myblog.util.cache;

import cn.yiheng.myblog.common.SpringContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redicache 工具类
 */
@Component
public class RedisUtil {

    private RedisTemplate<Serializable, Object> redisTemplate = SpringContext.getBean("redisTemplate");

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        try {
            for (String key : keys) {
                remove(key);
            }
        } catch (Exception e) {


        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        try {
            if (redisTemplate != null) {
                Set<Serializable> keys = redisTemplate.keys(pattern);
                if (keys.size() > 0)
                    redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        try {
            if (redisTemplate != null) {
                if (exists(key)) {
                    redisTemplate.delete(key);
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        try {
            if (redisTemplate == null)
                return false;
            return redisTemplate.hasKey(key);
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        try {
            if (redisTemplate == null) {
                return null;
            }
            Object result = null;
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result = operations.get(key);
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {

        boolean result = false;
        try {
            if (redisTemplate == null) {
                return false;
            }
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            if (!expireTime.equals(0L)) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, int minutes) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            if (minutes > 0) {
                redisTemplate.expire(key, minutes, TimeUnit.MINUTES);
            }
            result = true;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result;
    }

    public boolean expireTime(final String key, int minutes) {
        boolean result = false;
        try {
            if (minutes > 0) {
                redisTemplate.expire(key, minutes, TimeUnit.MINUTES);
            }
            result = true;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result;
    }

}