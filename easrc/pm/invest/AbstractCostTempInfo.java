package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostTempInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractCostTempInfo()
    {
        this("id");
    }
    protected AbstractCostTempInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.invest.CostTempE1Collection());
    }
    /**
     * Object: 费用模板 's 费用模板 property 
     */
    public com.kingdee.eas.port.pm.invest.CostTempE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.invest.CostTempE1Collection)get("E1");
    }
    /**
     * Object:费用模板's 模板名称property 
     */
    public String getTempName()
    {
        return getString("tempName");
    }
    public void setTempName(String item)
    {
        setString("tempName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7130F81E");
    }
}