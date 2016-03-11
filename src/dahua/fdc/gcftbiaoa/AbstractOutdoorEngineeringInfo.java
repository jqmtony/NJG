package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOutdoorEngineeringInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractOutdoorEngineeringInfo()
    {
        this("id");
    }
    protected AbstractOutdoorEngineeringInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.gcftbiaoa.OutdoorEngineeringEntryCollection());
    }
    /**
     * Object: ���⹤�̱� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.OutdoorEngineeringEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.OutdoorEngineeringEntryCollection)get("entrys");
    }
    /**
     * Object:���⹤�̱�'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:���⹤�̱�'s ��˾����property 
     */
    public String getCompany()
    {
        return getString("company");
    }
    public void setCompany(String item)
    {
        setString("company", item);
    }
    /**
     * Object: ���⹤�̱� 's ��Ŀ���� property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProjectName()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("ProjectName");
    }
    public void setProjectName(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("ProjectName", item);
    }
    /**
     * Object:���⹤�̱�'s �汾��property 
     */
    public int getVersion()
    {
        return getInt("Version");
    }
    public void setVersion(int item)
    {
        setInt("Version", item);
    }
    /**
     * Object:���⹤�̱�'s �Ƿ�����property 
     */
    public boolean isLasted()
    {
        return getBoolean("lasted");
    }
    public void setLasted(boolean item)
    {
        setBoolean("lasted", item);
    }
    /**
     * Object: ���⹤�̱� 's ��ʽ property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.StyleInfo getStyle()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.StyleInfo)get("Style");
    }
    public void setStyle(com.kingdee.eas.fdc.gcftbiaoa.StyleInfo item)
    {
        put("Style", item);
    }
    /**
     * Object:���⹤�̱�'s ״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:���⹤�̱�'s ���ʱ��property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object:���⹤�̱�'s �����ܲ������ƽ�ף�property 
     */
    public java.math.BigDecimal getSumArea()
    {
        return getBigDecimal("SumArea");
    }
    public void setSumArea(java.math.BigDecimal item)
    {
        setBigDecimal("SumArea", item);
    }
    /**
     * Object:���⹤�̱�'s Ӳ�����̻���۱�property 
     */
    public java.math.BigDecimal getCostThan()
    {
        return getBigDecimal("CostThan");
    }
    public void setCostThan(java.math.BigDecimal item)
    {
        setBigDecimal("CostThan", item);
    }
    /**
     * Object:���⹤�̱�'s Ӳ�����̻������property 
     */
    public java.math.BigDecimal getAreaThan()
    {
        return getBigDecimal("AreaThan");
    }
    public void setAreaThan(java.math.BigDecimal item)
    {
        setBigDecimal("AreaThan", item);
    }
    /**
     * Object:���⹤�̱�'s �����ܲ����ָ�꣨Ԫ/ƽ�ף�property 
     */
    public java.math.BigDecimal getSumAreaIndax()
    {
        return getBigDecimal("SumAreaIndax");
    }
    public void setSumAreaIndax(java.math.BigDecimal item)
    {
        setBigDecimal("SumAreaIndax", item);
    }
    /**
     * Object:���⹤�̱�'s �̻�property 
     */
    public java.math.BigDecimal getGreenH()
    {
        return getBigDecimal("GreenH");
    }
    public void setGreenH(java.math.BigDecimal item)
    {
        setBigDecimal("GreenH", item);
    }
    /**
     * Object:���⹤�̱�'s Ӳ��property 
     */
    public java.math.BigDecimal getLandscape()
    {
        return getBigDecimal("landscape");
    }
    public void setLandscape(java.math.BigDecimal item)
    {
        setBigDecimal("landscape", item);
    }
    /**
     * Object:���⹤�̱�'s С����������ʩproperty 
     */
    public java.math.BigDecimal getOther()
    {
        return getBigDecimal("other");
    }
    public void setOther(java.math.BigDecimal item)
    {
        setBigDecimal("other", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F44B7C3E");
    }
}