package com.kingdee.eas.bpm.common;

import java.math.BigDecimal;
import java.util.Properties;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.bpm.BPMServerConfigFactory;
import com.kingdee.eas.bpm.BPMServerConfigInfo;
import com.kingdee.eas.util.client.EASResource;

public class StringUtilBPM{
	/**
	 * 获取BPM服务器地址
	 * */
	public static String getBPMServerURL(){
		String str = "http://10.130.12.20/BPMStart.aspx";
		//String str = "http://bpm.cpmlg.net/BPMStart.aspx";              
		BPMServerConfigInfo info;
		try {
			info = BPMServerConfigFactory.getRemoteInstance().getBPMServerConfigCollection().get(0);
			if(info!=null){
				str = info.getNumber();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return str;
	}
	public static String isNULl(String str)
    {
		if(str == null)
			return "";
		return str.trim();
    }
	public static String getChineseFormat(BigDecimal number, boolean hasFull)
	{
	        if(number == null)
	            return "";
	        String nagative = "";
	        String nagVirText = "";
	        boolean isNagativeB = false;
	        if(number.toString().indexOf("-") >= 0)
	        {
	            nagative = getChineseName("nagative");
	            isNagativeB = true;
	        }
	        Properties decimalPro = getDecimalUnitMapping();
	        Properties integerPro = getIntegerUnitMapping(true);
	        Properties numberPro = getNumberMapping();
	        String valTemp = number.toString();
	        if(isNagativeB)
	            valTemp = valTemp.replace('-', ' ').trim();
	        int dotIndex = valTemp.indexOf(".");
	        String integerStr = "";
	        String integerTempStr = null;
	        String decimalStr = "";
	        String decimalTempStr = null;
	        if(dotIndex == -1)
	        {
	            integerTempStr = valTemp;
	        } else
	        {
	            integerTempStr = valTemp.substring(0, dotIndex);
	            decimalTempStr = valTemp.substring(dotIndex + 1);
	        }
	        if(integerTempStr != null)
	        {
	            int integerLen = integerTempStr.length() - 1;
	            boolean zeroAttached = false;
	            boolean hasTem = true;
	            for(int i = integerLen; i >= 0; i--)
	            {
	                String index = String.valueOf(i);
	                String str = String.valueOf(integerTempStr.charAt(integerLen - i));
	                String numStr = numberPro.getProperty(str);
	                String uombStr = integerPro.getProperty(index);
	                boolean needed = false;
	                if(uombStr.startsWith("*"))
	                {
	                    needed = true;
	                    uombStr = uombStr.substring(1);
	                    if(hasTem && i == 4 && integerLen >= 8)
	                    {
	                        if('0' == integerTempStr.charAt(0) && '0' == integerTempStr.charAt(1) && '0' == integerTempStr.charAt(2) && '0' == integerTempStr.charAt(3) && '0' == integerTempStr.charAt(integerLen - 8) && '0' == integerTempStr.charAt(integerLen - 7) && '0' == integerTempStr.charAt(integerLen - 6) && '0' == integerTempStr.charAt(integerLen - 5) && ('0' != integerTempStr.charAt(integerLen - 4) || '0' != integerTempStr.charAt(integerLen - 3) || '0' != integerTempStr.charAt(integerLen - 2) || '0' != integerTempStr.charAt(integerLen - 1)))
	                            uombStr = getChineseName("zero");
	                        else
	                        if('0' == integerTempStr.charAt(integerLen - 4))
	                            uombStr = "";
	                        hasTem = false;
	                    }
	                }
	                if("0".equals(str))
	                {
	                    if(zeroAttached)
	                    {
	                        numStr = "";
	                        if(needed)
	                        {
	                            zeroAttached = false;
	                            integerStr = integerStr.substring(0, integerStr.length() - 1);
	                        } else
	                        {
	                            uombStr = "";
	                        }
	                    } else
	                    if(!needed)
	                    {
	                        zeroAttached = true;
	                        uombStr = "";
	                    } else
	                    {
	                        zeroAttached = false;
	                        numStr = "";
	                    }
	                } else
	                {
	                    if(i <= 7 && i >= 4)
	                        hasTem = false;
	                    zeroAttached = false;
	                }
	                integerStr = (new StringBuilder()).append(integerStr).append(numStr).append(uombStr).toString();
	            }

	        }
	        boolean integerEmpty = integerStr.length() < 2;
	        if(integerEmpty)
	            integerStr = "";
	        boolean decimalEmpty = true;
	        boolean hasJiao = false;
	        if(decimalTempStr != null)
	        {
	            int decimalLen = decimalTempStr.length();
	            int n = decimalPro.size();
	            n = decimalLen <= n ? decimalLen : n;
	            boolean zeroAttached = false;
	            boolean ignoreLeadingZero = true;
	            for(int j = 0; j < n; j++)
	            {
	                String index = String.valueOf(j);
	                String str = String.valueOf(decimalTempStr.charAt(j));
	                String numStr = numberPro.getProperty(str);
	                String uombStr = decimalPro.getProperty(index);
	                if("0".equals(str))
	                {
	                    if(zeroAttached)
	                    {
	                        numStr = "";
	                        uombStr = "";
	                        if(j == n - 1)
	                            decimalStr = decimalStr.substring(0, decimalStr.length() - 1);
	                    } else
	                    {
	                        if(j < n - 1 && !ignoreLeadingZero)
	                            zeroAttached = true;
	                        else
	                            numStr = "";
	                        uombStr = "";
	                    }
	                } else
	                {
	                    if(j == 0)
	                        hasJiao = true;
	                    else
	                        hasJiao = false;
	                    zeroAttached = false;
	                    ignoreLeadingZero = false;
	                }
	                String temp = (new StringBuilder()).append(numStr).append(uombStr).toString();
	                decimalStr = (new StringBuilder()).append(decimalStr).append(temp).toString();
	            }

	            decimalEmpty = decimalStr.length() < 2;
	            if(decimalEmpty)
	                decimalStr = "";
	            String unDescriptable = "";
	            int index = -1;
	            for(int i = n; i < decimalLen; i++)
	            {
	                char c = decimalTempStr.charAt(i);
	                String numStr = numberPro.getProperty(String.valueOf(c));
	                unDescriptable = (new StringBuilder()).append(unDescriptable).append(numStr).toString();
	                if('0' != c)
	                    index = i;
	            }

	            if(index >= 0)
	            {
	                decimalEmpty = false;
	                if('0' == decimalTempStr.charAt(n - 1))
	                    decimalStr = (new StringBuilder()).append(decimalStr).append(getChineseName("zero")).append(decimalPro.getProperty(String.valueOf(n - 1))).toString();
	                decimalStr = (new StringBuilder()).append(decimalStr).append(unDescriptable.substring(0, (index - n) + 1)).toString();
	            } else
	            if(decimalTempStr.length() > 1)
	            {
	                if('0' == decimalTempStr.charAt(0) && '0' != decimalTempStr.charAt(1))
	                    decimalStr = (new StringBuilder()).append(getChineseName("zero")).append(decimalStr).toString();
	            } else
	            if('0' == decimalTempStr.charAt(0))
	                decimalStr = (new StringBuilder()).append(getChineseName("zero")).append(decimalStr).toString();
	        }
	        if(integerEmpty && decimalEmpty)
	            integerStr = (new StringBuilder()).append(getChineseName("zero")).append(getChineseName("money")).toString();
	        String value = (new StringBuilder()).append(nagative).append(nagVirText).append(integerStr).append(decimalStr).toString();
	        value = (new StringBuilder()).append(nagative).append(nagVirText).append(integerStr).append(decimalStr).toString();
	        if(decimalStr == null || decimalStr.length() == 0 || decimalStr.length() == 1 || hasJiao)
	            value = (new StringBuilder()).append(value).append(getChineseName("full")).toString();
	        return value;
	    }
	 
	    private static String getChineseName(String key)
	    {
	        return EASResource.getString("com.kingdee.eas.fi.gl.GLResource", key);
	    }
	    
	    private static Properties getDecimalUnitMapping()
	    {
	        Properties decimalCurrencyUomb = new Properties();
	        decimalCurrencyUomb.setProperty("0", getChineseName("jiao"));
	        decimalCurrencyUomb.setProperty("1", getChineseName("fen"));
	        decimalCurrencyUomb.setProperty("2", getChineseName("li"));
	        return decimalCurrencyUomb;
	    }
	    
	    
	    
	    
	    private static Properties getIntegerUnitMapping(boolean flag)
	    {
	        Properties integerCurrencyUomb = new Properties();
	        if(flag)
	            integerCurrencyUomb.setProperty("0", (new StringBuilder()).append("*").append(getChineseName("money")).toString());
	        else
	            integerCurrencyUomb.setProperty("0", "*");
	        integerCurrencyUomb.setProperty("1", getChineseName("ten"));
	        integerCurrencyUomb.setProperty("2", getChineseName("handred"));
	        integerCurrencyUomb.setProperty("3", getChineseName("thrasand"));
	        integerCurrencyUomb.setProperty("4", (new StringBuilder()).append("*").append(getChineseName("tenshand")).toString());
	        integerCurrencyUomb.setProperty("5", getChineseName("ten"));
	        integerCurrencyUomb.setProperty("6", getChineseName("handred"));
	        integerCurrencyUomb.setProperty("7", getChineseName("thrasand"));
	        integerCurrencyUomb.setProperty("8", (new StringBuilder()).append("*").append(getChineseName("handredmill")).toString());
	        integerCurrencyUomb.setProperty("9", getChineseName("ten"));
	        integerCurrencyUomb.setProperty("10", getChineseName("handred"));
	        integerCurrencyUomb.setProperty("11", getChineseName("thrasand"));
	        integerCurrencyUomb.setProperty("12", getChineseName("tenshand"));
	        integerCurrencyUomb.setProperty("13", getChineseName("handredmill"));
	        integerCurrencyUomb.setProperty("14", getChineseName("ten"));
	        return integerCurrencyUomb;
	    }
	    
	    private static Properties getNumberMapping()
	    {
	        Properties numberMapping = new Properties();
	        numberMapping.setProperty("0", getChineseName("zero"));
	        numberMapping.setProperty("1", getChineseName("one"));
	        numberMapping.setProperty("2", getChineseName("two"));
	        numberMapping.setProperty("3", getChineseName("three"));
	        numberMapping.setProperty("4", getChineseName("four"));
	        numberMapping.setProperty("5", getChineseName("five"));
	        numberMapping.setProperty("6", getChineseName("six"));
	        numberMapping.setProperty("7", getChineseName("seven"));
	        numberMapping.setProperty("8", getChineseName("eight"));
	        numberMapping.setProperty("9", getChineseName("nine"));
	        return numberMapping;
	    }
	    
}
