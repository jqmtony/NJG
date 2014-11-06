package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPressurePipingInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractPressurePipingInfo()
    {
        this("id");
    }
    protected AbstractPressurePipingInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:压力管道档案's 输送介质property 
     */
    public String getMedium()
    {
        return getString("medium");
    }
    public void setMedium(String item)
    {
        setString("medium", item);
    }
    /**
     * Object:压力管道档案's 管道根数property 
     */
    public java.math.BigDecimal getPipelineNo()
    {
        return getBigDecimal("pipelineNo");
    }
    public void setPipelineNo(java.math.BigDecimal item)
    {
        setBigDecimal("pipelineNo", item);
    }
    /**
     * Object:压力管道档案's 起止点property 
     */
    public String getLoadPoint()
    {
        return getString("loadPoint");
    }
    public void setLoadPoint(String item)
    {
        setString("loadPoint", item);
    }
    /**
     * Object:压力管道档案's 设计单位property 
     */
    public String getDesignUnits()
    {
        return getString("designUnits");
    }
    public void setDesignUnits(String item)
    {
        setString("designUnits", item);
    }
    /**
     * Object:压力管道档案's 安装单位property 
     */
    public String getInstalliUnit()
    {
        return getString("installiUnit");
    }
    public void setInstalliUnit(String item)
    {
        setString("installiUnit", item);
    }
    /**
     * Object:压力管道档案's 安装日期property 
     */
    public java.util.Date getInsterDate()
    {
        return getDate("insterDate");
    }
    public void setInsterDate(java.util.Date item)
    {
        setDate("insterDate", item);
    }
    /**
     * Object:压力管道档案's 投用日期property 
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
     * Object:压力管道档案's 公称直径mmproperty 
     */
    public java.math.BigDecimal getGongcheng()
    {
        return getBigDecimal("gongcheng");
    }
    public void setGongcheng(java.math.BigDecimal item)
    {
        setBigDecimal("gongcheng", item);
    }
    /**
     * Object:压力管道档案's 公称壁厚mmproperty 
     */
    public java.math.BigDecimal getBihou()
    {
        return getBigDecimal("bihou");
    }
    public void setBihou(java.math.BigDecimal item)
    {
        setBigDecimal("bihou", item);
    }
    /**
     * Object:压力管道档案's 管道长度mproperty 
     */
    public java.math.BigDecimal getGuandaoLenth()
    {
        return getBigDecimal("guandaoLenth");
    }
    public void setGuandaoLenth(java.math.BigDecimal item)
    {
        setBigDecimal("guandaoLenth", item);
    }
    /**
     * Object:压力管道档案's 设计压力MPaproperty 
     */
    public java.math.BigDecimal getDesign()
    {
        return getBigDecimal("design");
    }
    public void setDesign(java.math.BigDecimal item)
    {
        setBigDecimal("design", item);
    }
    /**
     * Object:压力管道档案's 设计温度℃property 
     */
    public java.math.BigDecimal getShejiwendu()
    {
        return getBigDecimal("shejiwendu");
    }
    public void setShejiwendu(java.math.BigDecimal item)
    {
        setBigDecimal("shejiwendu", item);
    }
    /**
     * Object:压力管道档案's 工作压力MPa property 
     */
    public java.math.BigDecimal getWorkYali()
    {
        return getBigDecimal("workYali");
    }
    public void setWorkYali(java.math.BigDecimal item)
    {
        setBigDecimal("workYali", item);
    }
    /**
     * Object:压力管道档案's 工作温度℃property 
     */
    public java.math.BigDecimal getWorkWendu()
    {
        return getBigDecimal("workWendu");
    }
    public void setWorkWendu(java.math.BigDecimal item)
    {
        setBigDecimal("workWendu", item);
    }
    /**
     * Object:压力管道档案's 管道材质property 
     */
    public String getGuandaocaizhi()
    {
        return getString("guandaocaizhi");
    }
    public void setGuandaocaizhi(String item)
    {
        setString("guandaocaizhi", item);
    }
    /**
     * Object:压力管道档案's 焊口数量property 
     */
    public java.math.BigDecimal getHankoushuliang()
    {
        return getBigDecimal("hankoushuliang");
    }
    public void setHankoushuliang(java.math.BigDecimal item)
    {
        setBigDecimal("hankoushuliang", item);
    }
    /**
     * Object:压力管道档案's 标识property 
     */
    public String getBiaoshi()
    {
        return getString("biaoshi");
    }
    public void setBiaoshi(String item)
    {
        setString("biaoshi", item);
    }
    /**
     * Object:压力管道档案's 管道级别property 
     */
    public String getGuandaojibie()
    {
        return getString("guandaojibie");
    }
    public void setGuandaojibie(String item)
    {
        setString("guandaojibie", item);
    }
    /**
     * Object:压力管道档案's 铺设方式property 
     */
    public String getPushefangshi()
    {
        return getString("pushefangshi");
    }
    public void setPushefangshi(String item)
    {
        setString("pushefangshi", item);
    }
    /**
     * Object:压力管道档案's 防腐方式property 
     */
    public String getFangfufangshi()
    {
        return getString("fangfufangshi");
    }
    public void setFangfufangshi(String item)
    {
        setString("fangfufangshi", item);
    }
    /**
     * Object:压力管道档案's 保温绝热property 
     */
    public String getBaowenjuere()
    {
        return getString("baowenjuere");
    }
    public void setBaowenjuere(String item)
    {
        setString("baowenjuere", item);
    }
    /**
     * Object:压力管道档案's 监检/检验/评定单位property 
     */
    public String getPingdingUnit()
    {
        return getString("pingdingUnit");
    }
    public void setPingdingUnit(String item)
    {
        setString("pingdingUnit", item);
    }
    /**
     * Object:压力管道档案's 注册代码property 
     */
    public String getZhucedaima()
    {
        return getString("zhucedaima");
    }
    public void setZhucedaima(String item)
    {
        setString("zhucedaima", item);
    }
    /**
     * Object:压力管道档案's 下次检验日期property 
     */
    public java.util.Date getNextDate()
    {
        return getDate("nextDate");
    }
    public void setNextDate(java.util.Date item)
    {
        setDate("nextDate", item);
    }
    /**
     * Object:压力管道档案's 检验报告编号property 
     */
    public String getJianyanbaogao()
    {
        return getString("jianyanbaogao");
    }
    public void setJianyanbaogao(String item)
    {
        setString("jianyanbaogao", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("59EDF8D2");
    }
}