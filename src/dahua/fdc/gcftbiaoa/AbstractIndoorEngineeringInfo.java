package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIndoorEngineeringInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractIndoorEngineeringInfo()
    {
        this("id");
    }
    protected AbstractIndoorEngineeringInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.gcftbiaoa.IndoorEngineeringEntryCollection());
    }
    /**
     * Object: ����װ�ޱ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.IndoorEngineeringEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IndoorEngineeringEntryCollection)get("entrys");
    }
    /**
     * Object:����װ�ޱ�'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:����װ�ޱ�'s ״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("State"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("State", item.getValue());
		}
    }
    /**
     * Object: ����װ�ޱ� 's ��Ŀ���� property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProjectName()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("ProjectName");
    }
    public void setProjectName(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("ProjectName", item);
    }
    /**
     * Object:����װ�ޱ�'s �汾��property 
     */
    public int getVersion()
    {
        return getInt("Version");
    }
    public void setVersion(int item)
    {
        setInt("Version", item);
    }
    /**
     * Object:����װ�ޱ�'s �Ƿ�����property 
     */
    public boolean isLasted()
    {
        return getBoolean("lasted");
    }
    public void setLasted(boolean item)
    {
        setBoolean("lasted", item);
    }
    /**
     * Object:����װ�ޱ�'s ���ʱ��property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object: ����װ�ޱ� 's ���� property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.HousingInfo getRoom()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.HousingInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.gcftbiaoa.HousingInfo item)
    {
        put("room", item);
    }
    /**
     * Object:����װ�ޱ�'s �������property 
     */
    public java.math.BigDecimal getSalesArea()
    {
        return getBigDecimal("SalesArea");
    }
    public void setSalesArea(java.math.BigDecimal item)
    {
        setBigDecimal("SalesArea", item);
    }
    /**
     * Object:����װ�ޱ�'s �������ָ��property 
     */
    public java.math.BigDecimal getSalesAreaIndicator()
    {
        return getBigDecimal("SalesAreaIndicator");
    }
    public void setSalesAreaIndicator(java.math.BigDecimal item)
    {
        setBigDecimal("SalesAreaIndicator", item);
    }
    /**
     * Object:����װ�ޱ�'s ��װproperty 
     */
    public java.math.BigDecimal getSoft()
    {
        return getBigDecimal("Soft");
    }
    public void setSoft(java.math.BigDecimal item)
    {
        setBigDecimal("Soft", item);
    }
    /**
     * Object:����װ�ޱ�'s Ӳװproperty 
     */
    public java.math.BigDecimal getHard()
    {
        return getBigDecimal("Hard");
    }
    public void setHard(java.math.BigDecimal item)
    {
        setBigDecimal("Hard", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("38843319");
    }
}