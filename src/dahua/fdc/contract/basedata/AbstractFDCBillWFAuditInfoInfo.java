package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCBillWFAuditInfoInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCBillWFAuditInfoInfo()
    {
        this("id");
    }
    protected AbstractFDCBillWFAuditInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ز����������ڵ㲻��ӡ's ����Idproperty 
     */
    public String getBillId()
    {
        return getString("billId");
    }
    public void setBillId(String item)
    {
        setString("billId", item);
    }
    /**
     * Object:���ز����������ڵ㲻��ӡ's ������Idproperty 
     */
    public String getAuditorId()
    {
        return getString("auditorId");
    }
    public void setAuditorId(String item)
    {
        setString("auditorId", item);
    }
    /**
     * Object:���ز����������ڵ㲻��ӡ's ��Ϣ����property 
     */
    public String getInfoType()
    {
        return getString("infoType");
    }
    public void setInfoType(String item)
    {
        setString("infoType", item);
    }
    /**
     * Object:���ز����������ڵ㲻��ӡ's ��֯��Ϣproperty 
     */
    public String getOrgInfo()
    {
        return getString("orgInfo");
    }
    public void setOrgInfo(String item)
    {
        setString("orgInfo", item);
    }
    /**
     * Object:���ز����������ڵ㲻��ӡ's ���������property 
     */
    public String getActDefID()
    {
        return getString("actDefID");
    }
    public void setActDefID(String item)
    {
        setString("actDefID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D4F99700");
    }
}