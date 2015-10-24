package com.kingdee.eas.fdc.photomanager;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPhotoAuditEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPhotoAuditEntryInfo()
    {
        this("id");
    }
    protected AbstractPhotoAuditEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.photomanager.PhotoAuditInfo getParent()
    {
        return (com.kingdee.eas.fdc.photomanager.PhotoAuditInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.photomanager.PhotoAuditInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("54EB6037");
    }
}