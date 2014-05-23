package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPreProjectTempE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPreProjectTempE1Info()
    {
        this("id");
    }
    protected AbstractPreProjectTempE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ŀǰ��ģ�� 's null property 
     */
    public com.kingdee.eas.port.pm.invest.PreProjectTempInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.PreProjectTempInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.PreProjectTempInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Ŀǰ��ģ��'s ǰ�ڹ�������property 
     */
    public String getPreWorkContent()
    {
        return getString("preWorkContent");
    }
    public void setPreWorkContent(String item)
    {
        setString("preWorkContent", item);
    }
    /**
     * Object:��Ŀǰ��ģ��'s ����Ҫ��property 
     */
    public String getWorkRequest()
    {
        return getString("workRequest");
    }
    public void setWorkRequest(String item)
    {
        setString("workRequest", item);
    }
    /**
     * Object: ��Ŀǰ��ģ�� 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespondDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respondDepart");
    }
    public void setRespondDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respondDepart", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BF9E97D3");
    }
}