package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractGlobalTargetBillInfo extends com.kingdee.eas.fdc.basedata.mobile.FdcMobileBillInfo implements Serializable 
{
    public AbstractGlobalTargetBillInfo()
    {
        this("id");
    }
    protected AbstractGlobalTargetBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.basedata.mobile.GlobalTargetEntryCollection());
    }
    /**
     * Object: ȫ��ָ�굥�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.GlobalTargetEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.GlobalTargetEntryCollection)get("entries");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("07D1D10F");
    }
}