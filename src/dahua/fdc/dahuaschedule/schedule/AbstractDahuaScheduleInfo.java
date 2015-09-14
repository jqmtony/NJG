package com.kingdee.eas.fdc.dahuaschedule.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDahuaScheduleInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractDahuaScheduleInfo()
    {
        this("id");
    }
    protected AbstractDahuaScheduleInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleEntryCollection());
    }
    /**
     * Object: ���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleEntryCollection)get("entrys");
    }
    /**
     * Object:����'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:����'s ��������property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:����'s �汾property 
     */
    public java.math.BigDecimal getVersion()
    {
        return getBigDecimal("version");
    }
    public void setVersion(java.math.BigDecimal item)
    {
        setBigDecimal("version", item);
    }
    /**
     * Object: ���� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BD91F978");
    }
}