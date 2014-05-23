package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEqmAccidentInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEqmAccidentInfo()
    {
        this("id");
    }
    protected AbstractEqmAccidentInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 设备事故单 's 设备名称 property 
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
     * Object:设备事故单's 设备编号property 
     */
    public String getEqmNumner()
    {
        return getString("eqmNumner");
    }
    public void setEqmNumner(String item)
    {
        setString("eqmNumner", item);
    }
    /**
     * Object:设备事故单's 设备类型property 
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
     * Object:设备事故单's 设备规格型号property 
     */
    public String getEqmModelType()
    {
        return getString("eqmModelType");
    }
    public void setEqmModelType(String item)
    {
        setString("eqmModelType", item);
    }
    /**
     * Object:设备事故单's 设备存放地点property 
     */
    public String getEqmAdress()
    {
        return getString("eqmAdress");
    }
    public void setEqmAdress(String item)
    {
        setString("eqmAdress", item);
    }
    /**
     * Object: 设备事故单 's 使用部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseingDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useingDept");
    }
    public void setUseingDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useingDept", item);
    }
    /**
     * Object: 设备事故单 's 事故类别 property 
     */
    public com.kingdee.eas.port.equipment.base.AccidentTypeInfo getAccidentType()
    {
        return (com.kingdee.eas.port.equipment.base.AccidentTypeInfo)get("accidentType");
    }
    public void setAccidentType(com.kingdee.eas.port.equipment.base.AccidentTypeInfo item)
    {
        put("accidentType", item);
    }
    /**
     * Object: 设备事故单 's 填写人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getInputPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("inputPerson");
    }
    public void setInputPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("inputPerson", item);
    }
    /**
     * Object: 设备事故单 's 所属部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getSsOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("ssOrgUnit");
    }
    public void setSsOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("ssOrgUnit", item);
    }
    /**
     * Object: 设备事故单 's 事故性质 property 
     */
    public com.kingdee.eas.port.equipment.base.AccidentNatureInfo getAccidentNature()
    {
        return (com.kingdee.eas.port.equipment.base.AccidentNatureInfo)get("accidentNature");
    }
    public void setAccidentNature(com.kingdee.eas.port.equipment.base.AccidentNatureInfo item)
    {
        put("accidentNature", item);
    }
    /**
     * Object:设备事故单's 事故发生日期property 
     */
    public java.util.Date getHappenDate()
    {
        return getDate("happenDate");
    }
    public void setHappenDate(java.util.Date item)
    {
        setDate("happenDate", item);
    }
    /**
     * Object:设备事故单's 事故结束日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:设备事故单's 事故损失property 
     */
    public String getLoss()
    {
        return getString("loss");
    }
    public void setLoss(String item)
    {
        setString("loss", item);
    }
    /**
     * Object:设备事故单's 事故停机时间（h）property 
     */
    public java.math.BigDecimal getStopTime()
    {
        return getBigDecimal("stopTime");
    }
    public void setStopTime(java.math.BigDecimal item)
    {
        setBigDecimal("stopTime", item);
    }
    /**
     * Object:设备事故单's 事故描述property 
     */
    public String getAccidentDescription()
    {
        return getString("accidentDescription");
    }
    public void setAccidentDescription(String item)
    {
        setString("accidentDescription", item);
    }
    /**
     * Object:设备事故单's 事故处理情况property 
     */
    public String getAccidentManagement()
    {
        return getString("accidentManagement");
    }
    public void setAccidentManagement(String item)
    {
        setString("accidentManagement", item);
    }
    /**
     * Object:设备事故单's 事故原因分析property 
     */
    public String getAccidentCause()
    {
        return getString("accidentCause");
    }
    public void setAccidentCause(String item)
    {
        setString("accidentCause", item);
    }
    /**
     * Object:设备事故单's 处理结果property 
     */
    public String getResultCode()
    {
        return getString("ResultCode");
    }
    public void setResultCode(String item)
    {
        setString("ResultCode", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DB643291");
    }
}