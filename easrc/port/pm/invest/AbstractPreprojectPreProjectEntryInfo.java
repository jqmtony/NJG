package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPreprojectPreProjectEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPreprojectPreProjectEntryInfo()
    {
        this("id");
    }
    protected AbstractPreprojectPreProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第1个表体 's null property 
     */
    public com.kingdee.eas.port.pm.invest.PreprojectInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.PreprojectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.PreprojectInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("08C833E9");
    }
}