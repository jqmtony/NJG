package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialEnterSumInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMaterialEnterSumInfo()
    {
        this("id");
    }
    protected AbstractMaterialEnterSumInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.material.MaterialEnterSumEntryCollection());
    }
    /**
     * Object: ���Ͻ����ƻ����� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.material.MaterialEnterSumEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.material.MaterialEnterSumEntryCollection)get("entrys");
    }
    /**
     * Object:���Ͻ����ƻ�����'s �Ƿ�����ƾ֤property 
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
     * Object:���Ͻ����ƻ�����'s ����״̬property 
     */
    public com.kingdee.eas.fdc.material.MaterialBillStateEnum getBillstate()
    {
        return com.kingdee.eas.fdc.material.MaterialBillStateEnum.getEnum(getString("billstate"));
    }
    public void setBillstate(com.kingdee.eas.fdc.material.MaterialBillStateEnum item)
    {
		if (item != null) {
        setString("billstate", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("842589E0");
    }
}