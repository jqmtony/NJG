package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectStartRequestInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractProjectStartRequestInfo()
    {
        this("id");
    }
    protected AbstractProjectStartRequestInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ŀ��������'s ʵʩ����property 
     */
    public String getScheme()
    {
        return getString("scheme");
    }
    public void setScheme(String item)
    {
        setString("scheme", item);
    }
    /**
     * Object: ��Ŀ�������� 's ��Ŀ���� property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProjectName()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("projectName");
    }
    public void setProjectName(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("projectName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3BA1FA89");
    }
}