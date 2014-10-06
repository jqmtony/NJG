package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShipReportFormE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractShipReportFormE1Info()
    {
        this("id");
    }
    protected AbstractShipReportFormE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第1个表体 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.ShipReportFormInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.ShipReportFormInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.ShipReportFormInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:第1个表体's 主机缸号property 
     */
    public String getZhujigangone()
    {
        return getString("zhujigangone");
    }
    public void setZhujigangone(String item)
    {
        setString("zhujigangone", item);
    }
    /**
     * Object:第1个表体's 主机缸号property 
     */
    public String getZhujigangtwo()
    {
        return getString("zhujigangtwo");
    }
    public void setZhujigangtwo(String item)
    {
        setString("zhujigangtwo", item);
    }
    /**
     * Object:第1个表体's 1property 
     */
    public String getOne()
    {
        return getString("one");
    }
    public void setOne(String item)
    {
        setString("one", item);
    }
    /**
     * Object:第1个表体's 2property 
     */
    public String getTwo()
    {
        return getString("two");
    }
    public void setTwo(String item)
    {
        setString("two", item);
    }
    /**
     * Object:第1个表体's 3property 
     */
    public String getThree()
    {
        return getString("three");
    }
    public void setThree(String item)
    {
        setString("three", item);
    }
    /**
     * Object:第1个表体's 4property 
     */
    public String getFour()
    {
        return getString("four");
    }
    public void setFour(String item)
    {
        setString("four", item);
    }
    /**
     * Object:第1个表体's 5property 
     */
    public String getFive()
    {
        return getString("five");
    }
    public void setFive(String item)
    {
        setString("five", item);
    }
    /**
     * Object:第1个表体's 6property 
     */
    public String getSix()
    {
        return getString("six");
    }
    public void setSix(String item)
    {
        setString("six", item);
    }
    /**
     * Object:第1个表体's 7property 
     */
    public String getSeven()
    {
        return getString("seven");
    }
    public void setSeven(String item)
    {
        setString("seven", item);
    }
    /**
     * Object:第1个表体's 8property 
     */
    public String getEight()
    {
        return getString("eight");
    }
    public void setEight(String item)
    {
        setString("eight", item);
    }
    /**
     * Object:第1个表体's 9property 
     */
    public String getNine()
    {
        return getString("nine");
    }
    public void setNine(String item)
    {
        setString("nine", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BA308D50");
    }
}