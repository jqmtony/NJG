package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTargetInfoEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTargetInfoEntryInfo()
    {
        this("id");
    }
    protected AbstractTargetInfoEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:移动运营-指标录入信息分录's 指标值property 
     */
    public String getValue()
    {
        return getString("value");
    }
    public void setValue(String item)
    {
        setString("value", item);
    }
    /**
     * Object: 移动运营-指标录入信息分录 's 指标录入头 property 
     */
    public com.kingdee.eas.fdc.basedata.TargetInfoInfo getHead()
    {
        return (com.kingdee.eas.fdc.basedata.TargetInfoInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.basedata.TargetInfoInfo item)
    {
        put("head", item);
    }
    /**
     * Object:移动运营-指标录入信息分录's 指标Idproperty 
     */
    public String getTargetId()
    {
        return getString("targetId");
    }
    public void setTargetId(String item)
    {
        setString("targetId", item);
    }
    /**
     * Object:移动运营-指标录入信息分录's 数据类型property 
     */
    public com.kingdee.eas.fdc.basedata.TargetDataTypeEnum getDataType()
    {
        return com.kingdee.eas.fdc.basedata.TargetDataTypeEnum.getEnum(getString("dataType"));
    }
    public void setDataType(com.kingdee.eas.fdc.basedata.TargetDataTypeEnum item)
    {
		if (item != null) {
        setString("dataType", item.getValue());
		}
    }
    /**
     * Object:移动运营-指标录入信息分录's 分组property 
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
     * Object:移动运营-指标录入信息分录's 指标名称property 
     */
    public String getTargetName()
    {
        return getString("targetName");
    }
    public void setTargetName(String item)
    {
        setString("targetName", item);
    }
    /**
     * Object:移动运营-指标录入信息分录's 统计类型property 
     */
    public com.kingdee.eas.fdc.basedata.TargetDataStatisticsTypeEnum getStatisticsType()
    {
        return com.kingdee.eas.fdc.basedata.TargetDataStatisticsTypeEnum.getEnum(getString("statisticsType"));
    }
    public void setStatisticsType(com.kingdee.eas.fdc.basedata.TargetDataStatisticsTypeEnum item)
    {
		if (item != null) {
        setString("statisticsType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AD0D38A1");
    }
}