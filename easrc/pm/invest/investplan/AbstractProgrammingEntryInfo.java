package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingEntryInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractProgrammingEntryInfo()
    {
        this("id");
    }
    protected AbstractProgrammingEntryInfo(String pkField)
    {
        super(pkField);
        put("costEntries", new com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryCollection());
        put("economyEntries", new com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryEconomyEntryCollection());
    }
    /**
     * Object: ��¼ 's ��Ŀ��Լ�滮 property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo getProgramming()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo item)
    {
        put("programming", item);
    }
    /**
     * Object: ��¼ 's �ϼ��滮��Լ property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s �滮���property 
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
     * Object:��¼'s ���ƽ��property 
     */
    public java.math.BigDecimal getControlAmount()
    {
        return getBigDecimal("controlAmount");
    }
    public void setControlAmount(java.math.BigDecimal item)
    {
        setBigDecimal("controlAmount", item);
    }
    /**
     * Object:��¼'s �滮���property 
     */
    public java.math.BigDecimal getBalance()
    {
        return getBigDecimal("balance");
    }
    public void setBalance(java.math.BigDecimal item)
    {
        setBigDecimal("balance", item);
    }
    /**
     * Object:��¼'s �������property 
     */
    public java.math.BigDecimal getControlBalance()
    {
        return getBigDecimal("controlBalance");
    }
    public void setControlBalance(java.math.BigDecimal item)
    {
        setBigDecimal("controlBalance", item);
    }
    /**
     * Object:��¼'s ǩԼ���property 
     */
    public java.math.BigDecimal getSignUpAmount()
    {
        return getBigDecimal("signUpAmount");
    }
    public void setSignUpAmount(java.math.BigDecimal item)
    {
        setBigDecimal("signUpAmount", item);
    }
    /**
     * Object:��¼'s ������property 
     */
    public java.math.BigDecimal getChangeAmount()
    {
        return getBigDecimal("changeAmount");
    }
    public void setChangeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmount", item);
    }
    /**
     * Object:��¼'s ������property 
     */
    public java.math.BigDecimal getSettleAmount()
    {
        return getBigDecimal("settleAmount");
    }
    public void setSettleAmount(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmount", item);
    }
    /**
     * Object:��¼'s �Ƿ�����property 
     */
    public boolean isIsCiting()
    {
        return getBoolean("isCiting");
    }
    public void setIsCiting(boolean item)
    {
        setBoolean("isCiting", item);
    }
    /**
     * Object:��¼'s ���ð汾property 
     */
    public int getCiteVersion()
    {
        return getInt("citeVersion");
    }
    public void setCiteVersion(int item)
    {
        setInt("citeVersion", item);
    }
    /**
     * Object:��¼'s �滮��ԼԴIDproperty 
     */
    public String getSrcId()
    {
        return getString("srcId");
    }
    public void setSrcId(String item)
    {
        setString("srcId", item);
    }
    /**
     * Object:��¼'s ����property 
     */
    public String getAttachment()
    {
        return getString("attachment");
    }
    public void setAttachment(String item)
    {
        setString("attachment", item);
    }
    /**
     * Object: ��¼ 's �ɱ����� property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryCollection getCostEntries()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryCollection)get("costEntries");
    }
    /**
     * Object: ��¼ 's �������� property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryEconomyEntryCollection getEconomyEntries()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryEconomyEntryCollection)get("economyEntries");
    }
    /**
     * Object:��¼'s ������property 
     */
    public int getSortNumber()
    {
        return getInt("sortNumber");
    }
    public void setSortNumber(int item)
    {
        setInt("sortNumber", item);
    }
    /**
     * Object:��¼'s ��������property 
     */
    public String getWorkContent()
    {
        return getString("workContent");
    }
    public void setWorkContent(String item)
    {
        setString("workContent", item);
    }
    /**
     * Object:��¼'s �׹�����ָ����property 
     */
    public String getSupMaterial()
    {
        return getString("supMaterial");
    }
    public void setSupMaterial(String item)
    {
        setString("supMaterial", item);
    }
    /**
     * Object:��¼'s �б귽ʽproperty 
     */
    public com.kingdee.eas.fdc.invite.InviteFormEnum getInviteWay()
    {
        return com.kingdee.eas.fdc.invite.InviteFormEnum.getEnum(getString("inviteWay"));
    }
    public void setInviteWay(com.kingdee.eas.fdc.invite.InviteFormEnum item)
    {
		if (item != null) {
        setString("inviteWay", item.getValue());
		}
    }
    /**
     * Object: ��¼ 's �б���֯ property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getInviteOrg()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("inviteOrg");
    }
    public void setInviteOrg(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("inviteOrg", item);
    }
    /**
     * Object:��¼'s ��������property 
     */
    public java.math.BigDecimal getBuildPerSquare()
    {
        return getBigDecimal("buildPerSquare");
    }
    public void setBuildPerSquare(java.math.BigDecimal item)
    {
        setBigDecimal("buildPerSquare", item);
    }
    /**
     * Object:��¼'s ���۵���property 
     */
    public java.math.BigDecimal getSoldPerSquare()
    {
        return getBigDecimal("soldPerSquare");
    }
    public void setSoldPerSquare(java.math.BigDecimal item)
    {
        setBigDecimal("soldPerSquare", item);
    }
    /**
     * Object:��¼'s �ɱ������������property 
     */
    public String getCostAccountNames()
    {
        return getString("costAccountNames");
    }
    public void setCostAccountNames(String item)
    {
        setString("costAccountNames", item);
    }
    /**
     * Object:��¼'s Ԥ�����property 
     */
    public java.math.BigDecimal getEstimateAmount()
    {
        return getBigDecimal("estimateAmount");
    }
    public void setEstimateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("estimateAmount", item);
    }
    /**
     * Object:��¼'s ���ı���ͬ���property 
     */
    public java.math.BigDecimal getWithOutTextAmount()
    {
        return getBigDecimal("withOutTextAmount");
    }
    public void setWithOutTextAmount(java.math.BigDecimal item)
    {
        setBigDecimal("withOutTextAmount", item);
    }
    /**
     * Object:��¼'s δǩ��ͬ���property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    /**
     * Object:��¼'s ���ı���ͬ����property 
     */
    public String getWithOutTextNumber()
    {
        return getString("withOutTextNumber");
    }
    public void setWithOutTextNumber(String item)
    {
        setString("withOutTextNumber", item);
    }
    /**
     * Object:��¼'s ���ı���ͬ����property 
     */
    public String getWithOutTextName()
    {
        return getString("withOutTextName");
    }
    public void setWithOutTextName(String item)
    {
        setString("withOutTextName", item);
    }
    /**
     * Object:��¼'s ��ͬ�����ı���ͬIDproperty 
     */
    public String getBillId()
    {
        return getString("billId");
    }
    public void setBillId(String item)
    {
        setString("billId", item);
    }
    /**
     * Object: ��¼ 's ��ͬ���� property 
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
     * Object:��¼'s �����ͬ������Ԥ��������property 
     */
    public java.math.BigDecimal getUnAuditContractEA()
    {
        return getBigDecimal("unAuditContractEA");
    }
    public void setUnAuditContractEA(java.math.BigDecimal item)
    {
        setBigDecimal("unAuditContractEA", item);
    }
    /**
     * Object:��¼'s ��ͬ���㷴����Ԥ��������property 
     */
    public java.math.BigDecimal getUnAuditContractSettleEA()
    {
        return getBigDecimal("unAuditContractSettleEA");
    }
    public void setUnAuditContractSettleEA(java.math.BigDecimal item)
    {
        setBigDecimal("unAuditContractSettleEA", item);
    }
    /**
     * Object:��¼'s �����ɹ�property 
     */
    public boolean isIsInvite()
    {
        return getBoolean("isInvite");
    }
    public void setIsInvite(boolean item)
    {
        setBoolean("isInvite", item);
    }
    /**
     * Object:��¼'s �Ƿ����ı�����property 
     */
    public boolean isIsWTCiting()
    {
        return getBoolean("isWTCiting");
    }
    public void setIsWTCiting(boolean item)
    {
        setBoolean("isWTCiting", item);
    }
    /**
     * Object:��¼'s ������ɫproperty 
     */
    public String getCompare()
    {
        return getString("compare");
    }
    public void setCompare(String item)
    {
        setString("compare", item);
    }
    /**
     * Object: ��¼ 's Ͷ����� property 
     */
    public com.kingdee.eas.port.pm.base.InvestYearInfo getInvestYear()
    {
        return (com.kingdee.eas.port.pm.base.InvestYearInfo)get("investYear");
    }
    public void setInvestYear(com.kingdee.eas.port.pm.base.InvestYearInfo item)
    {
        put("investYear", item);
    }
    /**
     * Object:��¼'s �����Ͷ�ʽ��property 
     */
    public java.math.BigDecimal getInvestAmount()
    {
        return getBigDecimal("investAmount");
    }
    public void setInvestAmount(java.math.BigDecimal item)
    {
        setBigDecimal("investAmount", item);
    }
    /**
     * Object:��¼'s �ۼ�Ͷ��property 
     */
    public java.math.BigDecimal getCumulativeInvest()
    {
        return getBigDecimal("cumulativeInvest");
    }
    public void setCumulativeInvest(java.math.BigDecimal item)
    {
        setBigDecimal("cumulativeInvest", item);
    }
    /**
     * Object:��¼'s Ͷ�ʱ���property 
     */
    public double getInvestProportion()
    {
        return getDouble("investProportion");
    }
    public void setInvestProportion(double item)
    {
        setDouble("investProportion", item);
    }
    /**
     * Object:��¼'s ����property 
     */
    public java.math.BigDecimal getQuantities()
    {
        return getBigDecimal("quantities");
    }
    public void setQuantities(java.math.BigDecimal item)
    {
        setBigDecimal("quantities", item);
    }
    /**
     * Object: ��¼ 's ��λ property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object:��¼'s ����property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7D5CF726");
    }
}