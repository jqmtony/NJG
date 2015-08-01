package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractGlobalTargetEntryInfo extends com.kingdee.eas.fdc.basedata.mobile.FdcMobileEntryInfo implements Serializable 
{
    public AbstractGlobalTargetEntryInfo()
    {
        this("id");
    }
    protected AbstractGlobalTargetEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 全局指标分录 's 单据 property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.GlobalTargetBillInfo getBill()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.GlobalTargetBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.fdc.basedata.mobile.GlobalTargetBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: 全局指标分录 's 项目指标分录 property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryInfo getProjectTargetEntry()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryInfo)get("projectTargetEntry");
    }
    public void setProjectTargetEntry(com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryInfo item)
    {
        put("projectTargetEntry", item);
    }
    /**
     * Object:全局指标分录's 统计类型property 
     */
    public com.kingdee.eas.fdc.basedata.FdcStatisticsTypeEnum getStatisticsType()
    {
        return com.kingdee.eas.fdc.basedata.FdcStatisticsTypeEnum.getEnum(getString("statisticsType"));
    }
    public void setStatisticsType(com.kingdee.eas.fdc.basedata.FdcStatisticsTypeEnum item)
    {
		if (item != null) {
        setString("statisticsType", item.getValue());
		}
    }
    /**
     * Object:全局指标分录's 是否预定义property 
     */
    public boolean isIsPreDefine()
    {
        return getBoolean("isPreDefine");
    }
    public void setIsPreDefine(boolean item)
    {
        setBoolean("isPreDefine", item);
    }
    /**
     * Object:全局指标分录's 是否自动取数property 
     */
    public boolean isIsAutoGetData()
    {
        return getBoolean("isAutoGetData");
    }
    public void setIsAutoGetData(boolean item)
    {
        setBoolean("isAutoGetData", item);
    }
    /**
     * Object:全局指标分录's 是否启用property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F294FC6A");
    }
}