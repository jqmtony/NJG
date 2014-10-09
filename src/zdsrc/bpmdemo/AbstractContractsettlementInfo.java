package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractsettlementInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractContractsettlementInfo()
    {
        this("id");
    }
    protected AbstractContractsettlementInfo(String pkField)
    {
        super(pkField);
        put("OtherEntrys", new com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryCollection());
        put("SettlementEntrys", new com.kingdee.eas.bpmdemo.ContractsettlementSettlementEntryCollection());
        put("entrys", new com.kingdee.eas.bpmdemo.ContractsettlementEntryCollection());
        put("JlEntrys", new com.kingdee.eas.bpmdemo.ContractsettlementJlEntryCollection());
        put("AssEntrys", new com.kingdee.eas.bpmdemo.ContractsettlementAssEntryCollection());
    }
    /**
     * Object: 合同结算 's 基础资料分录 property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementEntryCollection getEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementEntryCollection)get("entrys");
    }
    /**
     * Object:合同结算's 是否生成凭证property 
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
     * Object: 合同结算 's 辅助资料分录 property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementAssEntryCollection getAssEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementAssEntryCollection)get("AssEntrys");
    }
    /**
     * Object: 合同结算 's 其它资料分录 property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryCollection getOtherEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryCollection)get("OtherEntrys");
    }
    /**
     * Object:合同结算's 组织property 
     */
    public String getOrgunit()
    {
        return getString("orgunit");
    }
    public void setOrgunit(String item)
    {
        setString("orgunit", item);
    }
    /**
     * Object:合同结算's 工程项目property 
     */
    public String getCurProject()
    {
        return getString("curProject");
    }
    public void setCurProject(String item)
    {
        setString("curProject", item);
    }
    /**
     * Object:合同结算's 合同编码property 
     */
    public String getContractNo()
    {
        return getString("contractNo");
    }
    public void setContractNo(String item)
    {
        setString("contractNo", item);
    }
    /**
     * Object:合同结算's 合同名称property 
     */
    public String getContractName()
    {
        return getString("contractName");
    }
    public void setContractName(String item)
    {
        setString("contractName", item);
    }
    /**
     * Object:合同结算's 结算单名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:合同结算's 结算造价原币property 
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return getBigDecimal("OriginalAmount");
    }
    public void setOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("OriginalAmount", item);
    }
    /**
     * Object:合同结算's 取费标准property 
     */
    public java.math.BigDecimal getGetFeeCriteria()
    {
        return getBigDecimal("getFeeCriteria");
    }
    public void setGetFeeCriteria(java.math.BigDecimal item)
    {
        setBigDecimal("getFeeCriteria", item);
    }
    /**
     * Object:合同结算's 结算造价本币property 
     */
    public java.math.BigDecimal getSettlePrice()
    {
        return getBigDecimal("SettlePrice");
    }
    public void setSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("SettlePrice", item);
    }
    /**
     * Object:合同结算's 单位造价property 
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
     * Object:合同结算's 信息价property 
     */
    public String getInfoPrice()
    {
        return getString("infoPrice");
    }
    public void setInfoPrice(String item)
    {
        setString("infoPrice", item);
    }
    /**
     * Object:合同结算's 建筑面积property 
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
     * Object:合同结算's 保修金property 
     */
    public java.math.BigDecimal getGuaranceAmt()
    {
        return getBigDecimal("GuaranceAmt");
    }
    public void setGuaranceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("GuaranceAmt", item);
    }
    /**
     * Object:合同结算's 保修期间property 
     */
    public java.util.Date getQualityTime()
    {
        return getDate("QualityTime");
    }
    public void setQualityTime(java.util.Date item)
    {
        setDate("QualityTime", item);
    }
    /**
     * Object:合同结算's 保修金比例property 
     */
    public java.math.BigDecimal getQualityGuaranteRate()
    {
        return getBigDecimal("qualityGuaranteRate");
    }
    public void setQualityGuaranteRate(java.math.BigDecimal item)
    {
        setBigDecimal("qualityGuaranteRate", item);
    }
    /**
     * Object:合同结算's 累计保修金property 
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
     * Object:合同结算's 附件列表property 
     */
    public String getAttchment()
    {
        return getString("Attchment");
    }
    public void setAttchment(String item)
    {
        setString("Attchment", item);
    }
    /**
     * Object:合同结算's 累计结算原币property 
     */
    public java.math.BigDecimal getTotalOriginalAmount()
    {
        return getBigDecimal("TotalOriginalAmount");
    }
    public void setTotalOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("TotalOriginalAmount", item);
    }
    /**
     * Object:合同结算's 累计结算本币property 
     */
    public java.math.BigDecimal getTotalSettlePrice()
    {
        return getBigDecimal("TotalSettlePrice");
    }
    public void setTotalSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("TotalSettlePrice", item);
    }
    /**
     * Object:合同结算's 归档稿property 
     */
    public String getAttenTwo()
    {
        return getString("AttenTwo");
    }
    public void setAttenTwo(String item)
    {
        setString("AttenTwo", item);
    }
    /**
     * Object: 合同结算 's 币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("Currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("Currency", item);
    }
    /**
     * Object: 合同结算 's 结算期间 property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("Period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("Period", item);
    }
    /**
     * Object:合同结算's 汇率property 
     */
    public java.math.BigDecimal getExchangeRate()
    {
        return getBigDecimal("ExchangeRate");
    }
    public void setExchangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("ExchangeRate", item);
    }
    /**
     * Object:合同结算's 施工方报审价property 
     */
    public java.math.BigDecimal getConstructPrice()
    {
        return getBigDecimal("constructPrice");
    }
    public void setConstructPrice(java.math.BigDecimal item)
    {
        setBigDecimal("constructPrice", item);
    }
    /**
     * Object: 合同结算 's 结算奖励分录 property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementJlEntryCollection getJlEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementJlEntryCollection)get("JlEntrys");
    }
    /**
     * Object: 合同结算 's 结算列表分录 property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementSettlementEntryCollection getSettlementEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementSettlementEntryCollection)get("SettlementEntrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0173D879");
    }
}