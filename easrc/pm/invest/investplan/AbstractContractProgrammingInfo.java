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
     * Object:Ͷ�ʹ滮��ϸ's �滮���property 
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
     * Object:Ͷ�ʹ滮��ϸ's �Ƿ�������ȸ���property 
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
     * Object: Ͷ�ʹ滮��ϸ 's ��¼ property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingEntryCollection)get("entrys");
    }
    /**
     * Object:Ͷ�ʹ滮��ϸ's �Ƿ����հ汾property 
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
     * Object:Ͷ�ʹ滮��ϸ's �汾��property 
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
     * Object:Ͷ�ʹ滮��ϸ's �Ƿ����汾property 
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