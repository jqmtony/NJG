package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichCompayWriteOffE2Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRichCompayWriteOffE2Info()
    {
        this("id");
    }
    protected AbstractRichCompayWriteOffE2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 应收发票 's null property 
     */
    public com.kingdee.eas.custom.richinf.RichCompayWriteOffInfo getParent()
    {
        return (com.kingdee.eas.custom.richinf.RichCompayWriteOffInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.richinf.RichCompayWriteOffInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 应收发票 's 发票号 property 
     */
    public com.kingdee.eas.fi.ar.OtherBillInfo getFpNo()
    {
        return (com.kingdee.eas.fi.ar.OtherBillInfo)get("fpNo");
    }
    public void setFpNo(com.kingdee.eas.fi.ar.OtherBillInfo item)
    {
        put("fpNo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DE7B2B8E");
    }
}