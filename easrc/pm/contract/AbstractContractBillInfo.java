package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractBillInfo()
    {
        this("id");
    }
    protected AbstractContractBillInfo(String pkField)
    {
        super(pkField);
        put("otherLandDevelpers", new com.kingdee.eas.port.pm.contract.ContractBillOtherLandDevelperCollection());
        put("contractPlan", new com.kingdee.eas.port.pm.contract.ContractBillContractPlanCollection());
        put("entrys", new com.kingdee.eas.port.pm.contract.ContractBillEntryCollection());
        put("payItems", new com.kingdee.eas.port.pm.contract.ContractBillPayItemCollection());
    }
    /**
     * Object: ��Ŀ��ͬ 's ��¼ property 
     */
    public com.kingdee.eas.port.pm.contract.ContractBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.pm.contract.ContractBillEntryCollection)get("entrys");
    }
    /**
     * Object:��Ŀ��ͬ's �����ʾ����property 
     */
    public java.math.BigDecimal getChgPercForWarn()
    {
        return getBigDecimal("chgPercForWarn");
    }
    public void setChgPercForWarn(java.math.BigDecimal item)
    {
        setBigDecimal("chgPercForWarn", item);
    }
    /**
     * Object:��Ŀ��ͬ's ������ʾ����property 
     */
    public java.math.BigDecimal getPayPercForWarn()
    {
        return getBigDecimal("payPercForWarn");
    }
    public void setPayPercForWarn(java.math.BigDecimal item)
    {
        setBigDecimal("payPercForWarn", item);
    }
    /**
     * Object:��Ŀ��ͬ's ǩԼ����property 
     */
    public java.util.Date getSignDate()
    {
        return getDate("signDate");
    }
    public void setSignDate(java.util.Date item)
    {
        setDate("signDate", item);
    }
    /**
     * Object:��Ŀ��ͬ's ���Ͷ���property 
     */
    public java.math.BigDecimal getLowestPrice()
    {
        return getBigDecimal("lowestPrice");
    }
    public void setLowestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("lowestPrice", item);
    }
    /**
     * Object:��Ŀ��ͬ's �ε�Ͷ���property 
     */
    public java.math.BigDecimal getLowerPrice()
    {
        return getBigDecimal("lowerPrice");
    }
    public void setLowerPrice(java.math.BigDecimal item)
    {
        setBigDecimal("lowerPrice", item);
    }
    /**
     * Object:��Ŀ��ͬ's �м�Ͷ���property 
     */
    public java.math.BigDecimal getMiddlePrice()
    {
        return getBigDecimal("middlePrice");
    }
    public void setMiddlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("middlePrice", item);
    }
    /**
     * Object:��Ŀ��ͬ's �θ�Ͷ���property 
     */
    public java.math.BigDecimal getHigherPrice()
    {
        return getBigDecimal("higherPrice");
    }
    public void setHigherPrice(java.math.BigDecimal item)
    {
        setBigDecimal("higherPrice", item);
    }
    /**
     * Object:��Ŀ��ͬ's ���Ͷ���property 
     */
    public java.math.BigDecimal getHighestPrice()
    {
        return getBigDecimal("highestPrice");
    }
    public void setHighestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("highestPrice", item);
    }
    /**
     * Object:��Ŀ��ͬ's �б��property 
     */
    public java.math.BigDecimal getWinPrice()
    {
        return getBigDecimal("winPrice");
    }
    public void setWinPrice(java.math.BigDecimal item)
    {
        setBigDecimal("winPrice", item);
    }
    /**
     * Object:��Ŀ��ͬ's ��λ����property 
     */
    public java.math.BigDecimal getQuantity()
    {
        return getBigDecimal("quantity");
    }
    public void setQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("quantity", item);
    }
    /**
     * Object:��Ŀ��ͬ's �б��ļ���property 
     */
    public String getFileNo()
    {
        return getString("fileNo");
    }
    public void setFileNo(String item)
    {
        setString("fileNo", item);
    }
    /**
     * Object:��Ŀ��ͬ's �׼�property 
     */
    public java.math.BigDecimal getBasePrice()
    {
        return getBigDecimal("basePrice");
    }
    public void setBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("basePrice", item);
    }
    /**
     * Object:��Ŀ��ͬ's �����property 
     */
    public java.math.BigDecimal getSecondPrice()
    {
        return getBigDecimal("secondPrice");
    }
    public void setSecondPrice(java.math.BigDecimal item)
    {
        setBigDecimal("secondPrice", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �׷� property 
     */
    public com.kingdee.eas.fdc.basedata.LandDeveloperInfo getLandDeveloper()
    {
        return (com.kingdee.eas.fdc.basedata.LandDeveloperInfo)get("landDeveloper");
    }
    public void setLandDeveloper(com.kingdee.eas.fdc.basedata.LandDeveloperInfo item)
    {
        put("landDeveloper", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �����׷� property 
     */
    public com.kingdee.eas.port.pm.contract.ContractBillOtherLandDevelperCollection getOtherLandDevelpers()
    {
        return (com.kingdee.eas.port.pm.contract.ContractBillOtherLandDevelperCollection)get("otherLandDevelpers");
    }
    /**
     * Object: ��Ŀ��ͬ 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �б����� property 
     */
    public com.kingdee.eas.port.pm.base.InviteTypeInfo getInviteType()
    {
        return (com.kingdee.eas.port.pm.base.InviteTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.port.pm.base.InviteTypeInfo item)
    {
        put("inviteType", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's ���Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getLowestPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("lowestPriceUnit");
    }
    public void setLowestPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("lowestPriceUnit", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �м�Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getMiddlePriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("middlePriceUnit");
    }
    public void setMiddlePriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("middlePriceUnit", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �ε�Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getLowerPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("lowerPriceUnit");
    }
    public void setLowerPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("lowerPriceUnit", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �θ�Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getHigherPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("higherPriceUnit");
    }
    public void setHigherPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("higherPriceUnit", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's ���Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getHighestPriceUni()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("highestPriceUni");
    }
    public void setHighestPriceUni(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("highestPriceUni", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �б굥λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getWinUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("winUnit");
    }
    public void setWinUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("winUnit", item);
    }
    /**
     * Object:��Ŀ��ͬ's �������property 
     */
    public com.kingdee.eas.fdc.contract.CostPropertyEnum getCostProperty()
    {
        return com.kingdee.eas.fdc.contract.CostPropertyEnum.getEnum(getString("costProperty"));
    }
    public void setCostProperty(com.kingdee.eas.fdc.contract.CostPropertyEnum item)
    {
		if (item != null) {
        setString("costProperty", item.getValue());
		}
    }
    /**
     * Object:��Ŀ��ͬ's ��ͬ����property 
     */
    public com.kingdee.eas.fdc.contract.ContractPropertyEnum getContractPropert()
    {
        return com.kingdee.eas.fdc.contract.ContractPropertyEnum.getEnum(getString("contractPropert"));
    }
    public void setContractPropert(com.kingdee.eas.fdc.contract.ContractPropertyEnum item)
    {
		if (item != null) {
        setString("contractPropert", item.getValue());
		}
    }
    /**
     * Object:��Ŀ��ͬ's �γɷ�ʽproperty 
     */
    public com.kingdee.eas.fdc.contract.ContractSourceEnum getContractSource()
    {
        return com.kingdee.eas.fdc.contract.ContractSourceEnum.getEnum(getString("contractSource"));
    }
    public void setContractSource(com.kingdee.eas.fdc.contract.ContractSourceEnum item)
    {
		if (item != null) {
        setString("contractSource", item.getValue());
		}
    }
    /**
     * Object: ��Ŀ��ͬ 's �ҷ� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getPartB()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("partB");
    }
    public void setPartB(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("partB", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's ���� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getPartC()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("partC");
    }
    public void setPartC(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("partC", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's ��ͬ�ƻ� property 
     */
    public com.kingdee.eas.port.pm.contract.ContractBillContractPlanCollection getContractPlan()
    {
        return (com.kingdee.eas.port.pm.contract.ContractBillContractPlanCollection)get("contractPlan");
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ�ɱ����property 
     */
    public boolean isIsCoseSplit()
    {
        return getBoolean("isCoseSplit");
    }
    public void setIsCoseSplit(boolean item)
    {
        setBoolean("isCoseSplit", item);
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ��ѽ���property 
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
     * Object:��Ŀ��ͬ's ��ͬ�ڹ��̿��ۼ�ʵ����property 
     */
    public java.math.BigDecimal getPrjPriceInConPaid()
    {
        return getBigDecimal("prjPriceInConPaid");
    }
    public void setPrjPriceInConPaid(java.math.BigDecimal item)
    {
        setBigDecimal("prjPriceInConPaid", item);
    }
    /**
     * Object:��Ŀ��ͬ's ���ӹ��̿��ۼ�ʵ��property 
     */
    public java.math.BigDecimal getAddPrjAmtPaid()
    {
        return getBigDecimal("addPrjAmtPaid");
    }
    public void setAddPrjAmtPaid(java.math.BigDecimal item)
    {
        setBigDecimal("addPrjAmtPaid", item);
    }
    /**
     * Object:��Ŀ��ͬ's Ӧ���׹������ۼ�ʵ����property 
     */
    public java.math.BigDecimal getPaidPartAMatlAmt()
    {
        return getBigDecimal("paidPartAMatlAmt");
    }
    public void setPaidPartAMatlAmt(java.math.BigDecimal item)
    {
        setBigDecimal("paidPartAMatlAmt", item);
    }
    /**
     * Object:��Ŀ��ͬ's �����property 
     */
    public java.math.BigDecimal getSettleAmt()
    {
        return getBigDecimal("settleAmt");
    }
    public void setSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmt", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �ұ� property 
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
     * Object:��Ŀ��ͬ's ���޽��property 
     */
    public java.math.BigDecimal getGrtAmount()
    {
        return getBigDecimal("grtAmount");
    }
    public void setGrtAmount(java.math.BigDecimal item)
    {
        setBigDecimal("grtAmount", item);
    }
    /**
     * Object:��Ŀ��ͬ's ���ޱ�λ�ҽ��property 
     */
    public java.math.BigDecimal getGrtLocalAmount()
    {
        return getBigDecimal("grtLocalAmount");
    }
    public void setGrtLocalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("grtLocalAmount", item);
    }
    /**
     * Object:��Ŀ��ͬ's ����property 
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
     * Object:��Ŀ��ͬ's ���޽����property 
     */
    public java.math.BigDecimal getGrtRate()
    {
        return getBigDecimal("grtRate");
    }
    public void setGrtRate(java.math.BigDecimal item)
    {
        setBigDecimal("grtRate", item);
    }
    /**
     * Object:��Ŀ��ͬ's ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:��Ŀ��ͬ's ���״̬property 
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
     * Object: ��Ŀ��ͬ 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object:��Ŀ��ͬ's �������property 
     */
    public java.math.BigDecimal getPayScale()
    {
        return getBigDecimal("payScale");
    }
    public void setPayScale(java.math.BigDecimal item)
    {
        setBigDecimal("payScale", item);
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ񲻼Ƴɱ��Ľ��property 
     */
    public boolean isIsAmtWithoutCost()
    {
        return getBoolean("isAmtWithoutCost");
    }
    public void setIsAmtWithoutCost(boolean item)
    {
        setBoolean("isAmtWithoutCost", item);
    }
    /**
     * Object:��Ŀ��ͬ's ӡ��˰��property 
     */
    public java.math.BigDecimal getStampTaxRate()
    {
        return getBigDecimal("stampTaxRate");
    }
    public void setStampTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("stampTaxRate", item);
    }
    /**
     * Object:��Ŀ��ͬ's ӡ��˰���property 
     */
    public java.math.BigDecimal getStampTaxAmt()
    {
        return getBigDecimal("stampTaxAmt");
    }
    public void setStampTaxAmt(java.math.BigDecimal item)
    {
        setBigDecimal("stampTaxAmt", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �������� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo getCodeType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo)get("codeType");
    }
    public void setCodeType(com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo item)
    {
        put("codeType", item);
    }
    /**
     * Object:��Ŀ��ͬ's ����ͬ����property 
     */
    public String getMainContractNumber()
    {
        return getString("mainContractNumber");
    }
    public void setMainContractNumber(String item)
    {
        setString("mainContractNumber", item);
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ��ѹ鵵property 
     */
    public boolean isIsArchived()
    {
        return getBoolean("isArchived");
    }
    public void setIsArchived(boolean item)
    {
        setBoolean("isArchived", item);
    }
    /**
     * Object:��Ŀ��ͬ's ִ��״̬property 
     */
    public com.kingdee.eas.fdc.contract.ContractExecStateEnum getExecState()
    {
        return com.kingdee.eas.fdc.contract.ContractExecStateEnum.getEnum(getString("execState"));
    }
    public void setExecState(com.kingdee.eas.fdc.contract.ContractExecStateEnum item)
    {
		if (item != null) {
        setString("execState", item.getValue());
		}
    }
    /**
     * Object:��Ŀ��ͬ's ս�Ժ�������property 
     */
    public com.kingdee.eas.fdc.contract.CoopLevelEnum getCoopLevel()
    {
        return com.kingdee.eas.fdc.contract.CoopLevelEnum.getEnum(getString("coopLevel"));
    }
    public void setCoopLevel(com.kingdee.eas.fdc.contract.CoopLevelEnum item)
    {
		if (item != null) {
        setString("coopLevel", item.getValue());
		}
    }
    /**
     * Object:��Ŀ��ͬ's �Ƽ۷�ʽproperty 
     */
    public com.kingdee.eas.fdc.contract.PriceTypeEnum getPriceType()
    {
        return com.kingdee.eas.fdc.contract.PriceTypeEnum.getEnum(getString("priceType"));
    }
    public void setPriceType(com.kingdee.eas.fdc.contract.PriceTypeEnum item)
    {
		if (item != null) {
        setString("priceType", item.getValue());
		}
    }
    /**
     * Object:��Ŀ��ͬ's ������״̬property 
     */
    public com.kingdee.eas.fdc.contract.ConSplitExecStateEnum getConSplitExecState()
    {
        return com.kingdee.eas.fdc.contract.ConSplitExecStateEnum.getEnum(getString("conSplitExecState"));
    }
    public void setConSplitExecState(com.kingdee.eas.fdc.contract.ConSplitExecStateEnum item)
    {
		if (item != null) {
        setString("conSplitExecState", item.getValue());
		}
    }
    /**
     * Object:��Ŀ��ͬ's �ⲿ���ݺ���property 
     */
    public String getWebSrvNumber()
    {
        return getString("webSrvNumber");
    }
    public void setWebSrvNumber(String item)
    {
        setString("webSrvNumber", item);
    }
    /**
     * Object:��Ŀ��ͬ's ������Դ��ʽproperty 
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
     * Object:��Ŀ��ͬ's �鵵ǰ����property 
     */
    public String getArchivingNumber()
    {
        return getString("archivingNumber");
    }
    public void setArchivingNumber(String item)
    {
        setString("archivingNumber", item);
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ�ײ��Ϻ�ͬproperty 
     */
    public boolean isIsPartAMaterialCon()
    {
        return getBoolean("isPartAMaterialCon");
    }
    public void setIsPartAMaterialCon(boolean item)
    {
        setBoolean("isPartAMaterialCon", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's ��ͬ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo getConChargeType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo)get("conChargeType");
    }
    public void setConChargeType(com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo item)
    {
        put("conChargeType", item);
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ����۲��property 
     */
    public boolean isIsMeasureContract()
    {
        return getBoolean("isMeasureContract");
    }
    public void setIsMeasureContract(boolean item)
    {
        setBoolean("isMeasureContract", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �γɷ�ʽ property 
     */
    public com.kingdee.eas.fdc.basedata.ContractSourceInfo getContractSourceId()
    {
        return (com.kingdee.eas.fdc.basedata.ContractSourceInfo)get("contractSourceId");
    }
    public void setContractSourceId(com.kingdee.eas.fdc.basedata.ContractSourceInfo item)
    {
        put("contractSourceId", item);
    }
    /**
     * Object:��Ŀ��ͬ's ���Ƴɱ��Ľ��property 
     */
    public java.math.BigDecimal getAmtWithoutCost()
    {
        return getBigDecimal("amtWithoutCost");
    }
    public void setAmtWithoutCost(java.math.BigDecimal item)
    {
        setBigDecimal("amtWithoutCost", item);
    }
    /**
     * Object:��Ŀ��ͬ's ��ͬ����property 
     */
    public String getCodingNumber()
    {
        return getString("codingNumber");
    }
    public void setCodingNumber(String item)
    {
        setString("codingNumber", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �������� property 
     */
    public com.kingdee.eas.port.pm.contract.ContractBillPayItemCollection getPayItems()
    {
        return (com.kingdee.eas.port.pm.contract.ContractBillPayItemCollection)get("payItems");
    }
    /**
     * Object: ��Ŀ��ͬ 's ��Լ��֤�𼰷������� property 
     */
    public com.kingdee.eas.fdc.contract.ContractBailInfo getBail()
    {
        return (com.kingdee.eas.fdc.contract.ContractBailInfo)get("bail");
    }
    public void setBail(com.kingdee.eas.fdc.contract.ContractBailInfo item)
    {
        put("bail", item);
    }
    /**
     * Object:��Ŀ��ͬ's ����������ʾ����property 
     */
    public double getOverRate()
    {
        return getDouble("overRate");
    }
    public void setOverRate(double item)
    {
        setDouble("overRate", item);
    }
    /**
     * Object:��Ŀ��ͬ's �鵵��ͬ���property 
     */
    public String getArchivedNumber()
    {
        return getString("archivedNumber");
    }
    public void setArchivedNumber(String item)
    {
        setString("archivedNumber", item);
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ�ս���Ӻ�ͬproperty 
     */
    public boolean isIsSubContract()
    {
        return getBoolean("isSubContract");
    }
    public void setIsSubContract(boolean item)
    {
        setBoolean("isSubContract", item);
    }
    /**
     * Object:��Ŀ��ͬ's ��Ч��ʼ����property 
     */
    public java.util.Date getEffectiveStartDate()
    {
        return getDate("effectiveStartDate");
    }
    public void setEffectiveStartDate(java.util.Date item)
    {
        setDate("effectiveStartDate", item);
    }
    /**
     * Object:��Ŀ��ͬ's ��Ч��������property 
     */
    public java.util.Date getEffectiveEndDate()
    {
        return getDate("effectiveEndDate");
    }
    public void setEffectiveEndDate(java.util.Date item)
    {
        setDate("effectiveEndDate", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's ս������ͬ property 
     */
    public com.kingdee.eas.port.pm.contract.ContractBillInfo getMainContract()
    {
        return (com.kingdee.eas.port.pm.contract.ContractBillInfo)get("mainContract");
    }
    public void setMainContract(com.kingdee.eas.port.pm.contract.ContractBillInfo item)
    {
        put("mainContract", item);
    }
    /**
     * Object:��Ŀ��ͬ's ��ͬժҪ��Ϣproperty 
     */
    public String getInformation()
    {
        return getString("information");
    }
    public void setInformation(String item)
    {
        setString("information", item);
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ����ʷ��ͬ������ܺ�Լproperty 
     */
    public int getIsRenewRelateProg()
    {
        return getInt("isRenewRelateProg");
    }
    public void setIsRenewRelateProg(int item)
    {
        setInt("isRenewRelateProg", item);
    }
    /**
     * Object:��Ŀ��ͬ's Դ��ܺ�ԼIDproperty 
     */
    public String getSrcProID()
    {
        return getString("srcProID");
    }
    public void setSrcProID(String item)
    {
        setString("srcProID", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's ��ͬ���� property 
     */
    public com.kingdee.eas.base.attachment.AttachmentInfo getModel()
    {
        return (com.kingdee.eas.base.attachment.AttachmentInfo)get("model");
    }
    public void setModel(com.kingdee.eas.base.attachment.AttachmentInfo item)
    {
        put("model", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �Ƶ���ְλ property 
     */
    public com.kingdee.eas.basedata.org.PositionInfo getCreatorPosition()
    {
        return (com.kingdee.eas.basedata.org.PositionInfo)get("creatorPosition");
    }
    public void setCreatorPosition(com.kingdee.eas.basedata.org.PositionInfo item)
    {
        put("creatorPosition", item);
    }
    /**
     * Object: ��Ŀ��ͬ 's �Ƶ����� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getCreateDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("createDept");
    }
    public void setCreateDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("createDept", item);
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ��׼��ͬproperty 
     */
    public boolean isIsStardContract()
    {
        return getBoolean("isStardContract");
    }
    public void setIsStardContract(boolean item)
    {
        setBoolean("isStardContract", item);
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ�տں�ͬproperty 
     */
    public boolean isIsOpenContract()
    {
        return getBoolean("isOpenContract");
    }
    public void setIsOpenContract(boolean item)
    {
        setBoolean("isOpenContract", item);
    }
    /**
     * Object:��Ŀ��ͬ's ��ͬ��������property 
     */
    public com.kingdee.eas.fdc.contract.ContractSettleTypeEnum getContractSettleType()
    {
        return com.kingdee.eas.fdc.contract.ContractSettleTypeEnum.getEnum(getString("contractSettleType"));
    }
    public void setContractSettleType(com.kingdee.eas.fdc.contract.ContractSettleTypeEnum item)
    {
		if (item != null) {
        setString("contractSettleType", item.getValue());
		}
    }
    /**
     * Object:��Ŀ��ͬ's ��ͬ����property 
     */
    public String getContractModel()
    {
        return getString("contractModel");
    }
    public void setContractModel(String item)
    {
        setString("contractModel", item);
    }
    /**
     * Object:��Ŀ��ͬ's ����Э�����ҵ��IDproperty 
     */
    public String getAgreementID()
    {
        return getString("agreementID");
    }
    public void setAgreementID(String item)
    {
        setString("agreementID", item);
    }
    /**
     * Object:��Ŀ��ͬ's �������̷�����֯property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum getOrgType()
    {
        return com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.getEnum(getString("orgType"));
    }
    public void setOrgType(com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum item)
    {
		if (item != null) {
        setString("orgType", item.getValue());
		}
    }
    /**
     * Object:��Ŀ��ͬ's ԭ����ͬ���property 
     */
    public java.math.BigDecimal getSrcAmount()
    {
        return getBigDecimal("srcAmount");
    }
    public void setSrcAmount(java.math.BigDecimal item)
    {
        setBigDecimal("srcAmount", item);
    }
    /**
     * Object:��Ŀ��ͬ's �Ƿ�¼�����ָ���property 
     */
    public boolean isIsHasCostIndex()
    {
        return getBoolean("isHasCostIndex");
    }
    public void setIsHasCostIndex(boolean item)
    {
        setBoolean("isHasCostIndex", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7936D359");
    }
}