package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquLeaseInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEquLeaseInfo()
    {
        this("id");
    }
    protected AbstractEquLeaseInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.operate.EquLeaseE1Collection());
    }
    /**
     * Object:设备租赁单's 合同号property 
     */
    public String getConNo()
    {
        return getString("conNo");
    }
    public void setConNo(String item)
    {
        setString("conNo", item);
    }
    /**
     * Object:设备租赁单's 出租方property 
     */
    public String getTheLessor()
    {
        return getString("theLessor");
    }
    public void setTheLessor(String item)
    {
        setString("theLessor", item);
    }
    /**
     * Object:设备租赁单's 承租方property 
     */
    public String getTheLessee()
    {
        return getString("theLessee");
    }
    public void setTheLessee(String item)
    {
        setString("theLessee", item);
    }
    /**
     * Object:设备租赁单's 签订日期property 
     */
    public java.util.Date getSigningDate()
    {
        return getDate("signingDate");
    }
    public void setSigningDate(java.util.Date item)
    {
        setDate("signingDate", item);
    }
    /**
     * Object:设备租赁单's 租赁总额property 
     */
    public java.math.BigDecimal getLeaseSales()
    {
        return getBigDecimal("leaseSales");
    }
    public void setLeaseSales(java.math.BigDecimal item)
    {
        setBigDecimal("leaseSales", item);
    }
    /**
     * Object:设备租赁单's 租赁开始日期property 
     */
    public java.util.Date getLeaseStart()
    {
        return getDate("leaseStart");
    }
    public void setLeaseStart(java.util.Date item)
    {
        setDate("leaseStart", item);
    }
    /**
     * Object:设备租赁单's 租赁结束日期property 
     */
    public java.util.Date getLeaseEnd()
    {
        return getDate("leaseEnd");
    }
    public void setLeaseEnd(java.util.Date item)
    {
        setDate("leaseEnd", item);
    }
    /**
     * Object:设备租赁单's 租金缴费方式property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.RentPay getRentPay()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.RentPay.getEnum(getString("rentPay"));
    }
    public void setRentPay(com.kingdee.eas.port.equipment.base.enumbase.RentPay item)
    {
		if (item != null) {
        setString("rentPay", item.getValue());
		}
    }
    /**
     * Object:设备租赁单's 已租租期property 
     */
    public int getHaveRented()
    {
        return getInt("haveRented");
    }
    public void setHaveRented(int item)
    {
        setInt("haveRented", item);
    }
    /**
     * Object:设备租赁单's 剩余租期property 
     */
    public int getSurplus()
    {
        return getInt("surplus");
    }
    public void setSurplus(int item)
    {
        setInt("surplus", item);
    }
    /**
     * Object:设备租赁单's 租赁方式property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.LeaseMent getLeaseMent()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.LeaseMent.getEnum(getString("leaseMent"));
    }
    public void setLeaseMent(com.kingdee.eas.port.equipment.base.enumbase.LeaseMent item)
    {
		if (item != null) {
        setString("leaseMent", item.getValue());
		}
    }
    /**
     * Object: 设备租赁单 's 第1个表体 property 
     */
    public com.kingdee.eas.port.equipment.operate.EquLeaseE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.operate.EquLeaseE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("161AD210");
    }
}