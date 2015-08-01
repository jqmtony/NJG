package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanByMonthBaseInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPayPlanByMonthBaseInfo()
    {
        this("id");
    }
    protected AbstractPayPlanByMonthBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�¶ȸ���滮����'s ��������property 
     */
    public String getPaymentItem()
    {
        return getString("paymentItem");
    }
    public void setPaymentItem(String item)
    {
        setString("paymentItem", item);
    }
    /**
     * Object:�¶ȸ���滮����'s �ÿ�˵��property 
     */
    public String getUsage()
    {
        return getString("usage");
    }
    public void setUsage(String item)
    {
        setString("usage", item);
    }
    /**
     * Object: �¶ȸ���滮���� 's ������Ŀ property 
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
     * Object:�¶ȸ���滮����'s ��Դproperty 
     */
    public com.kingdee.bos.util.BOSUuid getSrcID()
    {
        return getBOSUuid("srcID");
    }
    public void setSrcID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("srcID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C549BD78");
    }
}