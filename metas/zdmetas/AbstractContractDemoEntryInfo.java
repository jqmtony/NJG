package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractDemoEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractDemoEntryInfo()
    {
        this("id");
    }
    protected AbstractContractDemoEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.bpmdemo.ContractDemoInfo getParent()
    {
        return (com.kingdee.eas.bpmdemo.ContractDemoInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.bpmdemo.ContractDemoInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 详细信息property 
     */
    public String getDetial()
    {
        return getString("detial");
    }
    public void setDetial(String item)
    {
        setString("detial", item);
    }
    /**
     * Object:分录's 内容property 
     */
    public String getContent()
    {
        return getString("Content");
    }
    public void setContent(String item)
    {
        setString("Content", item);
    }
    /**
     * Object:分录's 描述property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    /**
     * Object:分录's 金额明细property 
     */
    public java.math.BigDecimal getAmountDetail()
    {
        return getBigDecimal("amountDetail");
    }
    public void setAmountDetail(java.math.BigDecimal item)
    {
        setBigDecimal("amountDetail", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("94D8F6DF");
    }
}