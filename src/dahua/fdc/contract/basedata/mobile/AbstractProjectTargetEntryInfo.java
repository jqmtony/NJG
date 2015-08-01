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
     * Object: ��Ŀָ���¼ 's ���� property 
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
     * Object:��Ŀָ���¼'s ��������property 
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
     * Object: ��Ŀָ���¼ 's ������λ property 
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
     * Object:��Ŀָ���¼'s ָ�����property 
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
     * Object:��Ŀָ���¼'s �Ƿ�Ԥ����property 
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
     * Object:��Ŀָ���¼'s �Ƿ��Զ�ȡ��property 
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
     * Object:��Ŀָ���¼'s �Ƿ�����property 
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