package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBasePointTempInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBasePointTempInfo()
    {
        this("id");
    }
    protected AbstractBasePointTempInfo(String pkField)
    {
        super(pkField);
        put("Entrys", new com.kingdee.eas.fdc.costindexdb.database.BasePointTempEntryCollection());
    }
    /**
     * Object:����Ҫ��ģ��'s �Ƿ�����property 
     */
    public boolean isIsUse()
    {
        return getBoolean("isUse");
    }
    public void setIsUse(boolean item)
    {
        setBoolean("isUse", item);
    }
    /**
     * Object: ����Ҫ��ģ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BasePointTempEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BasePointTempEntryCollection)get("Entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D7E1E6EC");
    }
}