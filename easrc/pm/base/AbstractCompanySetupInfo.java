package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompanySetupInfo extends com.kingdee.eas.xr.xrbase.XRDataBaseInfo implements Serializable 
{
    public AbstractCompanySetupInfo()
    {
        this("id");
    }
    protected AbstractCompanySetupInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.pm.base.CompanySetupEntryCollection());
    }
    /**
     * Object: 公司性质维护 's 分录 property 
     */
    public com.kingdee.eas.port.pm.base.CompanySetupEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.base.CompanySetupEntryCollection)get("Entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A0A9CAC1");
    }
}