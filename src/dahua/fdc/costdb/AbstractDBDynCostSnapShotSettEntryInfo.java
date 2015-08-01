package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDBDynCostSnapShotSettEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDBDynCostSnapShotSettEntryInfo()
    {
        this("id");
    }
    protected AbstractDBDynCostSnapShotSettEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��̬�ɱ����ս����¼'s δ������property 
     */
    public java.math.BigDecimal getUnSettleAmt()
    {
        return getBigDecimal("unSettleAmt");
    }
    public void setUnSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unSettleAmt", item);
    }
    /**
     * Object:��̬�ɱ����ս����¼'s �ѽ�����property 
     */
    public java.math.BigDecimal getSettleAmt()
    {
        return getBigDecimal("settleAmt");
    }
    public void setSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmt", item);
    }
    /**
     * Object: ��̬�ɱ����ս����¼ 's ��̬�ɱ����� property 
     */
    public com.kingdee.eas.fdc.costdb.DBDynCostSnapShotInfo getParent()
    {
        return (com.kingdee.eas.fdc.costdb.DBDynCostSnapShotInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costdb.DBDynCostSnapShotInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��̬�ɱ����ս����¼ 's ������� property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeTypeInfo getChangeType()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeTypeInfo)get("changeType");
    }
    public void setChangeType(com.kingdee.eas.fdc.basedata.ChangeTypeInfo item)
    {
        put("changeType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("286E2AD6");
    }
}