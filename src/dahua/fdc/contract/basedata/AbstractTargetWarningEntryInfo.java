package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTargetWarningEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTargetWarningEntryInfo()
    {
        this("id");
    }
    protected AbstractTargetWarningEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:移动运营-预警设置分录's 基准值property 
     */
    public String getBaseValue()
    {
        return getString("baseValue");
    }
    public void setBaseValue(String item)
    {
        setString("baseValue", item);
    }
    /**
     * Object:移动运营-预警设置分录's 下限值property 
     */
    public String getLowerLimit()
    {
        return getString("lowerLimit");
    }
    public void setLowerLimit(String item)
    {
        setString("lowerLimit", item);
    }
    /**
     * Object:移动运营-预警设置分录's 上限值property 
     */
    public String getUpperLimit()
    {
        return getString("upperLimit");
    }
    public void setUpperLimit(String item)
    {
        setString("upperLimit", item);
    }
    /**
     * Object:移动运营-预警设置分录's 指标名称property 
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
     * Object:移动运营-预警设置分录's targetIdproperty 
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
     * Object:移动运营-预警设置分录's 分组property 
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
     * Object:移动运营-预警设置分录's 数据类型property 
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
     * Object: 移动运营-预警设置分录 's null property 
     */
    public com.kingdee.eas.fdc.basedata.TargetWarningInfo getHead()
    {
        return (com.kingdee.eas.fdc.basedata.TargetWarningInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.basedata.TargetWarningInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("73D5BCD9");
    }
}