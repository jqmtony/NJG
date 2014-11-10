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
     * Object: ά����ϸ 's null property 
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
     * Object:ά����ϸ's ά������property 
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
     * Object: ά����ϸ 's �������� property 
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
     * Object:ά����ϸ's ��ʱproperty 
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
     * Object: ά����ϸ 's ������Ա property 
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
     * Object:ά����ϸ's ��עproperty 
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
     * Object: ά����ϸ 's ���ϲ�λ property 
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
     * Object:ά����ϸ's ά�޷���������Ҫ��property 
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
     * Object:ά����ϸ's �Ƿ�ί��property 
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
     * Object:ά����ϸ's �������property 
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
     * Object:ά����ϸ's �������property 
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
     * Object:ά����ϸ's ��������ͺ�property 
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
     * Object:ά����ϸ's ������������property 
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
     * Object:ά����ϸ's Ԥ������(��)property 
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
     * Object:ά����ϸ's ������λproperty 
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
     * Object:ά����ϸ's Ԥ������property 
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
     * Object: ά����ϸ 's �豸���� property 
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
     * Object:ά����ϸ's ���ڱ��property 
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
     * Object:ά����ϸ's ��������property 
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
     * Object:ά����ϸ's Դ���ݷ�¼IDproperty 
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
     * Object:ά����ϸ's �豸����property 
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
     * Object:ά����ϸ's ������ԱIDproperty 
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