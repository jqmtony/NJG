package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNoticeInfoInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractNoticeInfoInfo()
    {
        this("id");
    }
    protected AbstractNoticeInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������Ϣ's ��������property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object:������Ϣ's ��������property 
     */
    public java.util.Date getContentDate()
    {
        return getDate("contentDate");
    }
    public void setContentDate(java.util.Date item)
    {
        setDate("contentDate", item);
    }
    /**
     * Object: ������Ϣ 's ������Ŀ property 
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
        return new BOSObjectType("DF7CD418");
    }
}