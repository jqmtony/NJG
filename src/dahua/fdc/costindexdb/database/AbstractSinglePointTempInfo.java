package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSinglePointTempInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSinglePointTempInfo()
    {
        this("id");
    }
    protected AbstractSinglePointTempInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.fdc.costindexdb.database.SinglePointTempEntryCollection());
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
    public com.kingdee.eas.fdc.costindexdb.database.SinglePointTempEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.SinglePointTempEntryCollection)get("Entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("66F13515");
    }
}