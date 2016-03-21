package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIndoorengInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractIndoorengInfo()
    {
        this("id");
    }
    protected AbstractIndoorengInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.gcftbiaoa.IndoorengEntryCollection());
    }
    /**
     * Object: ���ڹ��̱� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.IndoorengEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IndoorengEntryCollection)get("entrys");
    }
    /**
     * Object:���ڹ��̱�'s �Ƿ�����ƾ֤property 
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
     * Object:���ڹ��̱�'s ״̬property 
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
     * Object: ���ڹ��̱� 's ��Ŀ���� property 
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
     * Object:���ڹ��̱�'s �汾��property 
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
     * Object:���ڹ��̱�'s �Ƿ�����property 
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
     * Object: ���ڹ��̱� 's ���� property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.HousingInfo getRoom()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.HousingInfo)get("Room");
    }
    public void setRoom(com.kingdee.eas.fdc.gcftbiaoa.HousingInfo item)
    {
        put("Room", item);
    }
    /**
     * Object:���ڹ��̱�'s ����������O��property 
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
     * Object:���ڹ��̱�'s �������ָ�꣨Ԫ/�O��property 
     */
    public java.math.BigDecimal getSalesAreaIndex()
    {
        return getBigDecimal("SalesAreaIndex");
    }
    public void setSalesAreaIndex(java.math.BigDecimal item)
    {
        setBigDecimal("SalesAreaIndex", item);
    }
    /**
     * Object:���ڹ��̱�'s ��װproperty 
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
     * Object:���ڹ��̱�'s Ӳװproperty 
     */
    public java.math.BigDecimal getHard()
    {
        return getBigDecimal("Hard");
    }
    public void setHard(java.math.BigDecimal item)
    {
        setBigDecimal("Hard", item);
    }
    /**
     * Object:���ڹ��̱�'s ���ʱ��property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("939CCB04");
    }
}