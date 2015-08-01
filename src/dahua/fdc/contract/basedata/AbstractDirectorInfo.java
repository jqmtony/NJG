package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDirectorInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDirectorInfo()
    {
        this("id");
    }
    protected AbstractDirectorInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����'s ��������property 
     */
    public String getName()
    {
        return getName((Locale)null);
    }
    public void setName(String item)
    {
		setName(item,(Locale)null);
    }
    public String getName(Locale local)
    {
        return TypeConversionUtils.objToString(get("name", local));
    }
    public void setName(String item, Locale local)
    {
        put("name", item, local);
    }
    /**
     * Object: ���� 's �� property 
     */
    public com.kingdee.eas.fdc.basedata.UnitDataManagerInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.UnitDataManagerInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.UnitDataManagerInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1381587E");
    }
}