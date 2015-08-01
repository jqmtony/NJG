package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanCycleInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractPayPlanCycleInfo()
    {
        this("id");
    }
    protected AbstractPayPlanCycleInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ƻ�����'s ����ƻ�����property 
     */
    public com.kingdee.eas.fdc.basedata.PayPlanCycleEnum getCycle()
    {
        return com.kingdee.eas.fdc.basedata.PayPlanCycleEnum.getEnum(getInt("cycle"));
    }
    public void setCycle(com.kingdee.eas.fdc.basedata.PayPlanCycleEnum item)
    {
		if (item != null) {
        setInt("cycle", item.getValue());
		}
    }
    /**
     * Object: ����ƻ����� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("org");
    }
    public void setOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("org", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("39599767");
    }
}