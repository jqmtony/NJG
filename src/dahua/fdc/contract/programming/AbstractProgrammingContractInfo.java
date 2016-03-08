package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingContractInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractProgrammingContractInfo()
    {
        this("id");
    }
    protected AbstractProgrammingContractInfo(String pkField)
    {
        super(pkField);
        put("costEntries", new com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection());
        put("FxbdEntry", new com.kingdee.eas.fdc.contract.programming.ProgrammingContractFxbdEntryCollection());
        put("economyEntries", new com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection());
    }
    /**
     * Object: 分录 's 项目合约规划 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingInfo getProgramming()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.fdc.contract.programming.ProgrammingInfo item)
    {
        put("programming", item);
    }
    /**
     * Object: 分录 's 上级规划合约 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 规划金额property 
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
     * Object:分录's 控制金额property 
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
     * Object:分录's 规划余额property 
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
     * Object:分录's 控制余额property 
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
     * Object:分录's 签约金额property 
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
     * Object:分录's 变更金额property 
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
     * Object:分录's 结算金额property 
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
     * Object:分录's 是否被引用property 
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
     * Object:分录's 引用版本property 
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
     * Object:分录's 规划合约源IDproperty 
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
     * Object:分录's 附件property 
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
     * Object: 分录 's 成本构成 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection getCostEntries()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection)get("costEntries");
    }
    /**
     * Object: 分录 's 经济条款 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection getEconomyEntries()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection)get("economyEntries");
    }
    /**
     * Object:分录's 排序列property 
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
     * Object:分录's 工作内容property 
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
     * Object:分录's 甲供及甲指材设property 
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
     * Object:分录's 招标方式property 
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
     * Object: 分录 's 招标组织 property 
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
     * Object:分录's 建筑单方property 
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
     * Object:分录's 可售单方property 
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
     * Object:分录's 是否编制付款计划property 
     */
    public boolean isIsHasPlan()
    {
        return getBoolean("isHasPlan");
    }
    public void setIsHasPlan(boolean item)
    {
        setBoolean("isHasPlan", item);
    }
    /**
     * Object:分录's 预留变更率property 
     */
    public java.math.BigDecimal getReservedChangeRate()
    {
        return getBigDecimal("reservedChangeRate");
    }
    public void setReservedChangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("reservedChangeRate", item);
    }
    /**
     * Object:分录's 源idproperty 
     */
    public String getBaseId()
    {
        return getString("baseId");
    }
    public void setBaseId(String item)
    {
        setString("baseId", item);
    }
    /**
     * Object:分录's 预计发包开始时间property 
     */
    public java.util.Date getEstimateAwardStartDate()
    {
        return getDate("estimateAwardStartDate");
    }
    public void setEstimateAwardStartDate(java.util.Date item)
    {
        setDate("estimateAwardStartDate", item);
    }
    /**
     * Object:分录's 预计发包结束时间property 
     */
    public java.util.Date getEstimateAwardEndDate()
    {
        return getDate("estimateAwardEndDate");
    }
    public void setEstimateAwardEndDate(java.util.Date item)
    {
        setDate("estimateAwardEndDate", item);
    }
    /**
     * Object: 分录 's 招投标方式 property 
     */
    public com.kingdee.eas.fdc.invite.InviteModeInfo getInviteMode()
    {
        return (com.kingdee.eas.fdc.invite.InviteModeInfo)get("inviteMode");
    }
    public void setInviteMode(com.kingdee.eas.fdc.invite.InviteModeInfo item)
    {
        put("inviteMode", item);
    }
    /**
     * Object: 分录 's 发包方式 property 
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
     * Object: 分录 's 合约类型 property 
     */
    public com.kingdee.eas.fdc.contract.programming.PcTypeInfo getHyType()
    {
        return (com.kingdee.eas.fdc.contract.programming.PcTypeInfo)get("hyType");
    }
    public void setHyType(com.kingdee.eas.fdc.contract.programming.PcTypeInfo item)
    {
        put("hyType", item);
    }
    /**
     * Object:分录's 计价方式property 
     */
    public com.kingdee.eas.fdc.contract.programming.PriceWay getPriceWay()
    {
        return com.kingdee.eas.fdc.contract.programming.PriceWay.getEnum(getString("priceWay"));
    }
    public void setPriceWay(com.kingdee.eas.fdc.contract.programming.PriceWay item)
    {
		if (item != null) {
        setString("priceWay", item.getValue());
		}
    }
    /**
     * Object:分录's 发包方式property 
     */
    public com.kingdee.eas.fdc.contract.programming.SendContWay getSendPage()
    {
        return com.kingdee.eas.fdc.contract.programming.SendContWay.getEnum(getString("sendPage"));
    }
    public void setSendPage(com.kingdee.eas.fdc.contract.programming.SendContWay item)
    {
		if (item != null) {
        setString("sendPage", item.getValue());
		}
    }
    /**
     * Object:分录's 合同范围property 
     */
    public String getContractRange()
    {
        return getString("contractRange");
    }
    public void setContractRange(String item)
    {
        setString("contractRange", item);
    }
    /**
     * Object:分录's 施工图完成交接时间property 
     */
    public java.util.Date getSgtDate()
    {
        return getDate("sgtDate");
    }
    public void setSgtDate(java.util.Date item)
    {
        setDate("sgtDate", item);
    }
    /**
     * Object:分录's 合同签订时间property 
     */
    public java.util.Date getContSignDate()
    {
        return getDate("contSignDate");
    }
    public void setContSignDate(java.util.Date item)
    {
        setDate("contSignDate", item);
    }
    /**
     * Object:分录's 开工时间property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:分录's 竣工时间property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:分录's 合同签订完成时间property 
     */
    public java.util.Date getCsendDate()
    {
        return getDate("csendDate");
    }
    public void setCsendDate(java.util.Date item)
    {
        setDate("csendDate", item);
    }
    /**
     * Object: 分录 's 副项表单 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractFxbdEntryCollection getFxbdEntry()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractFxbdEntryCollection)get("FxbdEntry");
    }
    /**
     * Object:分录's 合同是否签完property 
     */
    public boolean isIscse()
    {
        return getBoolean("iscse");
    }
    public void setIscse(boolean item)
    {
        setBoolean("iscse", item);
    }
    /**
     * Object:分录's 合同内容及界面property 
     */
    public String getContractContUI()
    {
        return getString("contractContUI");
    }
    public void setContractContUI(String item)
    {
        setString("contractContUI", item);
    }
    /**
     * Object:分录's 关联工作property 
     */
    public String getAttachWork()
    {
        return getString("attachWork");
    }
    public void setAttachWork(String item)
    {
        setString("attachWork", item);
    }
    /**
     * Object:分录's 其他关联合同property 
     */
    public String getAttContract()
    {
        return getString("attContract");
    }
    public void setAttContract(String item)
    {
        setString("attContract", item);
    }
    /**
     * Object:分录's 是否强控property 
     */
    public boolean isIsQk()
    {
        return getBoolean("isQk");
    }
    public void setIsQk(boolean item)
    {
        setBoolean("isQk", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ECE079DB");
    }
}