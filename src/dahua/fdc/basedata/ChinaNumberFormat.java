package com.kingdee.eas.fdc.basedata;

import com.kingdee.eas.fm.nt.MoneyUnitEnum;

public class ChinaNumberFormat {
	/**
     * �õ���λ
     */
    private static String getUnit(String unit) {
        NumberUnitEnum unitEnum = NumberUnitEnum.getEnum(unit);
        return unitEnum.getAlias();
    }
    /**
     * �õ��������ֵĴ�Сд
     * @param digit
     * @return
     */
    private static String getCap(long digit) {
    	ChinaNumberEnum cap = ChinaNumberEnum.getEnum(new Long(digit).toString());
        return cap.getAlias();
    }
    
    /**
     * ���ݽ��ת����Сд
     * @param value
     * @return
     */
    public static String getChinaNumberValue(long value) {
        StringBuffer geWeiValue = new StringBuffer();
        long geWei = value - value / 10000 * 10000;
        if (geWei != 0) {
            //ǧ
            long qian = geWei / 1000;
            //��
            long bai = (geWei - qian * 1000) / 100;
            //ʮ
            long shi = (geWei - qian * 1000 - bai * 100) / 10;
            //��
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
        //�ݹ�ȡ�򲿷�
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
        //�ݹ�ȡ�ڲ���
        String yiWeiValue = getChinaNumberValue(yiWei);
        if (wanWei < 1000 && wanWei != 0) {
            wanWeiValue = getCap(0) + wanWeiValue;
        }
        yiWeiValue += getUnit(NumberUnitEnum.R5_VALUE) + wanWeiValue;
        return yiWeiValue;
    }
}
