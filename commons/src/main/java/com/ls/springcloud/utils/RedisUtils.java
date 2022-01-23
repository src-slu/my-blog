package com.ls.springcloud.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtils
 * @Description 封装redis工具类
 * @Author lushuai
 * @Date 2019/12/4 15:46
 */
@Slf4j
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate template;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.template = redisTemplate;
    }

    /**
     * 设置带有过期时间的key
     *
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                template.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.warn("RedisUtils.expire 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return template.getExpire(key);
    }

    /**
     * 判断key是否已存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return template.hasKey(key);
        } catch (Exception e) {
            log.warn("RedisUtils.getExpire 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void deleteKey(String... key) {
        /**
         * 如果只有一个key直接删除
         */
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                template.delete(key[0]);
            } else {
                template.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

/******************************************  String类型数据操作  ******************************************************/

    /**
     * 获取值
     *
     * @param key
     */
    public Object get(String key) {
        return key == null ? null : template.opsForValue().get(key);
    }

    /**
     * 设置key-value
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            template.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.warn("RedisUtils.set 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 设置带有过期时间的key-value
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, Object value, long time) {
        /**
         * 如果时间大于0，则设置过期时间
         */
        try {
            if (time > 0) {
                template.opsForValue().set(key, value, time);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.warn("RedisUtils.set(.,.,.) 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key
     * @param number
     * @return
     */
    public long incr(String key, long number) {
        if (number < 0) {
            log.warn("RedisUtils.incr 发生异常");
            throw new RuntimeException("递增因子必须大于0");
        }
        return template.opsForValue().increment(key, number);
    }

    /**
     * 递减
     *
     * @param key
     * @param number
     * @return
     */
    public long decr(String key, long number) {
        if (number < 0) {
            log.warn("RedisUtils.incr 发生异常");
            throw new RuntimeException("递减因子必须大于0");
        }
        return template.opsForValue().increment(key, -number);
    }

/******************************************  hash类型数据操作  ******************************************************/

    /**
     * 获取hash
     *
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key, String item) {
        return template.opsForHash().get(key, item);
    }

    /**
     * 根据key获取对应的所有键值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hmGet(String key) {
        return template.opsForHash().entries(key);
    }

    /**
     * 设置hashKey
     *
     * @param key
     * @param map
     * @return
     */
    public boolean hmSet(String key, Map<Object, Object> map) {
        try {
            template.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.warn("RedisUtils.hmSet 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 设置带有时间的hashKey
     *
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean hmSet(String key, Map<Object, Object> map, long time) {
        try {
            template.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.warn("RedisUtils.hmSet(.,.,.) 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 向一个hash表中存放数据，如果不存在则创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            template.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.warn("RedisUtils.hSet 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 向一个hash表中存放数据，如果不存在则创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间（秒）
     *              如果已存在的hash表有时间，这里将会替换原有的时间
     * @return
     */
    public boolean hSet(String key, String item, Object value, long time) {
        try {
            template.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.warn("RedisUtils.hSet(.,.,.,.) 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 删除hash中的key值
     *
     * @param key
     * @param itme
     */
    public void delHash(String key, Object... itme) {
        template.opsForHash().delete(key, itme);
    }

    /**
     * 判断hash中是否存在该值
     *
     * @param key
     * @param item
     * @return
     */
    public boolean hasHashKey(String key, String item) {
        return template.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增,如果key不存在，则新建，并将递增后的值返回
     *
     * @param key
     * @param item
     * @param number
     * @return
     */
    public double hIncr(String key, String item, double number) {
        return template.opsForHash().increment(key, item, number);
    }

    /**
     * hash递减
     *
     * @param key
     * @param item
     * @param number
     * @return
     */
    public double hDecr(String key, String item, double number) {
        return template.opsForHash().increment(key, item, -number);
    }

/******************************************  set类型数据操作  ******************************************************/

    /**
     * 获取set类型值
     *
     * @param key
     * @return
     */
    public Set sGet(String key) {
        try {
            return template.opsForSet().members(key);
        } catch (Exception e) {
            log.warn("RedisUtils.sGet 发生异常: {})", e.getCause());
            return null;
        }
    }

    /**
     * 判断set类型数据中是否存在该key
     *
     * @param key
     * @param value
     * @return
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return template.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.warn("RedisUtils.sHasKey 发生异常: {})", e.getCause());
            return false;
        }
    }

    /**
     * 设置set类型数据
     *
     * @param key
     * @param values
     * @return
     */
    public long sSet(String key, Object... values) {
        try {
            return template.opsForSet().add(key, values);
        } catch (Exception e) {
            log.warn("RedisUtils.sSet 发生异常: {}", e.getCause());
            return 0;
        }
    }

    /**
     * 设置带有过期时间的set类型数据
     *
     * @param key
     * @param time
     * @param values
     * @return
     */
    public long sSetWithTime(String key, long time, Object... values) {
        try {
            Long count = template.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.warn("RedisUtils.sSetWithTime 发生异常: {}", e.getCause());
            return 0;
        }
    }

    /**
     * 获取set类型数据长度
     *
     * @param key
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return template.opsForSet().size(key);
        } catch (Exception e) {
            log.warn("RedisUtils.sGetSetSize 发生异常: {}", e.getCause());
            return 0;
        }
    }

    /**
     * 移除set类型数据中指定value的key
     *
     * @param key
     * @param values
     * @return
     */
    public long sRemove(String key, Object... values) {
        try {
            return template.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.warn("RedisUtils.sRemove 发生异常: {}", e.getCause());
            return 0;
        }
    }

    /******************************************  list类型数据操作  ******************************************************/

    /**
     * 获取list数据内容
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lGet(String key, long start, long end){
        try{
            return template.opsForList().range(key, start, end);
        }catch (Exception e){
            log.warn("RedisUtils.lGet 发生异常: {}", e.getCause());
            return null;
        }
    }

    /**
     * 获取指定list数据的长度
     * @param key
     * @return
     */
    public long lGetSize(String key){
        try {
            return template.opsForList().size(key);
        }catch (Exception e){
            log.warn("RedisUtils.lGetSize 发生异常: {}", e.getCause());
            return 0;
        }
    }

    /**
     * 根据下标获取list数据中的值
     * @param key
     * @param index
     * @return
     */
    public Object lGetByIndex(String key, long index){
        try {
            return template.opsForList().index(key, index);
        }catch (Exception e){
            log.warn("RedisUtils.lGetByIndex 发生异常: {}", e.getCause());
            return null;
        }
    }

    /**
     * 存放list类型的值
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, Object value){
        try{
            template.opsForList().rightPush(key, value);
            return true;
        }catch (Exception e){
            log.warn("RedisUtils.lSet 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 创建带有过期时间的list值
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lSet(String key, Object value, long time){
        try{
            template.opsForList().rightPush(key, value);
            if(time > 0){
                expire(key, time);
            }
            return true;
        }catch (Exception e){
            log.warn("RedisUtils.lSet(.,.,.) 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 存放list对象
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, List<Object> value){
        try{
            template.opsForList().rightPush(key, value);
            return true;
        }catch (Exception e){
            log.warn("RedisUtils.lSet(.,.) 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 将带有过期时间的list对象放入redis
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            template.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.warn("RedisUtils.lSet(.,.,.) 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 根据index修改list数据
     * @param key
     * @param index
     * @param value
     * @return
     */
    public boolean lUpdateByIndex(String key, long index, Object value){
        try{
            template.opsForList().set(key, index, value);
            return true;
        }catch (Exception e){
            log.warn("RedisUtils.lUpdateByIndex 发生异常: {}", e.getCause());
            return false;
        }
    }

    /**
     * 删除key指定个数的值
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long lRemove(String key, long count, Object value){
        try{
            return template.opsForList().remove(key, count, value);
        }catch (Exception e){
            log.warn("RedisUtils.lRemove 发生异常: {}", e.getCause());
            return 0;
        }
    }

}