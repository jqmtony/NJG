package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectStartRequestEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectStartRequestEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectStartRequestEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标计划 's null property 
     */
    public com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:招标计划's 标段名称property 
     */
    public String getInviteName()
    {
        return getString("inviteName");
    }
    public void setInviteName(String item)
    {
        setString("inviteName", item);
    }
    /**
     * Object: 招标计划 's 招标方式 property 
     */
    public com.kingdee.eas.port.pm.base.InviteTypeInfo getType()
    {
        return (com.kingdee.eas.port.pm.base.InviteTypeInfo)get("type");
    }
    public void setType(com.kingdee.eas.port.pm.base.InviteTypeInfo item)
    {
        put("type", item);
    }
    /**
     * Object:招标计划's 计划招标时间property 
     */
    public java.util.Date getPlanDate()
    {
        return getDate("planDate");
    }
    public void setPlanDate(java.util.Date item)
    {
        setDate("planDate", item);
    }
    /**
     * Object:招标计划's 标段金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:招标计划's 联系人property 
     */
    public String getLinkPerson()
    {
        return getString("linkPerson");
    }
    public void setLinkPerson(String item)
    {
        setString("linkPerson", item);
    }
    /**
     * Object:招标计划's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6A524B69");
    }
}