package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPcTypeInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractPcTypeInfo()
    {
        this("id");
    }
    protected AbstractPcTypeInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.programming.PcTypeEntryCollection());
    }
    /**
     * Object: 合约类型 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.programming.PcTypeEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.programming.PcTypeEntryCollection)get("entrys");
    }
    /**
     * Object:合约类型's 是否生成凭证property 
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
     * Object:合约类型's 合约类型property 
     */
    public String getHyType()
    {
        return getString("hyType");
    }
    public void setHyType(String item)
    {
        setString("hyType", item);
    }
    /**
     * Object:合约类型's 计价方式property 
     */
    public com.kingdee.eas.fdc.contract.programming.PriceWay getPriceWay()
    {
        return com.kingdee.eas.fdc.contract.programming.PriceWay.getEnum(getString("priceWay"));
    }
    public void setPriceWay(com.kingdee.eas.fdc.contract.programming.PriceWay item)
    {
		if (item != null) {
        setString("priceWay", item.getValue());
		}
    }
    /**
     * Object:合约类型's 发包方式property 
     */
    public com.kingdee.eas.fdc.contract.programming.SendContWay getSendContWay()
    {
        return com.kingdee.eas.fdc.contract.programming.SendContWay.getEnum(getString("sendContWay"));
    }
    public void setSendContWay(com.kingdee.eas.fdc.contract.programming.SendContWay item)
    {
		if (item != null) {
        setString("sendContWay", item.getValue());
		}
    }
    /**
     * Object:合约类型's 备注property 
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
        return new BOSObjectType("B37DC0FD");
    }
}