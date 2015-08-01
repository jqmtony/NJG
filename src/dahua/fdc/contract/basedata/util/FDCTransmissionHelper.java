package com.kingdee.eas.fdc.basedata.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.PatternSyntaxException;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;
import common.Logger;

public class FDCTransmissionHelper {
	private static Logger logger = Logger.getLogger(FDCTransmissionHelper.class); 
	
	/** 判断字符串是否为空 */
	public static void isFitLength(String alertMsg, String value)
			throws TaskExternalException {
		if(value == null||value.trim().length()==0){
			isThrow(alertMsg);
		}
	}

	/** 判断对应字符串长度是否符合指定长度 */
	public static void isFitLength(String value, int length, String alertMsg)
			throws TaskExternalException {
		if(value!=null&&value.trim().length()!=0){
			if(value.trim().length()>length) isThrow(alertMsg);
		}
	}
	
	/** 向上抛出指定Msg的异常 */
	public static void isThrow(String alertMsg) throws TaskExternalException {
		isThrow(null, alertMsg);
	}
	
	/** 向上抛出指定Msg的异常 */
	public static void isThrow(String value, String alertMsg)
			throws TaskExternalException {
		String msg = (value == null) ? ("" + alertMsg) : (value.trim() + " " + alertMsg);
		throw new TaskExternalException(msg);
	}
	
