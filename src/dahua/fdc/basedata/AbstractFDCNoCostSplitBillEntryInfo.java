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
     * Object: 非成本拆分分录 's 会计科目 property 
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
     * Object: 非成本拆分分录 's 产品 property 
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
     * Object:非成本拆分分录's 归属金额property 
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
     * Object:非成本拆分分录's 拆分类型property 
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
     * Object:非成本拆分分录's 级次property 
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
     * Object:非成本拆分分录's 是否叶子节点property 
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
     * Object: 非成本拆分分录 's 分摊类型 property 
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
     * Object:非成本拆分分录's 分摊比值property 
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
     * Object:非成本拆分分录's 分摊比值汇总property 
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
     * Object:非成本拆分分录's 其他比值property 
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
     * Object:非成本拆分分录's 其他比值汇总property 
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
     * Object:非成本拆分分录's 直接费用property 
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
     * Object:非成本拆分分录's 直接费用汇总property 
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
     * Object:非成本拆分分录's 是否参与分摊property 
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
     * Object:非成本拆分分录's 是否附加科目property 
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
     * Object:非成本拆分分录's 成本来源单据property 
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
     * Object:非成本拆分分录's 序号property 
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
     * Object: 非成本拆分分录 's 工程项目 property 
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
     * Object:非成本拆分分录's 拆分单据IDproperty 
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