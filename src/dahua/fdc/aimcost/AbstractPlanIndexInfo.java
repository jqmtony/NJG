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
     * Object:�滮ָ��'s ��ռ�����property 
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
     * Object:�滮ָ��'s �����õ����property 
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
     * Object:�滮ָ��'s �ܽ������property 
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
     * Object:�滮ָ��'s ������ռ����� property 
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
     * Object:�滮ָ��'s �����ܶ� property 
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
     * Object:�滮ָ��'s �̵���property 
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
     * Object:�滮ָ��'s ���ݻ������property 
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
     * Object:�滮ָ��'s �ݻ���property 
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
     * Object:�滮ָ��'s ��·�õغϼ�property 
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
     * Object:�滮ָ��'s ����·�泵�е� property 
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
     * Object:�滮ָ��'s ��·�泵�е���ͣ������property 
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
     * Object:�滮ָ��'s Ӳ����װ���е� property 
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
     * Object:�滮ָ��'s Ӳ����װ�㳡 property 
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
     * Object:�滮ָ��'s Ӳ����װ���е�  property 
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
     * Object:�滮ָ��'s �̻��õغϼ� property 
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
     * Object:�滮ָ��'s ��Ҫ�����̵� property 
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
     * Object:�滮ָ��'s ����լ���̻�  property 
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
     * Object:�滮ָ��'s �ײ�˽�һ�԰ property 
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
     * Object:�滮ָ��'s ˮ����� property 
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
     * Object: �滮ָ�� 's ��Ʒָ���¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection)get("entrys");
    }
    /**
     * Object:�滮ָ��'s Ŀ�����гɱ�����property 
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
     * Object: �滮ָ�� 's �Զ����¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection getCustomEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection)get("customEntrys");
    }
    /**
     * Object:�滮ָ��'s ���������÷�property 
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
     * Object:�滮ָ��'s ʵ���ܽ������property 
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
     * Object:�滮ָ��'s ���������property 
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
     * Object:�滮ָ��'s ��װ�����property 
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