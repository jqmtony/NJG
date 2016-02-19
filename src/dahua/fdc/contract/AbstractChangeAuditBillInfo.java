package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeAuditBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractChangeAuditBillInfo()
    {
        this("id");
    }
    protected AbstractChangeAuditBillInfo(String pkField)
    {
        super(pkField);
        put("suppEntry", new com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection());
        put("entrys", new com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection());
        put("SplitEntry", new com.kingdee.eas.fdc.contract.ChangeAuditBillSplitEntryCollection());
        put("specialtyTypeEntry", new com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection());
    }
    /**
     * Object: 合同变更发起 's 变更内容分录 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection)get("entrys");
    }
    /**
     * Object: 合同变更发起 's 工程项目 property 
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
     * Object: 合同变更发起 's 变更类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeTypeInfo getAuditType()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeTypeInfo)get("auditType");
    }
    public void setAuditType(com.kingdee.eas.fdc.basedata.ChangeTypeInfo item)
    {
        put("auditType", item);
    }
    /**
     * Object: 合同变更发起 's 变更原因 property 
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
     * Object:合同变更发起's 状态property 
     */
    public com.kingdee.eas.fdc.contract.ChangeBillStateEnum getChangeState()
    {
        return com.kingdee.eas.fdc.contract.ChangeBillStateEnum.getEnum(getString("changeState"));
    }
    public void setChangeState(com.kingdee.eas.fdc.contract.ChangeBillStateEnum item)
    {
		if (item != null) {
        setString("changeState", item.getValue());
		}
    }
    /**
     * Object:合同变更发起's 紧急程度property 
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
     * Object: 合同变更发起 's 变更签证申请下发单位分录 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection getSuppEntry()
    {
        return (com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection)get("suppEntry");
    }
    /**
     * Object: 合同变更发起 's 承包类型 property 
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
     * Object: 合同变更发起 's 专业类型 property 
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
     * Object:合同变更发起's 变更主题property 
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
     * Object:合同变更发起's 附图情况property 
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
     * Object:合同变更发起's 下发单位个数property 
     */
    public int getSuppCount()
    {
        return getInt("suppCount");
    }
    public void setSuppCount(int item)
    {
        setInt("suppCount", item);
    }
    /**
     * Object:合同变更发起's 是否提前下发property 
     */
    public boolean isIsRegister()
    {
        return getBoolean("isRegister");
    }
    public void setIsRegister(boolean item)
    {
        setBoolean("isRegister", item);
    }
    /**
     * Object:合同变更发起's 成本测算金额汇总property 
     */
    public java.math.BigDecimal getTotalCost()
    {
        return getBigDecimal("totalCost");
    }
    public void setTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("totalCost", item);
    }
    /**
     * Object:合同变更发起's 涉及无效成本的金额property 
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
     * Object:合同变更发起's 涉及无效成本原因property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object:合同变更发起's 甲方承担费用property 
     */
    public java.math.BigDecimal getAmountA()
    {
        return getBigDecimal("amountA");
    }
    public void setAmountA(java.math.BigDecimal item)
    {
        setBigDecimal("amountA", item);
    }
    /**
     * Object:合同变更发起's 责任单位承担费用property 
     */
    public java.math.BigDecimal getAmountDutySupp()
    {
        return getBigDecimal("amountDutySupp");
    }
    public void setAmountDutySupp(java.math.BigDecimal item)
    {
        setBigDecimal("amountDutySupp", item);
    }
    /**
     * Object: 合同变更发起 's 提出部门 property 
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
     * Object:合同变更发起's 提前下发原因property 
     */
    public String getAheadReason()
    {
        return getString("aheadReason");
    }
    public void setAheadReason(String item)
    {
        setString("aheadReason", item);
    }
    /**
     * Object:合同变更发起's 批准人property 
     */
    public String getValidator()
    {
        return getString("validator");
    }
    public void setValidator(String item)
    {
        setString("validator", item);
    }
    /**
     * Object:合同变更发起's 沟通方式property 
     */
    public String getConnectType()
    {
        return getString("connectType");
    }
    public void setConnectType(String item)
    {
        setString("connectType", item);
    }
    /**
     * Object:合同变更发起's 工程项目property 
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
     * Object:合同变更发起's 变更类型property 
     */
    public String getAuditTypeName()
    {
        return getString("auditTypeName");
    }
    public void setAuditTypeName(String item)
    {
        setString("auditTypeName", item);
    }
    /**
     * Object:合同变更发起's 承包类型property 
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
     * Object:合同变更发起's 专业类型property 
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
     * Object:合同变更发起's 是否存在无效成本property 
     */
    public boolean isIsNoUse()
    {
        return getBoolean("isNoUse");
    }
    public void setIsNoUse(boolean item)
    {
        setBoolean("isNoUse", item);
    }
    /**
     * Object: 合同变更发起 's 无效成本原因 property 
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
     * Object:合同变更发起's 单据来源方式property 
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
     * Object:合同变更发起's 提出方property 
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
     * Object: 合同变更发起 's 施工单位 property 
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
     * Object: 合同变更发起 's 设计单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getDesignUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("designUnit");
    }
    public void setDesignUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("designUnit", item);
    }
    /**
     * Object:合同变更发起's 施工部位 property 
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
     * Object:合同变更发起's 原因说明property 
     */
    public String getReaDesc()
    {
        return getString("reaDesc");
    }
    public void setReaDesc(String item)
    {
        setString("reaDesc", item);
    }
    /**
     * Object: 合同变更发起 's 提出单位 property 
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
     * Object:合同变更发起's 是否重大变更property 
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
     * Object:合同变更发起's 归档稿property 
     */
    public String getOwnID()
    {
        return getString("ownID");
    }
    public void setOwnID(String item)
    {
        setString("ownID", item);
    }
    /**
     * Object: 合同变更发起 's 专业类型 property 
     */
    public com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection getSpecialtyTypeEntry()
    {
        return (com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection)get("specialtyTypeEntry");
    }
    /**
     * Object:合同变更发起's 专业类型property 
     */
    public String getSpecialName()
    {
        return getString("specialName");
    }
    public void setSpecialName(String item)
    {
        setString("specialName", item);
    }
    /**
     * Object:合同变更发起's 单据类型property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillType getBillType()
    {
        return com.kingdee.eas.fdc.contract.ChangeAuditBillType.getEnum(getString("billType"));
    }
    public void setBillType(com.kingdee.eas.fdc.contract.ChangeAuditBillType item)
    {
		if (item != null) {
        setString("billType", item.getValue());
		}
    }
    /**
     * Object:合同变更发起's 提出时间property 
     */
    public java.util.Date getPutForwardTime()
    {
        return getDate("putForwardTime");
    }
    public void setPutForwardTime(java.util.Date item)
    {
        setDate("putForwardTime", item);
    }
    /**
     * Object:合同变更发起's 产品品质property 
     */
    public String getQuality()
    {
        return getString("quality");
    }
    public void setQuality(String item)
    {
        setString("quality", item);
    }
    /**
     * Object:合同变更发起's 工期property 
     */
    public String getTimeLi()
    {
        return getString("timeLi");
    }
    public void setTimeLi(String item)
    {
        setString("timeLi", item);
    }
    /**
     * Object:合同变更发起's 销售property 
     */
    public String getSale()
    {
        return getString("sale");
    }
    public void setSale(String item)
    {
        setString("sale", item);
    }
    /**
     * Object:合同变更发起's 成本property 
     */
    public String getCost()
    {
        return getString("cost");
    }
    public void setCost(String item)
    {
        setString("cost", item);
    }
    /**
     * Object:合同变更发起's 返工签证费用估算property 
     */
    public java.math.BigDecimal getReworkVisa()
    {
        return getBigDecimal("reworkVisa");
    }
    public void setReworkVisa(java.math.BigDecimal item)
    {
        setBigDecimal("reworkVisa", item);
    }
    /**
     * Object:合同变更发起's 占合同价比例property 
     */
    public java.math.BigDecimal getContractAmPro()
    {
        return getBigDecimal("contractAmPro");
    }
    public void setContractAmPro(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmPro", item);
    }
    /**
     * Object:合同变更发起's 累计变更占合同比property 
     */
    public java.math.BigDecimal getTotalChangeAmount()
    {
        return getBigDecimal("totalChangeAmount");
    }
    public void setTotalChangeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalChangeAmount", item);
    }
    /**
     * Object:合同变更发起's 设计变更金额property 
     */
    public java.math.BigDecimal getDesignChangeAmount()
    {
        return getBigDecimal("designChangeAmount");
    }
    public void setDesignChangeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("designChangeAmount", item);
    }
    /**
     * Object:合同变更发起's 变更费用估算property 
     */
    public String getChangeEstimate()
    {
        return getString("changeEstimate");
    }
    public void setChangeEstimate(String item)
    {
        setString("changeEstimate", item);
    }
    /**
     * Object: 合同变更发起 's 变更拆分 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillSplitEntryCollection getSplitEntry()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditBillSplitEntryCollection)get("SplitEntry");
    }
    /**
     * Object:合同变更发起's 工程图纸编号property 
     */
    public String getImgNumber()
    {
        return getString("imgNumber");
    }
    public void setImgNumber(String item)
    {
        setString("imgNumber", item);
    }
    /**
     * Object:合同变更发起's 经济签证事项完成时间property 
     */
    public java.util.Date getCompDate()
    {
        return getDate("compDate");
    }
    public void setCompDate(java.util.Date item)
    {
        setDate("compDate", item);
    }
    /**
     * Object:合同变更发起's 施工方申报金额property 
     */
    public java.math.BigDecimal getAppliAmount()
    {
        return getBigDecimal("appliAmount");
    }
    public void setAppliAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appliAmount", item);
    }
    /**
     * Object:合同变更发起's 处罚金额property 
     */
    public java.math.BigDecimal getPunisAmount()
    {
        return getBigDecimal("punisAmount");
    }
    public void setPunisAmount(java.math.BigDecimal item)
    {
        setBigDecimal("punisAmount", item);
    }
    /**
     * Object:合同变更发起's 最终核定金额property 
     */
    public java.math.BigDecimal getApprovedAmount()
    {
        return getBigDecimal("approvedAmount");
    }
    public void setApprovedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("approvedAmount", item);
    }
    /**
     * Object:合同变更发起's 施工负责人property 
     */
    public String getConstructionHead()
    {
        return getString("constructionHead");
    }
    public void setConstructionHead(String item)
    {
        setString("constructionHead", item);
    }
    /**
     * Object:合同变更发起's 实际工作内容描述property 
     */
    public String getWorkNote()
    {
        return getString("workNote");
    }
    public void setWorkNote(String item)
    {
        setString("workNote", item);
    }
    /**
     * Object: 合同变更发起 's 经办人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getBIMUDF0052()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("BIMUDF0052");
    }
    public void setBIMUDF0052(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("BIMUDF0052", item);
    }
    /**
     * Object:合同变更发起's 经济签证延迟申报天数property 
     */
    public int getDelyDay()
    {
        return getInt("delyDay");
    }
    public void setDelyDay(int item)
    {
        setInt("delyDay", item);
    }
    /**
     * Object:合同变更发起's 是否影响二级节点property 
     */
    public boolean isSfejjd()
    {
        return getBoolean("Sfejjd");
    }
    public void setSfejjd(boolean item)
    {
        setBoolean("Sfejjd", item);
    }
    /**
     * Object:合同变更发起's 报建指标property 
     */
    public boolean isBjzb()
    {
        return getBoolean("Bjzb");
    }
    public void setBjzb(boolean item)
    {
        setBoolean("Bjzb", item);
    }
    /**
     * Object:合同变更发起's 销售承诺property 
     */
    public boolean isXscn()
    {
        return getBoolean("Xscn");
    }
    public void setXscn(boolean item)
    {
        setBoolean("Xscn", item);
    }
    /**
     * Object:合同变更发起's 有无图纸property 
     */
    public boolean isYwtz()
    {
        return getBoolean("Ywtz");
    }
    public void setYwtz(boolean item)
    {
        setBoolean("Ywtz", item);
    }
    /**
     * Object:合同变更发起's 附图编号property 
     */
    public String getFtbh()
    {
        return getString("Ftbh");
    }
    public void setFtbh(String item)
    {
        setString("Ftbh", item);
    }
    /**
     * Object:合同变更发起's 变更原因property 
     */
    public String getBgyy()
    {
        return getString("Bgyy");
    }
    public void setBgyy(String item)
    {
        setString("Bgyy", item);
    }
    /**
     * Object:合同变更发起's 是否影响一级节点  property 
     */
    public boolean isSfyjjd()
    {
        return getBoolean("sfyjjd");
    }
    public void setSfyjjd(boolean item)
    {
        setBoolean("sfyjjd", item);
    }
    /**
     * Object:合同变更发起's 备注property 
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
     * Object: 合同变更发起 's 关联设计变更单据 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillInfo getDesignChange()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)get("designChange");
    }
    public void setDesignChange(com.kingdee.eas.fdc.contract.ChangeAuditBillInfo item)
    {
        put("designChange", item);
    }
    /**
     * Object:合同变更发起's 是否超合约规划property 
     */
    public String getSfhygh()
    {
        return getString("sfhygh");
    }
    public void setSfhygh(String item)
    {
        setString("sfhygh", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("70116117");
    }
}