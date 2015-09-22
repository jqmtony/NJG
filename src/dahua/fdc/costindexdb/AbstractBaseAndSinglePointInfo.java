package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBaseAndSinglePointInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractBaseAndSinglePointInfo()
    {
        this("id");
    }
    protected AbstractBaseAndSinglePointInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryCollection());
        put("Ecost", new com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostCollection());
    }
    /**
     * Object: ����Ҫ��-����Ҫ�� 's ����Ҫ�� property 
     */
    public com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryCollection)get("entrys");
    }
    /**
     * Object:����Ҫ��-����Ҫ��'s ��Ŀ����property 
     */
    public String getProjectName()
    {
        return getString("projectName");
    }
    public void setProjectName(String item)
    {
        setString("projectName", item);
    }
    /**
     * Object:����Ҫ��-����Ҫ��'s ��ĿIDproperty 
     */
    public String getProjectId()
    {
        return getString("projectId");
    }
    public void setProjectId(String item)
    {
        setString("projectId", item);
    }
    /**
     * Object:����Ҫ��-����Ҫ��'s �汾��property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object:����Ҫ��-����Ҫ��'s �Ƿ�����property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object: ����Ҫ��-����Ҫ�� 's ����Ҫ�� property 
     */
    public com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostCollection getEcost()
    {
        return (com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostCollection)get("Ecost");
    }
    /**
     * Object:����Ҫ��-����Ҫ��'s ����״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getPointBillStatus()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("pointBillStatus"));
    }
    public void setPointBillStatus(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("pointBillStatus", item.getValue());
		}
    }
    /**
     * Object:����Ҫ��-����Ҫ��'s ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object:����Ҫ��-����Ҫ��'s ���ʱ��property 
     */
    public java.util.Date getAudiTime()
    {
        return getDate("audiTime");
    }
    public void setAudiTime(java.util.Date item)
    {
        setDate("audiTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4B9566A8");
    }
}