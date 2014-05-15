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
     * Object: ���̿�������֤���� 's null property 
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
     * Object: ���̿�������֤���� 's ���� property 
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
     * Object:���̿�������֤����'s Ʒ��property 
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
     * Object:���̿�������֤����'s ����property 
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
     * Object:���̿�������֤����'s �ͺ�property 
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
     * Object:���̿�������֤����'s ����property 
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
     * Object:���̿�������֤����'s ����property 
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
     * Object:���̿�������֤����'s ����property 
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
     * Object:���̿�������֤����'s С��property 
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
     * Object:���̿�������֤����'s λ��property 
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
     * Object: ���̿�������֤���� 's ʹ���� property 
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
     * Object:���̿�������֤����'s ������property 
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
     * Object:���̿�������֤����'s ԭֵproperty 
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
     * Object:���̿�������֤����'s ʹ������property 
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
     * Object:���̿�������֤����'s ������������property 
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
     * Object:���̿�������֤����'s �ӹܲ���property 
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
     * Object:���̿�������֤����'s ������property 
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