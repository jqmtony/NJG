package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynCostCtrlBillEntryCostAccountItemEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDynCostCtrlBillEntryCostAccountItemEntryInfo()
    {
        this("id");
    }
    protected AbstractDynCostCtrlBillEntryCostAccountItemEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ɱ����Ƶ���¼�ĳɱ���Ŀ��¼ 's �±���������¼�Ŀ�Ŀ property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryInfo getDynCostCtrlBillEntryCostAccountItem()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryInfo)get("dynCostCtrlBillEntryCostAccountItem");
    }
    public void setDynCostCtrlBillEntryCostAccountItem(com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryInfo item)
    {
        put("dynCostCtrlBillEntryCostAccountItem", item);
    }
    /**
     * Object: �ɱ����Ƶ���¼�ĳɱ���Ŀ��¼ 's null property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5360B310");
    }
}