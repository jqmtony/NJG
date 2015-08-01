package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanIndexInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPlanIndexInfo()
    {
        this("id");
    }
    protected AbstractPlanIndexInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection());
        put("customEntrys", new com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection());
    }
    /**
     * Object:规划指标's 总占地面积property 
     */
    public java.math.BigDecimal getTotalContainArea()
    {
        return getBigDecimal("totalContainArea");
    }
    public void setTotalContainArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalContainArea", item);
    }
    /**
     * Object:规划指标's 建筑用地面积property 
     */
    public java.math.BigDecimal getBuildArea()
    {
        return getBigDecimal("buildArea");
    }
    public void setBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildArea", item);
    }
    /**
     * Object:规划指标's 总建筑面积property 
     */
    public java.math.BigDecimal getTotalBuildArea()
    {
        return getBigDecimal("totalBuildArea");
    }
    public void setTotalBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalBuildArea", item);
    }
    /**
     * Object:规划指标's 建筑物占地面积 property 
     */
    public java.math.BigDecimal getBuildContainArea()
    {
        return getBigDecimal("buildContainArea");
    }
    public void setBuildContainArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildContainArea", item);
    }
    /**
     * Object:规划指标's 建筑密度 property 
     */
    public java.math.BigDecimal getBuildDensity()
    {
        return getBigDecimal("buildDensity");
    }
    public void setBuildDensity(java.math.BigDecimal item)
    {
        setBigDecimal("buildDensity", item);
    }
    /**
     * Object:规划指标's 绿地率property 
     */
    public java.math.BigDecimal getGreenAreaRate()
    {
        return getBigDecimal("greenAreaRate");
    }
    public void setGreenAreaRate(java.math.BigDecimal item)
    {
        setBigDecimal("greenAreaRate", item);
    }
    /**
     * Object:规划指标's 计容积率面积property 
     */
    public java.math.BigDecimal getCubageRateArea()
    {
        return getBigDecimal("cubageRateArea");
    }
    public void setCubageRateArea(java.math.BigDecimal item)
    {
        setBigDecimal("cubageRateArea", item);
    }
    /**
     * Object:规划指标's 容积率property 
     */
    public java.math.BigDecimal getCubageRate()
    {
        return getBigDecimal("cubageRate");
    }
    public void setCubageRate(java.math.BigDecimal item)
    {
        setBigDecimal("cubageRate", item);
    }
    /**
     * Object:规划指标's 道路用地合计property 
     */
    public java.math.BigDecimal getTotalRoadArea()
    {
        return getBigDecimal("totalRoadArea");
    }
    public void setTotalRoadArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalRoadArea", item);
    }
    /**
     * Object:规划指标's 沥青路面车行道 property 
     */
    public java.math.BigDecimal getPitchRoad()
    {
        return getBigDecimal("pitchRoad");
    }
    public void setPitchRoad(java.math.BigDecimal item)
    {
        setBigDecimal("pitchRoad", item);
    }
    /**
     * Object:规划指标's 砼路面车行道（停车场）property 
     */
    public java.math.BigDecimal getConcreteRoad()
    {
        return getBigDecimal("concreteRoad");
    }
    public void setConcreteRoad(java.math.BigDecimal item)
    {
        setBigDecimal("concreteRoad", item);
    }
    /**
     * Object:规划指标's 硬质铺装车行道 property 
     */
    public java.math.BigDecimal getHardRoad()
    {
        return getBigDecimal("hardRoad");
    }
    public void setHardRoad(java.math.BigDecimal item)
    {
        setBigDecimal("hardRoad", item);
    }
    /**
     * Object:规划指标's 硬质铺装广场 property 
     */
    public java.math.BigDecimal getHardSquare()
    {
        return getBigDecimal("hardSquare");
    }
    public void setHardSquare(java.math.BigDecimal item)
    {
        setBigDecimal("hardSquare", item);
    }
    /**
     * Object:规划指标's 硬质铺装人行道  property 
     */
    public java.math.BigDecimal getHardManRoad()
    {
        return getBigDecimal("hardManRoad");
    }
    public void setHardManRoad(java.math.BigDecimal item)
    {
        setBigDecimal("hardManRoad", item);
    }
    /**
     * Object:规划指标's 绿化用地合计 property 
     */
    public java.math.BigDecimal getTotalGreenArea()
    {
        return getBigDecimal("totalGreenArea");
    }
    public void setTotalGreenArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalGreenArea", item);
    }
    /**
     * Object:规划指标's 重要公共绿地 property 
     */
    public java.math.BigDecimal getImportPubGreenArea()
    {
        return getBigDecimal("importPubGreenArea");
    }
    public void setImportPubGreenArea(java.math.BigDecimal item)
    {
        setBigDecimal("importPubGreenArea", item);
    }
    /**
     * Object:规划指标's 组团宅间绿化  property 
     */
    public java.math.BigDecimal getHouseGreenArea()
    {
        return getBigDecimal("houseGreenArea");
    }
    public void setHouseGreenArea(java.math.BigDecimal item)
    {
        setBigDecimal("houseGreenArea", item);
    }
    /**
     * Object:规划指标's 底层私家花园 property 
     */
    public java.math.BigDecimal getPrivateGarden()
    {
        return getBigDecimal("privateGarden");
    }
    public void setPrivateGarden(java.math.BigDecimal item)
    {
        setBigDecimal("privateGarden", item);
    }
    /**
     * Object:规划指标's 水景面积 property 
     */
    public java.math.BigDecimal getWarterViewArea()
    {
        return getBigDecimal("warterViewArea");
    }
    public void setWarterViewArea(java.math.BigDecimal item)
    {
        setBigDecimal("warterViewArea", item);
    }
    /**
     * Object: 规划指标 's 产品指标分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection)get("entrys");
    }
    /**
     * Object:规划指标's 目标或可研成本测算property 
     */
    public String getHeadID()
    {
        return getString("headID");
    }
    public void setHeadID(String item)
    {
        setString("headID", item);
    }
    /**
     * Object: 规划指标 's 自定义分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection getCustomEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection)get("customEntrys");
    }
    /**
     * Object:规划指标's 公共配套用房property 
     */
    public java.math.BigDecimal getPublicSetHouse()
    {
        return getBigDecimal("publicSetHouse");
    }
    public void setPublicSetHouse(java.math.BigDecimal item)
    {
        setBigDecimal("publicSetHouse", item);
    }
    /**
     * Object:规划指标's 实际总建造面积property 
     */
    public java.math.BigDecimal getTotalConstructArea()
    {
        return getBigDecimal("totalConstructArea");
    }
    public void setTotalConstructArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalConstructArea", item);
    }
    /**
     * Object:规划指标's 地下室面积property 
     */
    public java.math.BigDecimal getBasementArea()
    {
        return getBigDecimal("basementArea");
    }
    public void setBasementArea(java.math.BigDecimal item)
    {
        setBigDecimal("basementArea", item);
    }
    /**
     * Object:规划指标's 精装修面积property 
     */
    public java.math.BigDecimal getDecorationArea()
    {
        return getBigDecimal("decorationArea");
    }
    public void setDecorationArea(java.math.BigDecimal item)
    {
        setBigDecimal("decorationArea", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("768287B2");
    }
}