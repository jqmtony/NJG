package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynCostCtrlBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractDynCostCtrlBillEntryInfo()
    {
        this("id");
    }
    protected AbstractDynCostCtrlBillEntryInfo(String pkField)
    {
        super(pkField);
        put("items", new com.kingdee.eas.fdc.aimcost.DynCostCtrlEntryItemsCollection());
    }
    /**
     * Object: ��̬�ɱ����Ƶ���¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostCtrlBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostCtrlBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.DynCostCtrlBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��̬�ɱ����Ƶ���¼'s ��ʾ������ر���property 
     */
    public java.math.BigDecimal getAlermValue()
    {
        return getBigDecimal("alermValue");
    }
    public void setAlermValue(java.math.BigDecimal item)
    {
        setBigDecimal("alermValue", item);
    }
    /**
     * Object:��̬�ɱ����Ƶ���¼'s �ϸ������ر���property 
     */
    public java.math.BigDecimal getStrictValue()
    {
        return getBigDecimal("strictValue");
    }
    public void setStrictValue(java.math.BigDecimal item)
    {
        setBigDecimal("strictValue", item);
    }
    /**
     * Object: ��̬�ɱ����Ƶ���¼ 's  property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object: ��̬�ɱ����Ƶ���¼ 's null property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostCtrlEntryItemsCollection getItems()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostCtrlEntryItemsCollection)get("items");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9EC82E11");
    }
}