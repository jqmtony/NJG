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
        put("SpareInfo", new com.kingdee.eas.port.equipment.record.EquIdSpareInfoCollection());
    }
    /**
     * Object:�豸����'s �豸����property 
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
     * Object: �豸���� 's ������֯ property 
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
     * Object: �豸���� 's �ƻ���֯ property 
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
     * Object: �豸���� 's ά����֯ property 
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
     * Object:�豸����'s ����ͺ�property 
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
     * Object:�豸����'s ��Ҫ��������property 
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
     * Object:�豸����'s ����property 
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
     * Object: �豸���� 's ά�޲��� property 
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
     * Object:�豸����'s ��������property 
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
     * Object:�豸����'s ���к�property 
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
     * Object:�豸����'s �豸״̬property 
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
     * Object: �豸���� 's ������λ property 
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
     * Object:�豸����'s �����豸property 
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
     * Object:�豸����'s ��עproperty 
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
     * Object: �豸���� 's ���豸 property 
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
     * Object:�豸����'s ����property 
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
     * Object: �豸���� 's ��װ�ص� property 
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
     * Object:�豸����'s ����λ��property 
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
     * Object: �豸���� 's ʹ�ò��� property 
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
     * Object: �豸���� 's ������ property 
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
     * Object:�豸����'s ������property 
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
     * Object: �豸���� 's ������� property 
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
     * Object:�豸����'s ��������property 
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
     * Object: �豸���� 's ��Ӧ�� property 
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
     * Object:�豸����'s ��������property 
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
     * Object:�豸����'s �������property 
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
     * Object: �豸���� 's ��װ��λ property 
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
     * Object: �豸���� 's ���Ե�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getDebuger()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("debuger");
    }
    public void setDebuger(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("debuger", item);
    }
    /**
     * Object:�豸����'s ��������property 
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
     * Object:�豸����'s ʹ������property 
     */
    public java.util.Date getDeadline()
    {
        return getDate("deadline");
    }
    public void setDeadline(java.util.Date item)
    {
        setDate("deadline", item);
    }
    /**
     * Object:�豸����'s ��Դ��λproperty 
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
     * Object:�豸����'s װ��������̨��property 
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
     * Object:�豸����'s ���ʣ�ǧ�ߣ�property 
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
     * Object:�豸����'s ��������property 
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
     * Object:�豸����'s ������;property 
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
     * Object:�豸����'s ����ϵ��f��property 
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
     * Object:�豸����'s ����ϵ��f��property 
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
     * Object: �豸���� 's �豸���� property 
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
     * Object: �豸���� 's ��ϸ�������� property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdTechnologyParCollection getTechnologyPar()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdTechnologyParCollection)get("TechnologyPar");
    }
    /**
     * Object: �豸���� 's ������Ϣ property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdSpareInfoCollection getSpareInfo()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdSpareInfoCollection)get("SpareInfo");
    }
    /**
     * Object:�豸����'s ���ڱ��property 
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
     * Object:�豸����'s Ŀǰ�ֿ�property 
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
     * Object:�豸����'s �����̼��property 
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
     * Object:�豸����'s �ۼ�property 
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
     * Object:�豸����'s �м�property 
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
     * Object:�豸����'s ���ڼ������property 
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
     * Object:�豸����'s �豸������property 
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
     * Object:�豸����'s �����豸״̬property 
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
     * Object: �豸���� 's �ʲ� property 
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
     * Object: �豸���� 's �ʲ�״̬ property 
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
     * Object:�豸����'s �ʲ�ԭֵ(Ԫ)property 
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
     * Object:�豸����'s ��װ���ӷ�(Ԫ)property 
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
     * Object:�豸����'s ��Ҫ�豸property 
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
     * Object:�豸����'s �豸���property 
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
     * Object: �豸���� 's �����豸���� property 
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
     * Object:�豸����'s �м�����property 
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
     * Object:�豸����'s �ۼ�����property 
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
     * Object:�豸����'s ע�����property 
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
     * Object:�豸����'s ���������property 
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
     * Object:�豸����'s ���ܺ�property 
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
     * Object:�豸����'s ���������(t)property 
     */
    public java.math.BigDecimal getRatedWeight()
    {
        return getBigDecimal("ratedWeight");
    }
    public void setRatedWeight(java.math.BigDecimal item)
    {
        setBigDecimal("ratedWeight", item);
    }
    /**
     * Object:�豸����'s �м쵽�ڼ������property 
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
     * Object:�豸����'s �м�ƻ�property 
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
     * Object:�豸����'s �ۼ�ƻ�property 
     */
    public java.util.Date getDaytow()
    {
        return getDate("daytow");
    }
    public void setDaytow(java.util.Date item)
    {
        setDate("daytow", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0ED4BEC2");
    }
}