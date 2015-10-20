package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractGuerdonBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractGuerdonBillInfo()
    {
        this("id");
    }
    protected AbstractGuerdonBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������ 's ������Ŀ property 
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
     * Object: ������ 's ��ͬ property 
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
     * Object: ������ 's �ұ� property 
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
     * Object:������'s ��������property 
     */
    public String getGuerdonThings()
    {
        return getString("guerdonThings");
    }
    public void setGuerdonThings(String item)
    {
        setString("guerdonThings", item);
    }
    /**
     * Object:������'s ������������property 
     */
    public String getGuerdonDes()
    {
        return getString("guerdonDes");
    }
    public void setGuerdonDes(String item)
    {
        setString("guerdonDes", item);
    }
    /**
     * Object:������'s ���ŷ�ʽproperty 
     */
    public com.kingdee.eas.fdc.contract.PutOutTypeEnum getPutOutType()
    {
        return com.kingdee.eas.fdc.contract.PutOutTypeEnum.getEnum(getString("putOutType"));
    }
    public void setPutOutType(com.kingdee.eas.fdc.contract.PutOutTypeEnum item)
    {
		if (item != null) {
        setString("putOutType", item.getValue());
		}
    }
    /**
     * Object:������'s �Ƿ��ѽ���property 
     */
    public boolean isIsGuerdoned()
    {
        return getBoolean("isGuerdoned");
    }
    public void setIsGuerdoned(boolean item)
    {
        setBoolean("isGuerdoned", item);
    }
    /**
     * Object:������'s ������Դ��ʽproperty 
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
     * Object:������'s ����property 
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
     * Object:������'s �Ƿ�������ƾ֤property 
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
     * Object: ������ 's ƾ֤ property 
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
     * Object: ������ 's ��Ŀ property 
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
        return new BOSObjectType("B7408454");
    }
}