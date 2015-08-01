package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHisProjProductEntriesInfo extends com.kingdee.eas.fdc.basedata.ProjProductEntriesInfo implements Serializable 
{
    public AbstractHisProjProductEntriesInfo()
    {
        this("id");
    }
    protected AbstractHisProjProductEntriesInfo(String pkField)
    {
        super(pkField);
        put("hisProjProEntrApporData", new com.kingdee.eas.fdc.basedata.HisProjProEntrApporDataCollection());
    }
    /**
     * Object: ��ʷ������Ŀ��Ʒ���÷�¼ 's ��ʷ������Ŀ��Ʒ���÷�̯���� property 
     */
    public com.kingdee.eas.fdc.basedata.HisProjProEntrApporDataCollection getHisProjProEntrApporData()
    {
        return (com.kingdee.eas.fdc.basedata.HisProjProEntrApporDataCollection)get("hisProjProEntrApporData");
    }
    /**
     * Object: ��ʷ������Ŀ��Ʒ���÷�¼ 's ��ʷ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.HisProjectInfo getHisProject()
    {
        return (com.kingdee.eas.fdc.basedata.HisProjectInfo)get("hisProject");
    }
    public void setHisProject(com.kingdee.eas.fdc.basedata.HisProjectInfo item)
    {
        put("hisProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("082AE0DE");
    }
}