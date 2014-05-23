package com.kingdee.eas.port.equipment.insurance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInsuranceDeclarationStateInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractInsuranceDeclarationStateInfo()
    {
        this("id");
    }
    protected AbstractInsuranceDeclarationStateInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.insurance.InsuranceDeclarationStateE1Collection());
    }
    /**
     * Object:�����걨��ϸ��'s ���property 
     */
    public java.util.Date getYear()
    {
        return getDate("year");
    }
    public void setYear(java.util.Date item)
    {
        setDate("year", item);
    }
    /**
     * Object:�����걨��ϸ��'s ��Ͷ�����property 
     */
    public java.math.BigDecimal getTotalAmountInsured()
    {
        return getBigDecimal("totalAmountInsured");
    }
    public void setTotalAmountInsured(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmountInsured", item);
    }
    /**
     * Object: �����걨��ϸ�� 's �����걨��ϸ property 
     */
    public com.kingdee.eas.port.equipment.insurance.InsuranceDeclarationStateE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.insurance.InsuranceDeclarationStateE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("78256D68");
    }
}