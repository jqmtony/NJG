package com.kingdee.eas.fdc.contract.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRealDateRelInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRealDateRelInfo()
    {
        this("id");
    }
    protected AbstractRealDateRelInfo(String pkField)
    {
        super(pkField);
        put("Entrys", new com.kingdee.eas.fdc.contract.basedata.RealDateRelEntryCollection());
    }
    /**
     * Object: ʱ�����ʱ���ϵ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.basedata.RealDateRelEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.basedata.RealDateRelEntryCollection)get("Entrys");
    }
    /**
     * Object:ʱ�����ʱ���ϵ��'s �汾��property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object:ʱ�����ʱ���ϵ��'s �Ƿ�����property 
     */
    public boolean isLatest()
    {
        return getBoolean("latest");
    }
    public void setLatest(boolean item)
    {
        setBoolean("latest", item);
    }
    /**
     * Object: ʱ�����ʱ���ϵ�� 's ������Ŀ property 
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
        return new BOSObjectType("FF80291B");
    }
}