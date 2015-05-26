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
     * Object: 售卡记录表 's 分录 property 
     */
    public com.kingdee.eas.custom.richinf.SaleCardEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.richinf.SaleCardEntryCollection)get("entrys");
    }
    /**
     * Object:售卡记录表's 是否生成凭证property 
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
     * Object:售卡记录表's 面值property 
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
     * Object:售卡记录表's 售价property 
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
     * Object:售卡记录表's 卡类型property 
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