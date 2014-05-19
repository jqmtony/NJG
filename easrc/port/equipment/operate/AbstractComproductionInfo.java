package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractComproductionInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractComproductionInfo()
    {
        this("id");
    }
    protected AbstractComproductionInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.port.equipment.operate.ComproductionEntryCollection());
    }
    /**
     * Object: ��˾���ܱ��� 's ��¼ property 
     */
    public com.kingdee.eas.port.equipment.operate.ComproductionEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.equipment.operate.ComproductionEntryCollection)get("entrys");
    }
    /**
     * Object:��˾���ܱ���'s �Ƿ�����ƾ֤property 
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
     * Object: ��˾���ܱ��� 's ���λ property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getReportingUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("reportingUnit");
    }
    public void setReportingUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("reportingUnit", item);
    }
    /**
     * Object:��˾���ܱ���'s �ܺ��������½����Ƚϴ��ԭ�����˵����property 
     */
    public String getNote()
    {
        return getString("note");
    }
    public void setNote(String item)
    {
        setString("note", item);
    }
    /**
     * Object:��˾���ܱ���'s �������property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("AuditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("AuditTime", item);
    }
    /**
     * Object:��˾���ܱ���'s ����״̬property 
     */
    public com.kingdee.eas.xr.app.XRBillStatusEnum getState()
    {
        return com.kingdee.eas.xr.app.XRBillStatusEnum.getEnum(getInt("state"));
    }
    public void setState(com.kingdee.eas.xr.app.XRBillStatusEnum item)
    {
		if (item != null) {
        setInt("state", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("232E84DB");
    }
}