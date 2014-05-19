package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquipmentInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractEquipmentInfo()
    {
        this("id");
    }
    protected AbstractEquipmentInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3C6AD10C");
    }
}