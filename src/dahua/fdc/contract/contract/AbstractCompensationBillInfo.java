package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompensationBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCompensationBillInfo()
    {
        this("id");
    }
    protected AbstractCompensationBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 违约金 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: 违约金 's 工程项目 property 
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
     * Object: 违约金 's 币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object: 违约金 's 违约类型 property 
     */
    public com.kingdee.eas.fdc.basedata.CounterclaimTypeInfo getCompensationType()
    {
        return (com.kingdee.eas.fdc.basedata.CounterclaimTypeInfo)get("compensationType");
    }
    public void setCompensationType(com.kingdee.eas.fdc.basedata.CounterclaimTypeInfo item)
    {
        put("compensationType", item);
    }
    /**
     * Object:违约金's 款项说明property 
     */
    public String getMoneyDes()
    {
        return getString("moneyDes");
    }
    public void setMoneyDes(String item)
    {
        setString("moneyDes", item);
    }
    /**
     * Object:违约金's 违约描述property 
     */
    public String getBreachFaichDes()
    {
        return getString("breachFaichDes");
    }
    public void setBreachFaichDes(String item)
    {
        setString("breachFaichDes", item);
    }
    /**
     * Object:违约金's 违约依据property 
     */
    public String getCompensationAccording()
    {
        return getString("compensationAccording");
    }
    public void setCompensationAccording(String item)
    {
        setString("compensationAccording", item);
    }
    /**
     * Object:违约金's 其他说明property 
     */
    public String getOtherDes()
    {
        return getString("otherDes");
    }
    public void setOtherDes(String item)
    {
        setString("otherDes", item);
    }
    /**
     * Object:违约金's 是否违约property 
     */
    public boolean isIsCompensated()
    {
        return getBoolean("isCompensated");
    }
    public void setIsCompensated(boolean item)
    {
        setBoolean("isCompensated", item);
    }
    /**
     * Object:违约金's 扣款方式property 
     */
    public com.kingdee.eas.fdc.basedata.DeductModeEnum getDeductMode()
    {
        return com.kingdee.eas.fdc.basedata.DeductModeEnum.getEnum(getString("deductMode"));
    }
    public void setDeductMode(com.kingdee.eas.fdc.basedata.DeductModeEnum item)
    {
		if (item != null) {
        setString("deductMode", item.getValue());
		}
    }
    /**
     * Object:违约金's 单据来源方式property 
     */
    public com.kingdee.eas.fdc.basedata.SourceTypeEnum getSourceType()
    {
        return com.kingdee.eas.fdc.basedata.SourceTypeEnum.getEnum(getInt("sourceType"));
    }
    public void setSourceType(com.kingdee.eas.fdc.basedata.SourceTypeEnum item)
    {
		if (item != null) {
        setInt("sourceType", item.getValue());
		}
    }
    /**
     * Object:违约金's 汇率property 
     */
    public java.math.BigDecimal getExRate()
    {
        return getBigDecimal("exRate");
    }
    public void setExRate(java.math.BigDecimal item)
    {
        setBigDecimal("exRate", item);
    }
    /**
     * Object:违约金's 是否已生成凭证property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object: 违约金 's 凭证 property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("voucher");
    }
    public void setVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("voucher", item);
    }
    /**
     * Object: 违约金 's 科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("40116BBC");
    }
}