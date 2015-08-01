package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSpecialtyTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSpecialtyTypeInfo()
    {
        this("id");
    }
    protected AbstractSpecialtyTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 专业类型 's 变更类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeTypeInfo getChangeType()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeTypeInfo)get("changeType");
    }
    public void setChangeType(com.kingdee.eas.fdc.basedata.ChangeTypeInfo item)
    {
        put("changeType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DD5D9BA6");
    }
}