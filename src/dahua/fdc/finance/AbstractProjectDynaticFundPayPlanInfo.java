package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectDynaticFundPayPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectDynaticFundPayPlanInfo()
    {
        this("id");
    }
    protected AbstractProjectDynaticFundPayPlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanEntryCollection());
    }
    /**
     * Object: 项目动态资金支付计划 's 工程项目 property 
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
     * Object:项目动态资金支付计划's 版本号property 
     */
    public String getVersionNumber()
    {
        return getString("versionNumber");
    }
    public void setVersionNumber(String item)
    {
        setString("versionNumber", item);
    }
    /**
     * Object:项目动态资金支付计划's 版本名称property 
     */
    public String getVersionName()
    {
        return getString("versionName");
    }
    public void setVersionName(String item)
    {
        setString("versionName", item);
    }
    /**
     * Object:项目动态资金支付计划's 主版本号property 
     */
    public int getMainVerNo()
    {
        return getInt("mainVerNo");
    }
    public void setMainVerNo(int item)
    {
        setInt("mainVerNo", item);
    }
    /**
     * Object:项目动态资金支付计划's 子版本号property 
     */
    public int getSubVerNo()
    {
        return getInt("subVerNo");
    }
    public void setSubVerNo(int item)
    {
        setInt("subVerNo", item);
    }
    /**
     * Object:项目动态资金支付计划's 修订日期property 
     */
    public java.util.Date getRecenseDate()
    {
        return getDate("recenseDate");
    }
    public void setRecenseDate(java.util.Date item)
    {
        setDate("recenseDate", item);
    }
    /**
     * Object:项目动态资金支付计划's 是否最终版本property 
     */
    public boolean isIsLastVersion()
    {
        return getBoolean("isLastVersion");
    }
    public void setIsLastVersion(boolean item)
    {
        setBoolean("isLastVersion", item);
    }
    /**
     * Object: 项目动态资金支付计划 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("078223D0");
    }
}