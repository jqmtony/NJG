package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractsettlementSettlementEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractsettlementSettlementEntryInfo()
    {
        this("id");
    }
    protected AbstractContractsettlementSettlementEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����б��¼ 's null property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementInfo getParent()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.bpmdemo.ContractsettlementInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�����б��¼'s ���㵥����property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:�����б��¼'s ���㵥����property 
     */
    public String getBillName()
    {
        return getString("billName");
    }
    public void setBillName(String item)
    {
        setString("billName", item);
    }
    /**
     * Object:�����б��¼'s ��λ�����property 
     */
    public java.math.BigDecimal getSettlePrice()
    {
        return getBigDecimal("settlePrice");
    }
    public void setSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("settlePrice", item);
    }
    /**
     * Object:�����б��¼'s �ʱ���property 
     */
    public java.math.BigDecimal getQualityGuarante()
    {
        return getBigDecimal("qualityGuarante");
    }
    public void setQualityGuarante(java.math.BigDecimal item)
    {
        setBigDecimal("qualityGuarante", item);
    }
    /**
     * Object:�����б��¼'s ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:�����б��¼'s ҵ������property 
     */
    public java.util.Date getBookedDate()
    {
        return getDate("bookedDate");
    }
    public void setBookedDate(java.util.Date item)
    {
        setDate("bookedDate", item);
    }
    /**
     * Object:�����б��¼'s �����ڼ�property 
     */
    public String getPeriod()
    {
        return getString("period");
    }
    public void setPeriod(String item)
    {
        setString("period", item);
    }
    /**
     * Object:�����б��¼'s ״̬property 
     */
    public String getState()
    {
        return getString("state");
    }
    public void setState(String item)
    {
        setString("state", item);
    }
    /**
     * Object:�����б��¼'s �������property 
     */
    public java.math.BigDecimal getBuildArea()
    {
        return getBigDecimal("buildArea");
    }
    public void setBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildArea", item);
    }
    /**
     * Object:�����б��¼'s ȡ�ѱ�׼property 
     */
    public String getGetFeeCriteria()
    {
        return getString("getFeeCriteria");
    }
    public void setGetFeeCriteria(String item)
    {
        setString("getFeeCriteria", item);
    }
    /**
     * Object:�����б��¼'s ��λ���property 
     */
    public java.math.BigDecimal getUnitPrice()
    {
        return getBigDecimal("unitPrice");
    }
    public void setUnitPrice(java.math.BigDecimal item)
    {
        setBigDecimal("unitPrice", item);
    }
    /**
     * Object:�����б��¼'s ��Ϣ��property 
     */
    public java.math.BigDecimal getInfoPrice()
    {
        return getBigDecimal("infoPrice");
    }
    public void setInfoPrice(java.math.BigDecimal item)
    {
        setBigDecimal("infoPrice", item);
    }
    /**
     * Object:�����б��¼'s �Ƶ���property 
     */
    public String getCreator()
    {
        return getString("creator");
    }
    public void setCreator(String item)
    {
        setString("creator", item);
    }
    /**
     * Object:�����б��¼'s �Ƶ�����property 
     */
    public java.util.Date getCreateTime()
    {
        return getDate("createTime");
    }
    public void setCreateTime(java.util.Date item)
    {
        setDate("createTime", item);
    }
    /**
     * Object:�����б��¼'s �����property 
     */
    public String getAuditor()
    {
        return getString("auditor");
    }
    public void setAuditor(String item)
    {
        setString("auditor", item);
    }
    /**
     * Object:�����б��¼'s ��������property 
     */
    public java.util.Date getAuditorTime()
    {
        return getDate("auditorTime");
    }
    public void setAuditorTime(java.util.Date item)
    {
        setDate("auditorTime", item);
    }
    /**
     * Object:�����б��¼'s ԭ�����property 
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return getBigDecimal("originalAmount");
    }
    public void setOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("originalAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DD239F10");
    }
}