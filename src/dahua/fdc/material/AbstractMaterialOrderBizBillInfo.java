package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialOrderBizBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMaterialOrderBizBillInfo()
    {
        this("id");
    }
    protected AbstractMaterialOrderBizBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryCollection());
        put("AssEntrys", new com.kingdee.eas.fdc.material.MaterialOrderBizBillAssEntryCollection());
    }
    /**
     * Object: ���϶����� 's ����ϸ property 
     */
    public com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryCollection)get("entrys");
    }
    /**
     * Object:���϶�����'s �Ƿ�����ƾ֤property 
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
     * Object: ���϶����� 's �л��� property 
     */
    public com.kingdee.eas.fdc.material.MaterialOrderBizBillAssEntryCollection getAssEntrys()
    {
        return (com.kingdee.eas.fdc.material.MaterialOrderBizBillAssEntryCollection)get("AssEntrys");
    }
    /**
     * Object: ���϶����� 's ��Ӧ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSuppliers()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("suppliers");
    }
    public void setSuppliers(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("suppliers", item);
    }
    /**
     * Object:���϶�����'s ����״̬property 
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
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("458C7B99");
    }
}