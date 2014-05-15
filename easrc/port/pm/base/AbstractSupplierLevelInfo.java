package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierLevelInfo extends com.kingdee.eas.xr.xrbase.XRDataBaseInfo implements Serializable 
{
    public AbstractSupplierLevelInfo()
    {
        this("id");
    }
    protected AbstractSupplierLevelInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:供应商等级's 供应商等级property 
     */
    public String getLevel()
    {
        return getString("level");
    }
    public void setLevel(String item)
    {
        setString("level", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B2A55277");
    }
}