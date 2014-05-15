package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationSumEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEvaluationSumEntryInfo()
    {
        this("id");
    }
    protected AbstractEvaluationSumEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �б������� 's null property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationSumInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationSumInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invite.EvaluationSumInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �б������� 's Ͷ�굥λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getUnitName()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("unitName");
    }
    public void setUnitName(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("unitName", item);
    }
    /**
     * Object:�б�������'s ����property 
     */
    public String getQuality()
    {
        return getString("quality");
    }
    public void setQuality(String item)
    {
        setString("quality", item);
    }
    /**
     * Object:�б�������'s ���� property 
     */
    public java.math.BigDecimal getProjectLimit()
    {
        return getBigDecimal("projectLimit");
    }
    public void setProjectLimit(java.math.BigDecimal item)
    {
        setBigDecimal("projectLimit", item);
    }
    /**
     * Object:�б�������'s Ͷ�걨�ۣ�Ԫ��property 
     */
    public java.math.BigDecimal getQuote()
    {
        return getBigDecimal("quote");
    }
    public void setQuote(java.math.BigDecimal item)
    {
        setBigDecimal("quote", item);
    }
    /**
     * Object:�б�������'s ʣ��ƽ������property 
     */
    public java.math.BigDecimal getAvgAmount()
    {
        return getBigDecimal("avgAmount");
    }
    public void setAvgAmount(java.math.BigDecimal item)
    {
        setBigDecimal("avgAmount", item);
    }
    /**
     * Object:�б�������'s �����׼��property 
     */
    public java.math.BigDecimal getBenchmarkAmount()
    {
        return getBigDecimal("benchmarkAmount");
    }
    public void setBenchmarkAmount(java.math.BigDecimal item)
    {
        setBigDecimal("benchmarkAmount", item);
    }
    /**
     * Object:�б�������'s ���property 
     */
    public String getDifference()
    {
        return getString("difference");
    }
    public void setDifference(String item)
    {
        setString("difference", item);
    }
    /**
     * Object:�б�������'s ���ٷֱ�property 
     */
    public java.math.BigDecimal getDiffRate()
    {
        return getBigDecimal("diffRate");
    }
    public void setDiffRate(java.math.BigDecimal item)
    {
        setBigDecimal("diffRate", item);
    }
    /**
     * Object:�б�������'s �۷�property 
     */
    public java.math.BigDecimal getDeduct()
    {
        return getBigDecimal("deduct");
    }
    public void setDeduct(java.math.BigDecimal item)
    {
        setBigDecimal("deduct", item);
    }
    /**
     * Object:�б�������'s �����÷�property 
     */
    public java.math.BigDecimal getBusinessSorce()
    {
        return getBigDecimal("businessSorce");
    }
    public void setBusinessSorce(java.math.BigDecimal item)
    {
        setBigDecimal("businessSorce", item);
    }
    /**
     * Object:�б�������'s ������÷�property 
     */
    public java.math.BigDecimal getTechnologyScore()
    {
        return getBigDecimal("technologyScore");
    }
    public void setTechnologyScore(java.math.BigDecimal item)
    {
        setBigDecimal("technologyScore", item);
    }
    /**
     * Object:�б�������'s �ܷ�property 
     */
    public java.math.BigDecimal getTotalScore()
    {
        return getBigDecimal("totalScore");
    }
    public void setTotalScore(java.math.BigDecimal item)
    {
        setBigDecimal("totalScore", item);
    }
    /**
     * Object:�б�������'s ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object:�б�������'s ����property 
     */
    public int getIndex()
    {
        return getInt("index");
    }
    public void setIndex(int item)
    {
        setInt("index", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("50F2494C");
    }
}