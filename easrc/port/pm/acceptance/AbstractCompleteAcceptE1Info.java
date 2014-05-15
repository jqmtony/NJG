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
    public com.kingdee.eas.port.equipment.record.EquIdInfo getType()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("type");
    }
    public void setType(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("type", item);
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
     * Object:工程竣工验收证明单's 位置property 
     */
    public String getLocation()
    {
        return getString("location");
    }
    public void setLocation(String item)
    {
        setString("location", item);
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
     * Object:工程竣工验收证明单's 责任人property 
     */
    public String getResponsible()
    {
        return getString("responsible");
    }
    public void setResponsible(String item)
    {
        setString("responsible", item);
    }
    /**
     * Object:工程竣工验收证明单's 原值property 
     */
    public java.math.BigDecimal getCost()
    {
        return getBigDecimal("cost");
    }
    public void setCost(java.math.BigDecimal item)
    {
        setBigDecimal("cost", item);
    }
    /**
     * Object:工程竣工验收证明单's 使用年限property 
     */
    public java.util.Date getUseLife()
    {
        return getDate("useLife");
    }
    public void setUseLife(java.util.Date item)
    {
        setDate("useLife", item);
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
     * Object:工程竣工验收证明单's 接管部门property 
     */
    public String getTakeoverDepart()
    {
        return getString("takeoverDepart");
    }
    public void setTakeoverDepart(String item)
    {
        setString("takeoverDepart", item);
    }
    /**
     * Object:工程竣工验收证明单's 管理部门property 
     */
    public String getManagement()
    {
        return getString("management");
    }
    public void setManagement(String item)
    {
        setString("management", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("046CA528");
    }
}