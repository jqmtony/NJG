package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTargetDescInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTargetDescInfo()
    {
        this("id");
    }
    protected AbstractTargetDescInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.basedata.TargetDescEntryCollection());
    }
    /**
     * Object:�ƶ���Ӫ-ָ��������Ϣ's ��property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:�ƶ���Ӫ-ָ��������Ϣ's �·�property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object: �ƶ���Ӫ-ָ��������Ϣ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: �ƶ���Ӫ-ָ��������Ϣ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.basedata.TargetDescEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.basedata.TargetDescEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("48234434");
    }
}