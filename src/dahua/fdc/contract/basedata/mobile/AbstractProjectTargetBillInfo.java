package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectTargetBillInfo extends com.kingdee.eas.fdc.basedata.mobile.FdcMobileBillInfo implements Serializable 
{
    public AbstractProjectTargetBillInfo()
    {
        this("id");
    }
    protected AbstractProjectTargetBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryCollection());
    }
    /**
     * Object: 项目指标单据 's 分录 property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryCollection)get("entries");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BB67A7DD");
    }
}