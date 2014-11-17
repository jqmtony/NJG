package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquMaintBookInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEquMaintBookInfo()
    {
        this("id");
    }
    protected AbstractEquMaintBookInfo(String pkField)
    {
        super(pkField);
        put("E2", new com.kingdee.eas.port.equipment.maintenance.EquMaintBookE2Collection());
        put("E3", new com.kingdee.eas.port.equipment.maintenance.EquMaintBookE3Collection());
        put("E1", new com.kingdee.eas.port.equipment.maintenance.EquMaintBookE1Collection());
    }
    /**
     * Object: �豸����ά�������� 's �豸��� property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEquNumber()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("equNumber");
    }
    public void setEquNumber(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("equNumber", item);
    }
    /**
     * Object:�豸����ά��������'s �豸����property 
     */
    public String getEquName()
    {
        return getString("equName");
    }
    public void setEquName(String item)
    {
        setString("equName", item);
    }
    /**
     * Object:�豸����ά��������'s ����ͺ�property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object:�豸����ά��������'s ʹ�ò���property 
     */
    public String getUseDepart()
    {
        return getString("useDepart");
    }
    public void setUseDepart(String item)
    {
        setString("useDepart", item);
    }
    /**
     * Object: �豸����ά�������� 's �´��� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getXiadaPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("xiadaPerson");
    }
    public void setXiadaPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("xiadaPerson", item);
    }
    /**
     * Object: �豸����ά�������� 's �´ﲿ�� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getXiadaDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("xiadaDepart");
    }
    public void setXiadaDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("xiadaDepart", item);
    }
    /**
     * Object:�豸����ά��������'s �´�ʱ��property 
     */
    public java.util.Date getXiadaTiem()
    {
        return getDate("xiadaTiem");
    }
    public void setXiadaTiem(java.util.Date item)
    {
        setDate("xiadaTiem", item);
    }
    /**
     * Object: �豸����ά�������� 's ά������ property 
     */
    public com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo getWeixiuType()
    {
        return (com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo)get("weixiuType");
    }
    public void setWeixiuType(com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo item)
    {
        put("weixiuType", item);
    }
    /**
     * Object:�豸����ά��������'s ��ǰ������Ŀproperty 
     */
    public String getBaoqianxiangmu()
    {
        return getString("baoqianxiangmu");
    }
    public void setBaoqianxiangmu(String item)
    {
        setString("baoqianxiangmu", item);
    }
    /**
     * Object:�豸����ά��������'s ����Ҫ���ݺ�Ҫ�󣨻�е�͵�����property 
     */
    public String getMainNeirong()
    {
        return getString("mainNeirong");
    }
    public void setMainNeirong(String item)
    {
        setString("mainNeirong", item);
    }
    /**
     * Object:�豸����ά��������'s ���������¼property 
     */
    public String getGenghuanling()
    {
        return getString("genghuanling");
    }
    public void setGenghuanling(String item)
    {
        setString("genghuanling", item);
    }
    /**
     * Object:�豸����ά��������'s �Լ�Ӳ��property 
     */
    public String getYingdu()
    {
        return getString("yingdu");
    }
    public void setYingdu(String item)
    {
        setString("yingdu", item);
    }
    /**
     * Object:�豸����ά��������'s �Լ�����property 
     */
    public String getShijian()
    {
        return getString("shijian");
    }
    public void setShijian(String item)
    {
        setString("shijian", item);
    }
    /**
     * Object: �豸����ά�������� 's ����ǯ�� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getZhuxiuqiangong()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("zhuxiuqiangong");
    }
    public void setZhuxiuqiangong(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("zhuxiuqiangong", item);
    }
    /**
     * Object: �豸����ά�������� 's ���޵繤 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getZhuxiudiangong()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("zhuxiudiangong");
    }
    public void setZhuxiudiangong(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("zhuxiudiangong", item);
    }
    /**
     * Object: �豸����ά�������� 's ����Ա����ʦ��೤���Σ� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getJianyanyuan()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("jianyanyuan");
    }
    public void setJianyanyuan(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("jianyanyuan", item);
    }
    /**
     * Object: �豸����ά�������� 's ��1������ property 
     */
    public com.kingdee.eas.port.equipment.maintenance.EquMaintBookE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.maintenance.EquMaintBookE1Collection)get("E1");
    }
    /**
     * Object: �豸����ά�������� 's ��2������ property 
     */
    public com.kingdee.eas.port.equipment.maintenance.EquMaintBookE2Collection getE2()
    {
        return (com.kingdee.eas.port.equipment.maintenance.EquMaintBookE2Collection)get("E2");
    }
    /**
     * Object: �豸����ά�������� 's ��3������ property 
     */
    public com.kingdee.eas.port.equipment.maintenance.EquMaintBookE3Collection getE3()
    {
        return (com.kingdee.eas.port.equipment.maintenance.EquMaintBookE3Collection)get("E3");
    }
    /**
     * Object:�豸����ά��������'s �����豸�������property 
     */
    public String getDianqishebei()
    {
        return getString("dianqishebei");
    }
    public void setDianqishebei(String item)
    {
        setString("dianqishebei", item);
    }
    /**
     * Object:�豸����ά��������'s ����װ�ã�Һѹϵͳ���������property 
     */
    public String getChuangdong()
    {
        return getString("chuangdong");
    }
    public void setChuangdong(String item)
    {
        setString("chuangdong", item);
    }
    /**
     * Object:�豸����ά��������'s �󻬡���ȴϵͳ�������property 
     */
    public String getRunhua()
    {
        return getString("runhua");
    }
    public void setRunhua(String item)
    {
        setString("runhua", item);
    }
    /**
     * Object:�豸����ά��������'s ����ʵ��property 
     */
    public String getQiexiao()
    {
        return getString("qiexiao");
    }
    public void setQiexiao(String item)
    {
        setString("qiexiao", item);
    }
    /**
     * Object: �豸����ά�������� 's �೤ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getBanzhang()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("banzhang");
    }
    public void setBanzhang(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("banzhang", item);
    }
    /**
     * Object: �豸����ά�������� 's ���� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getZhuxiuone()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("zhuxiuone");
    }
    public void setZhuxiuone(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("zhuxiuone", item);
    }
    /**
     * Object: �豸����ά�������� 's ����Ա property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getJianyanyuanone()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("jianyanyuanone");
    }
    public void setJianyanyuanone(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("jianyanyuanone", item);
    }
    /**
     * Object:�豸����ά��������'s ά������1property 
     */
    public String getLeixing()
    {
        return getString("leixing");
    }
    public void setLeixing(String item)
    {
        setString("leixing", item);
    }
    /**
     * Object:�豸����ά��������'s ά������2property 
     */
    public String getLeixingone()
    {
        return getString("leixingone");
    }
    public void setLeixingone(String item)
    {
        setString("leixingone", item);
    }
    /**
     * Object:�豸����ά��������'s ʹ�ó������property 
     */
    public String getChejianyijian()
    {
        return getString("chejianyijian");
    }
    public void setChejianyijian(String item)
    {
        setString("chejianyijian", item);
    }
    /**
     * Object: �豸����ά�������� 's �����쵼 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getChejianlingdao()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("chejianlingdao");
    }
    public void setChejianlingdao(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("chejianlingdao", item);
    }
    /**
     * Object: �豸����ά�������� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getJieshouren()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("jieshouren");
    }
    public void setJieshouren(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("jieshouren", item);
    }
    /**
     * Object: �豸����ά�������� 's �ƽ��� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getYijiaoren()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("yijiaoren");
    }
    public void setYijiaoren(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("yijiaoren", item);
    }
    /**
     * Object: �豸����ά�������� 's �ƽ���λ property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getYijiaodanwei()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("yijiaodanwei");
    }
    public void setYijiaodanwei(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("yijiaodanwei", item);
    }
    /**
     * Object:�豸����ά��������'s ����ʱ��property 
     */
    public java.util.Date getJiaojieTime()
    {
        return getDate("jiaojieTime");
    }
    public void setJiaojieTime(java.util.Date item)
    {
        setDate("jiaojieTime", item);
    }
    /**
     * Object:�豸����ά��������'s ʵ�ʿ�������property 
     */
    public java.util.Date getAcStartTime()
    {
        return getDate("acStartTime");
    }
    public void setAcStartTime(java.util.Date item)
    {
        setDate("acStartTime", item);
    }
    /**
     * Object:�豸����ά��������'s ʵ���깤����property 
     */
    public java.util.Date getAcEndTime()
    {
        return getDate("acEndTime");
    }
    public void setAcEndTime(java.util.Date item)
    {
        setDate("acEndTime", item);
    }
    /**
     * Object:�豸����ά��������'s �ƻ���������property 
     */
    public java.util.Date getPlanStartTime()
    {
        return getDate("planStartTime");
    }
    public void setPlanStartTime(java.util.Date item)
    {
        setDate("planStartTime", item);
    }
    /**
     * Object:�豸����ά��������'s �ƻ��깤����property 
     */
    public java.util.Date getPlanEndTime()
    {
        return getDate("planEndTime");
    }
    public void setPlanEndTime(java.util.Date item)
    {
        setDate("planEndTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("14FFF66B");
    }
}