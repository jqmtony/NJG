package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProProgressReportInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractProProgressReportInfo()
    {
        this("id");
    }
    protected AbstractProProgressReportInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.invest.ProProgressReportE1Collection());
    }
    /**
     * Object: ��Ŀ���Ȼ㱨 's ��1������ property 
     */
    public com.kingdee.eas.port.pm.invest.ProProgressReportE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.invest.ProProgressReportE1Collection)get("E1");
    }
    /**
     * Object: ��Ŀ���Ȼ㱨 's �·� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getMonth()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("month");
    }
    public void setMonth(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("month", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("677FE391");
    }
}