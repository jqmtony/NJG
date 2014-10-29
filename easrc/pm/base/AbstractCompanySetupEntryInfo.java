package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompanySetupEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCompanySetupEntryInfo()
    {
        this("id");
    }
    protected AbstractCompanySetupEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's null property 
     */
    public com.kingdee.eas.port.pm.base.CompanySetupInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.CompanySetupInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.CompanySetupInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 分录 's 公司性质 property 
     */
    public com.kingdee.eas.port.pm.base.CompanyPropertyInfo getCompanyProperty()
    {
        return (com.kingdee.eas.port.pm.base.CompanyPropertyInfo)get("companyProperty");
    }
    public void setCompanyProperty(com.kingdee.eas.port.pm.base.CompanyPropertyInfo item)
    {
        put("companyProperty", item);
    }
    /**
     * Object: 分录 's 公司名称 property 
     */
    public com.kingdee.eas.basedata.org.CtrlUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CtrlUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CtrlUnitInfo item)
    {
        put("company", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("613BDE31");
    }
}