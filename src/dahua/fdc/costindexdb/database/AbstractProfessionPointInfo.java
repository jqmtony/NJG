package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProfessionPointInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractProfessionPointInfo()
    {
        this("id");
    }
    protected AbstractProfessionPointInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.fdc.costindexdb.database.ProfessionPointEntryCollection());
    }
    /**
     * Object: רҵҪ��ά���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.ProfessionPointEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ProfessionPointEntryCollection)get("Entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8C4A44ED");
    }
}