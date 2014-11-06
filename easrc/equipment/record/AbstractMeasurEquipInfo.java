package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasurEquipInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractMeasurEquipInfo()
    {
        this("id");
    }
    protected AbstractMeasurEquipInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����豸����'s ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:�����豸����'s �ͺ�property 
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
     * Object:�����豸����'s ������Χproperty 
     */
    public String getMeasurRange()
    {
        return getString("measurRange");
    }
    public void setMeasurRange(String item)
    {
        setString("measurRange", item);
    }
    /**
     * Object:�����豸����'s ׼ȷ��property 
     */
    public String getAccuracy()
    {
        return getString("accuracy");
    }
    public void setAccuracy(String item)
    {
        setString("accuracy", item);
    }
    /**
     * Object:�����豸����'s ��������property 
     */
    public String getManufacturer()
    {
        return getString("manufacturer");
    }
    public void setManufacturer(String item)
    {
        setString("manufacturer", item);
    }
    /**
     * Object:�����豸����'s �������property 
     */
    public String getFactoryNumber()
    {
        return getString("factoryNumber");
    }
    public void setFactoryNumber(String item)
    {
        setString("factoryNumber", item);
    }
    /**
     * Object:�����豸����'s ��������property 
     */
    public java.util.Date getFactoryDate()
    {
        return getDate("factoryDate");
    }
    public void setFactoryDate(java.util.Date item)
    {
        setDate("factoryDate", item);
    }
    /**
     * Object: �����豸���� 's ʹ�ò��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useDepart");
    }
    public void setUseDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useDepart", item);
    }
    /**
     * Object: �����豸���� 's ʹ���� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getUsePerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("usePerson");
    }
    public void setUsePerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("usePerson", item);
    }
    /**
     * Object:�����豸����'s ��������property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:�����豸����'s �춨����(��)property 
     */
    public int getVerificatCycle()
    {
        return getInt("verificatCycle");
    }
    public void setVerificatCycle(int item)
    {
        setInt("verificatCycle", item);
    }
    /**
     * Object:�����豸����'s �춨����property 
     */
    public java.util.Date getCheckDate()
    {
        return getDate("checkDate");
    }
    public void setCheckDate(java.util.Date item)
    {
        setDate("checkDate", item);
    }
    /**
     * Object:�����豸����'s abcproperty 
     */
    public String getAbc()
    {
        return getString("abc");
    }
    public void setAbc(String item)
    {
        setString("abc", item);
    }
    /**
     * Object:�����豸����'s ��Ч��property 
     */
    public java.util.Date getValidityPeriod()
    {
        return getDate("validityPeriod");
    }
    public void setValidityPeriod(java.util.Date item)
    {
        setDate("validityPeriod", item);
    }
    /**
     * Object:�����豸����'s ����״̬property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.sbStatusType getManageState()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.sbStatusType.getEnum(getString("manageState"));
    }
    public void setManageState(com.kingdee.eas.port.equipment.base.enumbase.sbStatusType item)
    {
		if (item != null) {
        setString("manageState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8967FC87");
    }
}