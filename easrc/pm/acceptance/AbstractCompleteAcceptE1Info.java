package com.kingdee.eas.port.pm.acceptance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompleteAcceptE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCompleteAcceptE1Info()
    {
        this("id");
    }
    protected AbstractCompleteAcceptE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 工程竣工验收证明单 's null property 
     */
    public com.kingdee.eas.port.pm.acceptance.CompleteAcceptInfo getParent()
    {
        return (com.kingdee.eas.port.pm.acceptance.CompleteAcceptInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.acceptance.CompleteAcceptInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 工程竣工验收证明单 's 分类 property 
     */
    public com.kingdee.eas.fi.fa.basedata.FaCatInfo getType()
    {
        return (com.kingdee.eas.fi.fa.basedata.FaCatInfo)get("type");
    }
    public void setType(com.kingdee.eas.fi.fa.basedata.FaCatInfo item)
    {
        put("type", item);
    }
    /**
     * Object:工程竣工验收证明单's 名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:工程竣工验收证明单's 配置property 
     */
    public String getConfiguration()
    {
        return getString("configuration");
    }
    public void setConfiguration(String item)
    {
        setString("configuration", item);
    }
    /**
     * Object:工程竣工验收证明单's 数量property 
     */
    public java.math.BigDecimal getQuantity()
    {
        return getBigDecimal("quantity");
    }
    public void setQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("quantity", item);
    }
    /**
     * Object:工程竣工验收证明单's 单价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:工程竣工验收证明单's 小计property 
     */
    public java.math.BigDecimal getSubtotal()
    {
        return getBigDecimal("subtotal");
    }
    public void setSubtotal(java.math.BigDecimal item)
    {
        setBigDecimal("subtotal", item);
    }
    /**
     * Object: 工程竣工验收证明单 's 使用人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getUsePerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("usePerson");
    }
    public void setUsePerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("usePerson", item);
    }
    /**
     * Object:工程竣工验收证明单's 竣工或购置日期property 
     */
    public java.util.Date getCompleteDate()
    {
        return getDate("completeDate");
    }
    public void setCompleteDate(java.util.Date item)
    {
        setDate("completeDate", item);
    }
    /**
     * Object: 工程竣工验收证明单 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getResponsible()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("responsible");
    }
    public void setResponsible(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("responsible", item);
    }
    /**
     * Object:工程竣工验收证明单's 型号property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object:工程竣工验收证明单's 原值property 
     */
    public java.math.BigDecimal getOrigin()
    {
        return getBigDecimal("origin");
    }
    public void setOrigin(java.math.BigDecimal item)
    {
        setBigDecimal("origin", item);
    }
    /**
     * Object:工程竣工验收证明单's 品牌property 
     */
    public String getBrand()
    {
        return getString("brand");
    }
    public void setBrand(String item)
    {
        setString("brand", item);
    }
    /**
     * Object: 工程竣工验收证明单 's 管理部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getManagement()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("management");
    }
    public void setManagement(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("management", item);
    }
    /**
     * Object: 工程竣工验收证明单 's 接管部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getTakeOverDep()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("takeOverDep");
    }
    public void setTakeOverDep(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("takeOverDep", item);
    }
    /**
     * Object: 工程竣工验收证明单 's 存放位置 property 
     */
    public com.kingdee.eas.basedata.assistant.AddressInfo getLocation()
    {
        return (com.kingdee.eas.basedata.assistant.AddressInfo)get("location");
    }
    public void setLocation(com.kingdee.eas.basedata.assistant.AddressInfo item)
    {
        put("location", item);
    }
    /**
     * Object:工程竣工验收证明单's 使用年限property 
     */
    public java.math.BigDecimal getUseLife()
    {
        return getBigDecimal("useLife");
    }
    public void setUseLife(java.math.BigDecimal item)
    {
        setBigDecimal("useLife", item);
    }
    /**
     * Object: 工程竣工验收证明单 's 计量单位 property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getMeasureUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("measureUnit");
    }
    public void setMeasureUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("measureUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("046CA528");
    }
}