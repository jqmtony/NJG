package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractChangeBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractChangeBillInfo()
    {
        this("id");
    }
    protected AbstractContractChangeBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.ContractChangeEntryCollection());
        put("specialtyTypeEntry", new com.kingdee.eas.fdc.contract.ConSpecialtyTypeCollection());
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ������� property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeTypeInfo getChangeType()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeTypeInfo)get("changeType");
    }
    public void setChangeType(com.kingdee.eas.fdc.basedata.ChangeTypeInfo item)
    {
        put("changeType", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ������ʱ��property 
     */
    public java.util.Date getConductTime()
    {
        return getDate("conductTime");
    }
    public void setConductTime(java.util.Date item)
    {
        setDate("conductTime", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ������� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getConductDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("conductDept");
    }
    public void setConductDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("conductDept", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ���ԭ�� property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeReasonInfo getChangeReason()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeReasonInfo)get("changeReason");
    }
    public void setChangeReason(com.kingdee.eas.fdc.basedata.ChangeReasonInfo item)
    {
        put("changeReason", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ����������property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAmtPropEnum getChangeAmtProp()
    {
        return com.kingdee.eas.fdc.contract.ChangeAmtPropEnum.getEnum(getString("changeAmtProp"));
    }
    public void setChangeAmtProp(com.kingdee.eas.fdc.contract.ChangeAmtPropEnum item)
    {
		if (item != null) {
        setString("changeAmtProp", item.getValue());
		}
    }
    /**
     * Object:���ǩ֤ȷ��'s �������ʱ��property 
     */
    public java.util.Date getSettleTime()
    {
        return getDate("settleTime");
    }
    public void setSettleTime(java.util.Date item)
    {
        setDate("settleTime", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's Ԥ��Ա property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getBudgetPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("budgetPerson");
    }
    public void setBudgetPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("budgetPerson", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ��ͬ����property 
     */
    public String getContractBillNumber()
    {
        return getString("contractBillNumber");
    }
    public void setContractBillNumber(String item)
    {
        setString("contractBillNumber", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ���ǩ֤���� property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillInfo getChangeAudit()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)get("changeAudit");
    }
    public void setChangeAudit(com.kingdee.eas.fdc.contract.ChangeAuditBillInfo item)
    {
        put("changeAudit", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �������property 
     */
    public String getChangeSubject()
    {
        return getString("changeSubject");
    }
    public void setChangeSubject(String item)
    {
        setString("changeSubject", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �����̶�property 
     */
    public com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum getUrgentDegree()
    {
        return com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum.getEnum(getString("urgentDegree"));
    }
    public void setUrgentDegree(com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum item)
    {
		if (item != null) {
        setString("urgentDegree", item.getValue());
		}
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ������Ŀ property 
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
     * Object: ���ǩ֤ȷ�� 's �а����� property 
     */
    public com.kingdee.eas.fdc.basedata.JobTypeInfo getJobType()
    {
        return (com.kingdee.eas.fdc.basedata.JobTypeInfo)get("jobType");
    }
    public void setJobType(com.kingdee.eas.fdc.basedata.JobTypeInfo item)
    {
        put("jobType", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's רҵ���� property 
     */
    public com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo getSpecialtyType()
    {
        return (com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo)get("specialtyType");
    }
    public void setSpecialtyType(com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo item)
    {
        put("specialtyType", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ���㷽ʽproperty 
     */
    public String getBalanceType()
    {
        return getString("balanceType");
    }
    public void setBalanceType(String item)
    {
        setString("balanceType", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �Ƿ����οۿλproperty 
     */
    public boolean isIsDeduct()
    {
        return getBoolean("isDeduct");
    }
    public void setIsDeduct(boolean item)
    {
        setBoolean("isDeduct", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �ۿ���property 
     */
    public java.math.BigDecimal getDeductAmount()
    {
        return getBigDecimal("deductAmount");
    }
    public void setDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("deductAmount", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ������property 
     */
    public java.math.BigDecimal getBalanceAmount()
    {
        return getBigDecimal("balanceAmount");
    }
    public void setBalanceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("balanceAmount", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ���͵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getMainSupp()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("mainSupp");
    }
    public void setMainSupp(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("mainSupp", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �ۿ�ԭ��property 
     */
    public String getDeductReason()
    {
        return getString("deductReason");
    }
    public void setDeductReason(String item)
    {
        setString("deductReason", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ��ͼ���property 
     */
    public com.kingdee.eas.fdc.contract.GraphCountEnum getGraphCount()
    {
        return com.kingdee.eas.fdc.contract.GraphCountEnum.getEnum(getString("graphCount"));
    }
    public void setGraphCount(com.kingdee.eas.fdc.contract.GraphCountEnum item)
    {
		if (item != null) {
        setString("graphCount", item.getValue());
		}
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeEntryCollection)get("entrys");
    }
    /**
     * Object:���ǩ֤ȷ��'s ִ�����property 
     */
    public int getImplement()
    {
        return getInt("implement");
    }
    public void setImplement(int item)
    {
        setInt("implement", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ����ִ��˵��property 
     */
    public String getDisThisTime()
    {
        return getString("disThisTime");
    }
    public void setDisThisTime(String item)
    {
        setString("disThisTime", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s δִ��˵��property 
     */
    public String getImpleCondition()
    {
        return getString("impleCondition");
    }
    public void setImpleCondition(String item)
    {
        setString("impleCondition", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ������Ŀproperty 
     */
    public String getCurProjectName()
    {
        return getString("curProjectName");
    }
    public void setCurProjectName(String item)
    {
        setString("curProjectName", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �������property 
     */
    public String getChangeTypeName()
    {
        return getString("changeTypeName");
    }
    public void setChangeTypeName(String item)
    {
        setString("changeTypeName", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �а�����property 
     */
    public String getJobTypeName()
    {
        return getString("jobTypeName");
    }
    public void setJobTypeName(String item)
    {
        setString("jobTypeName", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s רҵ����property 
     */
    public String getSpecialtyTypeName()
    {
        return getString("specialtyTypeName");
    }
    public void setSpecialtyTypeName(String item)
    {
        setString("specialtyTypeName", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ���������property 
     */
    public String getChangeAuditNumber()
    {
        return getString("changeAuditNumber");
    }
    public void setChangeAuditNumber(String item)
    {
        setString("changeAuditNumber", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �Ƿ����property 
     */
    public boolean isHasSettled()
    {
        return getBoolean("hasSettled");
    }
    public void setHasSettled(boolean item)
    {
        setBoolean("hasSettled", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ��Ч�ɱ�ԭ�� property 
     */
    public com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo getInvalidCostReason()
    {
        return (com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo)get("invalidCostReason");
    }
    public void setInvalidCostReason(com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo item)
    {
        put("invalidCostReason", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ��Ч�ɱ����property 
     */
    public java.math.BigDecimal getCostNouse()
    {
        return getBigDecimal("costNouse");
    }
    public void setCostNouse(java.math.BigDecimal item)
    {
        setBigDecimal("costNouse", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ������Դ��ʽproperty 
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
     * Object:���ǩ֤ȷ��'s ��ͬ�Ƿ����property 
     */
    public boolean isIsConSetted()
    {
        return getBoolean("isConSetted");
    }
    public void setIsConSetted(boolean item)
    {
        setBoolean("isConSetted", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ���״̬property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitStateEnum getSplitState()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitStateEnum.getEnum(getString("splitState"));
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
		if (item != null) {
        setString("splitState", item.getValue());
		}
    }
    /**
     * Object:���ǩ֤ȷ��'s �Ƿ���붯̬�ɱ�property 
     */
    public boolean isIsCostSplit()
    {
        return getBoolean("isCostSplit");
    }
    public void setIsCostSplit(boolean item)
    {
        setBoolean("isCostSplit", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's  property 
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
     * Object:���ǩ֤ȷ��'s ����property 
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
     * Object:���ǩ֤ȷ��'s ������ԭ��property 
     */
    public java.math.BigDecimal getOriBalanceAmount()
    {
        return getBigDecimal("oriBalanceAmount");
    }
    public void setOriBalanceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oriBalanceAmount", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ǩ֤�󵥾ݵĽ�������״̬property 
     */
    public com.kingdee.eas.fdc.contract.ConChangeBillSettAfterSignEnum getForSettAfterSign()
    {
        return com.kingdee.eas.fdc.contract.ConChangeBillSettAfterSignEnum.getEnum(getString("forSettAfterSign"));
    }
    public void setForSettAfterSign(com.kingdee.eas.fdc.contract.ConChangeBillSettAfterSignEnum item)
    {
		if (item != null) {
        setString("forSettAfterSign", item.getValue());
		}
    }
    /**
     * Object:���ǩ֤ȷ��'s ����ʱ�ڹ������е�ԭ�ҽ��property 
     */
    public java.math.BigDecimal getSettAuditAmt()
    {
        return getBigDecimal("settAuditAmt");
    }
    public void setSettAuditAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settAuditAmt", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ����ʱ�ڹ������еĻ���property 
     */
    public java.math.BigDecimal getSettAuditExRate()
    {
        return getBigDecimal("settAuditExRate");
    }
    public void setSettAuditExRate(java.math.BigDecimal item)
    {
        setBigDecimal("settAuditExRate", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �����property 
     */
    public com.kingdee.eas.fdc.contract.OfferEnum getOffer()
    {
        return com.kingdee.eas.fdc.contract.OfferEnum.getEnum(getString("offer"));
    }
    public void setOffer(com.kingdee.eas.fdc.contract.OfferEnum item)
    {
		if (item != null) {
        setString("offer", item.getValue());
		}
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ʩ����λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getConstrUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("constrUnit");
    }
    public void setConstrUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("constrUnit", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ʩ����λproperty 
     */
    public String getConstrSite()
    {
        return getString("constrSite");
    }
    public void setConstrSite(String item)
    {
        setString("constrSite", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's ǩ֤���� property 
     */
    public com.kingdee.eas.fdc.basedata.VisaTypeInfo getVisaType()
    {
        return (com.kingdee.eas.fdc.basedata.VisaTypeInfo)get("visaType");
    }
    public void setVisaType(com.kingdee.eas.fdc.basedata.VisaTypeInfo item)
    {
        put("visaType", item);
    }
    /**
     * Object: ���ǩ֤ȷ�� 's �����λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getConductUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("conductUnit");
    }
    public void setConductUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("conductUnit", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �Ƿ����ݹ��ۺ�ͬ�����ʱ������������property 
     */
    public boolean isIsFromTempEvalChange()
    {
        return getBoolean("isFromTempEvalChange");
    }
    public void setIsFromTempEvalChange(boolean item)
    {
        setBoolean("isFromTempEvalChange", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ԭʼ��ϵ����property 
     */
    public String getOriginalContactNum()
    {
        return getString("originalContactNum");
    }
    public void setOriginalContactNum(String item)
    {
        setString("originalContactNum", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �Ƿ�ȷ��������property 
     */
    public boolean isIsSureChangeAmt()
    {
        return getBoolean("isSureChangeAmt");
    }
    public void setIsSureChangeAmt(boolean item)
    {
        setBoolean("isSureChangeAmt", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s �Ƿ��ش���property 
     */
    public boolean isIsImportChange()
    {
        return getBoolean("isImportChange");
    }
    public void setIsImportChange(boolean item)
    {
        setBoolean("isImportChange", item);
    }
    /**
     * Object:���ǩ֤ȷ��'s ʩ����������property 
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
     * Object: ���ǩ֤ȷ�� 's רҵ���ͷ�¼ property 
     */
    public com.kingdee.eas.fdc.contract.ConSpecialtyTypeCollection getSpecialtyTypeEntry()
    {
        return (com.kingdee.eas.fdc.contract.ConSpecialtyTypeCollection)get("specialtyTypeEntry");
    }
    /**
     * Object:���ǩ֤ȷ��'s רҵ����property 
     */
    public String getSpecialtyName()
    {
        return getString("specialtyName");
    }
    public void setSpecialtyName(String item)
    {
        setString("specialtyName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F2141C04");
    }
}