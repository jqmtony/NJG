package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectTargetValueBillInfo extends com.kingdee.eas.fdc.basedata.mobile.FdcMobileBillInfo implements Serializable 
{
    public AbstractProjectTargetValueBillInfo()
    {
        this("id");
    }
    protected AbstractProjectTargetValueBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueEntryCollection());
    }
    /**
     * Object: ��Ŀָ��ֵ���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueEntryCollection)get("entries");
    }
    /**
     * Object: ��Ŀָ��ֵ���� 's ������Ŀ property 
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
     * Object:��Ŀָ��ֵ����'s ���property 
     */
    public java.math.BigDecimal getYear()
    {
        return getBigDecimal("year");
    }
    public void setYear(java.math.BigDecimal item)
    {
        setBigDecimal("year", item);
    }
    /**
     * Object:��Ŀָ��ֵ����'s �·�property 
     */
    public java.math.BigDecimal getMonth()
    {
        return getBigDecimal("month");
    }
    public void setMonth(java.math.BigDecimal item)
    {
        setBigDecimal("month", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2AC17B02");
    }
}