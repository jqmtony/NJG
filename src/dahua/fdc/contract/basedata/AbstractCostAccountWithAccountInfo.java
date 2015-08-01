package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostAccountWithAccountInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractCostAccountWithAccountInfo()
    {
        this("id");
    }
    protected AbstractCostAccountWithAccountInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ɱ���Ŀ���ƿ�Ŀ��ϵ 's �ɱ���Ŀ property 
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
     * Object: �ɱ���Ŀ���ƿ�Ŀ��ϵ 's ��ƿ�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("account");
    }
    public void setAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("account", item);
    }
    /**
     * Object:�ɱ���Ŀ���ƿ�Ŀ��ϵ's ����property 
     */
    public String getDescription()
    {
        return getDescription((Locale)null);
    }
    public void setDescription(String item)
    {
		setDescription(item,(Locale)null);
    }
    public String getDescription(Locale local)
    {
        return TypeConversionUtils.objToString(get("description", local));
    }
    public void setDescription(String item, Locale local)
    {
        put("description", item, local);
    }
    /**
     * Object: �ɱ���Ŀ���ƿ�Ŀ��ϵ 's ��Ʊ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getInvoiceAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("invoiceAccount");
    }
    public void setInvoiceAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("invoiceAccount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("256C7E39");
    }
}