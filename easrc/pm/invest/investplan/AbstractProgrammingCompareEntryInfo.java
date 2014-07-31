package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingCompareEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProgrammingCompareEntryInfo()
    {
        this("id");
    }
    protected AbstractProgrammingCompareEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ԭ�� 's ��Լ�滮 property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo getHead()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo)get("head");
    }
    public void setHead(com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo item)
    {
        put("head", item);
    }
    /**
     * Object:����ԭ��'s ������Լ�滮property 
     */
    public String getProgrammingContract()
    {
        return getString("programmingContract");
    }
    public void setProgrammingContract(String item)
    {
        setString("programmingContract", item);
    }
    /**
     * Object:����ԭ��'s ��������property 
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
     * Object:����ԭ��'s ����ԭ��property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E1960159");
    }
}