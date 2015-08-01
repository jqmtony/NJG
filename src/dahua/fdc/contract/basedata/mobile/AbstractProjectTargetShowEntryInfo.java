package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectTargetShowEntryInfo extends com.kingdee.eas.fdc.basedata.mobile.FdcMobileEntryInfo implements Serializable 
{
    public AbstractProjectTargetShowEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectTargetShowEntryInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowItemCollection());
    }
    /**
     * Object: 项目指标显示分录 's 单据 property 
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
     * Object: 项目指标显示分录 's 分录 property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowItemCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowItemCollection)get("entries");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E58A055F");
    }
}