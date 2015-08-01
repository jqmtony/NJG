package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractApportionTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractApportionTypeInfo()
    {
        this("id");
    }
    protected AbstractApportionTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ָ�� 's ָ������ property 
     */
    public com.kingdee.eas.fdc.basedata.TargetTypeInfo getTargetType()
    {
        return (com.kingdee.eas.fdc.basedata.TargetTypeInfo)get("targetType");
    }
    public void setTargetType(com.kingdee.eas.fdc.basedata.TargetTypeInfo item)
    {
        put("targetType", item);
    }
    /**
     * Object: ָ�� 's ������λ property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getMeasureUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("measureUnit");
    }
    public void setMeasureUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("measureUnit", item);
    }
    /**
     * Object:ָ��'s �ɱ���̯ʱʹ��property 
     */
    public boolean isForCostApportion()
    {
        return getBoolean("forCostApportion");
    }
    public void setForCostApportion(boolean item)
    {
        setBoolean("forCostApportion", item);
    }
    /**
     * Object:ָ��'s �ɻ���property 
     */
    public boolean isForGather()
    {
        return getBoolean("forGather");
    }
    public void setForGather(boolean item)
    {
        setBoolean("forGather", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("35B46E80");
    }
}