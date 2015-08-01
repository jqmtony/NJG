package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFdcMaterialInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractFdcMaterialInfo()
    {
        this("id");
    }
    protected AbstractFdcMaterialInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:材料设备's 型号property 
     */
    public String getModel()
    {
        return getModel((Locale)null);
    }
    public void setModel(String item)
    {
		setModel(item,(Locale)null);
    }
    public String getModel(Locale local)
    {
        return TypeConversionUtils.objToString(get("model", local));
    }
    public void setModel(String item, Locale local)
    {
        put("model", item, local);
    }
    /**
     * Object:材料设备's 详细类别或材质property 
     */
    public String getDetailType()
    {
        return getDetailType((Locale)null);
    }
    public void setDetailType(String item)
    {
		setDetailType(item,(Locale)null);
    }
    public String getDetailType(Locale local)
    {
        return TypeConversionUtils.objToString(get("detailType", local));
    }
    public void setDetailType(String item, Locale local)
    {
        put("detailType", item, local);
    }
    /**
     * Object:材料设备's 产地property 
     */
    public String getProductArea()
    {
        return getProductArea((Locale)null);
    }
    public void setProductArea(String item)
    {
		setProductArea(item,(Locale)null);
    }
    public String getProductArea(Locale local)
    {
        return TypeConversionUtils.objToString(get("productArea", local));
    }
    public void setProductArea(String item, Locale local)
    {
        put("productArea", item, local);
    }
    /**
     * Object:材料设备's 品牌property 
     */
    public String getBrand()
    {
        return getBrand((Locale)null);
    }
    public void setBrand(String item)
    {
		setBrand(item,(Locale)null);
    }
    public String getBrand(Locale local)
    {
        return TypeConversionUtils.objToString(get("brand", local));
    }
    public void setBrand(String item, Locale local)
    {
        put("brand", item, local);
    }
    /**
     * Object:材料设备's 材料单价property 
     */
    public java.math.BigDecimal getMaterialPrice()
    {
        return getBigDecimal("materialPrice");
    }
    public void setMaterialPrice(java.math.BigDecimal item)
    {
        setBigDecimal("materialPrice", item);
    }
    /**
     * Object:材料设备's 施工单价property 
     */
    public java.math.BigDecimal getProjectPrice()
    {
        return getBigDecimal("projectPrice");
    }
    public void setProjectPrice(java.math.BigDecimal item)
    {
        setBigDecimal("projectPrice", item);
    }
    /**
     * Object:材料设备's 综合单价property 
     */
    public java.math.BigDecimal getIntegratePrice()
    {
        return getBigDecimal("integratePrice");
    }
    public void setIntegratePrice(java.math.BigDecimal item)
    {
        setBigDecimal("integratePrice", item);
    }
    /**
     * Object:材料设备's 定标时间property 
     */
    public java.util.Date getDecideDate()
    {
        return getDate("decideDate");
    }
    public void setDecideDate(java.util.Date item)
    {
        setDate("decideDate", item);
    }
    /**
     * Object: 材料设备 's 物料 property 
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
     * Object: 材料设备 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 材料设备 's 供应商 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C469880A");
    }
}