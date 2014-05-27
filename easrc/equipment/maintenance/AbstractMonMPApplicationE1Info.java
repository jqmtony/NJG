package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonMPApplicationE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonMPApplicationE1Info()
    {
        this("id");
    }
    protected AbstractMonMPApplicationE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �¶�ά���ƻ����� 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.MonMPApplicationInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.MonMPApplicationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.MonMPApplicationInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �¶�ά���ƻ����� 's �豸��� property 
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
     * Object:�¶�ά���ƻ�����'s �豸����property 
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
     * Object:�¶�ά���ƻ�����'s ά������property 
     */
    public String getMainContent()
    {
        return getString("mainContent");
    }
    public void setMainContent(String item)
    {
        setString("mainContent", item);
    }
    /**
     * Object:�¶�ά���ƻ�����'s �ƻ�����ʱ��property 
     */
    public java.util.Date getPlanStartTime()
    {
        return getDate("planStartTime");
    }
    public void setPlanStartTime(java.util.Date item)
    {
        setDate("planStartTime", item);
    }
    /**
     * Object:�¶�ά���ƻ�����'s �ƻ��깤ʱ��property 
     */
    public java.util.Date getPlanCompleteT()
    {
        return getDate("planCompleteT");
    }
    public void setPlanCompleteT(java.util.Date item)
    {
        setDate("planCompleteT", item);
    }
    /**
     * Object:�¶�ά���ƻ�����'s �ƻ�����property 
     */
    public java.math.BigDecimal getPlanCost()
    {
        return getBigDecimal("planCost");
    }
    public void setPlanCost(java.math.BigDecimal item)
    {
        setBigDecimal("planCost", item);
    }
    /**
     * Object:�¶�ά���ƻ�����'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B99906BD");
    }
}