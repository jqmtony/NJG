package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAcctAccreditAcctsInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAcctAccreditAcctsInfo()
    {
        this("id");
    }
    protected AbstractAcctAccreditAcctsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 授权科目 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object: 授权科目 's 方案 property 
     */
    public com.kingdee.eas.fdc.basedata.AcctAccreditSchemeInfo getScheme()
    {
        return (com.kingdee.eas.fdc.basedata.AcctAccreditSchemeInfo)get("scheme");
    }
    public void setScheme(com.kingdee.eas.fdc.basedata.AcctAccreditSchemeInfo item)
    {
        put("scheme", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E98F27E0");
    }
}