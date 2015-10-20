package com.kingdee.eas.fdc.aimcost.costkf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCQGSInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractCQGSInfo()
    {
        this("id");
    }
    protected AbstractCQGSInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.costkf.CQGSEntryCollection());
    }
    /**
     * Object: ��Ȩ�����걨�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.costkf.CQGSEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.CQGSEntryCollection)get("entrys");
    }
    /**
     * Object:��Ȩ�����걨��'s �Ƿ�����ƾ֤property 
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
     * Object:��Ȩ�����걨��'s ��˾����property 
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
     * Object:��Ȩ�����걨��'s �ƻ�����ʱ��property 
     */
    public java.util.Date getStartdate()
    {
        return getDate("startdate");
    }
    public void setStartdate(java.util.Date item)
    {
        setDate("startdate", item);
    }
    /**
     * Object:��Ȩ�����걨��'s �ƻ�����ʱ��property 
     */
    public java.util.Date getEnddate()
    {
        return getDate("enddate");
    }
    public void setEnddate(java.util.Date item)
    {
        setDate("enddate", item);
    }
    /**
     * Object:��Ȩ�����걨��'s �����õ����property 
     */
    public java.math.BigDecimal getRedarea()
    {
        return getBigDecimal("redarea");
    }
    public void setRedarea(java.math.BigDecimal item)
    {
        setBigDecimal("redarea", item);
    }
    /**
     * Object:��Ȩ�����걨��'s �ݻ���property 
     */
    public String getVolumeRatio()
    {
        return getString("VolumeRatio");
    }
    public void setVolumeRatio(String item)
    {
        setString("VolumeRatio", item);
    }
    /**
     * Object:��Ȩ�����걨��'s �ܽ������property 
     */
    public String getTotalArea()
    {
        return getString("TotalArea");
    }
    public void setTotalArea(String item)
    {
        setString("TotalArea", item);
    }
    /**
     * Object:��Ȩ�����걨��'s ״̬property 
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
     * Object: ��Ȩ�����걨�� 's ��Ŀ���� property 
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
     * Object:��Ȩ�����걨��'s �汾��property 
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
     * Object:��Ȩ�����걨��'s �Ƿ�����property 
     */
    public boolean isLasted()
    {
        return getBoolean("lasted");
    }
    public void setLasted(boolean item)
    {
        setBoolean("lasted", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D1BD4D83");
    }
}