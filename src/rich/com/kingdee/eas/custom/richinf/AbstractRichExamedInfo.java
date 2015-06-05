package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichExamedInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractRichExamedInfo()
    {
        this("id");
    }
    protected AbstractRichExamedInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.custom.richinf.RichExamedEntryCollection());
    }
    /**
     * Object: 到检单 's 分录 property 
     */
    public com.kingdee.eas.custom.richinf.RichExamedEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.richinf.RichExamedEntryCollection)get("entrys");
    }
    /**
     * Object:到检单's 是否生成凭证property 
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
     * Object:到检单's 落单号property 
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
     * Object:到检单's 到检日期property 
     */
    public java.util.Date getDjDate()
    {
        return getDate("djDate");
    }
    public void setDjDate(java.util.Date item)
    {
        setDate("djDate", item);
    }
    /**
     * Object:到检单's 业务单据编号property 
     */
    public String getYwNumber()
    {
        return getString("ywNumber");
    }
    public void setYwNumber(String item)
    {
        setString("ywNumber", item);
    }
    /**
     * Object: 到检单 's 签约单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getQyUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("qyUnit");
    }
    public void setQyUnit(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("qyUnit", item);
    }
    /**
     * Object: 到检单 's 到检单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getDjUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("djUnit");
    }
    public void setDjUnit(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("djUnit", item);
    }
    /**
     * Object: 到检单 's 开票单位 property 
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
     * Object: 到检单 's 付款单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getFkUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("fkUnit");
    }
    public void setFkUnit(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("fkUnit", item);
    }
    /**
     * Object:到检单's 发票号property 
     */
    public String getFpNumber()
    {
        return getString("fpNumber");
    }
    public void setFpNumber(String item)
    {
        setString("fpNumber", item);
    }
    /**
     * Object:到检单's 总金额property 
     */
    public String getAmount()
    {
        return getString("amount");
    }
    public void setAmount(String item)
    {
        setString("amount", item);
    }
    /**
     * Object: 到检单 's 销售员 property 
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
     * Object: 到检单 's 体检类别 property 
     */
    public com.kingdee.eas.custom.richbase.ExamTypeInfo getTjType()
    {
        return (com.kingdee.eas.custom.richbase.ExamTypeInfo)get("tjType");
    }
    public void setTjType(com.kingdee.eas.custom.richbase.ExamTypeInfo item)
    {
        put("tjType", item);
    }
    /**
     * Object:到检单's 单据状态property 
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
     * Object:到检单's 备注property 
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
     * Object: 到检单 's 开票机构 property 
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
     * Object: 到检单 's 到检机构 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getDjCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("djCompany");
    }
    public void setDjCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("djCompany", item);
    }
    /**
     * Object:到检单's 代检property 
     */
    public boolean isDj()
    {
        return getBoolean("dj");
    }
    public void setDj(boolean item)
    {
        setBoolean("dj", item);
    }
    /**
     * Object:到检单's 审核日期property 
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
     * Object:到检单's 储值卡号property 
     */
    public String getCardNo()
    {
        return getString("cardNo");
    }
    public void setCardNo(String item)
    {
        setString("cardNo", item);
    }
    /**
     * Object:到检单's 卡类别property 
     */
    public com.kingdee.eas.custom.richbase.CardType getCardType()
    {
        return com.kingdee.eas.custom.richbase.CardType.getEnum(getString("cardType"));
    }
    public void setCardType(com.kingdee.eas.custom.richbase.CardType item)
    {
		if (item != null) {
        setString("cardType", item.getValue());
		}
    }
    /**
     * Object:到检单's 面值property 
     */
    public java.math.BigDecimal getCardAmount()
    {
        return getBigDecimal("cardAmount");
    }
    public void setCardAmount(java.math.BigDecimal item)
    {
        setBigDecimal("cardAmount", item);
    }
    /**
     * Object:到检单's 售价property 
     */
    public java.math.BigDecimal getSaleAmount()
    {
        return getBigDecimal("saleAmount");
    }
    public void setSaleAmount(java.math.BigDecimal item)
    {
        setBigDecimal("saleAmount", item);
    }
    /**
     * Object:到检单's 红冲property 
     */
    public boolean isHc()
    {
        return getBoolean("hc");
    }
    public void setHc(boolean item)
    {
        setBoolean("hc", item);
    }
    /**
     * Object: 到检单 's 中介单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getZjjg()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("zjjg");
    }
    public void setZjjg(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("zjjg", item);
    }
    /**
     * Object:到检单's 已核销金额property 
     */
    public java.math.BigDecimal getYhxAmount()
    {
        return getBigDecimal("yhxAmount");
    }
    public void setYhxAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yhxAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7E855488");
    }
}