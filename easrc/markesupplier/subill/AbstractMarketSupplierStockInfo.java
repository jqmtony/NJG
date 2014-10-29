package com.kingdee.eas.port.markesupplier.subill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockInfo(String pkField)
    {
        super(pkField);
        put("E4", new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockE4Collection());
        put("entrys", new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryCollection());
        put("EntryAtt", new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryAttCollection());
        put("EntryPerson", new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonCollection());
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��¼ property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryCollection)get("entrys");
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �Ƿ�����ƾ֤property 
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
     * Object:��Ӧ�̵����Ǽ�'s ��Ӧ������property 
     */
    public String getSupplierName()
    {
        return getString("supplierName");
    }
    public void setSupplierName(String item)
    {
        setString("supplierName", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��Ӧ����� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo getInviteType()
    {
        return (com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo)get("InviteType");
    }
    public void setInviteType(com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo item)
    {
        put("InviteType", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ����ʡ��property 
     */
    public String getProvince()
    {
        return getString("Province");
    }
    public void setProvince(String item)
    {
        setString("Province", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ���ڳ���property 
     */
    public String getCity()
    {
        return getString("City");
    }
    public void setCity(String item)
    {
        setString("City", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��˾��ַproperty 
     */
    public String getAddress()
    {
        return getString("Address");
    }
    public void setAddress(String item)
    {
        setString("Address", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��˾�绰property 
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
     * Object:��Ӧ�̵����Ǽ�'s ��˾����property 
     */
    public String getLinkFax()
    {
        return getString("LinkFax");
    }
    public void setLinkFax(String item)
    {
        setString("LinkFax", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��˾����property 
     */
    public String getLinkMail()
    {
        return getString("LinkMail");
    }
    public void setLinkMail(String item)
    {
        setString("LinkMail", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��������property 
     */
    public String getPostNumber()
    {
        return getString("PostNumber");
    }
    public void setPostNumber(String item)
    {
        setString("PostNumber", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��˾��ҳproperty 
     */
    public String getWebSite()
    {
        return getString("WebSite");
    }
    public void setWebSite(String item)
    {
        setString("WebSite", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo getPurchaseOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo)get("PurchaseOrgUnit");
    }
    public void setPurchaseOrgUnit(com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo item)
    {
        put("PurchaseOrgUnit", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ���ʵȼ� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarktQuaLevelInfo getQuaLevel()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarktQuaLevelInfo)get("QuaLevel");
    }
    public void setQuaLevel(com.kingdee.eas.port.markesupplier.subase.MarktQuaLevelInfo item)
    {
        put("QuaLevel", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �Ƽ���property 
     */
    public String getRecommended()
    {
        return getString("Recommended");
    }
    public void setRecommended(String item)
    {
        setString("Recommended", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ֪���� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.VisibilityInfo getVisibility()
    {
        return (com.kingdee.eas.port.markesupplier.subase.VisibilityInfo)get("Visibility");
    }
    public void setVisibility(com.kingdee.eas.port.markesupplier.subase.VisibilityInfo item)
    {
        put("Visibility", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �������property 
     */
    public java.math.BigDecimal getServerfees()
    {
        return getBigDecimal("serverfees");
    }
    public void setServerfees(java.math.BigDecimal item)
    {
        setBigDecimal("serverfees", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �������Ƿ��з��ɾ��ף��Ƿ��������Ŵ���property 
     */
    public String getPunish()
    {
        return getString("Punish");
    }
    public void setPunish(String item)
    {
        setString("Punish", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��עproperty 
     */
    public String getMarketRemake()
    {
        return getString("marketRemake");
    }
    public void setMarketRemake(String item)
    {
        setString("marketRemake", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ���˴���property 
     */
    public String getEnterpriseMaster()
    {
        return getString("EnterpriseMaster");
    }
    public void setEnterpriseMaster(String item)
    {
        setString("EnterpriseMaster", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��ҵ����property 
     */
    public String getBoEnterpriseKind()
    {
        return getString("boEnterpriseKind");
    }
    public void setBoEnterpriseKind(String item)
    {
        setString("boEnterpriseKind", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ע���ʽ�(��Ԫ)property 
     */
    public java.math.BigDecimal getRegisterMoney()
    {
        return getBigDecimal("RegisterMoney");
    }
    public void setRegisterMoney(java.math.BigDecimal item)
    {
        setBigDecimal("RegisterMoney", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��Ա����property 
     */
    public int getPeopleCount()
    {
        return getInt("PeopleCount");
    }
    public void setPeopleCount(int item)
    {
        setInt("PeopleCount", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s Ӫҵִ��property 
     */
    public String getBusinessNum()
    {
        return getString("BusinessNum");
    }
    public void setBusinessNum(String item)
    {
        setString("BusinessNum", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's �������� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSplAreaInfo getSupplierSplAreaEntry()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSplAreaInfo)get("SupplierSplAreaEntry");
    }
    public void setSupplierSplAreaEntry(com.kingdee.eas.port.markesupplier.subase.MarketSplAreaInfo item)
    {
        put("SupplierSplAreaEntry", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's �������� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSplServiceTypeInfo getServiceType()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSplServiceTypeInfo)get("ServiceType");
    }
    public void setServiceType(com.kingdee.eas.port.markesupplier.subase.MarketSplServiceTypeInfo item)
    {
        put("ServiceType", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ˰��ǼǺ�property 
     */
    public String getTaxRegisterNo()
    {
        return getString("TaxRegisterNo");
    }
    public void setTaxRegisterNo(String item)
    {
        setString("TaxRegisterNo", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's �������� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo getSupplierFileType()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo)get("SupplierFileType");
    }
    public void setSupplierFileType(com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo item)
    {
        put("SupplierFileType", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ����ע���property 
     */
    public String getBizRegisterNo()
    {
        return getString("BizRegisterNo");
    }
    public void setBizRegisterNo(String item)
    {
        setString("BizRegisterNo", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��Ӫģʽ property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierBusinessModeInfo getSupplierBusinessMode()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierBusinessModeInfo)get("SupplierBusinessMode");
    }
    public void setSupplierBusinessMode(com.kingdee.eas.port.markesupplier.subase.MarketSupplierBusinessModeInfo item)
    {
        put("SupplierBusinessMode", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��2������ property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonCollection getEntryPerson()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonCollection)get("EntryPerson");
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��3������ property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryAttCollection getEntryAtt()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryAttCollection)get("EntryAtt");
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ���ʱ��property 
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
     * Object: ��Ӧ�̵����Ǽ� 's ϵͳ��Ӧ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSysSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("sysSupplier");
    }
    public void setSysSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("sysSupplier", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ����״̬property 
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
     * Object: ��Ӧ�̵����Ǽ� 's ��Ӧ�̼��� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketLevelSetUpInfo getLevel()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketLevelSetUpInfo)get("Level");
    }
    public void setLevel(com.kingdee.eas.port.markesupplier.subase.MarketLevelSetUpInfo item)
    {
        put("Level", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��������� property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockE4Collection getE4()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockE4Collection)get("E4");
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ���ģ�� property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationTemplateInfo getEvatemp()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationTemplateInfo)get("Evatemp");
    }
    public void setEvatemp(com.kingdee.eas.port.pm.base.EvaluationTemplateInfo item)
    {
        put("Evatemp", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5ABA1AAE");
    }
}