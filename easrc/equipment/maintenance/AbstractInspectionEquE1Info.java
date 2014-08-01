package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInspectionEquE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInspectionEquE1Info()
    {
        this("id");
    }
    protected AbstractInspectionEquE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第1个表体 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.InspectionEquInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.InspectionEquInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.InspectionEquInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 第1个表体 's 设备编号 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEquNumber()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("equNumber");
    }
    public void setEquNumber(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("equNumber", item);
    }
    /**
     * Object:第1个表体's 设备名称property 
     */
    public String getEquName()
    {
        return getString("equName");
    }
    public void setEquName(String item)
    {
        setString("equName", item);
    }
    /**
     * Object:第1个表体's 规格型号property 
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
     * Object: 第1个表体 's 巡检人员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getIncPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("incPerson");
    }
    public void setIncPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("incPerson", item);
    }
    /**
     * Object:第1个表体's 巡检结果property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.CheckResult getXunjianResult()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.CheckResult.getEnum(getString("xunjianResult"));
    }
    public void setXunjianResult(com.kingdee.eas.port.equipment.base.enumbase.CheckResult item)
    {
		if (item != null) {
        setString("xunjianResult", item.getValue());
		}
    }
    /**
     * Object:第1个表体's 问题描述property 
     */
    public String getQuestion()
    {
        return getString("question");
    }
    public void setQuestion(String item)
    {
        setString("question", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9DFCCA31");
    }
}