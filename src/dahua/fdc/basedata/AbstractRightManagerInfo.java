package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRightManagerInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRightManagerInfo()
    {
        this("id");
    }
    protected AbstractRightManagerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:权益管理信息's 是否内部单位property 
     */
    public boolean isIsInnerUnit()
    {
        return getBoolean("isInnerUnit");
    }
    public void setIsInnerUnit(boolean item)
    {
        setBoolean("isInnerUnit", item);
    }
    /**
     * Object:权益管理信息's 公司定义property 
     */
    public String getRightCompanyDefine()
    {
        return getRightCompanyDefine((Locale)null);
    }
    public void setRightCompanyDefine(String item)
    {
		setRightCompanyDefine(item,(Locale)null);
    }
    public String getRightCompanyDefine(Locale local)
    {
        return TypeConversionUtils.objToString(get("rightCompanyDefine", local));
    }
    public void setRightCompanyDefine(String item, Locale local)
    {
        put("rightCompanyDefine", item, local);
    }
    /**
     * Object: 权益管理信息 's 权益公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getRightCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("rightCompany");
    }
    public void setRightCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("rightCompany", item);
    }
    /**
     * Object:权益管理信息's 权益比例property 
     */
    public java.math.BigDecimal getRightScale()
    {
        return getBigDecimal("rightScale");
    }
    public void setRightScale(java.math.BigDecimal item)
    {
        setBigDecimal("rightScale", item);
    }
    /**
     * Object:权益管理信息's 核算方法property 
     */
    public String getAuditType()
    {
        return getString("auditType");
    }
    public void setAuditType(String item)
    {
        setString("auditType", item);
    }
    /**
     * Object:权益管理信息's 开始时间property 
     */
    public java.util.Date getDateFrom()
    {
        return getDate("dateFrom");
    }
    public void setDateFrom(java.util.Date item)
    {
        setDate("dateFrom", item);
    }
    /**
     * Object:权益管理信息's 结束时间property 
     */
    public java.util.Date getDateTo()
    {
        return getDate("dateTo");
    }
    public void setDateTo(java.util.Date item)
    {
        setDate("dateTo", item);
    }
    /**
     * Object:权益管理信息's 股东property 
     */
    public String getMan()
    {
        return getString("man");
    }
    public void setMan(String item)
    {
        setString("man", item);
    }
    /**
     * Object:权益管理信息's 持股比例property 
     */
    public java.math.BigDecimal getHoldScale()
    {
        return getBigDecimal("holdScale");
    }
    public void setHoldScale(java.math.BigDecimal item)
    {
        setBigDecimal("holdScale", item);
    }
    /**
     * Object:权益管理信息's 总出资额property 
     */
    public java.math.BigDecimal getAmt()
    {
        return getBigDecimal("amt");
    }
    public void setAmt(java.math.BigDecimal item)
    {
        setBigDecimal("amt", item);
    }
    /**
     * Object: 权益管理信息 's 父 property 
     */
    public com.kingdee.eas.fdc.basedata.UnitDataManagerInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.UnitDataManagerInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.UnitDataManagerInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B253C9E3");
    }
}