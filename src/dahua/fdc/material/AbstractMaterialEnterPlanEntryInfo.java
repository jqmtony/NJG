package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialEnterPlanEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMaterialEnterPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractMaterialEnterPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���Ͻ����ƻ���¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.material.MaterialEnterPlanBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.material.MaterialEnterPlanBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.material.MaterialEnterPlanBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ���Ͻ����ƻ���¼ 's ���� property 
     */
    public com.kingdee.eas.basedata.master.material.MaterialInfo getMaterial()
    {
        return (com.kingdee.eas.basedata.master.material.MaterialInfo)get("material");
    }
    public void setMaterial(com.kingdee.eas.basedata.master.material.MaterialInfo item)
    {
        put("material", item);
    }
    /**
     * Object:���Ͻ����ƻ���¼'s ����ͺ�property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object: ���Ͻ����ƻ���¼ 's ��λ property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object:���Ͻ����ƻ���¼'s ����property 
     */
    public java.math.BigDecimal getQuantity()
    {
        return getBigDecimal("quantity");
    }
    public void setQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("quantity", item);
    }
    /**
     * Object:���Ͻ����ƻ���¼'s ����ʱ��property 
     */
    public java.util.Date getEnterTime()
    {
        return getDate("enterTime");
    }
    public void setEnterTime(java.util.Date item)
    {
        setDate("enterTime", item);
    }
    /**
     * Object:���Ͻ����ƻ���¼'s �˶�����property 
     */
    public java.math.BigDecimal getAuditQuantity()
    {
        return getBigDecimal("auditQuantity");
    }
    public void setAuditQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("auditQuantity", item);
    }
    /**
     * Object:���Ͻ����ƻ���¼'s ��עproperty 
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
     * Object:���Ͻ����ƻ���¼'s �Ѷ�������property 
     */
    public java.math.BigDecimal getHasOrderQty()
    {
        return getBigDecimal("hasOrderQty");
    }
    public void setHasOrderQty(java.math.BigDecimal item)
    {
        setBigDecimal("hasOrderQty", item);
    }
    /**
     * Object:���Ͻ����ƻ���¼'s �ѻ�������property 
     */
    public java.math.BigDecimal getHasSumQty()
    {
        return getBigDecimal("hasSumQty");
    }
    public void setHasSumQty(java.math.BigDecimal item)
    {
        setBigDecimal("hasSumQty", item);
    }
    /**
     * Object:���Ͻ����ƻ���¼'s �ջ�����ǰ����property 
     */
    public java.math.BigDecimal getProduceLeadTime()
    {
        return getBigDecimal("produceLeadTime");
    }
    public void setProduceLeadTime(java.math.BigDecimal item)
    {
        setBigDecimal("produceLeadTime", item);
    }
    /**
     * Object:���Ͻ����ƻ���¼'s ���Ϻ�ͬproperty 
     */
    public String getMaterialCon()
    {
        return getString("materialCon");
    }
    public void setMaterialCon(String item)
    {
        setString("materialCon", item);
    }
    /**
     * Object:���Ͻ����ƻ���¼'s ��Ӧ��property 
     */
    public String getSupplier()
    {
        return getString("supplier");
    }
    public void setSupplier(String item)
    {
        setString("supplier", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("74C1269E");
    }
}