	/**
	 * 	从Hashtable中提取字段值
	 * @param hsData  数据源	
	 * @param key	键
	 * @return	根据键返回值	
	 */
	public static String getFieldValue(Hashtable hsData, String key) {
		return ((String) ((DataToken) hsData.get(key)).data).trim();
	}
	
	
	 /**
	 * 描述：String转换为日期，String格式要求为：yyyy-mm-dd，时间清0
	 */
	public static Date strToDate(String str) {
		Calendar cal = Calendar.getInstance();
		if (!StringUtils.isEmpty(str)) {
			String[] strArray = str.split("-");
			cal.set(Calendar.YEAR, Integer.parseInt(strArray[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(strArray[1])-1);
			cal.set(Calendar.DATE, Integer.parseInt(strArray[2]));
		}
		return DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 * 将String  转换成boolean 类型  
	 * str是将要进行转换的值， trueValue是str为 真的时候  的值
	 * str=trueVlue返回true；!=或者为空的时候返回false
	 * 例如：合同录入中“是否确认变更金额-FIsSureChangeAmt”，模板上要求 为真的时候 填写“是”
	 * strToBoolean(FIsSureChangeAmt,"是")这样就能转换成true
	 */
	public static boolean strToBoolean(String str,String trueValue){
		if(str.trim().equals(trueValue)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 将String  转换成BigDecimal 类型 
	 * str是将要进行转换的值(格式:数字的字符串) str为空返回0
	 */
	public static BigDecimal strToBigDecimal(String str){
		if(str==null || str.trim().equals(""))
			return FDCHelper.toBigDecimal(str, 2);
		else	
		    return FDCHelper.toBigDecimal(str);
	}
	
	/**
	 * 将String  转换成Double 类型 
	 * str是将要进行转换的值(格式:数字的字符串) str为空返回0
	 */
	public static double strToDouble(String str){
		if(str==null || str.trim().equals(""))
			return 0.00;
		else	
		    return Double.parseDouble(str);
	}
	
	/**
	 * 将String  转换成int 类型 
	 * str是将要进行转换的值(格式:数字的字符串) str为空返回0
	 */
	public static int strToInt(String str){
		if(str==null || str.trim().equals(""))
			return 0;
		else	
		    return Integer.parseInt(str);
	}
	
	/**
	* 判断日期格式是否正确
	* 
	* @param date 要验证的数据
	* @param format 验证规则
	* @return true/fase 通过/不通过
	*/
	public static boolean isDateFormat(Object date,String format){
		 
		boolean isDateFormat = false ;
		if(null != date && !"".equals(date.toString().trim()) && date.toString().trim().matches(format)){
			isDateFormat = true ;
		}
		return isDateFormat ;
	}

	
	
	/**
	 * 判断取到的数据的格式是否合法 、 必填项是否为空、 数据的长度是不是过长   的方法  
	 * 
	 * chinaName-字段的中文名字         
	 * value-字段的值      
	 * dataFormat-字段的数据类型 分为 String字符  Double数字(格式：number(19,2))  Data日期(格式：yyyy-mm-dd)
	 * b 判断是否为必填的字段 true是必填  false不是必填
	 * length-字段最大的长度   -1的时候不判断      
	 * 例如：
	 * valueFormat("审批单据号", FChangeAuditNumber, "String", false, 40)审批单据号 字符型 不是必填项 最大长度不超过40
	 * valueFormat("结算金额原币", FOriBalanceAmount, "Double", true, -1)结算金额原币 数字型 是必填项 不用判断长度
	 * 日期格式填写成2011-03-56这样的错误日期也会有提示
	 */
    public static void valueFormat(String chinaName,String value,String dataFormat,boolean b,int length) throws TaskExternalException{
    	
    	if(dataFormat.equals("String")){//字符的情况
    		if(null != value && !"".equals(value.trim())){
    			if(value.trim().length() > length){
    				isThrow(chinaName,"长度不能大于"+length+"个字符！");
    			}
    		}else{
    			if(b){//为空的情况  但是是必填的字段
    				isThrow(chinaName,"不能为空！");	
    			}
    		}
    		
    	}else if(dataFormat.equals("Double")){//数据的情况
			if(null != value && !"".equals(value.trim()) ){
				if(!value.matches("([1-9]\\d{0,16}(.)\\d{0,2})|(0(.)\\d{0,2})||([0-9]\\d{0,16})")){
					isThrow(chinaName,"必须以 1－17 位整数或加 1－2位小数构成！" );
        		}
			}else{
				if(b){//为空的情况  但是是必填的字段
    				isThrow(chinaName,"不能为空！");	
    			}
			}
	
    	}else if(dataFormat.equals("Date")){//日期的情况 
    		if(!StringUtils.isEmpty(value)){
				if(value.matches("([1-9]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])")){
					String[] strlen = value.split("-");//分离字符串  取得年月日
					int yearInt = Integer.parseInt(strlen[0]);
					int monthInt = Integer.parseInt(strlen[1]);
					int dayInt = Integer.parseInt(strlen[2]);
					if((yearInt%400==0 || (yearInt%4==0 && yearInt%100!=0)) && monthInt==2 && dayInt>=30 ){//如果是润年
					    isThrow(chinaName+"中填写的日期："+value+"是不正确的日期，","润年的 2月没有"+dayInt+"号 ！" );
					}else if(yearInt%4!=0 && monthInt==2 && dayInt>=29){//不是润年
						isThrow(chinaName+"中填写的日期："+value+"是不正确的日期，","2月没有"+dayInt+"号 ！" );
					}
					if((monthInt==4 || monthInt==6 || monthInt==9 || monthInt==11) && dayInt>=31){
						isThrow(chinaName+"中填写的日期："+value+"是不正确的日期，",monthInt+"月没有"+dayInt+"号 ！" );
					}
        		}else{
        			isThrow(chinaName,"的格式必须是  “yyyy-mm-dd” ！并确保填写的是正确的日期。" );
        		}
    			DateFormat df = null;
    			df = new SimpleDateFormat("yyyy-MM-dd");
    			try {
    				df.setLenient(false);
    				df.parse(value.trim());
    			} catch (ParseException e) {
    				// @AbortException
    				logger.error(e.getMessage(), e);
    				isThrow(chinaName, "日期格式必须是  “yyyy-mm-dd” ！并确保填写的是正确的日期。");
    			}
			}else{
				if(b){//为空的情况  但是是必填的字段
					isThrow(chinaName, "不能为空！");	
    			}
			}
    	}
    	
		
	}

	/**
	 * 判断boolean 类型的值是否合法
	 * @param chineseName 中文名字
	 * @param value 值
	 * @param isRequire 是否必填
	 * @param tValue true的值，即“是”
	 * @param fValue false的值，即“否”
	 * @param length 允许的长度。当判断的长度为-1的时候 不需要再判断长度
	 */
    public static void isBoolean(String chineseName, String value, boolean isRequire, String tValue, String fValue, int length)
			throws TaskExternalException {
		if (isRequire) {
			if (StringUtils.isEmpty(value)) {
				isThrow(chineseName, "不能为空！");
			}
		}
		if (value.trim().equalsIgnoreCase(tValue) || value.trim().equalsIgnoreCase(fValue)) {
			if (length != -1 && value.length() > length) {
				isThrow(chineseName, "的长度不能大于" + length + "个字符！");
			}
		} else {//填写了该项目  但不是给定的值
			isThrow(chineseName, "只能是" + tValue + "或者" + fValue + "！");
		}
    }
    
	/**
	 * 判断 数字型的数据是否合法  必填项是否为空、 数据的长度是不是过长   的方法  
	 * name 中文名字 value 值  b是否必填 iv整数位 fv小数位
	 */
	public static void bdValueFormat(String name,String value,boolean b,int iv,int fv) throws TaskExternalException{
		if(null != value && !"".equals(value.trim()) ){
			if(!value.matches("([1-9]\\d{0,"+(iv-1)+"}(.)\\d{0,"+fv+"})|(0(.)\\d{0,"+fv+"})||([0-9]\\d{0,"+(iv-1)+"})")){
				FDCTransmissionHelper.isThrow(name,"必须以 1－"+iv+"位整数或加 1－"+fv+"位小数构成！" );
    		}
		}else{
			if(b){//为空的情况  但是是必填的字段
				FDCTransmissionHelper.isThrow(name,"不能为空！");	
			}
		}
	}
    
    /** 
	 * 指定正则表达式匹配value字符串
	 * value需要被匹配的字符串
	 * regex指定的正则表达式 如果为空或者表达式语法无效 返回false
	 */
	public static boolean isMatches(String value,String regex){
		if(value==null) value="";
		if(regex==null) return false;
		try {
			return value.matches(regex);
		} catch (PatternSyntaxException e) {
			// @AbortException
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 将字符串转为为0-100的BigDecimal类型
	 * 如果字符串的字面值不是数值或范围不在0-100，都返回null
	 */
	public static BigDecimal isRangedInHundred(String value) throws TaskExternalException{
		String regex="^1?\\d{1,2}(\\.\\d+)?$";
		value = (value == null || value.trim().equals("")) ? "" : value.trim();   
		
		if (isMatches(value, regex)) {
			double d = Double.parseDouble(value);
			BigDecimal decimal = d > 100 ? null : new BigDecimal(d);
			return decimal;
		} else {
			return null;
		}
	}
	

	/**
	 * 将字符串转换成BigDecimal类型
	 * 如果字符串字面量非有效数值，抛出提示
	 * 返回的BigDecimal值保留两位小数
	 */
	public static BigDecimal str2BigDecimal(String str) throws TaskExternalException {
		String regex="^\\d+(\\.\\d+)?$";
		str = (str == null) ? "" : str.trim();
		
		if(!FDCTransmissionHelper.isMatches(str, regex)){
			FDCTransmissionHelper.isThrow(str, "请输入正确的数值!");
		}
		
		return new BigDecimal(str).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 校验日期格式，如果用户录入的格式不正确，给出出错异常提示信息
	 * @author 郑杰元
	 */
	public static Date checkDateFormat(String dateStr, String msg) throws TaskExternalException{
		DateFormat df = null;
		if(dateStr.trim().length() <= "yyyy-MM-dd".length()){ // 处理 "yyyy-MM-d"
			df = new SimpleDateFormat("yyyy-MM-dd");
		}else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm".length()){ //处理 yyyy-MM-d HH:mm情况
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
		}else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm:ss".length()){
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		}else{
			throw new TaskExternalException(msg);
		}
		try {
			df.setLenient(false);
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new TaskExternalException(msg);
		}
	}
	
	/**
	 * 判断是否是数字	
	 * @param value 要判断的值
	 * @return	true/false		
	 */
	public static boolean isNumber(String value) {
		return value.matches("^[-+]?\\d+(\\.\\d+)?$");
	}
}
