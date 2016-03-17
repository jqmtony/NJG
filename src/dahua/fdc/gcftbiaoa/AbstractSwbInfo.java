package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSwbInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSwbInfo()
    {
        this("id");
    }
    protected AbstractSwbInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.gcftbiaoa.SwbEntryCollection());
    }
    /**
     * Object: ���̱� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.SwbEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.SwbEntryCollection)get("entrys");
    }
    /**
     * Object:���̱�'s �Ƿ�����ƾ֤property 
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
     * Object:���̱�'s ��˾����property 
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
     * Object:���̱�'s ״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("State"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("State", item.getValue());
		}
    }
    /**
     * Object: ���̱� 's ��Ŀ���� property 
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
     * Object:���̱�'s �汾��property 
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
     * Object:���̱�'s �Ƿ�����property 
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
     * Object:���̱�'s �����ܲ����(ƽ��)property 
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
     * Object:���̱�'s Ӳ�����̻���۱�property 
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
     * Object:���̱�'s Ӳ�����̻������property 
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
     * Object:���̱�'s Ӳ��property 
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
     * Object:���̱�'s �����ܲ����ָ�꣨Ԫ/ƽ�ף�property 
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
     * Object:���̱�'s �̻�property 
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
     * Object:���̱�'s ����property 
     */
    public java.math.BigDecimal getOther()
    {
        return getBigDecimal("other");
    }
    public void setOther(java.math.BigDecimal item)
    {
        setBigDecimal("other", item);
    }
    /**
     * Object:���̱�'s ���ʱ��property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5FE45297");
    }
}