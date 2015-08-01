package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCNoCostSplitBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractFDCNoCostSplitBillEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCNoCostSplitBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ǳɱ���ַ�¼ 's ��ƿ�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    /**
     * Object: �ǳɱ���ַ�¼ 's ��Ʒ property 
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
     * Object:�ǳɱ���ַ�¼'s �������property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s �������property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitTypeEnum getSplitType()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitTypeEnum.getEnum(getString("splitType"));
    }
    public void setSplitType(com.kingdee.eas.fdc.basedata.CostSplitTypeEnum item)
    {
		if (item != null) {
        setString("splitType", item.getValue());
		}
    }
    /**
     * Object:�ǳɱ���ַ�¼'s ����property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s �Ƿ�Ҷ�ӽڵ�property 
     */
    public boolean isIsLeaf()
    {
        return getBoolean("isLeaf");
    }
    public void setIsLeaf(boolean item)
    {
        setBoolean("isLeaf", item);
    }
    /**
     * Object: �ǳɱ���ַ�¼ 's ��̯���� property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getApportionType()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("apportionType");
    }
    public void setApportionType(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("apportionType", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s ��̯��ֵproperty 
     */
    public java.math.BigDecimal getApportionValue()
    {
        return getBigDecimal("apportionValue");
    }
    public void setApportionValue(java.math.BigDecimal item)
    {
        setBigDecimal("apportionValue", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s ��̯��ֵ����property 
     */
    public java.math.BigDecimal getApportionValueTotal()
    {
        return getBigDecimal("apportionValueTotal");
    }
    public void setApportionValueTotal(java.math.BigDecimal item)
    {
        setBigDecimal("apportionValueTotal", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s ������ֵproperty 
     */
    public java.math.BigDecimal getOtherRatio()
    {
        return getBigDecimal("otherRatio");
    }
    public void setOtherRatio(java.math.BigDecimal item)
    {
        setBigDecimal("otherRatio", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s ������ֵ����property 
     */
    public java.math.BigDecimal getOtherRatioTotal()
    {
        return getBigDecimal("otherRatioTotal");
    }
    public void setOtherRatioTotal(java.math.BigDecimal item)
    {
        setBigDecimal("otherRatioTotal", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s ֱ�ӷ���property 
     */
    public java.math.BigDecimal getDirectAmount()
    {
        return getBigDecimal("directAmount");
    }
    public void setDirectAmount(java.math.BigDecimal item)
    {
        setBigDecimal("directAmount", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s ֱ�ӷ��û���property 
     */
    public java.math.BigDecimal getDirectAmountTotal()
    {
        return getBigDecimal("directAmountTotal");
    }
    public void setDirectAmountTotal(java.math.BigDecimal item)
    {
        setBigDecimal("directAmountTotal", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s �Ƿ�����̯property 
     */
    public boolean isIsApportion()
    {
        return getBoolean("isApportion");
    }
    public void setIsApportion(boolean item)
    {
        setBoolean("isApportion", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s �Ƿ񸽼ӿ�Ŀproperty 
     */
    public boolean isIsAddlAccount()
    {
        return getBoolean("isAddlAccount");
    }
    public void setIsAddlAccount(boolean item)
    {
        setBoolean("isAddlAccount", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s �ɱ���Դ����property 
     */
    public com.kingdee.bos.util.BOSUuid getCostBillId()
    {
        return getBOSUuid("costBillId");
    }
    public void setCostBillId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("costBillId", item);
    }
    /**
     * Object:�ǳɱ���ַ�¼'s ���property 
     */
    public int getIndex()
    {
        return getInt("index");
    }
    public void setIndex(int item)
    {
        setInt("index", item);
    }
    /**
     * Object: �ǳɱ���ַ�¼ 's ������Ŀ property 
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
     * Object:�ǳɱ���ַ�¼'s ��ֵ���IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getSplitBillId()
    {
        return getBOSUuid("splitBillId");
    }
    public void setSplitBillId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("splitBillId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D9B1F52");
    }
}