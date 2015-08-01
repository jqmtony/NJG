package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractExecInfosInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractExecInfosInfo()
    {
        this("id");
    }
    protected AbstractContractExecInfosInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同执行信息 's 工程项目 property 
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
     * Object: 合同执行信息 's 合同 property 
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
     * Object:合同执行信息's 变更金额(本币)property 
     */
    public java.math.BigDecimal getChangeAmt()
    {
        return getBigDecimal("changeAmt");
    }
    public void setChangeAmt(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmt", item);
    }
    /**
     * Object:合同执行信息's 结算金额((本币)property 
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
     * Object:合同执行信息's 合同成本(本币)property 
     */
    public java.math.BigDecimal getCostAmt()
    {
        return getBigDecimal("costAmt");
    }
    public void setCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("costAmt", item);
    }
    /**
     * Object:合同执行信息's 已完工工程量property 
     */
    public java.math.BigDecimal getCompletedAmt()
    {
        return getBigDecimal("completedAmt");
    }
    public void setCompletedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("completedAmt", item);
    }
    /**
     * Object:合同执行信息's 已付款金额(本币)property 
     */
    public java.math.BigDecimal getPaidAmt()
    {
        return getBigDecimal("paidAmt");
    }
    public void setPaidAmt(java.math.BigDecimal item)
    {
        setBigDecimal("paidAmt", item);
    }
    /**
     * Object:合同执行信息's 已开发票金额property 
     */
    public java.math.BigDecimal getInvoicedAmt()
    {
        return getBigDecimal("invoicedAmt");
    }
    public void setInvoicedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoicedAmt", item);
    }
    /**
     * Object:合同执行信息's 未完工工程量property 
     */
    public java.math.BigDecimal getNotCompletedAmt()
    {
        return getBigDecimal("notCompletedAmt");
    }
    public void setNotCompletedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("notCompletedAmt", item);
    }
    /**
     * Object:合同执行信息's 扣款金额(本币)property 
     */
    public java.math.BigDecimal getDeductedAmt()
    {
        return getBigDecimal("deductedAmt");
    }
    public void setDeductedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("deductedAmt", item);
    }
    /**
     * Object:合同执行信息's 违约金额(本币)property 
     */
    public java.math.BigDecimal getCompensatedAmt()
    {
        return getBigDecimal("compensatedAmt");
    }
    public void setCompensatedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("compensatedAmt", item);
    }
    /**
     * Object:合同执行信息's 是否已经结算property 
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
     * Object:合同执行信息's 变更金额(原币)property 
     */
    public java.math.BigDecimal getChangeAmtOri()
    {
        return getBigDecimal("changeAmtOri");
    }
    public void setChangeAmtOri(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmtOri", item);
    }
    /**
     * Object:合同执行信息's 结算金额(原币)property 
     */
    public java.math.BigDecimal getSettleAmtOri()
    {
        return getBigDecimal("settleAmtOri");
    }
    public void setSettleAmtOri(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmtOri", item);
    }
    /**
     * Object:合同执行信息's 合同成本(原币)property 
     */
    public java.math.BigDecimal getCostAmtOri()
    {
        return getBigDecimal("costAmtOri");
    }
    public void setCostAmtOri(java.math.BigDecimal item)
    {
        setBigDecimal("costAmtOri", item);
    }
    /**
     * Object:合同执行信息's 已付款金额(原币)property 
     */
    public java.math.BigDecimal getPaidAmtOri()
    {
        return getBigDecimal("paidAmtOri");
    }
    public void setPaidAmtOri(java.math.BigDecimal item)
    {
        setBigDecimal("paidAmtOri", item);
    }
    /**
     * Object:合同执行信息's 扣款金额(原币)property 
     */
    public java.math.BigDecimal getDeductedAmtOri()
    {
        return getBigDecimal("deductedAmtOri");
    }
    public void setDeductedAmtOri(java.math.BigDecimal item)
    {
        setBigDecimal("deductedAmtOri", item);
    }
    /**
     * Object:合同执行信息's 违约金额(原币)property 
     */
    public java.math.BigDecimal getCompensatedAmtOri()
    {
        return getBigDecimal("compensatedAmtOri");
    }
    public void setCompensatedAmtOri(java.math.BigDecimal item)
    {
        setBigDecimal("compensatedAmtOri", item);
    }
    /**
     * Object:合同执行信息's 奖励金额(本币)property 
     */
    public java.math.BigDecimal getGuerdonAmt()
    {
        return getBigDecimal("guerdonAmt");
    }
    public void setGuerdonAmt(java.math.BigDecimal item)
    {
        setBigDecimal("guerdonAmt", item);
    }
    /**
     * Object:合同执行信息's 奖励金额(原币)property 
     */
    public java.math.BigDecimal getGuerdonAmtOri()
    {
        return getBigDecimal("guerdonAmtOri");
    }
    public void setGuerdonAmtOri(java.math.BigDecimal item)
    {
        setBigDecimal("guerdonAmtOri", item);
    }
    /**
     * Object:合同执行信息's 是否无文本合同property 
     */
    public boolean isIsNoText()
    {
        return getBoolean("isNoText");
    }
    public void setIsNoText(boolean item)
    {
        setBoolean("isNoText", item);
    }
    /**
     * Object: 合同执行信息 's 无文本合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getConWithoutText()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("conWithoutText");
    }
    public void setConWithoutText(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("conWithoutText", item);
    }
    /**
     * Object:合同执行信息's 甲供材扣款原币金额property 
     */
    public java.math.BigDecimal getPartAMatlAmt()
    {
        return getBigDecimal("partAMatlAmt");
    }
    public void setPartAMatlAmt(java.math.BigDecimal item)
    {
        setBigDecimal("partAMatlAmt", item);
    }
    /**
     * Object:合同执行信息's 甲供材扣款本币金额property 
     */
    public java.math.BigDecimal getPartAMatLoaAmt()
    {
        return getBigDecimal("partAMatLoaAmt");
    }
    public void setPartAMatLoaAmt(java.math.BigDecimal item)
    {
        setBigDecimal("partAMatLoaAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E97B39A7");
    }
}