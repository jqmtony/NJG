package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProProgressReportE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProProgressReportE1Info()
    {
        this("id");
    }
    protected AbstractProProgressReportE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��1������ 's null property 
     */
    public com.kingdee.eas.port.pm.invest.ProProgressReportInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.ProProgressReportInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.ProProgressReportInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��1������ 's ��Ŀ���� property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProName()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("proName");
    }
    public void setProName(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("proName", item);
    }
    /**
     * Object:��1������'s ��ĿԤ��property 
     */
    public java.math.BigDecimal getProjectBudget()
    {
        return getBigDecimal("projectBudget");
    }
    public void setProjectBudget(java.math.BigDecimal item)
    {
        setBigDecimal("projectBudget", item);
    }
    /**
     * Object:��1������'s ��ǩ��ͬproperty 
     */
    public java.math.BigDecimal getSigContract()
    {
        return getBigDecimal("sigContract");
    }
    public void setSigContract(java.math.BigDecimal item)
    {
        setBigDecimal("sigContract", item);
    }
    /**
     * Object:��1������'s �Ѹ���property 
     */
    public java.math.BigDecimal getPayment()
    {
        return getBigDecimal("payment");
    }
    public void setPayment(java.math.BigDecimal item)
    {
        setBigDecimal("payment", item);
    }
    /**
     * Object:��1������'s ���½�չ���property 
     */
    public String getLastMonth()
    {
        return getString("lastMonth");
    }
    public void setLastMonth(String item)
    {
        setString("lastMonth", item);
    }
    /**
     * Object:��1������'s ���½�չ���property 
     */
    public String getTheMonth()
    {
        return getString("theMonth");
    }
    public void setTheMonth(String item)
    {
        setString("theMonth", item);
    }
    /**
     * Object:��1������'s ��עproperty 
     */
    public String getNote()
    {
        return getString("note");
    }
    public void setNote(String item)
    {
        setString("note", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("87154BDD");
    }
}