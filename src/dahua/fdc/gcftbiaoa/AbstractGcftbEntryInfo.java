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
     * Object: ��¼ 's ����ͷ property 
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
     * Object: ��¼ 's ������Ŀ property 
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
     * Object: ��¼ 's ��ʩ���� property 
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
     * Object: ��¼ 's ��Ȩ��� property 
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
     * Object:��¼'s �������property 
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
     * Object:��¼'s ����ʱ��property 
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
     * Object:��¼'s ʵ�ʿ���ʱ��property 
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
     * Object:��¼'s ����ʱ��property 
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
     * Object:��¼'s ʵ�ʿ���ʱ��property 
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
     * Object:��¼'s �ɱ��ܶ�property 
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
     * Object:��¼'s �ѷ����ɱ�property 
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
     * Object:��¼'s ��ָ̯��property 
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
     * Object:��¼'s Ӧ��̯����property 
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
     * Object:��¼'s ����̯����property 
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
     * Object:��¼'s ��̯����property 
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
     * Object: ��¼ 's ��̯��Ŀ property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryDetailCollection getDetail()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryDetailCollection)get("Detail");
    }
    /**
     * Object:��¼'s ����̯�Ƿ����幤����Ŀproperty 
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
     * Object:��¼'s ��עproperty 
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