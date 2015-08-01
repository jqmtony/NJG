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
     * Object:�ƶ���Ӫ-Ԥ�����÷�¼'s ��׼ֵproperty 
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
     * Object:�ƶ���Ӫ-Ԥ�����÷�¼'s ����ֵproperty 
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
     * Object:�ƶ���Ӫ-Ԥ�����÷�¼'s ����ֵproperty 
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
     * Object:�ƶ���Ӫ-Ԥ�����÷�¼'s ָ������property 
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
     * Object:�ƶ���Ӫ-Ԥ�����÷�¼'s targetIdproperty 
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
     * Object:�ƶ���Ӫ-Ԥ�����÷�¼'s ����property 
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
     * Object:�ƶ���Ӫ-Ԥ�����÷�¼'s ��������property 
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
     * Object: �ƶ���Ӫ-Ԥ�����÷�¼ 's null property 
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