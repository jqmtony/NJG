package com.kingdee.eas.fi.ar;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOtherBillInfo extends com.kingdee.eas.fi.ar.ArApBillBaseInfo implements Serializable 
{
    public AbstractOtherBillInfo()
    {
        this("id");
    }
    protected AbstractOtherBillInfo(String pkField)
    {
        super(pkField);
        put("recievePlan", new com.kingdee.eas.fi.ar.OtherBillPlanCollection());
        put("entry", new com.kingdee.eas.fi.ar.OtherBillentryCollection());
    }
    /**
     * Object: Ӧ�յ� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.SaleOrgUnitInfo getSaleOrg()
    {
        return (com.kingdee.eas.basedata.org.SaleOrgUnitInfo)get("saleOrg");
    }
    public void setSaleOrg(com.kingdee.eas.basedata.org.SaleOrgUnitInfo item)
    {
        put("saleOrg", item);
    }
    /**
     * Object:Ӧ�յ�'s ���˽��property 
     */
    public java.math.BigDecimal getTotalBadAmount()
    {
        return getBigDecimal("totalBadAmount");
    }
    public void setTotalBadAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalBadAmount", item);
    }
    /**
     * Object:Ӧ�յ�'s ���˽�λ��property 
     */
    public java.math.BigDecimal getTotalBadAmountLocal()
    {
        return getBigDecimal("totalBadAmountLocal");
    }
    public void setTotalBadAmountLocal(java.math.BigDecimal item)
    {
        setBigDecimal("totalBadAmountLocal", item);
    }
    /**
     * Object: Ӧ�յ� 's ��¼ property 
     */
    public com.kingdee.eas.fi.ar.OtherBillentryCollection getEntry()
    {
        return (com.kingdee.eas.fi.ar.OtherBillentryCollection)get("entry");
    }
    /**
     * Object: Ӧ�յ� 's �տ�ƻ� property 
     */
    public com.kingdee.eas.fi.ar.OtherBillPlanCollection getRecievePlan()
    {
        return (com.kingdee.eas.fi.ar.OtherBillPlanCollection)get("recievePlan");
    }
    /**
     * Object:Ӧ�յ�'s ��������property 
     */
    public com.kingdee.eas.fi.ar.OtherBillTypeEnum getBillType()
    {
        return com.kingdee.eas.fi.ar.OtherBillTypeEnum.getEnum(getInt("billType"));
    }
    public void setBillType(com.kingdee.eas.fi.ar.OtherBillTypeEnum item)
    {
		if (item != null) {
        setInt("billType", item.getValue());
		}
    }
    /**
     * Object: Ӧ�յ� 's ������ property 
     */
    public com.kingdee.eas.basedata.scm.sd.sale.SaleGroupInfo getSaleGroup()
    {
        return (com.kingdee.eas.basedata.scm.sd.sale.SaleGroupInfo)get("saleGroup");
    }
    public void setSaleGroup(com.kingdee.eas.basedata.scm.sd.sale.SaleGroupInfo item)
    {
        put("saleGroup", item);
    }
    /**
     * Object:Ӧ�յ�'s ��Դ����������property 
     */
    public com.kingdee.eas.fi.ar.OtherBillTypeEnum getBillType_SourceBill()
    {
        return com.kingdee.eas.fi.ar.OtherBillTypeEnum.getEnum(getInt("billType_SourceBill"));
    }
    public void setBillType_SourceBill(com.kingdee.eas.fi.ar.OtherBillTypeEnum item)
    {
		if (item != null) {
        setInt("billType_SourceBill", item.getValue());
		}
    }
    /**
     * Object:Ӧ�յ�'s Эͬ��������property 
     */
    public String getSYNbillType()
    {
        return getString("SYNbillType");
    }
    public void setSYNbillType(String item)
    {
        setString("SYNbillType", item);
    }
    /**
     * Object:Ӧ�յ�'s Эͬ���ݱ��property 
     */
    public String getSYNbillNumber()
    {
        return getString("SYNbillNumber");
    }
    public void setSYNbillNumber(String item)
    {
        setString("SYNbillNumber", item);
    }
    /**
     * Object:Ӧ�յ�'s Эͬ����IDproperty 
     */
    public String getSYNBillID()
    {
        return getString("SYNBillID");
    }
    public void setSYNBillID(String item)
    {
        setString("SYNBillID", item);
    }
    /**
     * Object:Ӧ�յ�'s Эͬ���ݷ�¼IDproperty 
     */
    public String getSYNBillEntryID()
    {
        return getString("SYNBillEntryID");
    }
    public void setSYNBillEntryID(String item)
    {
        setString("SYNBillEntryID", item);
    }
    /**
     * Object:Ӧ�յ�'s �Ѻ������property 
     */
    public java.math.BigDecimal getYhxAmount()
    {
        return getBigDecimal("yhxAmount");
    }
    public void setYhxAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yhxAmount", item);
    }
    /**
     * Object:Ӧ�յ�'s �䵥��property 
     */
    public String getLdNo()
    {
        return getString("ldNo");
    }
    public void setLdNo(String item)
    {
        setString("ldNo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FC910EF3");
    }
}