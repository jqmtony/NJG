package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquIdInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEquIdInfo()
    {
        this("id");
    }
    protected AbstractEquIdInfo(String pkField)
    {
        super(pkField);
        put("TechnologyPar", new com.kingdee.eas.port.equipment.record.EquIdTechnologyParCollection());
        put("E3", new com.kingdee.eas.port.equipment.record.EquIdE3Collection());
        put("SpareInfo", new com.kingdee.eas.port.equipment.record.EquIdSpareInfoCollection());
    }
    /**
     * Object:设备档案's 设备名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object: 设备档案 's 所属组织 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getSsOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("ssOrgUnit");
    }
    public void setSsOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("ssOrgUnit", item);
    }
    /**
     * Object: 设备档案 's 计划组织 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getJhOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("jhOrgUnit");
    }
    public void setJhOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("jhOrgUnit", item);
    }
    /**
     * Object: 设备档案 's 维修组织 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getWxOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("wxOrgUnit");
    }
    public void setWxOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("wxOrgUnit", item);
    }
    /**
     * Object:设备档案's 规格型号property 
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
     * Object:设备档案's 主要技术参数property 
     */
    public String getSize()
    {
        return getString("size");
    }
    public void setSize(String item)
    {
        setString("size", item);
    }
    /**
     * Object:设备档案's 重量property 
     */
    public String getWeight()
    {
        return getString("weight");
    }
    public void setWeight(String item)
    {
        setString("weight", item);
    }
    /**
     * Object: 设备档案 's 维修部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getWxDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("wxDept");
    }
    public void setWxDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("wxDept", item);
    }
    /**
     * Object:设备档案's 启用日期property 
     */
    public java.util.Date getQyDate()
    {
        return getDate("qyDate");
    }
    public void setQyDate(java.util.Date item)
    {
        setDate("qyDate", item);
    }
    /**
     * Object:设备档案's 序列号property 
     */
    public String getSerialNumber()
    {
        return getString("serialNumber");
    }
    public void setSerialNumber(String item)
    {
        setString("serialNumber", item);
    }
    /**
     * Object:设备档案's 设备状态property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.sbStatusType getSbStatus()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.sbStatusType.getEnum(getString("sbStatus"));
    }
    public void setSbStatus(com.kingdee.eas.port.equipment.base.enumbase.sbStatusType item)
    {
		if (item != null) {
        setString("sbStatus", item.getValue());
		}
    }
    /**
     * Object: 设备档案 's 计量单位 property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object:设备档案's 特种设备property 
     */
    public boolean isSpecial()
    {
        return getBoolean("special");
    }
    public void setSpecial(boolean item)
    {
        setBoolean("special", item);
    }
    /**
     * Object:设备档案's 备注property 
     */
    public String getSbDescription()
    {
        return getString("sbDescription");
    }
    public void setSbDescription(String item)
    {
        setString("sbDescription", item);
    }
    /**
     * Object: 设备档案 's 父设备 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:设备档案's 依赖property 
     */
    public boolean isDependable()
    {
        return getBoolean("dependable");
    }
    public void setDependable(boolean item)
    {
        setBoolean("dependable", item);
    }
    /**
     * Object: 设备档案 's 使用单位地址 property 
     */
    public com.kingdee.eas.basedata.assistant.AddressInfo getAddress()
    {
        return (com.kingdee.eas.basedata.assistant.AddressInfo)get("address");
    }
    public void setAddress(com.kingdee.eas.basedata.assistant.AddressInfo item)
    {
        put("address", item);
    }
    /**
     * Object:设备档案's 设备使用地点property 
     */
    public String getLocation()
    {
        return getString("location");
    }
    public void setLocation(String item)
    {
        setString("location", item);
    }
    /**
     * Object: 设备档案 's 使用部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUsingDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("usingDept");
    }
    public void setUsingDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("usingDept", item);
    }
    /**
     * Object: 设备档案 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getResPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("resPerson");
    }
    public void setResPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("resPerson", item);
    }
    /**
     * Object:设备档案's 制造商property 
     */
    public String getMader()
    {
        return getString("mader");
    }
    public void setMader(String item)
    {
        setString("mader", item);
    }
    /**
     * Object: 设备档案 's 制造国家 property 
     */
    public com.kingdee.eas.basedata.assistant.CountryInfo getMadedCountry()
    {
        return (com.kingdee.eas.basedata.assistant.CountryInfo)get("madedCountry");
    }
    public void setMadedCountry(com.kingdee.eas.basedata.assistant.CountryInfo item)
    {
        put("madedCountry", item);
    }
    /**
     * Object:设备档案's 制造日期property 
     */
    public java.util.Date getMadeDate()
    {
        return getDate("madeDate");
    }
    public void setMadeDate(java.util.Date item)
    {
        setDate("madeDate", item);
    }
    /**
     * Object: 设备档案 's 供应商 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:设备档案's 到货日期property 
     */
    public java.util.Date getReachedDate()
    {
        return getDate("reachedDate");
    }
    public void setReachedDate(java.util.Date item)
    {
        setDate("reachedDate", item);
    }
    /**
     * Object:设备档案's 出厂编号property 
     */
    public String getCcNumber()
    {
        return getString("ccNumber");
    }
    public void setCcNumber(String item)
    {
        setString("ccNumber", item);
    }
    /**
     * Object: 设备档案 's 安装单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getInstaller()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("installer");
    }
    public void setInstaller(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("installer", item);
    }
    /**
     * Object:设备档案's 验收日期property 
     */
    public java.util.Date getCheckDate()
    {
        return getDate("checkDate");
    }
    public void setCheckDate(java.util.Date item)
    {
        setDate("checkDate", item);
    }
    /**
     * Object:设备档案's 来源单位property 
     */
    public String getSourceUnit()
    {
        return getString("sourceUnit");
    }
    public void setSourceUnit(String item)
    {
        setString("sourceUnit", item);
    }
    /**
     * Object:设备档案's 装机容量（台）property 
     */
    public int getCapacity()
    {
        return getInt("capacity");
    }
    public void setCapacity(int item)
    {
        setInt("capacity", item);
    }
    /**
     * Object:设备档案's 功率（千瓦）property 
     */
    public java.math.BigDecimal getPower()
    {
        return getBigDecimal("power");
    }
    public void setPower(java.math.BigDecimal item)
    {
        setBigDecimal("power", item);
    }
    /**
     * Object:设备档案's 生产能力property 
     */
    public String getScCapacity()
    {
        return getString("scCapacity");
    }
    public void setScCapacity(String item)
    {
        setString("scCapacity", item);
    }
    /**
     * Object:设备档案's 生产用途property 
     */
    public String getScUsage()
    {
        return getString("scUsage");
    }
    public void setScUsage(String item)
    {
        setString("scUsage", item);
    }
    /**
     * Object:设备档案's 复杂系数f机property 
     */
    public java.math.BigDecimal getComRatiof()
    {
        return getBigDecimal("comRatiof");
    }
    public void setComRatiof(java.math.BigDecimal item)
    {
        setBigDecimal("comRatiof", item);
    }
    /**
     * Object:设备档案's 复杂系数f电property 
     */
    public String getComRatiod()
    {
        return getString("comRatiod");
    }
    public void setComRatiod(String item)
    {
        setString("comRatiod", item);
    }
    /**
     * Object: 设备档案 's 设备类型 property 
     */
    public com.kingdee.eas.fi.fa.basedata.FaCatInfo getEqmType()
    {
        return (com.kingdee.eas.fi.fa.basedata.FaCatInfo)get("eqmType");
    }
    public void setEqmType(com.kingdee.eas.fi.fa.basedata.FaCatInfo item)
    {
        put("eqmType", item);
    }
    /**
     * Object: 设备档案 's 详细技术参数 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdTechnologyParCollection getTechnologyPar()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdTechnologyParCollection)get("TechnologyPar");
    }
    /**
     * Object: 设备档案 's 备件信息 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdSpareInfoCollection getSpareInfo()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdSpareInfoCollection)get("SpareInfo");
    }
    /**
     * Object:设备档案's 厂内编号property 
     */
    public String getInnerNumber()
    {
        return getString("innerNumber");
    }
    public void setInnerNumber(String item)
    {
        setString("innerNumber", item);
    }
    /**
     * Object:设备档案's 目前现况property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.nowStatusType getNowStatus()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.nowStatusType.getEnum(getString("nowStatus"));
    }
    public void setNowStatus(com.kingdee.eas.port.equipment.base.enumbase.nowStatusType item)
    {
		if (item != null) {
        setString("nowStatus", item.getValue());
		}
    }
    /**
     * Object:设备档案's 联系人property 
     */
    public String getZzsShortName()
    {
        return getString("zzsShortName");
    }
    public void setZzsShortName(String item)
    {
        setString("zzsShortName", item);
    }
    /**
     * Object:设备档案's 港检property 
     */
    public boolean isPortTest()
    {
        return getBoolean("portTest");
    }
    public void setPortTest(boolean item)
    {
        setBoolean("portTest", item);
    }
    /**
     * Object:设备档案's 市检property 
     */
    public boolean isCityTest()
    {
        return getBoolean("cityTest");
    }
    public void setCityTest(boolean item)
    {
        setBoolean("cityTest", item);
    }
    /**
     * Object:设备档案's 到期检测日期property 
     */
    public java.util.Date getTestDay()
    {
        return getDate("testDay");
    }
    public void setTestDay(java.util.Date item)
    {
        setDate("testDay", item);
    }
    /**
     * Object:设备档案's 设备档案号property 
     */
    public String getTzdaNumber()
    {
        return getString("tzdaNumber");
    }
    public void setTzdaNumber(String item)
    {
        setString("tzdaNumber", item);
    }
    /**
     * Object:设备档案's 特种设备状态property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.sbStatusType getTzsbStatus()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.sbStatusType.getEnum(getString("tzsbStatus"));
    }
    public void setTzsbStatus(com.kingdee.eas.port.equipment.base.enumbase.sbStatusType item)
    {
		if (item != null) {
        setString("tzsbStatus", item.getValue());
		}
    }
    /**
     * Object: 设备档案 's 资产 property 
     */
    public com.kingdee.eas.fi.fa.manage.FaCurCardInfo getAsset()
    {
        return (com.kingdee.eas.fi.fa.manage.FaCurCardInfo)get("asset");
    }
    public void setAsset(com.kingdee.eas.fi.fa.manage.FaCurCardInfo item)
    {
        put("asset", item);
    }
    /**
     * Object: 设备档案 's 资产状态 property 
     */
    public com.kingdee.eas.fi.fa.basedata.FaUseStatusInfo getAssetStatus()
    {
        return (com.kingdee.eas.fi.fa.basedata.FaUseStatusInfo)get("assetStatus");
    }
    public void setAssetStatus(com.kingdee.eas.fi.fa.basedata.FaUseStatusInfo item)
    {
        put("assetStatus", item);
    }
    /**
     * Object:设备档案's 资产原值(元)property 
     */
    public java.math.BigDecimal getAssetValue()
    {
        return getBigDecimal("assetValue");
    }
    public void setAssetValue(java.math.BigDecimal item)
    {
        setBigDecimal("assetValue", item);
    }
    /**
     * Object:设备档案's 安装及杂费(元)property 
     */
    public java.math.BigDecimal getInstallCost()
    {
        return getBigDecimal("installCost");
    }
    public void setInstallCost(java.math.BigDecimal item)
    {
        setBigDecimal("installCost", item);
    }
    /**
     * Object:设备档案's 主要设备property 
     */
    public boolean isIsMainEqm()
    {
        return getBoolean("isMainEqm");
    }
    public void setIsMainEqm(boolean item)
    {
        setBoolean("isMainEqm", item);
    }
    /**
     * Object:设备档案's 设备类别property 
     */
    public String getEqmCategory()
    {
        return getString("EqmCategory");
    }
    public void setEqmCategory(String item)
    {
        setString("EqmCategory", item);
    }
    /**
     * Object: 设备档案 's 特种设备类型 property 
     */
    public com.kingdee.eas.port.equipment.base.SpecialTypeInfo getSpecialType()
    {
        return (com.kingdee.eas.port.equipment.base.SpecialTypeInfo)get("specialType");
    }
    public void setSpecialType(com.kingdee.eas.port.equipment.base.SpecialTypeInfo item)
    {
        put("specialType", item);
    }
    /**
     * Object:设备档案's 市检周期property 
     */
    public java.math.BigDecimal getCityPeriod()
    {
        return getBigDecimal("cityPeriod");
    }
    public void setCityPeriod(java.math.BigDecimal item)
    {
        setBigDecimal("cityPeriod", item);
    }
    /**
     * Object:设备档案's 港检周期property 
     */
    public java.math.BigDecimal getPortPeriod()
    {
        return getBigDecimal("portPeriod");
    }
    public void setPortPeriod(java.math.BigDecimal item)
    {
        setBigDecimal("portPeriod", item);
    }
    /**
     * Object:设备档案's 注册代码property 
     */
    public String getCode()
    {
        return getString("code");
    }
    public void setCode(String item)
    {
        setString("code", item);
    }
    /**
     * Object:设备档案's 发动机编号property 
     */
    public String getEngineNumber()
    {
        return getString("engineNumber");
    }
    public void setEngineNumber(String item)
    {
        setString("engineNumber", item);
    }
    /**
     * Object:设备档案's 车架号property 
     */
    public String getCarNumber()
    {
        return getString("carNumber");
    }
    public void setCarNumber(String item)
    {
        setString("carNumber", item);
    }
    /**
     * Object:设备档案's 额定起载重量(t)property 
     */
    public String getRatedWeight()
    {
        return getString("ratedWeight");
    }
    public void setRatedWeight(String item)
    {
        setString("ratedWeight", item);
    }
    /**
     * Object:设备档案's 市检到期检测日期property 
     */
    public java.util.Date getTextDate1()
    {
        return getDate("textDate1");
    }
    public void setTextDate1(java.util.Date item)
    {
        setDate("textDate1", item);
    }
    /**
     * Object:设备档案's 市检计划property 
     */
    public java.util.Date getDayone()
    {
        return getDate("dayone");
    }
    public void setDayone(java.util.Date item)
    {
        setDate("dayone", item);
    }
    /**
     * Object:设备档案's 港检计划property 
     */
    public java.util.Date getDaytow()
    {
        return getDate("daytow");
    }
    public void setDaytow(java.util.Date item)
    {
        setDate("daytow", item);
    }
    /**
     * Object:设备档案's 联系方式property 
     */
    public String getTelePhoneNumber()
    {
        return getString("telePhoneNumber");
    }
    public void setTelePhoneNumber(String item)
    {
        setString("telePhoneNumber", item);
    }
    /**
     * Object:设备档案's 实际检测日期property 
     */
    public java.util.Date getActrueTime()
    {
        return getDate("actrueTime");
    }
    public void setActrueTime(java.util.Date item)
    {
        setDate("actrueTime", item);
    }
    /**
     * Object:设备档案's 责任检验员property 
     */
    public String getResponsible()
    {
        return getString("responsible");
    }
    public void setResponsible(String item)
    {
        setString("responsible", item);
    }
    /**
     * Object:设备档案's 设备所在街道/乡镇property 
     */
    public String getInStreet()
    {
        return getString("inStreet");
    }
    public void setInStreet(String item)
    {
        setString("inStreet", item);
    }
    /**
     * Object: 设备档案 's 检验类别 property 
     */
    public com.kingdee.eas.port.equipment.base.TestTypeInfo getTextType()
    {
        return (com.kingdee.eas.port.equipment.base.TestTypeInfo)get("textType");
    }
    public void setTextType(com.kingdee.eas.port.equipment.base.TestTypeInfo item)
    {
        put("textType", item);
    }
    /**
     * Object: 设备档案 's 设备类型 property 
     */
    public com.kingdee.eas.port.equipment.base.EquTypeInfo getEquTypeone()
    {
        return (com.kingdee.eas.port.equipment.base.EquTypeInfo)get("equTypeone");
    }
    public void setEquTypeone(com.kingdee.eas.port.equipment.base.EquTypeInfo item)
    {
        put("equTypeone", item);
    }
    /**
     * Object: 设备档案 's 第3个表体 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdE3Collection getE3()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdE3Collection)get("E3");
    }
    /**
     * Object:设备档案's 是否参加保险property 
     */
    public boolean isIsbaoxian()
    {
        return getBoolean("isbaoxian");
    }
    public void setIsbaoxian(boolean item)
    {
        setBoolean("isbaoxian", item);
    }
    /**
     * Object:设备档案's 资产现值property 
     */
    public java.math.BigDecimal getNowAmount()
    {
        return getBigDecimal("nowAmount");
    }
    public void setNowAmount(java.math.BigDecimal item)
    {
        setBigDecimal("nowAmount", item);
    }
    /**
     * Object:设备档案's 折旧年限property 
     */
    public java.math.BigDecimal getOldYear()
    {
        return getBigDecimal("oldYear");
    }
    public void setOldYear(java.math.BigDecimal item)
    {
        setBigDecimal("oldYear", item);
    }
    /**
     * Object:设备档案's 使用期限property 
     */
    public String getDeadline()
    {
        return getString("deadline");
    }
    public void setDeadline(String item)
    {
        setString("deadline", item);
    }
    /**
     * Object:设备档案's 调试单位property 
     */
    public String getDebuger()
    {
        return getString("debuger");
    }
    public void setDebuger(String item)
    {
        setString("debuger", item);
    }
    /**
     * Object:设备档案's 是否参与定检巡检property 
     */
    public boolean isIsccCheck()
    {
        return getBoolean("isccCheck");
    }
    public void setIsccCheck(boolean item)
    {
        setBoolean("isccCheck", item);
    }
    /**
     * Object:设备档案's 船舶国籍证书property 
     */
    public String getCpgjzs()
    {
        return getString("cpgjzs");
    }
    public void setCpgjzs(String item)
    {
        setString("cpgjzs", item);
    }
    /**
     * Object:设备档案's 船舶所有权号property 
     */
    public String getCpsyqh()
    {
        return getString("cpsyqh");
    }
    public void setCpsyqh(String item)
    {
        setString("cpsyqh", item);
    }
    /**
     * Object:设备档案's 船舶识别号property 
     */
    public String getCpsbh()
    {
        return getString("cpsbh");
    }
    public void setCpsbh(String item)
    {
        setString("cpsbh", item);
    }
    /**
     * Object:设备档案's 船检property 
     */
    public boolean isChuanCheck()
    {
        return getBoolean("chuanCheck");
    }
    public void setChuanCheck(boolean item)
    {
        setBoolean("chuanCheck", item);
    }
    /**
     * Object: 设备档案 's 使用单位 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useUnit");
    }
    public void setUseUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useUnit", item);
    }
    /**
     * Object:设备档案's 工作级别property 
     */
    public String getGongzuojibie()
    {
        return getString("gongzuojibie");
    }
    public void setGongzuojibie(String item)
    {
        setString("gongzuojibie", item);
    }
    /**
     * Object:设备档案's 跨度property 
     */
    public String getKuadu()
    {
        return getString("kuadu");
    }
    public void setKuadu(String item)
    {
        setString("kuadu", item);
    }
    /**
     * Object:设备档案's 悬臂长度property 
     */
    public String getXuanbichangdu()
    {
        return getString("xuanbichangdu");
    }
    public void setXuanbichangdu(String item)
    {
        setString("xuanbichangdu", item);
    }
    /**
     * Object:设备档案's 起升高度property 
     */
    public String getQishengaodu()
    {
        return getString("qishengaodu");
    }
    public void setQishengaodu(String item)
    {
        setString("qishengaodu", item);
    }
    /**
     * Object:设备档案's 大车轨道长度单侧property 
     */
    public String getDacheguidao()
    {
        return getString("dacheguidao");
    }
    public void setDacheguidao(String item)
    {
        setString("dacheguidao", item);
    }
    /**
     * Object:设备档案's 起重力矩property 
     */
    public String getQizhongliju()
    {
        return getString("qizhongliju");
    }
    public void setQizhongliju(String item)
    {
        setString("qizhongliju", item);
    }
    /**
     * Object:设备档案's 最大工作幅度property 
     */
    public String getZuida()
    {
        return getString("zuida");
    }
    public void setZuida(String item)
    {
        setString("zuida", item);
    }
    /**
     * Object:设备档案's 最小 工作幅度property 
     */
    public String getZuixiao()
    {
        return getString("zuixiao");
    }
    public void setZuixiao(String item)
    {
        setString("zuixiao", item);
    }
    /**
     * Object:设备档案's 额度速度property 
     */
    public String getEdusudu()
    {
        return getString("edusudu");
    }
    public void setEdusudu(String item)
    {
        setString("edusudu", item);
    }
    /**
     * Object:设备档案's 组织机构代码property 
     */
    public String getZuzhijigou()
    {
        return getString("zuzhijigou");
    }
    public void setZuzhijigou(String item)
    {
        setString("zuzhijigou", item);
    }
    /**
     * Object:设备档案's 邮政编码property 
     */
    public String getYouzhengbianma()
    {
        return getString("youzhengbianma");
    }
    public void setYouzhengbianma(String item)
    {
        setString("youzhengbianma", item);
    }
    /**
     * Object:设备档案's 车管所property 
     */
    public boolean isCarGuanSuo()
    {
        return getBoolean("carGuanSuo");
    }
    public void setCarGuanSuo(boolean item)
    {
        setBoolean("carGuanSuo", item);
    }
    /**
     * Object:设备档案's 仪表property 
     */
    public boolean isYibiao()
    {
        return getBoolean("yibiao");
    }
    public void setYibiao(boolean item)
    {
        setBoolean("yibiao", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0ED4BEC2");
    }
}