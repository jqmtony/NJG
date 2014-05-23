package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPreProjectTempInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractPreProjectTempInfo()
    {
        this("id");
    }
    protected AbstractPreProjectTempInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.invest.PreProjectTempE1Collection());
    }
    /**
     * Object:��Ŀǰ��ģ��'s ģ������property 
     */
    public String getTempName()
    {
        return getString("tempName");
    }
    public void setTempName(String item)
    {
        setString("tempName", item);
    }
    /**
     * Object: ��Ŀǰ��ģ�� 's ��Ŀǰ��ģ�� property 
     */
    public com.kingdee.eas.port.pm.invest.PreProjectTempE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.invest.PreProjectTempE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8EB7B507");
    }
}