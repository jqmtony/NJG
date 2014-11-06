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
     * Object:ѹ���ܵ�����'s ���ͽ���property 
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
     * Object:ѹ���ܵ�����'s �ܵ�����property 
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
     * Object:ѹ���ܵ�����'s ��ֹ��property 
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
     * Object:ѹ���ܵ�����'s ��Ƶ�λproperty 
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
     * Object:ѹ���ܵ�����'s ��װ��λproperty 
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
     * Object:ѹ���ܵ�����'s ��װ����property 
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
     * Object:ѹ���ܵ�����'s Ͷ������property 
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
     * Object:ѹ���ܵ�����'s ����ֱ��mmproperty 
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
     * Object:ѹ���ܵ�����'s ���Ʊں�mmproperty 
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
     * Object:ѹ���ܵ�����'s �ܵ�����mproperty 
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
     * Object:ѹ���ܵ�����'s ���ѹ��MPaproperty 
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
     * Object:ѹ���ܵ�����'s ����¶ȡ�property 
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
     * Object:ѹ���ܵ�����'s ����ѹ��MPa property 
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
     * Object:ѹ���ܵ�����'s �����¶ȡ�property 
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
     * Object:ѹ���ܵ�����'s �ܵ�����property 
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
     * Object:ѹ���ܵ�����'s ��������property 
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
     * Object:ѹ���ܵ�����'s ��ʶproperty 
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
     * Object:ѹ���ܵ�����'s �ܵ�����property 
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
     * Object:ѹ���ܵ�����'s ���跽ʽproperty 
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
     * Object:ѹ���ܵ�����'s ������ʽproperty 
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
     * Object:ѹ���ܵ�����'s ���¾���property 
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
     * Object:ѹ���ܵ�����'s ���/����/������λproperty 
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
     * Object:ѹ���ܵ�����'s ע�����property 
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
     * Object:ѹ���ܵ�����'s �´μ�������property 
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
     * Object:ѹ���ܵ�����'s ���鱨����property 
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