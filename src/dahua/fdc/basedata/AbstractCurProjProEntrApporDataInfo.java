package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCurProjProEntrApporDataInfo extends com.kingdee.eas.fdc.basedata.ProjProEntrApporDataInfo implements Serializable 
{
    public AbstractCurProjProEntrApporDataInfo()
    {
        this("id");
    }
    protected AbstractCurProjProEntrApporDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ǰ������Ŀ��Ʒ���÷�̯���� 's ��ǰ������Ŀ��Ʒ���÷�¼ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo getCurProjProductEntries()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo)get("curProjProductEntries");
    }
    public void setCurProjProductEntries(com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo item)
    {
        put("curProjProductEntries", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("00F65F65");
    }
}