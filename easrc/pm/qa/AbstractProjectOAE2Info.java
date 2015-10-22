package com.kingdee.eas.port.pm.qa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectOAE2Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectOAE2Info()
    {
        this("id");
    }
    protected AbstractProjectOAE2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 关联单据 's null property 
     */
    public com.kingdee.eas.port.pm.qa.ProjectOAInfo getParent()
    {
        return (com.kingdee.eas.port.pm.qa.ProjectOAInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.qa.ProjectOAInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 关联单据 's 单据类型 property 
     */
    public com.kingdee.eas.port.pm.qa.LinkBillInfo getBillType()
    {
        return (com.kingdee.eas.port.pm.qa.LinkBillInfo)get("billType");
    }
    public void setBillType(com.kingdee.eas.port.pm.qa.LinkBillInfo item)
    {
        put("billType", item);
    }
    /**
     * Object:关联单据's 单据编号property 
     */
    public String getBillNumber()
    {
        return getString("billNumber");
    }
    public void setBillNumber(String item)
    {
        setString("billNumber", item);
    }
    /**
     * Object:关联单据's 单据内容property 
     */
    public String getBillContent()
    {
        return getString("billContent");
    }
    public void setBillContent(String item)
    {
        setString("billContent", item);
    }
    /**
     * Object:关联单据's 单据IDproperty 
     */
    public String getBillID()
    {
        return getString("billID");
    }
    public void setBillID(String item)
    {
        setString("billID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("61EB6916");
    }
}