package com.kingdee.eas.fdc.contract;

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
        put("entrys", new com.kingdee.eas.fdc.contract.ContractProgrammingEntryCollection());
    }
    /**
     * Object: ��Լ�滮 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:��Լ�滮's �滮���property 
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
     * Object:��Լ�滮's �Ƿ�������ȸ���property 
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
     * Object: ��Լ�滮 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractProgrammingEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ContractProgrammingEntryCollection)get("entrys");
    }
    /**
     * Object:��Լ�滮's �Ƿ����հ汾property 
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
     * Object:��Լ�滮's �汾��property 
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
     * Object:��Լ�滮's �Ƿ����汾property 
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
        return new BOSObjectType("F0F19E4C");
    }
}