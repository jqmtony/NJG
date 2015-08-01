package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynCostCtrlBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractDynCostCtrlBillInfo()
    {
        this("id");
    }
    protected AbstractDynCostCtrlBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryCollection());
    }
    /**
     * Object: ��̬�ɱ����Ƶ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryCollection)get("entrys");
    }
    /**
     * Object:��̬�ɱ����Ƶ�'s �Ƿ����հ�property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    /**
     * Object:��̬�ɱ����Ƶ�'s �汾��property 
     */
    public java.math.BigDecimal getVerNumber()
    {
        return getBigDecimal("verNumber");
    }
    public void setVerNumber(java.math.BigDecimal item)
    {
        setBigDecimal("verNumber", item);
    }
    /**
     * Object: ��̬�ɱ����Ƶ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:��̬�ɱ����Ƶ�'s �Ƿ��Ǽ���ģ��property 
     */
    public boolean isIsTemp()
    {
        return getBoolean("isTemp");
    }
    public void setIsTemp(boolean item)
    {
        setBoolean("isTemp", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9EE30EE1");
    }
}