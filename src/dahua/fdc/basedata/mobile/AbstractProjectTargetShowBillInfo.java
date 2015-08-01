package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectTargetShowBillInfo extends com.kingdee.eas.fdc.basedata.mobile.FdcMobileBillInfo implements Serializable 
{
    public AbstractProjectTargetShowBillInfo()
    {
        this("id");
    }
    protected AbstractProjectTargetShowBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryCollection());
    }
    /**
     * Object: ��Ŀָ����ʾ���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryCollection)get("entries");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A44D557A");
    }
}