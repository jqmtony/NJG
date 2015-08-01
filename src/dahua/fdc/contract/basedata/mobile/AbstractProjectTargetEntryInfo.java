package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectTargetEntryInfo extends com.kingdee.eas.fdc.basedata.mobile.FdcMobileEntryInfo implements Serializable 
{
    public AbstractProjectTargetEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectTargetEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目指标分录 's 单据 property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.ProjectTargetBillInfo getBill()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.ProjectTargetBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.fdc.basedata.mobile.ProjectTargetBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object:项目指标分录's 数据类型property 
     */
    public com.kingdee.eas.fdc.basedata.DataTypeEnum getDataType()
    {
        return com.kingdee.eas.fdc.basedata.DataTypeEnum.getEnum(getInt("dataType"));
    }
    public void setDataType(com.kingdee.eas.fdc.basedata.DataTypeEnum item)
    {
		if (item != null) {
        setInt("dataType", item.getValue());
		}
    }
    /**
     * Object: 项目指标分录 's 计量单位 property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object:项目指标分录's 指标分组property 
     */
    public com.kingdee.eas.fdc.basedata.TargetGroupEnum getTargetGroup()
    {
        return com.kingdee.eas.fdc.basedata.TargetGroupEnum.getEnum(getString("targetGroup"));
    }
    public void setTargetGroup(com.kingdee.eas.fdc.basedata.TargetGroupEnum item)
    {
		if (item != null) {
        setString("targetGroup", item.getValue());
		}
    }
    /**
     * Object:项目指标分录's 是否预定义property 
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
     * Object:项目指标分录's 是否自动取数property 
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
     * Object:项目指标分录's 是否启用property 
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
        return new BOSObjectType("B1B9FF5C");
    }
}