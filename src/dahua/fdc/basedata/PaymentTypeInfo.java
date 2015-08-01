package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class PaymentTypeInfo extends AbstractPaymentTypeInfo implements Serializable 
{
    public PaymentTypeInfo()
    {
        super();
    }
    protected PaymentTypeInfo(String pkField)
    {
        super(pkField);
    }
    
	/** �������� ��� �����ID */
	public static final String settlementID = TypeInfo.settlementID;

	/** �������� ��� ���ȿ�ID */
	public static final String progressID = TypeInfo.progressID;

	/** �������� ��� ���޿�ID */
	public static final String keepID = TypeInfo.keepID;
	
	/** �������� ��� �ݹ���ID */
	public static final String tempID = TypeInfo.tempID;

	
	/** �������� ��� Ԥ����ID */
	public boolean isPreType() {
		return "Ԥ����".equals(getName());
	}
 
	//�Ƿ���ȿ�
	public boolean isScheuleType() {
		return "���ȿ�".equals(getName());
	}

	//�Ƿ�����
	public boolean isSettleType() {
		return "�����".equals(getName());
	}
	/**
	 * Ԥ����ID
	 */
	// public static final String preID="Ga7RLQETEADgAAD9wKgOlOwp3Sw=";//Ԥ����
	

	
}