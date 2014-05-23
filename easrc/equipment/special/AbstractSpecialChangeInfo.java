package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSpecialChangeInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractSpecialChangeInfo()
    {
        this("id");
    }
    protected AbstractSpecialChangeInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.equipment.special.SpecialChangeEntryCollection());
    }
    /**
     * Object: 设备变更登记表 's 原使用单位名称 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getOldUseUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("oldUseUnit");
    }
    public void setOldUseUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("oldUseUnit", item);
    }
    /**
     * Object: 设备变更登记表 's 新使用单位名称 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getNewUseUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("newUseUnit");
    }
    public void setNewUseUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("newUseUnit", item);
    }
    /**
     * Object:设备变更登记表's 原单位组织机构代码property 
     */
    public String getOldUnitCode()
    {
        return getString("oldUnitCode");
    }
    public void setOldUnitCode(String item)
    {
        setString("oldUnitCode", item);
    }
    /**
     * Object:设备变更登记表's 新单位组织机构代码property 
     */
    public String getNewUnitCode()
    {
        return getString("newUnitCode");
    }
    public void setNewUnitCode(String item)
    {
        setString("newUnitCode", item);
    }
    /**
     * Object: 设备变更登记表 's 经办人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getHander()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("Hander");
    }
    public void setHander(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("Hander", item);
    }
    /**
     * Object:设备变更登记表's 联系电话property 
     */
    public String getTel()
    {
        return getString("tel");
    }
    public void setTel(String item)
    {
        setString("tel", item);
    }
    /**
     * Object:设备变更登记表's 锅炉property 
     */
    public boolean isWr()
    {
        return getBoolean("wr");
    }
    public void setWr(boolean item)
    {
        setBoolean("wr", item);
    }
    /**
     * Object:设备变更登记表's 压力容器property 
     */
    public boolean isYlrq()
    {
        return getBoolean("ylrq");
    }
    public void setYlrq(boolean item)
    {
        setBoolean("ylrq", item);
    }
    /**
     * Object:设备变更登记表's 压力管道property 
     */
    public boolean isYlgd()
    {
        return getBoolean("ylgd");
    }
    public void setYlgd(boolean item)
    {
        setBoolean("ylgd", item);
    }
    /**
     * Object:设备变更登记表's 电梯property 
     */
    public boolean isDt()
    {
        return getBoolean("dt");
    }
    public void setDt(boolean item)
    {
        setBoolean("dt", item);
    }
    /**
     * Object:设备变更登记表's 起重机property 
     */
    public boolean isQzj()
    {
        return getBoolean("qzj");
    }
    public void setQzj(boolean item)
    {
        setBoolean("qzj", item);
    }
    /**
     * Object:设备变更登记表's 厂内车辆property 
     */
    public boolean isCnqc()
    {
        return getBoolean("cnqc");
    }
    public void setCnqc(boolean item)
    {
        setBoolean("cnqc", item);
    }
    /**
     * Object:设备变更登记表's 游乐设施property 
     */
    public boolean isYlss()
    {
        return getBoolean("ylss");
    }
    public void setYlss(boolean item)
    {
        setBoolean("ylss", item);
    }
    /**
     * Object:设备变更登记表's 停用property 
     */
    public boolean isTy()
    {
        return getBoolean("ty");
    }
    public void setTy(boolean item)
    {
        setBoolean("ty", item);
    }
    /**
     * Object:设备变更登记表's 注销property 
     */
    public boolean isZx()
    {
        return getBoolean("zx");
    }
    public void setZx(boolean item)
    {
        setBoolean("zx", item);
    }
    /**
     * Object:设备变更登记表's 过户property 
     */
    public boolean isGh()
    {
        return getBoolean("gh");
    }
    public void setGh(boolean item)
    {
        setBoolean("gh", item);
    }
    /**
     * Object:设备变更登记表's 迁移property 
     */
    public boolean isQy()
    {
        return getBoolean("qy");
    }
    public void setQy(boolean item)
    {
        setBoolean("qy", item);
    }
    /**
     * Object:设备变更登记表's 改造property 
     */
    public boolean isGz()
    {
        return getBoolean("gz");
    }
    public void setGz(boolean item)
    {
        setBoolean("gz", item);
    }
    /**
     * Object:设备变更登记表's 拆卸单位property 
     */
    public String getCxdw()
    {
        return getString("cxdw");
    }
    public void setCxdw(String item)
    {
        setString("cxdw", item);
    }
    /**
     * Object:设备变更登记表's 数量（台）property 
     */
    public java.math.BigDecimal getSl()
    {
        return getBigDecimal("sl");
    }
    public void setSl(java.math.BigDecimal item)
    {
        setBigDecimal("sl", item);
    }
    /**
     * Object: 设备变更登记表 's 变更明细 property 
     */
    public com.kingdee.eas.port.equipment.special.SpecialChangeEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.equipment.special.SpecialChangeEntryCollection)get("Entry");
    }
    /**
     * Object:设备变更登记表's 情况说明property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object:设备变更登记表's 监察机构意见property 
     */
    public String getBIMUDF0050()
    {
        return getString("BIMUDF0050");
    }
    public void setBIMUDF0050(String item)
    {
        setString("BIMUDF0050", item);
    }
    /**
     * Object:设备变更登记表's 经办人property 
     */
    public String getJbr()
    {
        return getString("jbr");
    }
    public void setJbr(String item)
    {
        setString("jbr", item);
    }
    /**
     * Object:设备变更登记表's 经办日期property 
     */
    public java.util.Date getJbrq()
    {
        return getDate("jbrq");
    }
    public void setJbrq(java.util.Date item)
    {
        setDate("jbrq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("73EE059F");
    }
}