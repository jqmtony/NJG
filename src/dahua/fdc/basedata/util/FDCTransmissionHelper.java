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
	
	/** �ж��ַ����Ƿ�Ϊ�� */
	public static void isFitLength(String alertMsg, String value)
			throws TaskExternalException {
		if(value == null||value.trim().length()==0){
			isThrow(alertMsg);
		}
	}

	/** �ж϶�Ӧ�ַ��������Ƿ����ָ������ */
	public static void isFitLength(String value, int length, String alertMsg)
			throws TaskExternalException {
		if(value!=null&&value.trim().length()!=0){
			if(value.trim().length()>length) isThrow(alertMsg);
		}
	}
	
	/** �����׳�ָ��Msg���쳣 */
	public static void isThrow(String alertMsg) throws TaskExternalException {
		isThrow(null, alertMsg);
	}
	
	/** �����׳�ָ��Msg���쳣 */
	public static void isThrow(String value, String alertMsg)
			throws TaskExternalException {
		String msg = (value == null) ? ("" + alertMsg) : (value.trim() + " " + alertMsg);
		throw new TaskExternalException(msg);
	}
	
	/**
	 * 	��Hashtable����ȡ�ֶ�ֵ
	 * @param hsData  ����Դ	
	 * @param key	��
	 * @return	���ݼ�����ֵ	
	 */
	public static String getFieldValue(Hashtable hsData, String key) {
		return ((String) ((DataToken) hsData.get(key)).data).trim();
	}
	
	
	 /**
	 * ������Stringת��Ϊ���ڣ�String��ʽҪ��Ϊ��yyyy-mm-dd��ʱ����0
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
	 * ��String  ת����boolean ����  
	 * str�ǽ�Ҫ����ת����ֵ�� trueValue��strΪ ���ʱ��  ��ֵ
	 * str=trueVlue����true��!=����Ϊ�յ�ʱ�򷵻�false
	 * ���磺��ͬ¼���С��Ƿ�ȷ�ϱ�����-FIsSureChangeAmt����ģ����Ҫ�� Ϊ���ʱ�� ��д���ǡ�
	 * strToBoolean(FIsSureChangeAmt,"��")��������ת����true
	 */
	public static boolean strToBoolean(String str,String trueValue){
		if(str.trim().equals(trueValue)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ��String  ת����BigDecimal ���� 
	 * str�ǽ�Ҫ����ת����ֵ(��ʽ:���ֵ��ַ���) strΪ�շ���0
	 */
	public static BigDecimal strToBigDecimal(String str){
		if(str==null || str.trim().equals(""))
			return FDCHelper.toBigDecimal(str, 2);
		else	
		    return FDCHelper.toBigDecimal(str);
	}
	
	/**
	 * ��String  ת����Double ���� 
	 * str�ǽ�Ҫ����ת����ֵ(��ʽ:���ֵ��ַ���) strΪ�շ���0
	 */
	public static double strToDouble(String str){
		if(str==null || str.trim().equals(""))
			return 0.00;
		else	
		    return Double.parseDouble(str);
	}
	
	/**
	 * ��String  ת����int ���� 
	 * str�ǽ�Ҫ����ת����ֵ(��ʽ:���ֵ��ַ���) strΪ�շ���0
	 */
	public static int strToInt(String str){
		if(str==null || str.trim().equals(""))
			return 0;
		else	
		    return Integer.parseInt(str);
	}
	
	/**
	* �ж����ڸ�ʽ�Ƿ���ȷ
	* 
	* @param date Ҫ��֤������
	* @param format ��֤����
	* @return true/fase ͨ��/��ͨ��
	*/
	public static boolean isDateFormat(Object date,String format){
		 
		boolean isDateFormat = false ;
		if(null != date && !"".equals(date.toString().trim()) && date.toString().trim().matches(format)){
			isDateFormat = true ;
		}
		return isDateFormat ;
	}

	
	
	/**
	 * �ж�ȡ�������ݵĸ�ʽ�Ƿ�Ϸ� �� �������Ƿ�Ϊ�ա� ���ݵĳ����ǲ��ǹ���   �ķ���  
	 * 
	 * chinaName-�ֶε���������         
	 * value-�ֶε�ֵ      
	 * dataFormat-�ֶε��������� ��Ϊ String�ַ�  Double����(��ʽ��number(19,2))  Data����(��ʽ��yyyy-mm-dd)
	 * b �ж��Ƿ�Ϊ������ֶ� true�Ǳ���  false���Ǳ���
	 * length-�ֶ����ĳ���   -1��ʱ���ж�      
	 * ���磺
	 * valueFormat("�������ݺ�", FChangeAuditNumber, "String", false, 40)�������ݺ� �ַ��� ���Ǳ����� ��󳤶Ȳ�����40
	 * valueFormat("������ԭ��", FOriBalanceAmount, "Double", true, -1)������ԭ�� ������ �Ǳ����� �����жϳ���
	 * ���ڸ�ʽ��д��2011-03-56�����Ĵ�������Ҳ������ʾ
	 */
    public static void valueFormat(String chinaName,String value,String dataFormat,boolean b,int length) throws TaskExternalException{
    	
    	if(dataFormat.equals("String")){//�ַ������
    		if(null != value && !"".equals(value.trim())){
    			if(value.trim().length() > length){
    				isThrow(chinaName,"���Ȳ��ܴ���"+length+"���ַ���");
    			}
    		}else{
    			if(b){//Ϊ�յ����  �����Ǳ�����ֶ�
    				isThrow(chinaName,"����Ϊ�գ�");	
    			}
    		}
    		
    	}else if(dataFormat.equals("Double")){//���ݵ����
			if(null != value && !"".equals(value.trim()) ){
				if(!value.matches("([1-9]\\d{0,16}(.)\\d{0,2})|(0(.)\\d{0,2})||([0-9]\\d{0,16})")){
					isThrow(chinaName,"������ 1��17 λ������� 1��2λС�����ɣ�" );
        		}
			}else{
				if(b){//Ϊ�յ����  �����Ǳ�����ֶ�
    				isThrow(chinaName,"����Ϊ�գ�");	
    			}
			}
	
    	}else if(dataFormat.equals("Date")){//���ڵ���� 
    		if(!StringUtils.isEmpty(value)){
				if(value.matches("([1-9]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])")){
					String[] strlen = value.split("-");//�����ַ���  ȡ��������
					int yearInt = Integer.parseInt(strlen[0]);
					int monthInt = Integer.parseInt(strlen[1]);
					int dayInt = Integer.parseInt(strlen[2]);
					if((yearInt%400==0 || (yearInt%4==0 && yearInt%100!=0)) && monthInt==2 && dayInt>=30 ){//���������
					    isThrow(chinaName+"����д�����ڣ�"+value+"�ǲ���ȷ�����ڣ�","����� 2��û��"+dayInt+"�� ��" );
					}else if(yearInt%4!=0 && monthInt==2 && dayInt>=29){//��������
						isThrow(chinaName+"����д�����ڣ�"+value+"�ǲ���ȷ�����ڣ�","2��û��"+dayInt+"�� ��" );
					}
					if((monthInt==4 || monthInt==6 || monthInt==9 || monthInt==11) && dayInt>=31){
						isThrow(chinaName+"����д�����ڣ�"+value+"�ǲ���ȷ�����ڣ�",monthInt+"��û��"+dayInt+"�� ��" );
					}
        		}else{
        			isThrow(chinaName,"�ĸ�ʽ������  ��yyyy-mm-dd�� ����ȷ����д������ȷ�����ڡ�" );
        		}
    			DateFormat df = null;
    			df = new SimpleDateFormat("yyyy-MM-dd");
    			try {
    				df.setLenient(false);
    				df.parse(value.trim());
    			} catch (ParseException e) {
    				// @AbortException
    				logger.error(e.getMessage(), e);
    				isThrow(chinaName, "���ڸ�ʽ������  ��yyyy-mm-dd�� ����ȷ����д������ȷ�����ڡ�");
    			}
			}else{
				if(b){//Ϊ�յ����  �����Ǳ�����ֶ�
					isThrow(chinaName, "����Ϊ�գ�");	
    			}
			}
    	}
    	
		
	}

	/**
	 * �ж�boolean ���͵�ֵ�Ƿ�Ϸ�
	 * @param chineseName ��������
	 * @param value ֵ
	 * @param isRequire �Ƿ����
	 * @param tValue true��ֵ�������ǡ�
	 * @param fValue false��ֵ��������
	 * @param length ����ĳ��ȡ����жϵĳ���Ϊ-1��ʱ�� ����Ҫ���жϳ���
	 */
    public static void isBoolean(String chineseName, String value, boolean isRequire, String tValue, String fValue, int length)
			throws TaskExternalException {
		if (isRequire) {
			if (StringUtils.isEmpty(value)) {
				isThrow(chineseName, "����Ϊ�գ�");
			}
		}
		if (value.trim().equalsIgnoreCase(tValue) || value.trim().equalsIgnoreCase(fValue)) {
			if (length != -1 && value.length() > length) {
				isThrow(chineseName, "�ĳ��Ȳ��ܴ���" + length + "���ַ���");
			}
		} else {//��д�˸���Ŀ  �����Ǹ�����ֵ
			isThrow(chineseName, "ֻ����" + tValue + "����" + fValue + "��");
		}
    }
    
	/**
	 * �ж� �����͵������Ƿ�Ϸ�  �������Ƿ�Ϊ�ա� ���ݵĳ����ǲ��ǹ���   �ķ���  
	 * name �������� value ֵ  b�Ƿ���� iv����λ fvС��λ
	 */
	public static void bdValueFormat(String name,String value,boolean b,int iv,int fv) throws TaskExternalException{
		if(null != value && !"".equals(value.trim()) ){
			if(!value.matches("([1-9]\\d{0,"+(iv-1)+"}(.)\\d{0,"+fv+"})|(0(.)\\d{0,"+fv+"})||([0-9]\\d{0,"+(iv-1)+"})")){
				FDCTransmissionHelper.isThrow(name,"������ 1��"+iv+"λ������� 1��"+fv+"λС�����ɣ�" );
    		}
		}else{
			if(b){//Ϊ�յ����  �����Ǳ�����ֶ�
				FDCTransmissionHelper.isThrow(name,"����Ϊ�գ�");	
			}
		}
	}
    
    /** 
	 * ָ��������ʽƥ��value�ַ���
	 * value��Ҫ��ƥ����ַ���
	 * regexָ����������ʽ ���Ϊ�ջ��߱��ʽ�﷨��Ч ����false
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
	 * ���ַ���תΪΪ0-100��BigDecimal����
	 * ����ַ���������ֵ������ֵ��Χ����0-100��������null
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
	 * ���ַ���ת����BigDecimal����
	 * ����ַ�������������Ч��ֵ���׳���ʾ
	 * ���ص�BigDecimalֵ������λС��
	 */
	public static BigDecimal str2BigDecimal(String str) throws TaskExternalException {
		String regex="^\\d+(\\.\\d+)?$";
		str = (str == null) ? "" : str.trim();
		
		if(!FDCTransmissionHelper.isMatches(str, regex)){
			FDCTransmissionHelper.isThrow(str, "��������ȷ����ֵ!");
		}
		
		return new BigDecimal(str).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * У�����ڸ�ʽ������û�¼��ĸ�ʽ����ȷ�����������쳣��ʾ��Ϣ
	 * @author ֣��Ԫ
	 */
	public static Date checkDateFormat(String dateStr, String msg) throws TaskExternalException{
		DateFormat df = null;
		if(dateStr.trim().length() <= "yyyy-MM-dd".length()){ // ���� "yyyy-MM-d"
			df = new SimpleDateFormat("yyyy-MM-dd");
		}else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm".length()){ //���� yyyy-MM-d HH:mm���
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
	 * �ж��Ƿ�������	
	 * @param value Ҫ�жϵ�ֵ
	 * @return	true/false		
	 */
	public static boolean isNumber(String value) {
		return value.matches("^[-+]?\\d+(\\.\\d+)?$");
	}
}
