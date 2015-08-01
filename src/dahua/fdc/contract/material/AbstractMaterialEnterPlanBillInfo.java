package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialEnterPlanBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMaterialEnterPlanBillInfo()
    {
        this("id");
    }
    protected AbstractMaterialEnterPlanBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.material.MaterialEnterPlanEntryCollection());
    }
    /**
     * Object: ���Ͻ����ƻ� 's ������Ŀ property 
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
     * Object: ���Ͻ����ƻ� 's ʩ����ͬ property 
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
     * Object: ���Ͻ����ƻ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.material.MaterialEnterPlanEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.material.MaterialEnterPlanEntryCollection)get("entrys");
    }
    /**
     * Object:���Ͻ����ƻ�'s ����״̬property 
     */
    public com.kingdee.eas.fdc.material.MaterialBillStateEnum getBillState()
    {
        return com.kingdee.eas.fdc.material.MaterialBillStateEnum.getEnum(getString("billState"));
    }
    public void setBillState(com.kingdee.eas.fdc.material.MaterialBillStateEnum item)
    {
		if (item != null) {
        setString("billState", item.getValue());
		}
    }
    /**
     * Object:���Ͻ����ƻ�'s �ͻ���ַproperty 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6F1D905B");
    }
}