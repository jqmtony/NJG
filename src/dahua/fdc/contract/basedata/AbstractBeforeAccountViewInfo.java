package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBeforeAccountViewInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractBeforeAccountViewInfo()
    {
        this("id");
    }
    protected AbstractBeforeAccountViewInfo(String pkField)
    {
        super(pkField);
        put("deductAccountEntrys", new com.kingdee.eas.fdc.basedata.DeductAccountEntrysCollection());
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's Ӧ���ʿ�_�����ɱ�_���ȿ� property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getProAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("proAccount");
    }
    public void setProAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("proAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's Ӧ���ʿ�_�����ɱ�_���޿� property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getSettAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("settAccount");
    }
    public void setSettAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("settAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's Ӧ���ʿ�_�����ɱ�_Ԥ��ɱ� property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getIntendAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("intendAccount");
    }
    public void setIntendAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("intendAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's ��˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's Ӧ���ʿ�_����_���ȿ� property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOtherProAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("otherProAccount");
    }
    public void setOtherProAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("otherProAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's Ӧ���ʿ�_����_���޿� property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOtherSettAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("otherSettAccount");
    }
    public void setOtherSettAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("otherSettAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's Ӧ���ʿ�_����_Ԥ��ɱ� property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOtherIntendAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("otherIntendAccount");
    }
    public void setOtherIntendAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("otherIntendAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's Ԥ���ʿ�_�����ɱ�_�����ɱ� property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getBeforeOtherAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("beforeOtherAccount");
    }
    public void setBeforeOtherAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("beforeOtherAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's �����ɱ�_Ԥ��ɱ� property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getBeforeDevAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("beforeDevAccount");
    }
    public void setBeforeDevAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("beforeDevAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's �����ɱ�_�ɱ���ת property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getBeforeSettAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("beforeSettAccount");
    }
    public void setBeforeSettAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("beforeSettAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's �깤������Ʒ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getProductAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("productAccount");
    }
    public void setProductAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("productAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's ������� property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAdminFees()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("adminFees");
    }
    public void setAdminFees(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("adminFees", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's Ӫ������ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getMarketFees()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("marketFees");
    }
    public void setMarketFees(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("marketFees", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's ΥԼ��Ӧ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getCompensationAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("compensationAccount");
    }
    public void setCompensationAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("compensationAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's �ۿ��¼ property 
     */
    public com.kingdee.eas.fdc.basedata.DeductAccountEntrysCollection getDeductAccountEntrys()
    {
        return (com.kingdee.eas.fdc.basedata.DeductAccountEntrysCollection)get("deductAccountEntrys");
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's ������Ӧ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getGuerdonAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("guerdonAccount");
    }
    public void setGuerdonAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("guerdonAccount", item);
    }
    /**
     * Object: ����ɱ�һ�廯��Ŀ���� 's Ӧ���ʿ�_�����ɱ�_�ݹ��� property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getTempAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("tempAccount");
    }
    public void setTempAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("tempAccount", item);
    }
    /**
     * Object:����ɱ�һ�廯��Ŀ����'s �Ƿ��뼯��ģ��property 
     */
    public boolean isIsImptedTemplate()
    {
        return getBoolean("isImptedTemplate");
    }
    public void setIsImptedTemplate(boolean item)
    {
        setBoolean("isImptedTemplate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("02DED8C1");
    }
}