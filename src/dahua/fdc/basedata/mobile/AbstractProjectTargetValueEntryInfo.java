package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectTargetValueEntryInfo extends com.kingdee.eas.fdc.basedata.mobile.FdcMobileEntryInfo implements Serializable 
{
    public AbstractProjectTargetValueEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectTargetValueEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ŀָ��ֵ��¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueBillInfo getBill()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object:��Ŀָ��ֵ��¼'s ָ�����property 
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
     * Object: ��Ŀָ��ֵ��¼ 's ��Ŀָ���¼ property 
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
     * Object:��Ŀָ��ֵ��¼'s ָ��ֵproperty 
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
     * Object:��Ŀָ��ֵ��¼'s �Ƿ�����property 
     */
    public boolean isIsLight()
    {
        return getBoolean("isLight");
    }
    public void setIsLight(boolean item)
    {
        setBoolean("isLight", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2D9A90D7");
    }
}