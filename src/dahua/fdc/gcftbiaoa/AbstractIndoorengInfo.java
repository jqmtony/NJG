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
     * Object: 户内工程表 's 分录 property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.IndoorengEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IndoorengEntryCollection)get("entrys");
    }
    /**
     * Object:户内工程表's 是否生成凭证property 
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
     * Object:户内工程表's 状态property 
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
     * Object: 户内工程表 's 项目名称 property 
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
     * Object:户内工程表's 版本号property 
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
     * Object:户内工程表's 是否最新property 
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
     * Object: 户内工程表 's 房型 property 
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
     * Object:户内工程表's 销售面积（㎡）property 
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
     * Object:户内工程表's 销售面积指标（元/㎡）property 
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
     * Object:户内工程表's 软装property 
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
     * Object:户内工程表's 硬装property 
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
     * Object:户内工程表's 审核时间property 
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