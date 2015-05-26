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
     * Object: ��¼ 's ����ͷ property 
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
     * Object:��¼'s �䵥��property 
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
     * Object:��¼'s ҵ�񵥾ݱ��property 
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
     * Object:��¼'s ��������property 
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
     * Object: ��¼ 's ��Ʊ��λ property 
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
     * Object: ��¼ 's ��Ʊ���� property 
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
     * Object: ��¼ 's ������� property 
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
     * Object:��¼'s Ӧ�ս��property 
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
     * Object:��¼'s ��עproperty 
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
     * Object: ��¼ 's ��Ʊ��¼ property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffEntryFpEntryCollection getFpEntry()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffEntryFpEntryCollection)get("FpEntry");
    }
    /**
     * Object: ��¼ 's ���쵥�� property 
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
     * Object: ��¼ 's ����Ա property 
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