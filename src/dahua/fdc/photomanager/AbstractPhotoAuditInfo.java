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
     * Object: 快照审批 's 分录 property 
     */
    public com.kingdee.eas.fdc.photomanager.PhotoAuditEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.photomanager.PhotoAuditEntryCollection)get("entrys");
    }
    /**
     * Object:快照审批's 是否生成凭证property 
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
     * Object:快照审批's 单据状态property 
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
     * Object:快照审批's 审批日期property 
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
     * Object:快照审批's 报表名称property 
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
     * Object:快照审批's 成本所属阶段property 
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