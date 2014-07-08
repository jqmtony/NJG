package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInitialConnectionE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInitialConnectionE1Info()
    {
        this("id");
    }
    protected AbstractInitialConnectionE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第1个表体 's null property 
     */
    public com.kingdee.eas.port.equipment.base.InitialConnectionInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.base.InitialConnectionInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.base.InitialConnectionInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 第1个表体 's 初始化设备 property 
     */
    public com.kingdee.eas.custom.fi.AssetCardInfo getInitial()
    {
        return (com.kingdee.eas.custom.fi.AssetCardInfo)get("initial");
    }
    public void setInitial(com.kingdee.eas.custom.fi.AssetCardInfo item)
    {
        put("initial", item);
    }
    /**
     * Object: 第1个表体 's 设备档案 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEqupment()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("equpment");
    }
    public void setEqupment(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("equpment", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("082ACFCC");
    }
}