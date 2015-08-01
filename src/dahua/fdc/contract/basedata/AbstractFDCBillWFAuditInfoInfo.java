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
     * Object:房地产单据审批节点不打印's 单据Idproperty 
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
     * Object:房地产单据审批节点不打印's 审批人Idproperty 
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
     * Object:房地产单据审批节点不打印's 信息类型property 
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
     * Object:房地产单据审批节点不打印's 组织信息property 
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
     * Object:房地产单据审批节点不打印's 活动定义内码property 
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