package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDecorationEngineeringInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractDecorationEngineeringInfo()
    {
        this("id");
    }
    protected AbstractDecorationEngineeringInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringEntryCollection());
    }
    /**
     * Object: 装修工程表 's 分录 property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringEntryCollection)get("entrys");
    }
    /**
     * Object:装修工程表's 是否生成凭证property 
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
     * Object:装修工程表's 公司名称property 
     */
    public String getCompany()
    {
        return getString("company");
    }
    public void setCompany(String item)
    {
        setString("company", item);
    }
    /**
     * Object: 装修工程表 's 项目名称 property 
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
     * Object:装修工程表's 版本号property 
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
     * Object:装修工程表's 是否最新property 
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
     * Object: 装修工程表 's 样式 property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.StyleInfo getStyle()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.StyleInfo)get("Style");
    }
    public void setStyle(com.kingdee.eas.fdc.gcftbiaoa.StyleInfo item)
    {
        put("Style", item);
    }
    /**
     * Object:装修工程表's 状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:装修工程表's 审核时间property 
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
        return new BOSObjectType("D5DC901C");
    }
}