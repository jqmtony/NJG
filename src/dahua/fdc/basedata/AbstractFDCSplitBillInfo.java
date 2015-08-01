package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplitBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCSplitBillInfo()
    {
        this("id");
    }
    protected AbstractFDCSplitBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:成本拆分's 是否生成凭证property 
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
     * Object:成本拆分's 拆分状态property 
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
     * Object:成本拆分's 是否作废property 
     */
    public boolean isIsInvalid()
    {
        return getBoolean("isInvalid");
    }
    public void setIsInvalid(boolean item)
    {
        setBoolean("isInvalid", item);
    }
    /**
     * Object:成本拆分's 是否做了指标刷新初始化property 
     */
    public boolean isHasInitIdx()
    {
        return getBoolean("hasInitIdx");
    }
    public void setHasInitIdx(boolean item)
    {
        setBoolean("hasInitIdx", item);
    }
    /**
     * Object:成本拆分's 是否此期间最新版本property 
     */
    public boolean isIslastVerThisPeriod()
    {
        return getBoolean("islastVerThisPeriod");
    }
    public void setIslastVerThisPeriod(boolean item)
    {
        setBoolean("islastVerThisPeriod", item);
    }
    /**
     * Object: 成本拆分 's 合同 property 
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
     * Object: 成本拆分 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("234AEC4E");
    }
}