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
     * Object: 财务成本一体化科目设置 's 应付帐款_开发成本_进度款 property 
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
     * Object: 财务成本一体化科目设置 's 应付帐款_开发成本_保修款 property 
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
     * Object: 财务成本一体化科目设置 's 应付帐款_开发成本_预提成本 property 
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
     * Object: 财务成本一体化科目设置 's 公司 property 
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
     * Object: 财务成本一体化科目设置 's 应付帐款_其他_进度款 property 
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
     * Object: 财务成本一体化科目设置 's 应付帐款_其他_保修款 property 
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
     * Object: 财务成本一体化科目设置 's 应付帐款_其他_预提成本 property 
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
     * Object: 财务成本一体化科目设置 's 预付帐款_开发成本_其他成本 property 
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
     * Object: 财务成本一体化科目设置 's 开发成本_预提成本 property 
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
     * Object: 财务成本一体化科目设置 's 开发成本_成本结转 property 
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
     * Object: 财务成本一体化科目设置 's 完工开发产品 property 
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
     * Object: 财务成本一体化科目设置 's 管理费用 property 
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
     * Object: 财务成本一体化科目设置 's 营销费用 property 
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
     * Object: 财务成本一体化科目设置 's 违约对应科目 property 
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
     * Object: 财务成本一体化科目设置 's 扣款分录 property 
     */
    public com.kingdee.eas.fdc.basedata.DeductAccountEntrysCollection getDeductAccountEntrys()
    {
        return (com.kingdee.eas.fdc.basedata.DeductAccountEntrysCollection)get("deductAccountEntrys");
    }
    /**
     * Object: 财务成本一体化科目设置 's 奖励对应科目 property 
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
     * Object: 财务成本一体化科目设置 's 应付帐款_开发成本_暂估款 property 
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
     * Object:财务成本一体化科目设置's 是否导入集团模板property 
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