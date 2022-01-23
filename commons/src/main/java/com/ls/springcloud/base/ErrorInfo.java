/**
 * Copyright (c) 2016 上海民航华东凯亚系统集成有限公司 版权所有
 * East China Cares System Limited. All rights reserved.
 */
package com.ls.springcloud.base;

/** 
* @ClassName: ErrorInfo 
* @Description: 错误信息 
* @author
* @date 2016年6月9日 上午11:24:16 
*  
*/
public class ErrorInfo {
	/**
	 * 
	 */
	private String code = "";
	/**
	 * 
	 */
	private String level = "";
	/**
	 * 
	 */
	private String enDesc = "";
	/**
	 * 
	 */
	private String cnDesc = "";
	/** 
	* 设置错误代码
	* @param val 错误代码
	*/
	public void setCode(String val){
		code = val;
	}
	/** 
	* 获取错误代码
	* @return 错误代码
	*/
	public String getCode(){
		return code;
	}
	/** 
	* 设置错误等级
	* @param val 错语等级
	*/
	public void setLevel(String val){
		level = val;
	}
	/** 
	* 设置错误等级
	* @return 错误等级
	*/
	public String getLevel(){
		return level;
	}
	/** 
	* 获取错误英文描述 
	* @return 错误英文描述
	*/
	public String getEnDesc(){
		return enDesc;
	}
	/** 
	* 设置错误英文描述
	* @param val 错误英文描述
	*/
	public void setEnDesc(String val){
		enDesc = val;
	}
	/** 
	* 设置错误中文描述
	* @param val 错误中文描述
	*/
	public void setCnDesc(String val){
		cnDesc = val;
	}
	/** 
	* 获取错误中文描述 
	* @return 错误中文描述
	*/
	public String getCnDesc(){
		return cnDesc;
	}
}
