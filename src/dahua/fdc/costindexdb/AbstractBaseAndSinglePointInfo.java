package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBaseAndSinglePointInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractBaseAndSinglePointInfo()
    {
        this("id");
    }
    protected AbstractBaseAndSinglePointInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryCollection());
        put("Ecost", new com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostCollection());
    }
    /**
     * Object: 基本要素-单项要素 's 基本要素 property 
     */
    public com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryCollection)get("entrys");
    }
    /**
     * Object:基本要素-单项要素's 项目名称property 
     */
    public String getProjectName()
    {
        return getString("projectName");
    }
    public void setProjectName(String item)
    {
        setString("projectName", item);
    }
    /**
     * Object:基本要素-单项要素's 项目IDproperty 
     */
    public String getProjectId()
    {
        return getString("projectId");
    }
    public void setProjectId(String item)
    {
        setString("projectId", item);
    }
    /**
     * Object:基本要素-单项要素's 版本号property 
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
     * Object:基本要素-单项要素's 是否最新property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object: 基本要素-单项要素 's 单项要素 property 
     */
    public com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostCollection getEcost()
    {
        return (com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostCollection)get("Ecost");
    }
    /**
     * Object:基本要素-单项要素's 单据状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getPointBillStatus()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("pointBillStatus"));
    }
    public void setPointBillStatus(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("pointBillStatus", item.getValue());
		}
    }
    /**
     * Object:基本要素-单项要素's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object:基本要素-单项要素's 审核时间property 
     */
    public java.util.Date getAudiTime()
    {
        return getDate("audiTime");
    }
    public void setAudiTime(java.util.Date item)
    {
        setDate("audiTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4B9566A8");
    }
}