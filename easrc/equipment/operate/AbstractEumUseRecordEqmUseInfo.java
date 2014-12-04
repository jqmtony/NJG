package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEumUseRecordEqmUseInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEumUseRecordEqmUseInfo()
    {
        this("id");
    }
    protected AbstractEumUseRecordEqmUseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �豸ʹ�ü�¼ 's null property 
     */
    public com.kingdee.eas.port.equipment.operate.EumUseRecordInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.operate.EumUseRecordInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.operate.EumUseRecordInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s �ͺż��������property 
     */
    public String getModelType()
    {
        return getString("modelType");
    }
    public void setModelType(String item)
    {
        setString("modelType", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s �ܺ���property 
     */
    public java.math.BigDecimal getPowerCost()
    {
        return getBigDecimal("powerCost");
    }
    public void setPowerCost(java.math.BigDecimal item)
    {
        setBigDecimal("powerCost", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s ������property 
     */
    public java.math.BigDecimal getCzCost()
    {
        return getBigDecimal("czCost");
    }
    public void setCzCost(java.math.BigDecimal item)
    {
        setBigDecimal("czCost", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s ��Դ����property 
     */
    public java.math.BigDecimal getPowerUnitCost()
    {
        return getBigDecimal("powerUnitCost");
    }
    public void setPowerUnitCost(java.math.BigDecimal item)
    {
        setBigDecimal("powerUnitCost", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s ����̨ʱ��h��property 
     */
    public java.math.BigDecimal getEqmTime()
    {
        return getBigDecimal("eqmTime");
    }
    public void setEqmTime(java.math.BigDecimal item)
    {
        setBigDecimal("eqmTime", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s ����̨ʱ��h��property 
     */
    public java.math.BigDecimal getEventTime()
    {
        return getBigDecimal("EventTime");
    }
    public void setEventTime(java.math.BigDecimal item)
    {
        setBigDecimal("EventTime", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s ʹ���ʣ�%��property 
     */
    public java.math.BigDecimal getUsageRate()
    {
        return getBigDecimal("usageRate");
    }
    public void setUsageRate(java.math.BigDecimal item)
    {
        setBigDecimal("usageRate", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s ʹ��̨ʱ��h��property 
     */
    public java.math.BigDecimal getUseTime()
    {
        return getBigDecimal("UseTime");
    }
    public void setUseTime(java.math.BigDecimal item)
    {
        setBigDecimal("UseTime", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s �����ʣ�%��property 
     */
    public java.math.BigDecimal getFaultRate()
    {
        return getBigDecimal("faultRate");
    }
    public void setFaultRate(java.math.BigDecimal item)
    {
        setBigDecimal("faultRate", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s �豸���property 
     */
    public String getEqmCategory()
    {
        return getString("eqmCategory");
    }
    public void setEqmCategory(String item)
    {
        setString("eqmCategory", item);
    }
    /**
     * Object: �豸ʹ�ü�¼ 's �ܺ�����λ property 
     */
    public com.kingdee.eas.port.equipment.base.PowerUnitInfo getPowerUnit()
    {
        return (com.kingdee.eas.port.equipment.base.PowerUnitInfo)get("powerUnit");
    }
    public void setPowerUnit(com.kingdee.eas.port.equipment.base.PowerUnitInfo item)
    {
        put("powerUnit", item);
    }
    /**
     * Object: �豸ʹ�ü�¼ 's ��������λ property 
     */
    public com.kingdee.eas.port.equipment.base.CzUnitInfo getCzUnit()
    {
        return (com.kingdee.eas.port.equipment.base.CzUnitInfo)get("czUnit");
    }
    public void setCzUnit(com.kingdee.eas.port.equipment.base.CzUnitInfo item)
    {
        put("czUnit", item);
    }
    /**
     * Object: �豸ʹ�ü�¼ 's ��Դ���ĵ�λ property 
     */
    public com.kingdee.eas.port.equipment.base.PuUnitInfo getPuUnit()
    {
        return (com.kingdee.eas.port.equipment.base.PuUnitInfo)get("puUnit");
    }
    public void setPuUnit(com.kingdee.eas.port.equipment.base.PuUnitInfo item)
    {
        put("puUnit", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s �ܺ����property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.CostType getCostType()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.CostType.getEnum(getString("CostType"));
    }
    public void setCostType(com.kingdee.eas.port.equipment.base.enumbase.CostType item)
    {
		if (item != null) {
        setString("CostType", item.getValue());
		}
    }
    /**
     * Object:�豸ʹ�ü�¼'s �豸����property 
     */
    public String getEqmType()
    {
        return getString("eqmType");
    }
    public void setEqmType(String item)
    {
        setString("eqmType", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s ���Ϸ�property 
     */
    public java.math.BigDecimal getMaterialAmount()
    {
        return getBigDecimal("materialAmount");
    }
    public void setMaterialAmount(java.math.BigDecimal item)
    {
        setBigDecimal("materialAmount", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s ���޷���property 
     */
    public java.math.BigDecimal getSelfAmount()
    {
        return getBigDecimal("selfAmount");
    }
    public void setSelfAmount(java.math.BigDecimal item)
    {
        setBigDecimal("selfAmount", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s ί���������property 
     */
    public java.math.BigDecimal getOutAmount()
    {
        return getBigDecimal("outAmount");
    }
    public void setOutAmount(java.math.BigDecimal item)
    {
        setBigDecimal("outAmount", item);
    }
    /**
     * Object: �豸ʹ�ü�¼ 's �豸���� property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEqmName()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("eqmName");
    }
    public void setEqmName(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("eqmName", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s ��ʼ����property 
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
     * Object:�豸ʹ�ü�¼'s ��ֹ����property 
     */
    public java.util.Date getEndTime()
    {
        return getDate("endTime");
    }
    public void setEndTime(java.util.Date item)
    {
        setDate("endTime", item);
    }
    /**
     * Object:�豸ʹ�ü�¼'s �豸����property 
     */
    public String getEqmNumber()
    {
        return getString("eqmNumber");
    }
    public void setEqmNumber(String item)
    {
        setString("eqmNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("460F30A0");
    }
}