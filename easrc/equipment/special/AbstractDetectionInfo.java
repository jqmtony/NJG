package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDetectionInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractDetectionInfo()
    {
        this("id");
    }
    protected AbstractDetectionInfo(String pkField)
    {
        super(pkField);
        put("E2", new com.kingdee.eas.port.equipment.special.DetectionE2Collection());
        put("E1", new com.kingdee.eas.port.equipment.special.DetectionE1Collection());
    }
    /**
     * Object: 设备检测小结 's 第1个表体 property 
     */
    public com.kingdee.eas.port.equipment.special.DetectionE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.special.DetectionE1Collection)get("E1");
    }
    /**
     * Object: 设备检测小结 's 第2个表体 property 
     */
    public com.kingdee.eas.port.equipment.special.DetectionE2Collection getE2()
    {
        return (com.kingdee.eas.port.equipment.special.DetectionE2Collection)get("E2");
    }
    /**
     * Object:设备检测小结's 实际检测日期property 
     */
    public java.util.Date getActualDate()
    {
        return getDate("actualDate");
    }
    public void setActualDate(java.util.Date item)
    {
        setDate("actualDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1A6F195B");
    }
}