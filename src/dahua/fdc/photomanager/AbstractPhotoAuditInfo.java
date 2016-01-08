package com.kingdee.eas.fdc.photomanager;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPhotoAuditInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractPhotoAuditInfo()
    {
        this("id");
    }
    protected AbstractPhotoAuditInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.photomanager.PhotoAuditEntryCollection());
    }
    /**
     * Object: �������� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.photomanager.PhotoAuditEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.photomanager.PhotoAuditEntryCollection)get("entrys");
    }
    /**
     * Object:��������'s �Ƿ�����ƾ֤property 
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
     * Object:��������'s ����״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getStatus()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("status"));
    }
    public void setStatus(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("status", item.getValue());
		}
    }
    /**
     * Object:��������'s ��������property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("AuditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("AuditDate", item);
    }
    /**
     * Object:��������'s ��������property 
     */
    public String getReportName()
    {
        return getString("reportName");
    }
    public void setReportName(String item)
    {
        setString("reportName", item);
    }
    /**
     * Object:��������'s �ɱ������׶�property 
     */
    public String getStage()
    {
        return getString("stage");
    }
    public void setStage(String item)
    {
        setString("stage", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("17269CFB");
    }
}