package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOtherSplitNewEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractOtherSplitNewEntryInfo()
    {
        this("id");
    }
    protected AbstractOtherSplitNewEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 拆分分录 's 其他拆分单据头 property 
     */
    public com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("09BD8D62");
    }
}