package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHisProjCostEntriesInfo extends com.kingdee.eas.fdc.basedata.ProjCostEntriesInfo implements Serializable 
{
    public AbstractHisProjCostEntriesInfo()
    {
        this("id");
    }
    protected AbstractHisProjCostEntriesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ʷ������Ŀ�ɱ���¼ 's ��ʷ������Ŀ property 
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
        return new BOSObjectType("951100E6");
    }
}