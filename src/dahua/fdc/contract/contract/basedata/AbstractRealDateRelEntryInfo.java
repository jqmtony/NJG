package com.kingdee.eas.fdc.contract.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRealDateRelEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRealDateRelEntryInfo()
    {
        this("id");
    }
    protected AbstractRealDateRelEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's null property 
     */
    public com.kingdee.eas.fdc.contract.basedata.RealDateRelInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.basedata.RealDateRelInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.basedata.RealDateRelInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 合同签订业务日期增加天数property 
     */
    public int getCostDays()
    {
        return getInt("costDays");
    }
    public void setCostDays(int item)
    {
        setInt("costDays", item);
    }
    /**
     * Object:分录's 开工日期增加天数property 
     */
    public int getStartDays()
    {
        return getInt("startDays");
    }
    public void setStartDays(int item)
    {
        setInt("startDays", item);
    }
    /**
     * Object:分录's 合约规划名称property 
     */
    public String getPcname()
    {
        return getString("pcname");
    }
    public void setPcname(String item)
    {
        setString("pcname", item);
    }
    /**
     * Object:分录's longNumberproperty 
     */
    public String getLongNumber()
    {
        return getString("longNumber");
    }
    public void setLongNumber(String item)
    {
        setString("longNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D9446817");
    }
}