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
     * Object: 供应商档案登记 's 分录 property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryCollection)get("entrys");
    }
    /**
     * Object:供应商档案登记's 是否生成凭证property 
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
     * Object:供应商档案登记's 供应商名称property 
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
     * Object: 供应商档案登记 's 供应商类别 property 
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
     * Object:供应商档案登记's 所在省份property 
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
     * Object:供应商档案登记's 所在城市property 
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
     * Object:供应商档案登记's 公司地址property 
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
     * Object:供应商档案登记's 公司电话property 
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
     * Object:供应商档案登记's 公司传真property 
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
     * Object:供应商档案登记's 公司邮箱property 
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
     * Object:供应商档案登记's 邮政编码property 
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
     * Object:供应商档案登记's 公司主页property 
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
     * Object: 供应商档案登记 's 所属组织 property 
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
     * Object: 供应商档案登记 's 资质等级 property 
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
     * Object:供应商档案登记's 推荐人property 
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
     * Object: 供应商档案登记 's 知名度 property 
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
     * Object:供应商档案登记's 服务费用property 
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
     * Object:供应商档案登记's 三年内是否有法律纠纷，是否被政府部门处罚property 
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
     * Object:供应商档案登记's 备注property 
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
     * Object:供应商档案登记's 法人代表property 
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
     * Object:供应商档案登记's 企业性质property 
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
     * Object:供应商档案登记's 注册资金(万元)property 
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
     * Object:供应商档案登记's 雇员总是property 
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
     * Object:供应商档案登记's 营业执照property 
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
     * Object: 供应商档案登记 's 服务区域 property 
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
     * Object: 供应商档案登记 's 服务类型 property 
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
     * Object:供应商档案登记's 税务登记号property 
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
     * Object: 供应商档案登记 's 档案分类 property 
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
     * Object:供应商档案登记's 工商注册号property 
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
     * Object: 供应商档案登记 's 经营模式 property 
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
     * Object: 供应商档案登记 's 第2个表体 property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonCollection getEntryPerson()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonCollection)get("EntryPerson");
    }
    /**
     * Object: 供应商档案登记 's 第3个表体 property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryAttCollection getEntryAtt()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryAttCollection)get("EntryAtt");
    }
    /**
     * Object:供应商档案登记's 审核时间property 
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
     * Object: 供应商档案登记 's 系统供应商 property 
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
     * Object:供应商档案登记's 单据状态property 
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
     * Object: 供应商档案登记 's 供应商级别 property 
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
     * Object: 供应商档案登记 's 符合性审查 property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockE4Collection getE4()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockE4Collection)get("E4");
    }
    /**
     * Object: 供应商档案登记 's 审查模板 property 
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