package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichInvoiceRequestInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractRichInvoiceRequestInfo()
    {
        this("id");
    }
    protected AbstractRichInvoiceRequestInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryCollection());
    }
    /**
     * Object: 开票申请单 's 分录 property 
     */
    public com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryCollection)get("entrys");
    }
    /**
     * Object:开票申请单's 是否生成凭证property 
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
     * Object:开票申请单's 落单号property 
     */
    public String getLdNumber()
    {
        return getString("ldNumber");
    }
    public void setLdNumber(String item)
    {
        setString("ldNumber", item);
    }
    /**
     * Object: 开票申请单 's 开票抬头 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getKpUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("kpUnit");
    }
    public void setKpUnit(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("kpUnit", item);
    }
    /**
     * Object: 开票申请单 's 销售员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getSales()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("sales");
    }
    public void setSales(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("sales", item);
    }
    /**
     * Object:开票申请单's 单据状态property 
     */
    public com.kingdee.eas.custom.richbase.BizStateEnum getBizState()
    {
        return com.kingdee.eas.custom.richbase.BizStateEnum.getEnum(getString("bizState"));
    }
    public void setBizState(com.kingdee.eas.custom.richbase.BizStateEnum item)
    {
		if (item != null) {
        setString("bizState", item.getValue());
		}
    }
    /**
     * Object:开票申请单's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object: 开票申请单 's 开票机构 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getKpCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("kpCompany");
    }
    public void setKpCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("kpCompany", item);
    }
    /**
     * Object:开票申请单's 审核日期property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:开票申请单's 累计申请开票金额property 
     */
    public java.math.BigDecimal getReqSumAmount()
    {
        return getBigDecimal("reqSumAmount");
    }
    public void setReqSumAmount(java.math.BigDecimal item)
    {
        setBigDecimal("reqSumAmount", item);
    }
    /**
     * Object:开票申请单's 累计已开票金额property 
     */
    public java.math.BigDecimal getInvoicedAmount()
    {
        return getBigDecimal("invoicedAmount");
    }
    public void setInvoicedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("invoicedAmount", item);
    }
    /**
     * Object:开票申请单's 本次申请开票金额property 
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
     * Object:开票申请单's 累计到检金额property 
     */
    public java.math.BigDecimal getDjAmount()
    {
        return getBigDecimal("djAmount");
    }
    public void setDjAmount(java.math.BigDecimal item)
    {
        setBigDecimal("djAmount", item);
    }
    /**
     * Object:开票申请单's 代检开票property 
     */
    public boolean isDjkp()
    {
        return getBoolean("djkp");
    }
    public void setDjkp(boolean item)
    {
        setBoolean("djkp", item);
    }
    /**
     * Object:开票申请单's 单据状态property 
     */
    public com.kingdee.eas.custom.richinf.BillState getBillState()
    {
        return com.kingdee.eas.custom.richinf.BillState.getEnum(getString("billState"));
    }
    public void setBillState(com.kingdee.eas.custom.richinf.BillState item)
    {
		if (item != null) {
        setString("billState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4140BC2C");
    }
}