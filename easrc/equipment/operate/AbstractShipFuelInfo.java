package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShipFuelInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractShipFuelInfo()
    {
        this("id");
    }
    protected AbstractShipFuelInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ȼ���������±��� 's ���� property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getShipName()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("shipName");
    }
    public void setShipName(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("shipName", item);
    }
    /**
     * Object:����ȼ���������±���'s ����property 
     */
    public String getPower()
    {
        return getString("Power");
    }
    public void setPower(String item)
    {
        setString("Power", item);
    }
    /**
     * Object:����ȼ���������±���'s ���½��property 
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
     * Object:����ȼ���������±���'s ��property 
     */
    public String getQione()
    {
        return getString("qione");
    }
    public void setQione(String item)
    {
        setString("qione", item);
    }
    /**
     * Object:����ȼ���������±���'s ��property 
     */
    public String getQitwo()
    {
        return getString("qitwo");
    }
    public void setQitwo(String item)
    {
        setString("qitwo", item);
    }
    /**
     * Object:����ȼ���������±���'s ֹproperty 
     */
    public String getZhione()
    {
        return getString("zhione");
    }
    public void setZhione(String item)
    {
        setString("zhione", item);
    }
    /**
     * Object:����ȼ���������±���'s ֹproperty 
     */
    public String getZhitwo()
    {
        return getString("zhitwo");
    }
    public void setZhitwo(String item)
    {
        setString("zhitwo", item);
    }
    /**
     * Object:����ȼ���������±���'s �ܶ�property 
     */
    public String getMiduone()
    {
        return getString("miduone");
    }
    public void setMiduone(String item)
    {
        setString("miduone", item);
    }
    /**
     * Object:����ȼ���������±���'s �ܶ�property 
     */
    public String getMidutwo()
    {
        return getString("midutwo");
    }
    public void setMidutwo(String item)
    {
        setString("midutwo", item);
    }
    /**
     * Object:����ȼ���������±���'s ����ϼ�property 
     */
    public String getIntoTotal()
    {
        return getString("intoTotal");
    }
    public void setIntoTotal(String item)
    {
        setString("intoTotal", item);
    }
    /**
     * Object:����ȼ���������±���'s ��property 
     */
    public String getQithree()
    {
        return getString("qithree");
    }
    public void setQithree(String item)
    {
        setString("qithree", item);
    }
    /**
     * Object:����ȼ���������±���'s ��property 
     */
    public String getQifour()
    {
        return getString("qifour");
    }
    public void setQifour(String item)
    {
        setString("qifour", item);
    }
    /**
     * Object:����ȼ���������±���'s ֹproperty 
     */
    public String getZhithree()
    {
        return getString("zhithree");
    }
    public void setZhithree(String item)
    {
        setString("zhithree", item);
    }
    /**
     * Object:����ȼ���������±���'s ֹproperty 
     */
    public String getZhifour()
    {
        return getString("zhifour");
    }
    public void setZhifour(String item)
    {
        setString("zhifour", item);
    }
    /**
     * Object:����ȼ���������±���'s �ܶ�property 
     */
    public String getMiduthree()
    {
        return getString("miduthree");
    }
    public void setMiduthree(String item)
    {
        setString("miduthree", item);
    }
    /**
     * Object:����ȼ���������±���'s �ܶ�property 
     */
    public String getMidufour()
    {
        return getString("midufour");
    }
    public void setMidufour(String item)
    {
        setString("midufour", item);
    }
    /**
     * Object:����ȼ���������±���'s ���ĺϼ�property 
     */
    public String getTotalConsum()
    {
        return getString("totalConsum");
    }
    public void setTotalConsum(String item)
    {
        setString("totalConsum", item);
    }
    /**
     * Object:����ȼ���������±���'s ���½��property 
     */
    public String getMonthBalance()
    {
        return getString("monthBalance");
    }
    public void setMonthBalance(String item)
    {
        setString("monthBalance", item);
    }
    /**
     * Object:����ȼ���������±���'s ������ʱproperty 
     */
    public String getPortShipment()
    {
        return getString("portShipment");
    }
    public void setPortShipment(String item)
    {
        setString("portShipment", item);
    }
    /**
     * Object:����ȼ���������±���'s С��ת��ʱproperty 
     */
    public String getSmallTransport()
    {
        return getString("smallTransport");
    }
    public void setSmallTransport(String item)
    {
        setString("smallTransport", item);
    }
    /**
     * Object:����ȼ���������±���'s �ϼ�property 
     */
    public String getTotal()
    {
        return getString("Total");
    }
    public void setTotal(String item)
    {
        setString("Total", item);
    }
    /**
     * Object:����ȼ���������±���'s ��ֵproperty 
     */
    public String getOutputValue()
    {
        return getString("outputValue");
    }
    public void setOutputValue(String item)
    {
        setString("outputValue", item);
    }
    /**
     * Object:����ȼ���������±���'s ��������property 
     */
    public String getGzde()
    {
        return getString("gzde");
    }
    public void setGzde(String item)
    {
        setString("gzde", item);
    }
    /**
     * Object:����ȼ���������±���'s ����������property 
     */
    public String getGzdel()
    {
        return getString("gzdel");
    }
    public void setGzdel(String item)
    {
        setString("gzdel", item);
    }
    /**
     * Object:����ȼ���������±���'s С��ת����property 
     */
    public String getXyzde()
    {
        return getString("xyzde");
    }
    public void setXyzde(String item)
    {
        setString("xyzde", item);
    }
    /**
     * Object:����ȼ���������±���'s С��ת������property 
     */
    public String getXyzdel()
    {
        return getString("xyzdel");
    }
    public void setXyzdel(String item)
    {
        setString("xyzdel", item);
    }
    /**
     * Object:����ȼ���������±���'s �ϼƶ���property 
     */
    public String getHjde()
    {
        return getString("hjde");
    }
    public void setHjde(String item)
    {
        setString("hjde", item);
    }
    /**
     * Object:����ȼ���������±���'s �ϼƶ�����property 
     */
    public String getHjdel()
    {
        return getString("hjdel");
    }
    public void setHjdel(String item)
    {
        setString("hjdel", item);
    }
    /**
     * Object:����ȼ���������±���'s ��ֵ1property 
     */
    public String getChanzhione()
    {
        return getString("chanzhione");
    }
    public void setChanzhione(String item)
    {
        setString("chanzhione", item);
    }
    /**
     * Object:����ȼ���������±���'s ��ֵ2property 
     */
    public String getChanzhitwo()
    {
        return getString("chanzhitwo");
    }
    public void setChanzhitwo(String item)
    {
        setString("chanzhitwo", item);
    }
    /**
     * Object:����ȼ���������±���'s �ۺ϶�����property 
     */
    public String getZhdel()
    {
        return getString("zhdel");
    }
    public void setZhdel(String item)
    {
        setString("zhdel", item);
    }
    /**
     * Object:����ȼ���������±���'s ʵ����property 
     */
    public String getShiyongliang()
    {
        return getString("shiyongliang");
    }
    public void setShiyongliang(String item)
    {
        setString("shiyongliang", item);
    }
    /**
     * Object:����ȼ���������±���'s ����property 
     */
    public String getJieyou()
    {
        return getString("jieyou");
    }
    public void setJieyou(String item)
    {
        setString("jieyou", item);
    }
    /**
     * Object:����ȼ���������±���'s ����property 
     */
    public String getChaohao()
    {
        return getString("chaohao");
    }
    public void setChaohao(String item)
    {
        setString("chaohao", item);
    }
    /**
     * Object:����ȼ���������±���'s ���¼�����������������property 
     */
    public String getSelfLeve()
    {
        return getString("selfLeve");
    }
    public void setSelfLeve(String item)
    {
        setString("selfLeve", item);
    }
    /**
     * Object:����ȼ���������±���'s ��עproperty 
     */
    public String getNote()
    {
        return getString("note");
    }
    public void setNote(String item)
    {
        setString("note", item);
    }
    /**
     * Object:����ȼ���������±���'s �ۼ��õ������ȣ�property 
     */
    public String getLeijiyongdian()
    {
        return getString("leijiyongdian");
    }
    public void setLeijiyongdian(String item)
    {
        setString("leijiyongdian", item);
    }
    /**
     * Object:����ȼ���������±���'s �ۼ�ȼ�ͺ�����t��property 
     */
    public String getLeijiranyou()
    {
        return getString("leijiranyou");
    }
    public void setLeijiranyou(String item)
    {
        setString("leijiranyou", item);
    }
    /**
     * Object:����ȼ���������±���'s �ۼ���ʱ��h��property 
     */
    public String getLeijiyunshi()
    {
        return getString("leijiyunshi");
    }
    public void setLeijiyunshi(String item)
    {
        setString("leijiyunshi", item);
    }
    /**
     * Object:����ȼ���������±���'s �ۼƲ�ֵ����Ԫ��property 
     */
    public String getLeijichanzhi()
    {
        return getString("leijichanzhi");
    }
    public void setLeijichanzhi(String item)
    {
        setString("leijichanzhi", item);
    }
    /**
     * Object:����ȼ���������±���'s �������½��property 
     */
    public String getRunhuayoujiecun()
    {
        return getString("runhuayoujiecun");
    }
    public void setRunhuayoujiecun(String item)
    {
        setString("runhuayoujiecun", item);
    }
    /**
     * Object:����ȼ���������±���'s ���͸�������property 
     */
    public String getRunhuayougangzuo()
    {
        return getString("runhuayougangzuo");
    }
    public void setRunhuayougangzuo(String item)
    {
        setString("runhuayougangzuo", item);
    }
    /**
     * Object:����ȼ���������±���'s ����С��ת����property 
     */
    public String getRunhuayouxiao()
    {
        return getString("runhuayouxiao");
    }
    public void setRunhuayouxiao(String item)
    {
        setString("runhuayouxiao", item);
    }
    /**
     * Object:����ȼ���������±���'s ���ͱ��½��property 
     */
    public String getRunhuayouben()
    {
        return getString("runhuayouben");
    }
    public void setRunhuayouben(String item)
    {
        setString("runhuayouben", item);
    }
    /**
     * Object:����ȼ���������±���'s ���������½��property 
     */
    public String getChilunyoushang()
    {
        return getString("chilunyoushang");
    }
    public void setChilunyoushang(String item)
    {
        setString("chilunyoushang", item);
    }
    /**
     * Object:����ȼ���������±���'s �����͸�������property 
     */
    public String getChilunyougang()
    {
        return getString("chilunyougang");
    }
    public void setChilunyougang(String item)
    {
        setString("chilunyougang", item);
    }
    /**
     * Object:����ȼ���������±���'s ������С��ת����property 
     */
    public String getChilunyouxiao()
    {
        return getString("chilunyouxiao");
    }
    public void setChilunyouxiao(String item)
    {
        setString("chilunyouxiao", item);
    }
    /**
     * Object:����ȼ���������±���'s �����ͱ��½��property 
     */
    public String getChilunyouben()
    {
        return getString("chilunyouben");
    }
    public void setChilunyouben(String item)
    {
        setString("chilunyouben", item);
    }
    /**
     * Object:����ȼ���������±���'s Һѹ�����½��property 
     */
    public String getYeyayoushang()
    {
        return getString("yeyayoushang");
    }
    public void setYeyayoushang(String item)
    {
        setString("yeyayoushang", item);
    }
    /**
     * Object:����ȼ���������±���'s Һѹ�͸�������property 
     */
    public String getYeyayougang()
    {
        return getString("yeyayougang");
    }
    public void setYeyayougang(String item)
    {
        setString("yeyayougang", item);
    }
    /**
     * Object:����ȼ���������±���'s Һѹ��С��ת����property 
     */
    public String getYeyayouxiao()
    {
        return getString("yeyayouxiao");
    }
    public void setYeyayouxiao(String item)
    {
        setString("yeyayouxiao", item);
    }
    /**
     * Object:����ȼ���������±���'s Һѹ�ͱ��½��property 
     */
    public String getYeyayouben()
    {
        return getString("yeyayouben");
    }
    public void setYeyayouben(String item)
    {
        setString("yeyayouben", item);
    }
    /**
     * Object:����ȼ���������±���'s ���ȼ��property 
     */
    public String getEdingrunranbi()
    {
        return getString("edingrunranbi");
    }
    public void setEdingrunranbi(String item)
    {
        setString("edingrunranbi", item);
    }
    /**
     * Object:����ȼ���������±���'s ʵ����ȼ��property 
     */
    public String getShijirunranbi()
    {
        return getString("shijirunranbi");
    }
    public void setShijirunranbi(String item)
    {
        setString("shijirunranbi", item);
    }
    /**
     * Object:����ȼ���������±���'s ������property 
     */
    public String getDingeliangxx()
    {
        return getString("dingeliangxx");
    }
    public void setDingeliangxx(String item)
    {
        setString("dingeliangxx", item);
    }
    /**
     * Object:����ȼ���������±���'s ʵ����property 
     */
    public String getShiyongliangone()
    {
        return getString("shiyongliangone");
    }
    public void setShiyongliangone(String item)
    {
        setString("shiyongliangone", item);
    }
    /**
     * Object:����ȼ���������±���'s ��property 
     */
    public String getJieone()
    {
        return getString("jieone");
    }
    public void setJieone(String item)
    {
        setString("jieone", item);
    }
    /**
     * Object:����ȼ���������±���'s ��property 
     */
    public String getChaoone()
    {
        return getString("chaoone");
    }
    public void setChaoone(String item)
    {
        setString("chaoone", item);
    }
    /**
     * Object:����ȼ���������±���'s ��������property 
     */
    public String getJishudinge()
    {
        return getString("jishudinge");
    }
    public void setJishudinge(String item)
    {
        setString("jishudinge", item);
    }
    /**
     * Object:����ȼ���������±���'s ��ֵ����property 
     */
    public String getChanzhidinge()
    {
        return getString("chanzhidinge");
    }
    public void setChanzhidinge(String item)
    {
        setString("chanzhidinge", item);
    }
    /**
     * Object:����ȼ���������±���'s ��������property 
     */
    public String getJishubenyue()
    {
        return getString("jishubenyue");
    }
    public void setJishubenyue(String item)
    {
        setString("jishubenyue", item);
    }
    /**
     * Object:����ȼ���������±���'s ��ֵ����property 
     */
    public String getChanzhibenyue()
    {
        return getString("chanzhibenyue");
    }
    public void setChanzhibenyue(String item)
    {
        setString("chanzhibenyue", item);
    }
    /**
     * Object:����ȼ���������±���'s �����ۼ�property 
     */
    public String getJishuleiji()
    {
        return getString("jishuleiji");
    }
    public void setJishuleiji(String item)
    {
        setString("jishuleiji", item);
    }
    /**
     * Object:����ȼ���������±���'s ��ֵ�ۼ�property 
     */
    public String getChanzhileiji()
    {
        return getString("chanzhileiji");
    }
    public void setChanzhileiji(String item)
    {
        setString("chanzhileiji", item);
    }
    /**
     * Object:����ȼ���������±���'s �������property 
     */
    public String getZuobenyue()
    {
        return getString("zuobenyue");
    }
    public void setZuobenyue(String item)
    {
        setString("zuobenyue", item);
    }
    /**
     * Object:����ȼ���������±���'s �һ�����property 
     */
    public String getYoujibenyue()
    {
        return getString("youjibenyue");
    }
    public void setYoujibenyue(String item)
    {
        setString("youjibenyue", item);
    }
    /**
     * Object:����ȼ���������±���'s ��������property 
     */
    public String getFujibenyue()
    {
        return getString("fujibenyue");
    }
    public void setFujibenyue(String item)
    {
        setString("fujibenyue", item);
    }
    /**
     * Object:����ȼ���������±���'s �����property 
     */
    public String getDianbiaobenyue()
    {
        return getString("dianbiaobenyue");
    }
    public void setDianbiaobenyue(String item)
    {
        setString("dianbiaobenyue", item);
    }
    /**
     * Object:����ȼ���������±���'s �������property 
     */
    public String getZuoshangyue()
    {
        return getString("zuoshangyue");
    }
    public void setZuoshangyue(String item)
    {
        setString("zuoshangyue", item);
    }
    /**
     * Object:����ȼ���������±���'s ����ϼ�property 
     */
    public String getZuoheji()
    {
        return getString("zuoheji");
    }
    public void setZuoheji(String item)
    {
        setString("zuoheji", item);
    }
    /**
     * Object:����ȼ���������±���'s �һ�����property 
     */
    public String getYoujishangyue()
    {
        return getString("youjishangyue");
    }
    public void setYoujishangyue(String item)
    {
        setString("youjishangyue", item);
    }
    /**
     * Object:����ȼ���������±���'s �һ��ϼ�property 
     */
    public String getYoujiheji()
    {
        return getString("youjiheji");
    }
    public void setYoujiheji(String item)
    {
        setString("youjiheji", item);
    }
    /**
     * Object:����ȼ���������±���'s ��������property 
     */
    public String getFujishangyue()
    {
        return getString("fujishangyue");
    }
    public void setFujishangyue(String item)
    {
        setString("fujishangyue", item);
    }
    /**
     * Object:����ȼ���������±���'s �����ϼ�property 
     */
    public String getFujiheji()
    {
        return getString("fujiheji");
    }
    public void setFujiheji(String item)
    {
        setString("fujiheji", item);
    }
    /**
     * Object:����ȼ���������±���'s �������property 
     */
    public String getDianbiaoshangyue()
    {
        return getString("dianbiaoshangyue");
    }
    public void setDianbiaoshangyue(String item)
    {
        setString("dianbiaoshangyue", item);
    }
    /**
     * Object:����ȼ���������±���'s ���ϼ�property 
     */
    public String getDianbiaoheji()
    {
        return getString("dianbiaoheji");
    }
    public void setDianbiaoheji(String item)
    {
        setString("dianbiaoheji", item);
    }
    /**
     * Object: ����ȼ���������±��� 's ���� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getChuanzhang()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("chuanzhang");
    }
    public void setChuanzhang(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("chuanzhang", item);
    }
    /**
     * Object: ����ȼ���������±��� 's �ֻ��� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getLunjizhang()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("lunjizhang");
    }
    public void setLunjizhang(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("lunjizhang", item);
    }
    /**
     * Object:����ȼ���������±���'s ������������property 
     */
    public String getLingrurhy()
    {
        return getString("lingrurhy");
    }
    public void setLingrurhy(String item)
    {
        setString("lingrurhy", item);
    }
    /**
     * Object:����ȼ���������±���'s �������������property 
     */
    public String getLingrucly()
    {
        return getString("lingrucly");
    }
    public void setLingrucly(String item)
    {
        setString("lingrucly", item);
    }
    /**
     * Object:����ȼ���������±���'s ��������Һѹ��property 
     */
    public String getLingruyyy()
    {
        return getString("lingruyyy");
    }
    public void setLingruyyy(String item)
    {
        setString("lingruyyy", item);
    }
    /**
     * Object: ����ȼ���������±��� 's �����·� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getReportMonth()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("reportMonth");
    }
    public void setReportMonth(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("reportMonth", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D5C68991");
    }
}