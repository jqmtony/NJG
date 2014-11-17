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
     * Object: 设备二级维保任务书 's 设备编号 property 
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
     * Object:设备二级维保任务书's 设备名称property 
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
     * Object:设备二级维保任务书's 规格型号property 
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
     * Object:设备二级维保任务书's 使用部门property 
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
     * Object: 设备二级维保任务书 's 下达人 property 
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
     * Object: 设备二级维保任务书 's 下达部门 property 
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
     * Object:设备二级维保任务书's 下达时间property 
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
     * Object: 设备二级维保任务书 's 维修类型 property 
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
     * Object:设备二级维保任务书's 保前存在项目property 
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
     * Object:设备二级维保任务书's 保主要内容和要求（机械和电气）property 
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
     * Object:设备二级维保任务书's 零件更换记录property 
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
     * Object:设备二级维保任务书's 试件硬度property 
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
     * Object:设备二级维保任务书's 试件材料property 
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
     * Object: 设备二级维保任务书 's 主修钳工 property 
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
     * Object: 设备二级维保任务书 's 主修电工 property 
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
     * Object: 设备二级维保任务书 's 检验员（技师或班长担任） property 
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
     * Object: 设备二级维保任务书 's 第1个表体 property 
     */
    public com.kingdee.eas.port.equipment.maintenance.EquMaintBookE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.maintenance.EquMaintBookE1Collection)get("E1");
    }
    /**
     * Object: 设备二级维保任务书 's 第2个表体 property 
     */
    public com.kingdee.eas.port.equipment.maintenance.EquMaintBookE2Collection getE2()
    {
        return (com.kingdee.eas.port.equipment.maintenance.EquMaintBookE2Collection)get("E2");
    }
    /**
     * Object: 设备二级维保任务书 's 第3个表体 property 
     */
    public com.kingdee.eas.port.equipment.maintenance.EquMaintBookE3Collection getE3()
    {
        return (com.kingdee.eas.port.equipment.maintenance.EquMaintBookE3Collection)get("E3");
    }
    /**
     * Object:设备二级维保任务书's 电器设备工作情况property 
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
     * Object:设备二级维保任务书's 传动装置（液压系统）操作情况property 
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
     * Object:设备二级维保任务书's 润滑、冷却系统工作情况property 
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
     * Object:设备二级维保任务书's 切削实验property 
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
     * Object: 设备二级维保任务书 's 班长 property 
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
     * Object: 设备二级维保任务书 's 主修 property 
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
     * Object: 设备二级维保任务书 's 检验员 property 
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
     * Object:设备二级维保任务书's 维保类型1property 
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
     * Object:设备二级维保任务书's 维修类型2property 
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
     * Object:设备二级维保任务书's 使用车间意见property 
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
     * Object: 设备二级维保任务书 's 车间领导 property 
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
     * Object: 设备二级维保任务书 's 接受人 property 
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
     * Object: 设备二级维保任务书 's 移交人 property 
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
     * Object: 设备二级维保任务书 's 移交单位 property 
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
     * Object:设备二级维保任务书's 交接时间property 
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
     * Object:设备二级维保任务书's 实际开工日期property 
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
     * Object:设备二级维保任务书's 实际完工日期property 
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
     * Object:设备二级维保任务书's 计划开工日期property 
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
     * Object:设备二级维保任务书's 计划完工日期property 
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