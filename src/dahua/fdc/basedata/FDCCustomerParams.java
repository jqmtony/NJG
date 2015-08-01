/*
 * @(#)FDCCustomerParams.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.basedata;

import java.util.Date;

import javax.swing.ComboBoxModel;

import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.util.enums.StringEnum;

/**
 * 
 * ����:���ز�ͨ�ù��˲�����
 * @author liupd  date:2006-10-18 <p>
 * @version EAS5.2
 */
public class FDCCustomerParams {
	private CustomerParams customerParams;

	public FDCCustomerParams() {
		super();
		customerParams = new CustomerParams();
	}

	public FDCCustomerParams(CustomerParams customerParams) {
		super();
		this.customerParams = customerParams;
	}
	
	public void add(String key, String[] strArray) {
		customerParams.addCustomerParam(key, FDCHelper.strArrayToString(strArray));
	}
	
	public void add(String key, Date date) {
		customerParams.addCustomerParam(key, FDCHelper.dateToString(date));
	}
	
	public void add(String key, String str) {
		customerParams.addCustomerParam(key, str);
	}
	
	public void add(String key, int i) {
		customerParams.addCustomerParam(key, String.valueOf(i));
	}

	public void add(String key, Integer integer) {
		customerParams.addCustomerParam(key, integer.toString());
	}
	
	public void add(String key, boolean b) {
		customerParams.addCustomerParam(key, String.valueOf(b));
	}
	
	public String[] getStringArray(String key) {
		return FDCHelper.stringToStrArray(customerParams.getCustomerParam(key));
	}
	
	public Date getDate(String key) {
		return FDCHelper.strToDate(customerParams.getCustomerParam(key));
	}
	
	public String getString(String key) {
		return customerParams.getCustomerParam(key);
	}
	
	/**
	 * 
	 * ��������ͬ��getString
	 * @param key
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-18 <p>
	 */
	public String get(String key) {
		return getString(key);
	}
	
	public int getInt(String key) {
		if(customerParams.getCustomerParam(key) == null) {
			return 0;
		}
		return Integer.parseInt(customerParams.getCustomerParam(key));
	}
	
	public Integer getInteger(String key) {
		//1.4��֧��
//		return Integer.valueOf(customerParams.getCustomerParam(key));
		return new Integer(customerParams.getCustomerParam(key));
	}

	public boolean getBoolean(String key) {
		if(customerParams.getCustomerParam(key) == null) {
			return false;
		}
		return Boolean.valueOf(customerParams.getCustomerParam(key)).booleanValue();
	}
	
	public CustomerParams getCustomerParams() {
		return customerParams;
	}

	public void setCustomerParams(CustomerParams customerParams) {
		this.customerParams = customerParams;
	}
	
	/**
	 * 
	 * �������ж�ָ��key��value�Ƿ�Ϊnull
	 * @param key
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-18 <p>
	 */
	public boolean isNull(String key) {
		return customerParams.getCustomerParam(key) == null;
	}
	
	/**
	 * 
	 * �������ж�ָ����key��value�Ƿ�Ϊnull
	 * @param key
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-18 <p>
	 */
	public boolean isNotNull(String key) {
		return !isNull(key);
	}
	
    public static void setSelectedItem(KDComboBox cb, Object obj) {
        ComboBoxModel model = cb.getModel();
        for (int i = 0, n = model.getSize(); i < n; i++) {
            Object element = model.getElementAt(i);
            if (element instanceof CoreBaseInfo 
            		&& ((CoreBaseInfo) element).getId().equals(obj)) {
                cb.setSelectedIndex(i);
                break;
            } if (element instanceof StringEnum && ((StringEnum) element).getValue().equals(obj)){
                cb.setSelectedIndex(i);
                break;
            }
            		
        }
    }
}
