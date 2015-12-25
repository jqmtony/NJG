package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractGcftbEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractGcftbEntryInfo()
    {
        this("id");
    }
    protected AbstractGcftbEntryInfo(String pkField)
    {
        super(pkField);
        put("Detail", new com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryDetailCollection());
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo getParent()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 分录 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getEngineeringProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("engineeringProject");
    }
    public void setEngineeringProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("engineeringProject", item);
    }
    /**
     * Object: 分录 's 设施名称 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getFacilityName()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("facilityName");
    }
    public void setFacilityName(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("facilityName", item);
    }
    /**
     * Object: 分录 's 产权情况 property 
     */
    public com.kingdee.eas.fdc.aimcost.costkf.CqgsBaseInfo getProptreyRight()
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.CqgsBaseInfo)get("proptreyRight");
    }
    public void setProptreyRight(com.kingdee.eas.fdc.aimcost.costkf.CqgsBaseInfo item)
    {
        put("proptreyRight", item);
    }
    /**
     * Object:分录's 建筑面积property 
     */
    public java.math.BigDecimal getConstructionArea()
    {
        return getBigDecimal("constructionArea");
    }
    public void setConstructionArea(java.math.BigDecimal item)
    {
        setBigDecimal("constructionArea", item);
    }
    /**
     * Object:分录's 开工时间property 
     */
    public java.util.Date getStartTime()
    {
        return getDate("startTime");
    }
    public void setStartTime(java.util.Date item)
    {
        setDate("startTime", item);
    }
    /**
     * Object:分录's 实际开工时间property 
     */
    public java.util.Date getActualStartTine()
    {
        return getDate("actualStartTine");
    }
    public void setActualStartTine(java.util.Date item)
    {
        setDate("actualStartTine", item);
    }
    /**
     * Object:分录's 竣工时间property 
     */
    public java.util.Date getCompletionTime()
    {
        return getDate("completionTime");
    }
    public void setCompletionTime(java.util.Date item)
    {
        setDate("completionTime", item);
    }
    /**
     * Object:分录's 实际竣工时间property 
     */
    public java.util.Date getActualCompeltionTime()
    {
        return getDate("actualCompeltionTime");
    }
    public void setActualCompeltionTime(java.util.Date item)
    {
        setDate("actualCompeltionTime", item);
    }
    /**
     * Object:分录's 成本总额property 
     */
    public java.math.BigDecimal getTotalCost()
    {
        return getBigDecimal("totalCost");
    }
    public void setTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("totalCost", item);
    }
    /**
     * Object:分录's 已发生成本property 
     */
    public java.math.BigDecimal getCostHasOccurred()
    {
        return getBigDecimal("costHasOccurred");
    }
    public void setCostHasOccurred(java.math.BigDecimal item)
    {
        setBigDecimal("costHasOccurred", item);
    }
    /**
     * Object:分录's 分摊指标property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.AllocationIndex getAllocationIndex()
    {
        return com.kingdee.eas.fdc.gcftbiaoa.AllocationIndex.getEnum(getString("allocationIndex"));
    }
    public void setAllocationIndex(com.kingdee.eas.fdc.gcftbiaoa.AllocationIndex item)
    {
		if (item != null) {
        setString("allocationIndex", item.getValue());
		}
    }
    /**
     * Object:分录's 应分摊总量property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:分录's 待分摊总量property 
     */
    public java.math.BigDecimal getShare()
    {
        return getBigDecimal("share");
    }
    public void setShare(java.math.BigDecimal item)
    {
        setBigDecimal("share", item);
    }
    /**
     * Object:分录's 分摊单价property 
     */
    public java.math.BigDecimal getSharePrice()
    {
        return getBigDecimal("sharePrice");
    }
    public void setSharePrice(java.math.BigDecimal item)
    {
        setBigDecimal("sharePrice", item);
    }
    /**
     * Object: 分录 's 分摊项目 property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryDetailCollection getDetail()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryDetailCollection)get("Detail");
    }
    /**
     * Object:分录's 待分摊是否整体工程项目property 
     */
    public boolean isAllshare()
    {
        return getBoolean("allshare");
    }
    public void setAllshare(boolean item)
    {
        setBoolean("allshare", item);
    }
    /**
     * Object:分录's 备注property 
     */
    public String getRemark()
    {
        return getString("Remark");
    }
    public void setRemark(String item)
    {
        setString("Remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("58157E21");
    }
}