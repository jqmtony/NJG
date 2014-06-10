package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractProgrammingInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractProgrammingInfo()
    {
        this("id");
    }
    protected AbstractContractProgrammingInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingEntryCollection());
    }
    /**
     * Object:投资规划明细's 规划金额property 
     */
    public java.math.BigDecimal getProgrammingMoney()
    {
        return getBigDecimal("programmingMoney");
    }
    public void setProgrammingMoney(java.math.BigDecimal item)
    {
        setBigDecimal("programmingMoney", item);
    }
    /**
     * Object:投资规划明细's 是否按形象进度付款property 
     */
    public boolean isIsImagePay()
    {
        return getBoolean("isImagePay");
    }
    public void setIsImagePay(boolean item)
    {
        setBoolean("isImagePay", item);
    }
    /**
     * Object: 投资规划明细 's 分录 property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingEntryCollection)get("entrys");
    }
    /**
     * Object:投资规划明细's 是否最终版本property 
     */
    public boolean isIsFinal()
    {
        return getBoolean("isFinal");
    }
    public void setIsFinal(boolean item)
    {
        setBoolean("isFinal", item);
    }
    /**
     * Object:投资规划明细's 版本号property 
     */
    public java.math.BigDecimal getEdition()
    {
        return getBigDecimal("edition");
    }
    public void setEdition(java.math.BigDecimal item)
    {
        setBigDecimal("edition", item);
    }
    /**
     * Object:投资规划明细's 是否最大版本property 
     */
    public boolean isIsLastVersion()
    {
        return getBoolean("isLastVersion");
    }
    public void setIsLastVersion(boolean item)
    {
        setBoolean("isLastVersion", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("565A413A");
    }
}