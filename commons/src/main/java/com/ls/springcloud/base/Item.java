package com.ls.springcloud.base;

import com.ls.springcloud.utils.DateTimeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @ClassName Item
 * @Description
 * @Author lushuai
 * @Date 2019/11/13 10:05
 */
public class Item implements Serializable,Map {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return paramMap.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }

    /**
     * 值类型
     */
    enum ItemType{
        Null,
        String,
        Long,
        Double,
        Boolean,
        List,
        Map
    }

    private static final long serialVersionUID = -901358723562212189L;

    private Object item;

    private ItemType type = ItemType.Null;

    private static Map<String, Object> paramMap = new HashMap<>();
    private static HttpServletRequest request;
    private HttpServletResponse response;

    public Item(HttpServletRequest request){
        this.request = request;
        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator iterator = parameterMap.entrySet().iterator();
        Entry entries;
        String key;
        String value = "";

        while (iterator.hasNext()) {
            entries = (Entry) iterator.next();
            key = (String) entries.getKey();
            Object objValue = entries.getValue();
            if (objValue == null) {
                value = "";
            } else if (objValue instanceof String[]) {
                String values[] = (String[]) objValue;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = objValue.toString();
            }
            paramMap.put(key, value);
        }
    }

    public Item(){
        this.item = paramMap;
    }

    public static Map<String, Object> getPram(){
        return paramMap;
    }

    public Item(String value){
        if(value == null){
            value = "";
        }
        this.item = value.trim();
        this.type = ItemType.String;
    }

    public Item(long value){
        this.item = value+"";
        this.type = ItemType.Long;
    }
    public Item(double value){
        this.item = value+"";
        this.type = ItemType.Double;
    }
    public Item(boolean value){
        this.item = value ? "true" : "false";
        this.type = ItemType.Boolean;
    }
    public Item(List value){
        this.item = value;
        this.type = ItemType.List;
    }

    /**
     * 获取字符串类型
     * @return
     */
    public String getStringColumn(String key){
//        if(this.item instanceof String){
//            return (String) this.item;
//        }
//        return "";
        this.item = this.get(key);
        return (String)item;
    }

    /**
     * 获取整型
     * @return
     */
    public int getIntegerColumn(String key){
//        if(this.item instanceof Integer){
//            return StringUtils.getInteger((String) this.item);
//        }
        this.item = this.get(key);
        return (int)item;
    }

    /**
     * 获取长整型
     * @return
     */
    public long getLongColumn(String key){
//        if(this.item instanceof Long){
//            return StringUtils.getLong((String) this.item);
//        }
        this.item = this.get(key);
        return Long.parseLong((String)item);
    }

    /**
     * 获取双精度
     * @return
     */
    public double getDoubleColumn(String key){
//        if(this.item instanceof Double){
//            return StringUtils.getDouble((String) this.item);
//        }
        this.item = this.get(key);
        return (double)item;
    }

    /**
     * 自动识别日期格式获取日期
     * @return
     */
//    public Date getDateColumn(String key){
//        this.item = this.get(key);
//        if(this.item instanceof Date){
//            return DateTimeUtils.getDate((String) this.item);
//        }
//        return null;
//    }

    /**
     * 根据格式获取日期
     * @param key
     * @param format
     * @return
     */
    public Date getDateColumn(String key, String format){
        this.item = this.get(key);
        if(this.item instanceof Date){
            return DateTimeUtils.getDate((String) this.item, format);
        }
        return null;
    }

    /**
     * 获取boolean
     * @return
     */
    public boolean getBooleanColumn(){
        return "true".equals(this.item);
    }

    /**
     * 生成当前值对象的深拷贝
     * @return
     */
    public Item copy(){
        try {
            /**
             * 将对象写入流中
             */
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objOutoutStream = new ObjectOutputStream(outputStream);
            objOutoutStream.writeObject(this.item);
            /**
             * 从流中读取
             */
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            return (Item) objInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setResponse(HttpServletResponse response){
        this.response = response;
    }

    public HttpServletResponse getResponse(){
        return this.response;
    }

    public static void setSession(String key, Object value){
        request.getSession().setAttribute(key, value);
    }

    public static void removeSession(String key){
        request.getSession().removeAttribute(key);
    }

}
