package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCurProjProductEntriesInfo extends com.kingdee.eas.fdc.basedata.ProjProductEntriesInfo implements Serializable 
{
    public AbstractCurProjProductEntriesInfo()
    {
        this("id");
    }
    protected AbstractCurProjProductEntriesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ǰ������Ŀ��Ʒ���÷�¼ 's ��ǰ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D719C00C");
    }
}