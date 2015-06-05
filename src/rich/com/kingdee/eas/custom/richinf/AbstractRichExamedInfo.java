package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichExamedInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractRichExamedInfo()
    {
        this("id");
    }
    protected AbstractRichExamedInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.custom.richinf.RichExamedEntryCollection());
    }
    /**
     * Object: ���쵥 's ��¼ property 
     */
    public com.kingdee.eas.custom.richinf.RichExamedEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.richinf.RichExamedEntryCollection)get("entrys");
    }
    /**
     * Object:���쵥's �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:���쵥's �䵥��property 
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
     * Object:���쵥's ��������property 
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
     * Object:���쵥's ҵ�񵥾ݱ��property 
     */
    public String getYwNumber()
    {
        return getString("ywNumber");
    }
    public void setYwNumber(String item)
    {
        setString("ywNumber", item);
    }
    /**
     * Object: ���쵥 's ǩԼ��λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getQyUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("qyUnit");
    }
    public void setQyUnit(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("qyUnit", item);
    }
    /**
     * Object: ���쵥 's ���쵥λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getDjUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("djUnit");
    }
    public void setDjUnit(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("djUnit", item);
    }
    /**
     * Object: ���쵥 's ��Ʊ��λ property 
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
     * Object: ���쵥 's ���λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getFkUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("fkUnit");
    }
    public void setFkUnit(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("fkUnit", item);
    }
    /**
     * Object:���쵥's ��Ʊ��property 
     */
    public String getFpNumber()
    {
        return getString("fpNumber");
    }
    public void setFpNumber(String item)
    {
        setString("fpNumber", item);
    }
    /**
     * Object:���쵥's �ܽ��property 
     */
    public String getAmount()
    {
        return getString("amount");
    }
    public void setAmount(String item)
    {
        setString("amount", item);
    }
    /**
     * Object: ���쵥 's ����Ա property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getSales()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("sales");
    }
    public void setSales(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("sales", item);
    }
    /**
     * Object: ���쵥 's ������ property 
     */
    public com.kingdee.eas.custom.richbase.ExamTypeInfo getTjType()
    {
        return (com.kingdee.eas.custom.richbase.ExamTypeInfo)get("tjType");
    }
    public void setTjType(com.kingdee.eas.custom.richbase.ExamTypeInfo item)
    {
        put("tjType", item);
    }
    /**
     * Object:���쵥's ����״̬property 
     */
    public com.kingdee.eas.custom.richbase.BizStateEnum getBizState()
    {
        return com.kingdee.eas.custom.richbase.BizStateEnum.getEnum(getString("bizState"));
    }
    public void setBizState(com.kingdee.eas.custom.richbase.BizStateEnum item)
    {
		if (item != null) {
        setString("bizState", item.getValue());
		}
    }
    /**
     * Object:���쵥's ��עproperty 
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
     * Object: ���쵥 's ��Ʊ���� property 
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
     * Object: ���쵥 's ������� property 
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
     * Object:���쵥's ����property 
     */
    public boolean isDj()
    {
        return getBoolean("dj");
    }
    public void setDj(boolean item)
    {
        setBoolean("dj", item);
    }
    /**
     * Object:���쵥's �������property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:���쵥's ��ֵ����property 
     */
    public String getCardNo()
    {
        return getString("cardNo");
    }
    public void setCardNo(String item)
    {
        setString("cardNo", item);
    }
    /**
     * Object:���쵥's �����property 
     */
    public com.kingdee.eas.custom.richbase.CardType getCardType()
    {
        return com.kingdee.eas.custom.richbase.CardType.getEnum(getString("cardType"));
    }
    public void setCardType(com.kingdee.eas.custom.richbase.CardType item)
    {
		if (item != null) {
        setString("cardType", item.getValue());
		}
    }
    /**
     * Object:���쵥's ��ֵproperty 
     */
    public java.math.BigDecimal getCardAmount()
    {
        return getBigDecimal("cardAmount");
    }
    public void setCardAmount(java.math.BigDecimal item)
    {
        setBigDecimal("cardAmount", item);
    }
    /**
     * Object:���쵥's �ۼ�property 
     */
    public java.math.BigDecimal getSaleAmount()
    {
        return getBigDecimal("saleAmount");
    }
    public void setSaleAmount(java.math.BigDecimal item)
    {
        setBigDecimal("saleAmount", item);
    }
    /**
     * Object:���쵥's ���property 
     */
    public boolean isHc()
    {
        return getBoolean("hc");
    }
    public void setHc(boolean item)
    {
        setBoolean("hc", item);
    }
    /**
     * Object: ���쵥 's �н鵥λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getZjjg()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("zjjg");
    }
    public void setZjjg(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("zjjg", item);
    }
    /**
     * Object:���쵥's �Ѻ������property 
     */
    public java.math.BigDecimal getYhxAmount()
    {
        return getBigDecimal("yhxAmount");
    }
    public void setYhxAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yhxAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7E855488");
    }
}