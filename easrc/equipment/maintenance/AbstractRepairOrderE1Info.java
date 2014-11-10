package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRepairOrderE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRepairOrderE1Info()
    {
        this("id");
    }
    protected AbstractRepairOrderE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 维修明细 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:维修明细's 维修内容property 
     */
    public String getRepairContent()
    {
        return getString("repairContent");
    }
    public void setRepairContent(String item)
    {
        setString("repairContent", item);
    }
    /**
     * Object: 维修明细 's 备件更换 property 
     */
    public com.kingdee.eas.basedata.master.material.MaterialInfo getReplaceSparePart()
    {
        return (com.kingdee.eas.basedata.master.material.MaterialInfo)get("replaceSparePart");
    }
    public void setReplaceSparePart(com.kingdee.eas.basedata.master.material.MaterialInfo item)
    {
        put("replaceSparePart", item);
    }
    /**
     * Object:维修明细's 工时property 
     */
    public java.math.BigDecimal getWorkTime()
    {
        return getBigDecimal("workTime");
    }
    public void setWorkTime(java.math.BigDecimal item)
    {
        setBigDecimal("workTime", item);
    }
    /**
     * Object: 维修明细 's 修理人员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRepairPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("repairPerson");
    }
    public void setRepairPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("repairPerson", item);
    }
    /**
     * Object:维修明细's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: 维修明细 's 故障部位 property 
     */
    public com.kingdee.eas.port.equipment.base.FaLocationInfo getFaLocation()
    {
        return (com.kingdee.eas.port.equipment.base.FaLocationInfo)get("FaLocation");
    }
    public void setFaLocation(com.kingdee.eas.port.equipment.base.FaLocationInfo item)
    {
        put("FaLocation", item);
    }
    /**
     * Object:维修明细's 维修方案及技术要求property 
     */
    public String getWxfa()
    {
        return getString("wxfa");
    }
    public void setWxfa(String item)
    {
        setString("wxfa", item);
    }
    /**
     * Object:维修明细's 是否委外property 
     */
    public boolean isSfww()
    {
        return getBoolean("sfww");
    }
    public void setSfww(boolean item)
    {
        setBoolean("sfww", item);
    }
    /**
     * Object:维修明细's 修理费用property 
     */
    public java.math.BigDecimal getXlAmount()
    {
        return getBigDecimal("xlAmount");
    }
    public void setXlAmount(java.math.BigDecimal item)
    {
        setBigDecimal("xlAmount", item);
    }
    /**
     * Object:维修明细's 故障情况property 
     */
    public String getGuzhangqingkuang()
    {
        return getString("guzhangqingkuang");
    }
    public void setGuzhangqingkuang(String item)
    {
        setString("guzhangqingkuang", item);
    }
    /**
     * Object:维修明细's 备件规格型号property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object:维修明细's 更换备件数量property 
     */
    public java.math.BigDecimal getShuliang()
    {
        return getBigDecimal("shuliang");
    }
    public void setShuliang(java.math.BigDecimal item)
    {
        setBigDecimal("shuliang", item);
    }
    /**
     * Object:维修明细's 预警周期(天)property 
     */
    public String getYujingzhouqi()
    {
        return getString("yujingzhouqi");
    }
    public void setYujingzhouqi(String item)
    {
        setString("yujingzhouqi", item);
    }
    /**
     * Object:维修明细's 计量单位property 
     */
    public String getJlUnit()
    {
        return getString("jlUnit");
    }
    public void setJlUnit(String item)
    {
        setString("jlUnit", item);
    }
    /**
     * Object:维修明细's 预警日期property 
     */
    public java.util.Date getYujingDate()
    {
        return getDate("yujingDate");
    }
    public void setYujingDate(java.util.Date item)
    {
        setDate("yujingDate", item);
    }
    /**
     * Object: 维修明细 's 设备名称 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEquNameOne()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("equNameOne");
    }
    public void setEquNameOne(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("equNameOne", item);
    }
    /**
     * Object:维修明细's 厂内编号property 
     */
    public String getChangNumber()
    {
        return getString("changNumber");
    }
    public void setChangNumber(String item)
    {
        setString("changNumber", item);
    }
    /**
     * Object:维修明细's 备件更换property 
     */
    public String getBeijiangenghuan()
    {
        return getString("beijiangenghuan");
    }
    public void setBeijiangenghuan(String item)
    {
        setString("beijiangenghuan", item);
    }
    /**
     * Object:维修明细's 源单据分录IDproperty 
     */
    public String getSourceBillID()
    {
        return getString("sourceBillID");
    }
    public void setSourceBillID(String item)
    {
        setString("sourceBillID", item);
    }
    /**
     * Object:维修明细's 设备编码property 
     */
    public String getEquNumber()
    {
        return getString("equNumber");
    }
    public void setEquNumber(String item)
    {
        setString("equNumber", item);
    }
    /**
     * Object:维修明细's 修理人员IDproperty 
     */
    public String getRepPersonID()
    {
        return getString("repPersonID");
    }
    public void setRepPersonID(String item)
    {
        setString("repPersonID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("58358BBD");
    }
}