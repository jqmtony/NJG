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
     * Object: �豸�¹ʵ� 's �豸���� property 
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
     * Object:�豸�¹ʵ�'s �豸���property 
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
     * Object:�豸�¹ʵ�'s �豸����property 
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
     * Object:�豸�¹ʵ�'s �豸����ͺ�property 
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
     * Object:�豸�¹ʵ�'s �豸��ŵص�property 
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
     * Object: �豸�¹ʵ� 's ʹ�ò��� property 
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
     * Object: �豸�¹ʵ� 's �¹���� property 
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
     * Object: �豸�¹ʵ� 's ��д�� property 
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
     * Object: �豸�¹ʵ� 's �������� property 
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
     * Object: �豸�¹ʵ� 's �¹����� property 
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
     * Object:�豸�¹ʵ�'s �¹ʷ�������property 
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
     * Object:�豸�¹ʵ�'s �¹ʽ�������property 
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
     * Object:�豸�¹ʵ�'s �¹���ʧproperty 
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
     * Object:�豸�¹ʵ�'s �¹�ͣ��ʱ�䣨h��property 
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
     * Object:�豸�¹ʵ�'s �¹�����property 
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
     * Object:�豸�¹ʵ�'s �¹ʴ������property 
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
     * Object:�豸�¹ʵ�'s �¹�ԭ�����property 
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
     * Object:�豸�¹ʵ�'s ������property 
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