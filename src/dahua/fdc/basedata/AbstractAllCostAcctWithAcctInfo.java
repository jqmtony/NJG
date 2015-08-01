package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAllCostAcctWithAcctInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAllCostAcctWithAcctInfo()
    {
        this("id");
    }
    protected AbstractAllCostAcctWithAcctInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�ɱ���Ŀ���ƿ�Ŀ��Ӧ��ϵ��ȫ��Ϣ��'s IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object:�ɱ���Ŀ���ƿ�Ŀ��Ӧ��ϵ��ȫ��Ϣ��'s ������ĿIDproperty 
     */
    public String getProjectId()
    {
        return getString("projectId");
    }
    public void setProjectId(String item)
    {
        setString("projectId", item);
    }
    /**
     * Object:�ɱ���Ŀ���ƿ�Ŀ��Ӧ��ϵ��ȫ��Ϣ��'s ��˾IDproperty 
     */
    public String getCompanyId()
    {
        return getString("companyId");
    }
    public void setCompanyId(String item)
    {
        setString("companyId", item);
    }
    /**
     * Object: �ɱ���Ŀ���ƿ�Ŀ��Ӧ��ϵ��ȫ��Ϣ�� 's �ɱ���Ŀ property 
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
     * Object: �ɱ���Ŀ���ƿ�Ŀ��Ӧ��ϵ��ȫ��Ϣ�� 's ��ƿ�Ŀ property 
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
     * Object: �ɱ���Ŀ���ƿ�Ŀ��Ӧ��ϵ��ȫ��Ϣ�� 's ��Ʊ��ƿ�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getInvoiceAcct()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("invoiceAcct");
    }
    public void setInvoiceAcct(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("invoiceAcct", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D3160728");
    }
}