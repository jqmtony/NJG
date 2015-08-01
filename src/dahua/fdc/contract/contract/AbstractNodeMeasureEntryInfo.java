package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNodeMeasureEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNodeMeasureEntryInfo()
    {
        this("id");
    }
    protected AbstractNodeMeasureEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ڵ�Ƽ۷�¼ 's �ڵ�Ƽ� property 
     */
    public com.kingdee.eas.fdc.contract.NodeMeasureInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.NodeMeasureInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.NodeMeasureInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �ڵ�Ƽ۷�¼ 's �ɱ���Ŀ property 
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
     * Object:�ڵ�Ƽ۷�¼'s ������λproperty 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ��ͬ������property 
     */
    public java.math.BigDecimal getConWorkLoad()
    {
        return getBigDecimal("conWorkLoad");
    }
    public void setConWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("conWorkLoad", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ��ͬ����property 
     */
    public java.math.BigDecimal getConPrice()
    {
        return getBigDecimal("conPrice");
    }
    public void setConPrice(java.math.BigDecimal item)
    {
        setBigDecimal("conPrice", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ��ͬ�ϼ�property 
     */
    public java.math.BigDecimal getConAmount()
    {
        return getBigDecimal("conAmount");
    }
    public void setConAmount(java.math.BigDecimal item)
    {
        setBigDecimal("conAmount", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ���������property 
     */
    public java.math.BigDecimal getChangeWorkLoad()
    {
        return getBigDecimal("changeWorkLoad");
    }
    public void setChangeWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("changeWorkLoad", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s �������property 
     */
    public java.math.BigDecimal getChangePrice()
    {
        return getBigDecimal("changePrice");
    }
    public void setChangePrice(java.math.BigDecimal item)
    {
        setBigDecimal("changePrice", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ����ϼ�property 
     */
    public java.math.BigDecimal getChangeAmount()
    {
        return getBigDecimal("changeAmount");
    }
    public void setChangeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmount", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ���㹤����property 
     */
    public java.math.BigDecimal getSettleWorkLoad()
    {
        return getBigDecimal("settleWorkLoad");
    }
    public void setSettleWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("settleWorkLoad", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ���㵥��property 
     */
    public java.math.BigDecimal getSettlePrice()
    {
        return getBigDecimal("settlePrice");
    }
    public void setSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("settlePrice", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ����ϼ�property 
     */
    public java.math.BigDecimal getSettleAmount()
    {
        return getBigDecimal("settleAmount");
    }
    public void setSettleAmount(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmount", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s �ڵ�Ƽ۹�����property 
     */
    public java.math.BigDecimal getNodeWorkLoad()
    {
        return getBigDecimal("nodeWorkLoad");
    }
    public void setNodeWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("nodeWorkLoad", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s �ڵ�Ƽ۵���property 
     */
    public java.math.BigDecimal getNodePrice()
    {
        return getBigDecimal("nodePrice");
    }
    public void setNodePrice(java.math.BigDecimal item)
    {
        setBigDecimal("nodePrice", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s �ڵ�Ƽۺϼ�property 
     */
    public java.math.BigDecimal getNodeAmount()
    {
        return getBigDecimal("nodeAmount");
    }
    public void setNodeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("nodeAmount", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ʵ��������property 
     */
    public java.math.BigDecimal getPayWorkLoad()
    {
        return getBigDecimal("payWorkLoad");
    }
    public void setPayWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("payWorkLoad", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ʵ������property 
     */
    public java.math.BigDecimal getPayPrice()
    {
        return getBigDecimal("payPrice");
    }
    public void setPayPrice(java.math.BigDecimal item)
    {
        setBigDecimal("payPrice", item);
    }
    /**
     * Object:�ڵ�Ƽ۷�¼'s ʵ���ϼ�property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3C97D911");
    }
}