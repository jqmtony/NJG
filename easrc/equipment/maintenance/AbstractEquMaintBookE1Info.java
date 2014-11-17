package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquMaintBookE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEquMaintBookE1Info()
    {
        this("id");
    }
    protected AbstractEquMaintBookE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第1个表体 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.EquMaintBookInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.EquMaintBookInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.EquMaintBookInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:第1个表体's 项目property 
     */
    public String getXiangmuthree()
    {
        return getString("xiangmuthree");
    }
    public void setXiangmuthree(String item)
    {
        setString("xiangmuthree", item);
    }
    /**
     * Object:第1个表体's 标准property 
     */
    public String getBiaozhun()
    {
        return getString("biaozhun");
    }
    public void setBiaozhun(String item)
    {
        setString("biaozhun", item);
    }
    /**
     * Object:第1个表体's 实际property 
     */
    public String getShiji()
    {
        return getString("shiji");
    }
    public void setShiji(String item)
    {
        setString("shiji", item);
    }
    /**
     * Object:第1个表体's 结论property 
     */
    public String getJielunthree()
    {
        return getString("jielunthree");
    }
    public void setJielunthree(String item)
    {
        setString("jielunthree", item);
    }
    /**
     * Object:第1个表体's 备注property 
     */
    public String getBeizhuone()
    {
        return getString("beizhuone");
    }
    public void setBeizhuone(String item)
    {
        setString("beizhuone", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D4DC1037");
    }
}