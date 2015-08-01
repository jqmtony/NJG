package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialInfoInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMaterialInfoInfo()
    {
        this("id");
    }
    protected AbstractMaterialInfoInfo(String pkField)
    {
        super(pkField);
        put("indexValue", new com.kingdee.eas.fdc.material.MaterialIndexValueCollection());
    }
    /**
     * Object: ���ϱ�����Ϣ 's ���� property 
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
     * Object: ���ϱ�����Ϣ 's ������Ŀ property 
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
     * Object: ���ϱ�����Ϣ 's ��ͬ property 
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
     * Object:���ϱ�����Ϣ's ����property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:���ϱ�����Ϣ's ����ʱ��property 
     */
    public java.sql.Timestamp getQuoteTime()
    {
        return getTimestamp("quoteTime");
    }
    public void setQuoteTime(java.sql.Timestamp item)
    {
        setTimestamp("quoteTime", item);
    }
    /**
     * Object: ���ϱ�����Ϣ 's ��Ӧ�� property 
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
     * Object:���ϱ�����Ϣ's ��Ч����property 
     */
    public java.sql.Timestamp getValidDate()
    {
        return getTimestamp("validDate");
    }
    public void setValidDate(java.sql.Timestamp item)
    {
        setTimestamp("validDate", item);
    }
    /**
     * Object:���ϱ�����Ϣ's �Ƿ�����property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object:���ϱ�����Ϣ's �Ƿ�����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:���ϱ�����Ϣ's ����״̬property 
     */
    public com.kingdee.eas.fdc.material.MaterialStateEnum getMState()
    {
        return com.kingdee.eas.fdc.material.MaterialStateEnum.getEnum(getString("mState"));
    }
    public void setMState(com.kingdee.eas.fdc.material.MaterialStateEnum item)
    {
		if (item != null) {
        setString("mState", item.getValue());
		}
    }
    /**
     * Object: ���ϱ�����Ϣ 's null property 
     */
    public com.kingdee.eas.fdc.material.MaterialIndexValueCollection getIndexValue()
    {
        return (com.kingdee.eas.fdc.material.MaterialIndexValueCollection)get("indexValue");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D390CBB");
    }
}