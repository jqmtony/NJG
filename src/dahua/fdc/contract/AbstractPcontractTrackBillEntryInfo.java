package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPcontractTrackBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPcontractTrackBillEntryInfo()
    {
        this("id");
    }
    protected AbstractPcontractTrackBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.contract.PcontractTrackBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.PcontractTrackBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.PcontractTrackBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s pcidproperty 
     */
    public String getPcid()
    {
        return getString("pcid");
    }
    public void setPcid(String item)
    {
        setString("pcid", item);
    }
    /**
     * Object:��¼'s levelproperty 
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
     * Object:��¼'s longNumberproperty 
     */
    public String getLongNumber()
    {
        return getString("longNumber");
    }
    public void setLongNumber(String item)
    {
        setString("longNumber", item);
    }
    /**
     * Object:��¼'s headNumberproperty 
     */
    public String getHeadNumber()
    {
        return getString("headNumber");
    }
    public void setHeadNumber(String item)
    {
        setString("headNumber", item);
    }
    /**
     * Object:��¼'s ��Լ�滮����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:��¼'s ��Լ����property 
     */
    public String getHyType()
    {
        return getString("hyType");
    }
    public void setHyType(String item)
    {
        setString("hyType", item);
    }
    /**
     * Object:��¼'s �滮���property 
     */
    public java.math.BigDecimal getPlanAmount()
    {
        return getBigDecimal("planAmount");
    }
    public void setPlanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("planAmount", item);
    }
    /**
     * Object:��¼'s Ԥ�������property 
     */
    public java.math.BigDecimal getChangeRate()
    {
        return getBigDecimal("changeRate");
    }
    public void setChangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("changeRate", item);
    }
    /**
     * Object:��¼'s �ɹ����ƽ��property 
     */
    public java.math.BigDecimal getContralAmount()
    {
        return getBigDecimal("contralAmount");
    }
    public void setContralAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contralAmount", item);
    }
    /**
     * Object:��¼'s ʩ��ͼ����ʱ��property 
     */
    public java.util.Date getSgtDate()
    {
        return getDate("sgtDate");
    }
    public void setSgtDate(java.util.Date item)
    {
        setDate("sgtDate", item);
    }
    /**
     * Object:��¼'s ʵ��ʩ��ͼ����ʱ��property 
     */
    public java.util.Date getSgtRealDate()
    {
        return getDate("sgtRealDate");
    }
    public void setSgtRealDate(java.util.Date item)
    {
        setDate("sgtRealDate", item);
    }
    /**
     * Object:��¼'s �Ƿ�����property 
     */
    public boolean isSgtOverdue()
    {
        return getBoolean("sgtOverdue");
    }
    public void setSgtOverdue(boolean item)
    {
        setBoolean("sgtOverdue", item);
    }
    /**
     * Object:��¼'s �ƻ�ʩ��ͼ����ʱ��property 
     */
    public java.util.Date getSgtPlanDate()
    {
        return getDate("sgtPlanDate");
    }
    public void setSgtPlanDate(java.util.Date item)
    {
        setDate("sgtPlanDate", item);
    }
    /**
     * Object:��¼'s ��ͬǩ��ʱ��property 
     */
    public java.util.Date getCsDate()
    {
        return getDate("csDate");
    }
    public void setCsDate(java.util.Date item)
    {
        setDate("csDate", item);
    }
    /**
     * Object:��¼'s ʵ�ʺ�ͬǩ��ʱ��property 
     */
    public java.util.Date getCsRealDate()
    {
        return getDate("csRealDate");
    }
    public void setCsRealDate(java.util.Date item)
    {
        setDate("csRealDate", item);
    }
    /**
     * Object:��¼'s �Ƿ�����property 
     */
    public boolean isCsOverdue()
    {
        return getBoolean("csOverdue");
    }
    public void setCsOverdue(boolean item)
    {
        setBoolean("csOverdue", item);
    }
    /**
     * Object:��¼'s �ƻ���ͬǩ��ʱ��property 
     */
    public java.util.Date getCsPlanDate()
    {
        return getDate("csPlanDate");
    }
    public void setCsPlanDate(java.util.Date item)
    {
        setDate("csPlanDate", item);
    }
    /**
     * Object:��¼'s ����ʱ��property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:��¼'s ʵ�ʿ���ʱ��property 
     */
    public java.util.Date getStartRealDate()
    {
        return getDate("startRealDate");
    }
    public void setStartRealDate(java.util.Date item)
    {
        setDate("startRealDate", item);
    }
    /**
     * Object:��¼'s �Ƿ�����property 
     */
    public boolean isStartOverdue()
    {
        return getBoolean("startOverdue");
    }
    public void setStartOverdue(boolean item)
    {
        setBoolean("startOverdue", item);
    }
    /**
     * Object:��¼'s �ƻ�����ʱ��property 
     */
    public java.util.Date getStartPlanDate()
    {
        return getDate("startPlanDate");
    }
    public void setStartPlanDate(java.util.Date item)
    {
        setDate("startPlanDate", item);
    }
    /**
     * Object:��¼'s ����ʱ��property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:��¼'s ʵ�ʿ���ʱ��property 
     */
    public java.util.Date getEndRealDate()
    {
        return getDate("endRealDate");
    }
    public void setEndRealDate(java.util.Date item)
    {
        setDate("endRealDate", item);
    }
    /**
     * Object:��¼'s �Ƿ�����property 
     */
    public boolean isEndOverdue()
    {
        return getBoolean("endOverdue");
    }
    public void setEndOverdue(boolean item)
    {
        setBoolean("endOverdue", item);
    }
    /**
     * Object:��¼'s �ƻ�����ʱ��property 
     */
    public java.util.Date getEndPlanDate()
    {
        return getDate("endPlanDate");
    }
    public void setEndPlanDate(java.util.Date item)
    {
        setDate("endPlanDate", item);
    }
    /**
     * Object:��¼'s ��ͬǩ�����ʱ��property 
     */
    public java.util.Date getCsendDate()
    {
        return getDate("csendDate");
    }
    public void setCsendDate(java.util.Date item)
    {
        setDate("csendDate", item);
    }
    /**
     * Object:��¼'s ʵ�ʺ�ͬǩ�����ʱ��property 
     */
    public java.util.Date getCsendRealDate()
    {
        return getDate("csendRealDate");
    }
    public void setCsendRealDate(java.util.Date item)
    {
        setDate("csendRealDate", item);
    }
    /**
     * Object:��¼'s �Ƿ�����property 
     */
    public boolean isCsendOverdue()
    {
        return getBoolean("csendOverdue");
    }
    public void setCsendOverdue(boolean item)
    {
        setBoolean("csendOverdue", item);
    }
    /**
     * Object:��¼'s �ƻ���ͬǩ�����ʱ��property 
     */
    public java.util.Date getCsendPlanDate()
    {
        return getDate("csendPlanDate");
    }
    public void setCsendPlanDate(java.util.Date item)
    {
        setDate("csendPlanDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("957FA127");
    }
}