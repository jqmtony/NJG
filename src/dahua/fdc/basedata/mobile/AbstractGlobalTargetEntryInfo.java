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
     * Object: ȫ��ָ���¼ 's ���� property 
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
     * Object: ȫ��ָ���¼ 's ��Ŀָ���¼ property 
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
     * Object:ȫ��ָ���¼'s ͳ������property 
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
     * Object:ȫ��ָ���¼'s �Ƿ�Ԥ����property 
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
     * Object:ȫ��ָ���¼'s �Ƿ��Զ�ȡ��property 
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
     * Object:ȫ��ָ���¼'s �Ƿ�����property 
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