package com.kingdee.eas.port.markesupplier.subill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierReviewGatherInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractMarketSupplierReviewGatherInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierReviewGatherInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryCollection());
        put("EntryPs", new com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryPCollection());
    }
    /**
     * Object: ��Ӧ������ 's ��¼ property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryCollection)get("entrys");
    }
    /**
     * Object:��Ӧ������'s �Ƿ�����ƾ֤property 
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
     * Object: ��Ӧ������ 's ��Ӧ�̱��� property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo)get("Supplier");
    }
    public void setSupplier(com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo item)
    {
        put("Supplier", item);
    }
    /**
     * Object:��Ӧ������'s ��Ӧ������property 
     */
    public String getSupplierName()
    {
        return getString("SupplierName");
    }
    public void setSupplierName(String item)
    {
        setString("SupplierName", item);
    }
    /**
     * Object:��Ӧ������'s ��Ӧ�����property 
     */
    public String getInviteType()
    {
        return getString("InviteType");
    }
    public void setInviteType(String item)
    {
        setString("InviteType", item);
    }
    /**
     * Object:��Ӧ������'s ��������property 
     */
    public String getSplArea()
    {
        return getString("SplArea");
    }
    public void setSplArea(String item)
    {
        setString("SplArea", item);
    }
    /**
     * Object:��Ӧ������'s �������Ŀproperty 
     */
    public String getPartProject()
    {
        return getString("PartProject");
    }
    public void setPartProject(String item)
    {
        setString("PartProject", item);
    }
    /**
     * Object:��Ӧ������'s ��Ȩ��ϵ��property 
     */
    public String getLinkPerson()
    {
        return getString("LinkPerson");
    }
    public void setLinkPerson(String item)
    {
        setString("LinkPerson", item);
    }
    /**
     * Object:��Ӧ������'s ��Ȩ��ϵ�˵绰property 
     */
    public String getLinkPhone()
    {
        return getString("LinkPhone");
    }
    public void setLinkPhone(String item)
    {
        setString("LinkPhone", item);
    }
    /**
     * Object:��Ӧ������'s ְ��property 
     */
    public String getLinkJob()
    {
        return getString("LinkJob");
    }
    public void setLinkJob(String item)
    {
        setString("LinkJob", item);
    }
    /**
     * Object: ��Ӧ������ 's �������� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo getEvaluationType()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo)get("EvaluationType");
    }
    public void setEvaluationType(com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo item)
    {
        put("EvaluationType", item);
    }
    /**
     * Object: ��Ӧ������ 's ����ģ�� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo getTemplate()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo)get("Template");
    }
    public void setTemplate(com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo item)
    {
        put("Template", item);
    }
    /**
     * Object: ��Ӧ������ 's ������Ա property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getEntry()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("Entry");
    }
    public void setEntry(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("Entry", item);
    }
    /**
     * Object:��Ӧ������'s ��ע��property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    /**
     * Object: ��Ӧ������ 's ��2������ property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryPCollection getEntryPs()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryPCollection)get("EntryPs");
    }
    /**
     * Object:��Ӧ������'s �������property 
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
     * Object:��Ӧ������'s ����״̬property 
     */
    public com.kingdee.eas.port.markesupplier.subase.SupplierState getState()
    {
        return com.kingdee.eas.port.markesupplier.subase.SupplierState.getEnum(getString("State"));
    }
    public void setState(com.kingdee.eas.port.markesupplier.subase.SupplierState item)
    {
		if (item != null) {
        setString("State", item.getValue());
		}
    }
    /**
     * Object: ��Ӧ������ 's ��֯ property 
     */
    public com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:��Ӧ������'s �ۺϵ÷�property 
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
     * Object:��Ӧ������'s �Ƿ�ϸ�property 
     */
    public com.kingdee.eas.port.markesupplier.subase.IsGradeEnum getIsPass()
    {
        return com.kingdee.eas.port.markesupplier.subase.IsGradeEnum.getEnum(getString("IsPass"));
    }
    public void setIsPass(com.kingdee.eas.port.markesupplier.subase.IsGradeEnum item)
    {
		if (item != null) {
        setString("IsPass", item.getValue());
		}
    }
    /**
     * Object:��Ӧ������'s ������property 
     */
    public String getSavePerson()
    {
        return getString("savePerson");
    }
    public void setSavePerson(String item)
    {
        setString("savePerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("869980DB");
    }
}