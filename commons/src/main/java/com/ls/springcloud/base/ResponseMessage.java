package com.ls.springcloud.base;

import com.ls.springcloud.annotion.Language;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @ClassName ResponseMessage
 * @Description
 * @Author lushuai
 * @Date 2020/1/17 13:38
 */
public class ResponseMessage {

    private static HashMap<Long, ErrorInfo> errorItems= null;

    // 初始化响应信息
    static{
        errorItems = new HashMap<>();
        Field[] fields = ResponseMessage.class.getFields();
        for (Field field : fields){
            long code = 0;
            try {
                code = (Long)field.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Language language = field.getAnnotation(Language.class);
            if(language != null){
                ErrorInfo errorInfo = new ErrorInfo();
                errorInfo.setCnDesc(language.Chinese());
                errorInfo.setEnDesc(language.English());
                errorItems.put(code, errorInfo);
            }
        }
    }

    /**
     * 获取错误描述
     * @param code
     * @param language
     * @return
     */
    public static String getDesc(long code, String language){
        String ret = "";
        if(errorItems.containsKey(code)){
            ErrorInfo errorInfo = errorItems.get(code);
            if(language.equalsIgnoreCase("en")) {
                ret = errorInfo.getEnDesc();
            }else if(language.equalsIgnoreCase("cn")){
                ret = errorInfo.getCnDesc();
            }else{
                ret = errorInfo.getCnDesc();
            }
        }
        return ret;
    }

    /**
     * 设置错误信息
     * @return
     */
    public static String setError(String language, ResultSet resultSet, long code, Object ... value){
        String desc = ResponseMessage.getDesc(code, language);
        String errorDesc = desc;
        try {
            errorDesc = String.format(desc, value);
        }catch (Exception e){
            e.printStackTrace();
        }
        resultSet.setCode(Math.toIntExact(code)).setMsg(errorDesc);
        return errorDesc;
    }

    @Language(Chinese = "系统异常", English = "System exception")
    public static final long SYSTEM_EXCEPTION = 1001;

    @Language(Chinese = "认证失败", English = "Authentication failed")
    public static final long AUTHENTICATION_FAILED = 1002;

    @Language(Chinese = "服务调用出错", English = "Service invoked error")
    public static final long SERVICE_INVOKED_ERROR = 1003;


}
