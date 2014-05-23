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
     * Object: 供应商评审 's 分录 property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryCollection)get("entrys");
    }
    /**
     * Object:供应商评审's 是否生成凭证property 
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
     * Object: 供应商评审 's 供应商编码 property 
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
     * Object:供应商评审's 供应商名称property 
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
     * Object:供应商评审's 供应商类别property 
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
     * Object:供应商评审's 服务区域property 
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
     * Object:供应商评审's 拟合作项目property 
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
     * Object:供应商评审's 授权联系人property 
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
     * Object:供应商评审's 授权联系人电话property 
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
     * Object:供应商评审's 职务property 
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
     * Object: 供应商评审 's 评审类型 property 
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
     * Object: 供应商评审 's 评审模板 property 
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
     * Object: 供应商评审 's 评审人员 property 
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
     * Object:供应商评审's 备注：property 
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
     * Object: 供应商评审 's 第2个表体 property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryPCollection getEntryPs()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryPCollection)get("EntryPs");
    }
    /**
     * Object:供应商评审's 审核日期property 
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
     * Object:供应商评审's 单据状态property 
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
     * Object: 供应商评审 's 组织 property 
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
     * Object:供应商评审's 综合得分property 
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
     * Object:供应商评审's 是否合格property 
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
     * Object:供应商评审's 评审人property 
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