package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichCustomWriteOffFpEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRichCustomWriteOffFpEntryInfo()
    {
        this("id");
    }
    protected AbstractRichCustomWriteOffFpEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ʊ 's null property 
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
     * Object: ��Ʊ 's ��Ʊ�� property 
     */
    public com.kingdee.eas.fi.ar.OtherBillInfo getFpNo()
    {
        return (com.kingdee.eas.fi.ar.OtherBillInfo)get("fpNo");
    }
    public void setFpNo(com.kingdee.eas.fi.ar.OtherBillInfo item)
    {
        put("fpNo", item);
    }
    /**
     * Object: ��Ʊ 's ��Ʊ̧ͷ property 
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
     * Object: ��Ʊ 's ��Ʊ��˾ property 
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
     * Object:��Ʊ's ��Ʊ���property 
     */
    public java.math.BigDecimal getFpAmount()
    {
        return getBigDecimal("fpAmount");
    }
    public void setFpAmount(java.math.BigDecimal item)
    {
        setBigDecimal("fpAmount", item);
    }
    /**
     * Object:��Ʊ's ��עproperty 
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
     * Object:��Ʊ's �Ѻ������property 
     */
    public java.math.BigDecimal getYhxAmount()
    {
        return getBigDecimal("yhxAmount");
    }
    public void setYhxAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yhxAmount", item);
    }
    /**
     * Object:��Ʊ's δ�������property 
     */
    public java.math.BigDecimal getWhxAmount()
    {
        return getBigDecimal("whxAmount");
    }
    public void setWhxAmount(java.math.BigDecimal item)
    {
        setBigDecimal("whxAmount", item);
    }
    /**
     * Object:��Ʊ's ���κ������property 
     */
    public java.math.BigDecimal getBencihx()
    {
        return getBigDecimal("bencihx");
    }
    public void setBencihx(java.math.BigDecimal item)
    {
        setBigDecimal("bencihx", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("144F3E1D");
    }
}