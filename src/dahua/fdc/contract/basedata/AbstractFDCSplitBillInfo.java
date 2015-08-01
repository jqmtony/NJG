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
     * Object:�ɱ����'s �Ƿ�����ƾ֤property 
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
     * Object:�ɱ����'s ���״̬property 
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
     * Object:�ɱ����'s �Ƿ�����property 
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
     * Object:�ɱ����'s �Ƿ�����ָ��ˢ�³�ʼ��property 
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
     * Object:�ɱ����'s �Ƿ���ڼ����°汾property 
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
     * Object: �ɱ���� 's ��ͬ property 
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
     * Object: �ɱ���� 's ������Ŀ property 
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