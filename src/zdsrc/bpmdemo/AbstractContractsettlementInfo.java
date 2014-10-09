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
     * Object: ��ͬ���� 's �������Ϸ�¼ property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementEntryCollection getEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementEntryCollection)get("entrys");
    }
    /**
     * Object:��ͬ����'s �Ƿ�����ƾ֤property 
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
     * Object: ��ͬ���� 's �������Ϸ�¼ property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementAssEntryCollection getAssEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementAssEntryCollection)get("AssEntrys");
    }
    /**
     * Object: ��ͬ���� 's �������Ϸ�¼ property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryCollection getOtherEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryCollection)get("OtherEntrys");
    }
    /**
     * Object:��ͬ����'s ��֯property 
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
     * Object:��ͬ����'s ������Ŀproperty 
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
     * Object:��ͬ����'s ��ͬ����property 
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
     * Object:��ͬ����'s ��ͬ����property 
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
     * Object:��ͬ����'s ���㵥����property 
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
     * Object:��ͬ����'s �������ԭ��property 
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
     * Object:��ͬ����'s ȡ�ѱ�׼property 
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
     * Object:��ͬ����'s ������۱���property 
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
     * Object:��ͬ����'s ��λ���property 
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
     * Object:��ͬ����'s ��Ϣ��property 
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
     * Object:��ͬ����'s �������property 
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
     * Object:��ͬ����'s ���޽�property 
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
     * Object:��ͬ����'s �����ڼ�property 
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
     * Object:��ͬ����'s ���޽����property 
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
     * Object:��ͬ����'s �ۼƱ��޽�property 
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
     * Object:��ͬ����'s �����б�property 
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
     * Object:��ͬ����'s �ۼƽ���ԭ��property 
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
     * Object:��ͬ����'s �ۼƽ��㱾��property 
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
     * Object:��ͬ����'s �鵵��property 
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
     * Object: ��ͬ���� 's �ұ� property 
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
     * Object: ��ͬ���� 's �����ڼ� property 
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
     * Object:��ͬ����'s ����property 
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
     * Object:��ͬ����'s ʩ���������property 
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
     * Object: ��ͬ���� 's ���㽱����¼ property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementJlEntryCollection getJlEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementJlEntryCollection)get("JlEntrys");
    }
    /**
     * Object: ��ͬ���� 's �����б��¼ property 
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