package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichCustomWriteOffEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRichCustomWriteOffEntryInfo()
    {
        this("id");
    }
    protected AbstractRichCustomWriteOffEntryInfo(String pkField)
    {
        super(pkField);
        put("FpEntry", new com.kingdee.eas.custom.richinf.RichCustomWriteOffEntryFpEntryCollection());
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo getParent()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 落单号property 
     */
    public String getLdNumber()
    {
        return getString("ldNumber");
    }
    public void setLdNumber(String item)
    {
        setString("ldNumber", item);
    }
    /**
     * Object:分录's 业务单据编号property 
     */
    public String getBizNumber()
    {
        return getString("bizNumber");
    }
    public void setBizNumber(String item)
    {
        setString("bizNumber", item);
    }
    /**
     * Object:分录's 到检日期property 
     */
    public java.util.Date getDjDate()
    {
        return getDate("djDate");
    }
    public void setDjDate(java.util.Date item)
    {
        setDate("djDate", item);
    }
    /**
     * Object: 分录 's 开票单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getKpUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("kpUnit");
    }
    public void setKpUnit(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("kpUnit", item);
    }
    /**
     * Object: 分录 's 开票机构 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getKpCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("kpCompany");
    }
    public void setKpCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("kpCompany", item);
    }
    /**
     * Object: 分录 's 到检机构 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getDjCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("djCompany");
    }
    public void setDjCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("djCompany", item);
    }
    /**
     * Object:分录's 应收金额property 
     */
    public java.math.BigDecimal getYsAmount()
    {
        return getBigDecimal("ysAmount");
    }
    public void setYsAmount(java.math.BigDecimal item)
    {
        setBigDecimal("ysAmount", item);
    }
    /**
     * Object:分录's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object: 分录 's 发票分录 property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffEntryFpEntryCollection getFpEntry()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffEntryFpEntryCollection)get("FpEntry");
    }
    /**
     * Object: 分录 's 到检单号 property 
     */
    public com.kingdee.eas.custom.richinf.RichExamedInfo getDjNumber()
    {
        return (com.kingdee.eas.custom.richinf.RichExamedInfo)get("djNumber");
    }
    public void setDjNumber(com.kingdee.eas.custom.richinf.RichExamedInfo item)
    {
        put("djNumber", item);
    }
    /**
     * Object: 分录 's 销售员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getSales()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("sales");
    }
    public void setSales(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("sales", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B8D6DE07");
    }
}