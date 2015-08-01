package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimCostCtrlBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractAimCostCtrlBillEntryInfo()
    {
        this("id");
    }
    protected AbstractAimCostCtrlBillEntryInfo(String pkField)
    {
        super(pkField);
        put("stageItems", new com.kingdee.eas.fdc.aimcost.AimCostCtrlItemCollection());
        put("costAccount", new com.kingdee.eas.fdc.aimcost.AimCostCtrlCostActItemsCollection());
    }
    /**
     * Object: Ŀ��ɱ����Ƶ���¼ 's  property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostCtrlBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostCtrlBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.AimCostCtrlBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:Ŀ��ɱ����Ƶ���¼'s ���Ʒ�ʽproperty 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostCtrlTypeEnum getCtrlType()
    {
        return com.kingdee.eas.fdc.aimcost.AimCostCtrlTypeEnum.getEnum(getInt("ctrlType"));
    }
    public void setCtrlType(com.kingdee.eas.fdc.aimcost.AimCostCtrlTypeEnum item)
    {
		if (item != null) {
        setInt("ctrlType", item.getValue());
		}
    }
    /**
     * Object: Ŀ��ɱ����Ƶ���¼ 's �������� property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostCtrlCostActItemsCollection getCostAccount()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostCtrlCostActItemsCollection)get("costAccount");
    }
    /**
     * Object: Ŀ��ɱ����Ƶ���¼ 's ����׶���Ŀ property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostCtrlItemCollection getStageItems()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostCtrlItemCollection)get("stageItems");
    }
    /**
     * Object:Ŀ��ɱ����Ƶ���¼'s ����property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: Ŀ��ɱ����Ƶ���¼ 's ��Ʒ property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("48AAACC5");
    }
}