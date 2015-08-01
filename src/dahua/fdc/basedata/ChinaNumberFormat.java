package com.kingdee.eas.fdc.basedata;

import com.kingdee.eas.fm.nt.MoneyUnitEnum;

public class ChinaNumberFormat {
	/**
     * 得到单位
     */
    private static String getUnit(String unit) {
        NumberUnitEnum unitEnum = NumberUnitEnum.getEnum(unit);
        return unitEnum.getAlias();
    }
    /**
     * 得到单个数字的大小写
     * @param digit
     * @return
     */
    private static String getCap(long digit) {
    	ChinaNumberEnum cap = ChinaNumberEnum.getEnum(new Long(digit).toString());
        return cap.getAlias();
    }
    
    /**
     * 根据金额转化大小写
     * @param value
     * @return
     */
    public static String getChinaNumberValue(long value) {
        StringBuffer geWeiValue = new StringBuffer();
        long geWei = value - value / 10000 * 10000;
        if (geWei != 0) {
            //千
            long qian = geWei / 1000;
            //百
            long bai = (geWei - qian * 1000) / 100;
            //十
            long shi = (geWei - qian * 1000 - bai * 100) / 10;
            //个
            long ge = (geWei - qian * 1000 - bai * 100 - shi * 10);
            if (qian != 0) {
                geWeiValue.append(getCap(qian));
                geWeiValue.append(getUnit(NumberUnitEnum.R3_VALUE));
            }
            if (bai == 0) {
                if (qian != 0 && (shi != 0 || ge != 0)) {
                    geWeiValue.append(getCap(0));
                }
            } else {
                geWeiValue.append(getCap(bai));
                geWeiValue.append(getUnit(NumberUnitEnum.R2_VALUE));
            }
            if (shi == 0) {
                if (bai != 0 && ge != 0) {
                    geWeiValue.append(getCap(0));
                }
            } else if(shi == 1){
            	geWeiValue.append(getUnit(NumberUnitEnum.R1_VALUE));
            } else{
                geWeiValue.append(getCap(shi));
                geWeiValue.append(getUnit(NumberUnitEnum.R1_VALUE));
            }
            if (ge != 0) {
                geWeiValue.append(getCap(ge));
            }
        }
        value = value / 10000;
        if (value == 0) {
            return geWeiValue.toString();
        }
        long wanWei = value - value / 10000 * 10000;
        //递归取万部分
        String wanWeiValue = getChinaNumberValue(wanWei);
        if (wanWei == 0) {
            wanWeiValue = geWeiValue.toString();
        } else {
            if (geWei < 1000 && geWei != 0) {
                geWeiValue.insert(0, getCap(0));
            }
            wanWeiValue += getUnit(NumberUnitEnum.R4_VALUE) + geWeiValue;
        }
        value = value / 10000;
        if (value == 0) {
            return wanWeiValue;
        }

        long yiWei = value;
        //递归取亿部分
        String yiWeiValue = getChinaNumberValue(yiWei);
        if (wanWei < 1000 && wanWei != 0) {
            wanWeiValue = getCap(0) + wanWeiValue;
        }
        yiWeiValue += getUnit(NumberUnitEnum.R5_VALUE) + wanWeiValue;
        return yiWeiValue;
    }
}
