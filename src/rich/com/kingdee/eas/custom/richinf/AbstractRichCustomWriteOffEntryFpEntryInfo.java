package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichCustomWriteOffEntryFpEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRichCustomWriteOffEntryFpEntryInfo()
    {
        this("id");
    }
    protected AbstractRichCustomWriteOffEntryFpEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 发票分录 's null property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffEntryInfo getParent1()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffEntryInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.custom.richinf.RichCustomWriteOffEntryInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:发票分录's 发票类型property 
     */
    public com.kingdee.eas.fi.arap.InvoiceTypeEnum getInvoiceType()
    {
        return com.kingdee.eas.fi.arap.InvoiceTypeEnum.getEnum(getInt("invoiceType"));
    }
    public void setInvoiceType(com.kingdee.eas.fi.arap.InvoiceTypeEnum item)
    {
		if (item != null) {
        setInt("invoiceType", item.getValue());
		}
    }
    /**
     * Object:发票分录's 落单号property 
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
     * Object:发票分录's 开票机构property 
     */
    public String getInvoiceCompany()
    {
        return getString("invoiceCompany");
    }
    public void setInvoiceCompany(String item)
    {
        setString("invoiceCompany", item);
    }
    /**
     * Object:发票分录's 已核销金额property 
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
     * Object:发票分录's 未核销金额property 
     */
    public java.math.BigDecimal getUnInvoiceAmount()
    {
        return getBigDecimal("unInvoiceAmount");
    }
    public void setUnInvoiceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("unInvoiceAmount", item);
    }
    /**
     * Object:发票分录's 发票金额property 
     */
    public java.math.BigDecimal getInvoiceAmount()
    {
        return getBigDecimal("invoiceAmount");
    }
    public void setInvoiceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmount", item);
    }
    /**
     * Object:发票分录's 本次核销金额property 
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
     * Object:发票分录's 开票单位IDproperty 
     */
    public String getKpUnitID()
    {
        return getString("kpUnitID");
    }
    public void setKpUnitID(String item)
    {
        setString("kpUnitID", item);
    }
    /**
     * Object:发票分录's 开票单位编码property 
     */
    public String getKpUnitNumber()
    {
        return getString("kpUnitNumber");
    }
    public void setKpUnitNumber(String item)
    {
        setString("kpUnitNumber", item);
    }
    /**
     * Object:发票分录's 备注property 
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
     * Object: 发票分录 's 发票号 property 
     */
    public com.kingdee.eas.fi.ar.OtherBillInfo getArInvoice()
    {
        return (com.kingdee.eas.fi.ar.OtherBillInfo)get("arInvoice");
    }
    public void setArInvoice(com.kingdee.eas.fi.ar.OtherBillInfo item)
    {
        put("arInvoice", item);
    }
    /**
     * Object:发票分录's 开票单位名称property 
     */
    public String getKpUnitName()
    {
        return getString("kpUnitName");
    }
    public void setKpUnitName(String item)
    {
        setString("kpUnitName", item);
    }
    /**
     * Object:发票分录's 开票日期property 
     */
    public java.util.Date getInvoiceDate()
    {
        return getDate("invoiceDate");
    }
    public void setInvoiceDate(java.util.Date item)
    {
        setDate("invoiceDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("208EADC1");
    }
}