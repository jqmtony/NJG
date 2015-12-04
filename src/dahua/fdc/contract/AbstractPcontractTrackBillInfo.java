package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPcontractTrackBillInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractPcontractTrackBillInfo()
    {
        this("id");
    }
    protected AbstractPcontractTrackBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.PcontractTrackBillEntryCollection());
    }
    /**
     * Object: ��Լ���ٵ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.PcontractTrackBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.PcontractTrackBillEntryCollection)get("entrys");
    }
    /**
     * Object:��Լ���ٵ�'s �Ƿ�����ƾ֤property 
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
     * Object: ��Լ���ٵ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:��Լ���ٵ�'s �汾��property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object:��Լ���ٵ�'s �Ƿ����°�property 
     */
    public boolean isIsNew()
    {
        return getBoolean("isNew");
    }
    public void setIsNew(boolean item)
    {
        setBoolean("isNew", item);
    }
    /**
     * Object:��Լ���ٵ�'s ����״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getTrackBillStatus()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("trackBillStatus"));
    }
    public void setTrackBillStatus(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("trackBillStatus", item.getValue());
		}
    }
    /**
     * Object:��Լ���ٵ�'s ���ʱ��property 
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
        return new BOSObjectType("F049860B");
    }
}