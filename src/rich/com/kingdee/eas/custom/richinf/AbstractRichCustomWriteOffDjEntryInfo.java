package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichCustomWriteOffDjEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRichCustomWriteOffDjEntryInfo()
    {
        this("id");
    }
    protected AbstractRichCustomWriteOffDjEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 到检单 's null property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo getParent()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 到检单 's 开票单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getKpUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("kpUnit");
    }
    public void setKpUnit(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("kpUnit", item);
    }
    /**
     * Object: 到检单 's 到检机构 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getDjjg()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("djjg");
    }
    public void setDjjg(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("djjg", item);
    }
    /**
     * Object: 到检单 's 到检编号 property 
     */
    public com.kingdee.eas.custom.richinf.RichExamedInfo getDjNo()
    {
        return (com.kingdee.eas.custom.richinf.RichExamedInfo)get("djNo");
    }
    public void setDjNo(com.kingdee.eas.custom.richinf.RichExamedInfo item)
    {
        put("djNo", item);
    }
    /**
     * Object:到检单's 落单号property 
     */
    public String getLdNo()
    {
        return getString("ldNo");
    }
    public void setLdNo(String item)
    {
        setString("ldNo", item);
    }
    /**
     * Object:到检单's 结算金额property 
     */
    public String getJsAmount()
    {
        return getString("jsAmount");
    }
    public void setJsAmount(String item)
    {
        setString("jsAmount", item);
    }
    /**
     * Object:到检单's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A045B3E1");
    }
}