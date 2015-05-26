package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSaleCardInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSaleCardInfo()
    {
        this("id");
    }
    protected AbstractSaleCardInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.custom.richinf.SaleCardEntryCollection());
    }
    /**
     * Object: �ۿ���¼�� 's ��¼ property 
     */
    public com.kingdee.eas.custom.richinf.SaleCardEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.richinf.SaleCardEntryCollection)get("entrys");
    }
    /**
     * Object:�ۿ���¼��'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:�ۿ���¼��'s ��ֵproperty 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:�ۿ���¼��'s �ۼ�property 
     */
    public java.math.BigDecimal getSaleAmount()
    {
        return getBigDecimal("saleAmount");
    }
    public void setSaleAmount(java.math.BigDecimal item)
    {
        setBigDecimal("saleAmount", item);
    }
    /**
     * Object:�ۿ���¼��'s ������property 
     */
    public com.kingdee.eas.custom.richbase.CardType getCardType()
    {
        return com.kingdee.eas.custom.richbase.CardType.getEnum(getString("cardType"));
    }
    public void setCardType(com.kingdee.eas.custom.richbase.CardType item)
    {
		if (item != null) {
        setString("cardType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E3B79B05");
    }
}