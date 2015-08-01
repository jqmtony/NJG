package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialEnterSumEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMaterialEnterSumEntryInfo()
    {
        this("id");
    }
    protected AbstractMaterialEnterSumEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.material.MaterialEnterSumInfo getParent()
    {
        return (com.kingdee.eas.fdc.material.MaterialEnterSumInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.material.MaterialEnterSumInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��¼ 's ������Ŀ property 
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
     * Object: ��¼ 's ��Ӧ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: ��¼ 's ���ϱ��� property 
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
     * Object:��¼'s ��������property 
     */
    public String getMaterialName()
    {
        return getString("materialName");
    }
    public void setMaterialName(String item)
    {
        setString("materialName", item);
    }
    /**
     * Object:��¼'s �滮����property 
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
     * Object: ��¼ 's ��λ property 
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
     * Object:��¼'s ��������property 
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
     * Object:��¼'s �ƻ�����property 
     */
    public java.math.BigDecimal getPlanQuantity()
    {
        return getBigDecimal("planQuantity");
    }
    public void setPlanQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("planQuantity", item);
    }
    /**
     * Object:��¼'s ��������property 
     */
    public java.math.BigDecimal getOrderQuantity()
    {
        return getBigDecimal("orderQuantity");
    }
    public void setOrderQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("orderQuantity", item);
    }
    /**
     * Object:��¼'s ����ʱ��property 
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
     * Object: ��¼ 's ʩ����λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getContractUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("contractUnit");
    }
    public void setContractUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("contractUnit", item);
    }
    /**
     * Object: ��¼ 's ʩ����ͬ property 
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
     * Object: ��¼ 's ���Ϻ�ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getMaterialBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("materialBill");
    }
    public void setMaterialBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("materialBill", item);
    }
    /**
     * Object:��¼'s Դ��property 
     */
    public String getSourceBill()
    {
        return getString("sourceBill");
    }
    public void setSourceBill(String item)
    {
        setString("sourceBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9525BA72");
    }
}