package com.kingdee.eas.fdc.aimcost.costkf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCQGSEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCQGSEntryInfo()
    {
        this("id");
    }
    protected AbstractCQGSEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.aimcost.costkf.CQGSInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.CQGSInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.costkf.CQGSInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 分录 's 建筑产品名称 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getBuildingName()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("BuildingName");
    }
    public void setBuildingName(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("BuildingName", item);
    }
    /**
     * Object:分录's 建筑面积（㎡）property 
     */
    public java.math.BigDecimal getBuidlingArea()
    {
        return getBigDecimal("BuidlingArea");
    }
    public void setBuidlingArea(java.math.BigDecimal item)
    {
        setBigDecimal("BuidlingArea", item);
    }
    /**
     * Object:分录's 可售面积（㎡）property 
     */
    public java.math.BigDecimal getSaleArea()
    {
        return getBigDecimal("SaleArea");
    }
    public void setSaleArea(java.math.BigDecimal item)
    {
        setBigDecimal("SaleArea", item);
    }
    /**
     * Object:分录's 建筑物占地面积（㎡）property 
     */
    public java.math.BigDecimal getBuildingFloorArea()
    {
        return getBigDecimal("BuildingFloorArea");
    }
    public void setBuildingFloorArea(java.math.BigDecimal item)
    {
        setBigDecimal("BuildingFloorArea", item);
    }
    /**
     * Object:分录's 用途property 
     */
    public String getUse()
    {
        return getString("Use");
    }
    public void setUse(String item)
    {
        setString("Use", item);
    }
    /**
     * Object:分录's 产权归属property 
     */
    public String getPropertyRight()
    {
        return getString("PropertyRight");
    }
    public void setPropertyRight(String item)
    {
        setString("PropertyRight", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0515C4AF");
    }
}