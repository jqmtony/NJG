package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectTargetShowItemInfo extends com.kingdee.eas.fdc.basedata.mobile.FdcMobileEntryInfo implements Serializable 
{
    public AbstractProjectTargetShowItemInfo()
    {
        this("id");
    }
    protected AbstractProjectTargetShowItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ŀָ��ֵ�嵥 's ���� property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowBillInfo getBill()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: ��Ŀָ��ֵ�嵥 's ������ property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��Ŀָ��ֵ�嵥 's ��Ŀָ���¼ property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryInfo getProjectTargetEntry()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryInfo)get("projectTargetEntry");
    }
    public void setProjectTargetEntry(com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryInfo item)
    {
        put("projectTargetEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A450AC86");
    }
}