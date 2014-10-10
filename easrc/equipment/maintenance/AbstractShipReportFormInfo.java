package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShipReportFormInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractShipReportFormInfo()
    {
        this("id");
    }
    protected AbstractShipReportFormInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.maintenance.ShipReportFormE1Collection());
    }
    /**
     * Object: �������豸ά��������״���±��� 's ���� property 
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
     * Object:�������豸ά��������״���±���'s �豸ά������ʵ��property 
     */
    public String getPerformance()
    {
        return getString("performance");
    }
    public void setPerformance(String item)
    {
        setString("performance", item);
    }
    /**
     * Object: �������豸ά��������״���±��� 's ��1������ property 
     */
    public com.kingdee.eas.port.equipment.maintenance.ShipReportFormE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.maintenance.ShipReportFormE1Collection)get("E1");
    }
    /**
     * Object:�������豸ά��������״���±���'s ����property 
     */
    public String getCourse()
    {
        return getString("course");
    }
    public void setCourse(String item)
    {
        setString("course", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s ����property 
     */
    public String getTowing()
    {
        return getString("towing");
    }
    public void setTowing(String item)
    {
        setString("towing", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s ��������property 
     */
    public java.util.Date getMeasuringDate()
    {
        return getDate("measuringDate");
    }
    public void setMeasuringDate(java.util.Date item)
    {
        setDate("measuringDate", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s �����豸��������property 
     */
    public String getExProblems()
    {
        return getString("exProblems");
    }
    public void setExProblems(String item)
    {
        setString("exProblems", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s ������ת��r/mproperty 
     */
    public String getZuozs()
    {
        return getString("zuozs");
    }
    public void setZuozs(String item)
    {
        setString("zuozs", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s ������ת��r/mproperty 
     */
    public String getYouzs()
    {
        return getString("youzs");
    }
    public void setYouzs(String item)
    {
        setString("youzs", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s ��������ѹѹ��MPaproperty 
     */
    public String getZuozy()
    {
        return getString("zuozy");
    }
    public void setZuozy(String item)
    {
        setString("zuozy", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s ��������ѹѹ��MPaproperty 
     */
    public String getYouzy()
    {
        return getString("youzy");
    }
    public void setYouzy(String item)
    {
        setString("youzy", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s ��������ȴˮ�¶�property 
     */
    public String getZuowd()
    {
        return getString("zuowd");
    }
    public void setZuowd(String item)
    {
        setString("zuowd", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s ��������ȴˮ�¶�property 
     */
    public String getYouwd()
    {
        return getString("youwd");
    }
    public void setYouwd(String item)
    {
        setString("youwd", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s �����������¶�property 
     */
    public String getZuorh()
    {
        return getString("zuorh");
    }
    public void setZuorh(String item)
    {
        setString("zuorh", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s �����������¶�property 
     */
    public String getYourh()
    {
        return getString("yourh");
    }
    public void setYourh(String item)
    {
        setString("yourh", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s ͣ�������죩property 
     */
    public String getSuspendRepair()
    {
        return getString("suspendRepair");
    }
    public void setSuspendRepair(String item)
    {
        setString("suspendRepair", item);
    }
    /**
     * Object:�������豸ά��������״���±���'s �������property 
     */
    public String getRepairType()
    {
        return getString("repairType");
    }
    public void setRepairType(String item)
    {
        setString("repairType", item);
    }
    /**
     * Object: �������豸ά��������״���±��� 's ���� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getShipzhang()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("shipzhang");
    }
    public void setShipzhang(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("shipzhang", item);
    }
    /**
     * Object: �������豸ά��������״���±��� 's �ֻ��� property 
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
     * Object: �������豸ά��������״���±��� 's �����·� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getMonth()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("month");
    }
    public void setMonth(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("month", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4352E5C4");
    }
}