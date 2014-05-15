package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRegulationsInfo extends com.kingdee.eas.xr.xrbase.XRDataBaseInfo implements Serializable 
{
    public AbstractRegulationsInfo()
    {
        this("id");
    }
    protected AbstractRegulationsInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.equipment.special.RegulationsEntryCollection());
    }
    /**
     * Object: 设备管理法规制度 's 附件明细 property 
     */
    public com.kingdee.eas.port.equipment.special.RegulationsEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.equipment.special.RegulationsEntryCollection)get("Entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("126D7B5F");
    }
}