package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBaseDataInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractContractBaseDataInfo()
    {
        this("id");
    }
    protected AbstractContractBaseDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ͬ��������'s ��ͬIDproperty 
     */
    public String getContractId()
    {
        return getString("contractId");
    }
    public void setContractId(String item)
    {
        setString("contractId", item);
    }
    /**
     * Object:��ͬ��������'s �Ƿ����ı���ͬproperty 
     */
    public boolean isIsNoText()
    {
        return getBoolean("isNoText");
    }
    public void setIsNoText(boolean item)
    {
        setBoolean("isNoText", item);
    }
    /**
     * Object: ��ͬ�������� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object:��ͬ��������'s ״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object: ��ͬ�������� 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("82098988");
    }
}