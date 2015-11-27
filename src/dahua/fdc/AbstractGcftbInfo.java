package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractGcftbInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractGcftbInfo()
    {
        this("id");
    }
    protected AbstractGcftbInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryCollection());
    }
    /**
     * Object: 公建配套工程分摊情况表 's 分录 property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryCollection)get("entrys");
    }
    /**
     * Object:公建配套工程分摊情况表's 公司名称property 
     */
    public String getGsmc()
    {
        return getString("gsmc");
    }
    public void setGsmc(String item)
    {
        setString("gsmc", item);
    }
    /**
     * Object:公建配套工程分摊情况表's 版本号property 
     */
    public String getBbh()
    {
        return getString("bbh");
    }
    public void setBbh(String item)
    {
        setString("bbh", item);
    }
    /**
     * Object:公建配套工程分摊情况表's 状态property 
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
     * Object:公建配套工程分摊情况表's 是否最新版property 
     */
    public boolean isIsLast()
    {
        return getBoolean("isLast");
    }
    public void setIsLast(boolean item)
    {
        setBoolean("isLast", item);
    }
    /**
     * Object:公建配套工程分摊情况表's 审核时间property 
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
     * Object: 公建配套工程分摊情况表 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.TreeNodeInfo getEngineeringProject()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.TreeNodeInfo)get("engineeringProject");
    }
    public void setEngineeringProject(com.kingdee.eas.fdc.gcftbiaoa.TreeNodeInfo item)
    {
        put("engineeringProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F767F4D1");
    }
}