package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettNoCostSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo implements Serializable 
{
    public AbstractSettNoCostSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractSettNoCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ǳɱ���ַ�¼ 's ���ͷ property 
     */
    public com.kingdee.eas.fdc.contract.SettNoCostSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.SettNoCostSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.SettNoCostSplitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:����ǳɱ���ַ�¼'s ��ͬ���property 
     */
    public java.math.BigDecimal getContractAmt()
    {
        return getBigDecimal("contractAmt");
    }
    public void setContractAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmt", item);
    }
    /**
     * Object:����ǳɱ���ַ�¼'s ������property 
     */
    public java.math.BigDecimal getChangeAmt()
    {
        return getBigDecimal("changeAmt");
    }
    public void setChangeAmt(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmt", item);
    }
    /**
     * Object:����ǳɱ���ַ�¼'s ֱ�ӽ��property 
     */
    public java.math.BigDecimal getDirectAmt()
    {
        return getBigDecimal("directAmt");
    }
    public void setDirectAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directAmt", item);
    }
    /**
     * Object:����ǳɱ���ַ�¼'s �������޽���property 
     */
    public java.math.BigDecimal getGrtSplitAmt()
    {
        return getBigDecimal("grtSplitAmt");
    }
    public void setGrtSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("grtSplitAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7E754873");
    }
}