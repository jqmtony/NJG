package com.kingdee.eas.custom.richbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOrgUnitInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractOrgUnitInfo()
    {
        this("id");
    }
    protected AbstractOrgUnitInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.custom.richbase.OrgUnitEntryCollection());
    }
    /**
     * Object: 体检机构对应表 's 对应信息 property 
     */
    public com.kingdee.eas.custom.richbase.OrgUnitEntryCollection getEntry()
    {
        return (com.kingdee.eas.custom.richbase.OrgUnitEntryCollection)get("Entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("257FF4D2");
    }
}