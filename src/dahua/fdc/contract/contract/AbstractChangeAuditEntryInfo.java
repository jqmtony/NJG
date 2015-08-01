package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeAuditEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractChangeAuditEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeAuditEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 变更签证申请分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ChangeAuditBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:变更签证申请分录's 变更内容property 
     */
    public String getChangeContent()
    {
        return getString("changeContent");
    }
    public void setChangeContent(String item)
    {
        setString("changeContent", item);
    }
    /**
     * Object:变更签证申请分录's 是否返工property 
     */
    public boolean isIsBack()
    {
        return getBoolean("isBack");
    }
    public void setIsBack(boolean item)
    {
        setBoolean("isBack", item);
    }
    /**
     * Object:变更签证申请分录's 变更内容编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("92476D62");
    }
}