package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDetectionE2Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDetectionE2Info()
    {
        this("id");
    }
    protected AbstractDetectionE2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��2������ 's null property 
     */
    public com.kingdee.eas.port.equipment.special.DetectionInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.special.DetectionInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.special.DetectionInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��2������ 's �豸������ property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getZdaNumber()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("zdaNumber");
    }
    public void setZdaNumber(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("zdaNumber", item);
    }
    /**
     * Object:��2������'s �豸����property 
     */
    public String getDeviceName()
    {
        return getString("deviceName");
    }
    public void setDeviceName(String item)
    {
        setString("deviceName", item);
    }
    /**
     * Object:��2������'s ʹ�õ�λproperty 
     */
    public String getUseUnit()
    {
        return getString("useUnit");
    }
    public void setUseUnit(String item)
    {
        setString("useUnit", item);
    }
    /**
     * Object:��2������'s �ƻ���������property 
     */
    public java.util.Date getPlanDate()
    {
        return getDate("planDate");
    }
    public void setPlanDate(java.util.Date item)
    {
        setDate("planDate", item);
    }
    /**
     * Object:��2������'s ���ڼ�������property 
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
     * Object:��2������'s �û��豸���property 
     */
    public String getUserNumber()
    {
        return getString("userNumber");
    }
    public void setUserNumber(String item)
    {
        setString("userNumber", item);
    }
    /**
     * Object:��2������'s ����ͺ�property 
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
     * Object:��2������'s Ͷ������property 
     */
    public java.util.Date getCommDate()
    {
        return getDate("commDate");
    }
    public void setCommDate(java.util.Date item)
    {
        setDate("commDate", item);
    }
    /**
     * Object:��2������'s ���쵥λproperty 
     */
    public String getMadeUnit()
    {
        return getString("madeUnit");
    }
    public void setMadeUnit(String item)
    {
        setString("madeUnit", item);
    }
    /**
     * Object:��2������'s �������property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.CheckType getTestCategory()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.CheckType.getEnum(getString("testCategory"));
    }
    public void setTestCategory(com.kingdee.eas.port.equipment.base.enumbase.CheckType item)
    {
		if (item != null) {
        setString("testCategory", item.getValue());
		}
    }
    /**
     * Object:��2������'s �Ƿ���property 
     */
    public boolean isCheck()
    {
        return getBoolean("check");
    }
    public void setCheck(boolean item)
    {
        setBoolean("check", item);
    }
    /**
     * Object:��2������'s �����property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.CheckResult getTestResults()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.CheckResult.getEnum(getString("testResults"));
    }
    public void setTestResults(com.kingdee.eas.port.equipment.base.enumbase.CheckResult item)
    {
		if (item != null) {
        setString("testResults", item.getValue());
		}
    }
    /**
     * Object:��2������'s ��עproperty 
     */
    public String getNote()
    {
        return getString("note");
    }
    public void setNote(String item)
    {
        setString("note", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3B0E3728");
    }
}