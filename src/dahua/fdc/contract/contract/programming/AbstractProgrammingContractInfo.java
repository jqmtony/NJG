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
        put("economyEntries", new com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection());
    }
    /**
     * Object: ��ܺ�Լ 's ��Ŀ��Լ�滮 property 
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
     * Object: ��ܺ�Լ 's �ϼ��滮��Լ property 
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
     * Object:��ܺ�Լ's �滮���property 
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
     * Object:��ܺ�Լ's ���ƽ��property 
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
     * Object:��ܺ�Լ's �滮���property 
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
     * Object:��ܺ�Լ's �������property 
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
     * Object:��ܺ�Լ's ǩԼ���property 
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
     * Object:��ܺ�Լ's ������property 
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
     * Object:��ܺ�Լ's ������property 
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
     * Object:��ܺ�Լ's �Ƿ�����property 
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
     * Object:��ܺ�Լ's ���ð汾property 
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
     * Object:��ܺ�Լ's �滮��ԼԴIDproperty 
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
     * Object:��ܺ�Լ's ����property 
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
     * Object: ��ܺ�Լ 's �ɱ����� property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection getCostEntries()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection)get("costEntries");
    }
    /**
     * Object: ��ܺ�Լ 's �������� property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection getEconomyEntries()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection)get("economyEntries");
    }
    /**
     * Object:��ܺ�Լ's ������property 
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
     * Object:��ܺ�Լ's ��������property 
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
     * Object:��ܺ�Լ's �׹�����ָ����property 
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
     * Object:��ܺ�Լ's �б귽ʽproperty 
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
     * Object: ��ܺ�Լ 's �б���֯ property 
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
     * Object:��ܺ�Լ's ��������property 
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
     * Object:��ܺ�Լ's ���۵���property 
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
     * Object:��ܺ�Լ's �Ƿ���Ƹ���ƻ�property 
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
     * Object:��ܺ�Լ's Ԥ�������property 
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
     * Object:��ܺ�Լ's Դidproperty 
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
     * Object:��ܺ�Լ's Ԥ�Ʒ�����ʼʱ��property 
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
     * Object:��ܺ�Լ's Ԥ�Ʒ�������ʱ��property 
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
     * Object: ��ܺ�Լ 's ��Ͷ�귽ʽ property 
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
     * Object: ��ܺ�Լ 's ������ʽ property 
     */
    public com.kingdee.eas.fdc.basedata.JobTypeInfo getJobType()
    {
        return (com.kingdee.eas.fdc.basedata.JobTypeInfo)get("jobType");
    }
    public void setJobType(com.kingdee.eas.fdc.basedata.JobTypeInfo item)
    {
        put("jobType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ECE079DB");
    }
}