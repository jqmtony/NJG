package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInitialConnectionInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractInitialConnectionInfo()
    {
        this("id");
    }
    protected AbstractInitialConnectionInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.base.InitialConnectionE1Collection());
    }
    /**
     * Object: ��ʼ���豸���豸�������� 's ��1������ property 
     */
    public com.kingdee.eas.port.equipment.base.InitialConnectionE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.base.InitialConnectionE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E6B39740");
    }
}