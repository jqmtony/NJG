package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynamicCostMonitorInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractDynamicCostMonitorInfo()
    {
        this("id");
    }
    protected AbstractDynamicCostMonitorInfo(String pkField)
    {
        super(pkField);
        put("costAccountEntries", new com.kingdee.eas.fdc.aimcost.DynamicCostMonitorCAEntriesCollection());
        put("contractEntries", new com.kingdee.eas.fdc.aimcost.DynamicCostMonitorContractEntryCollection());
    }
    /**
     * Object: ��̬�ɱ���� 's ������Ŀ property 
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
     * Object: ��̬�ɱ���� 's ��Լ��� property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingInfo getProgramming()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.fdc.contract.programming.ProgrammingInfo item)
    {
        put("programming", item);
    }
    /**
     * Object: ��̬�ɱ���� 's ��ͬ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.DynamicCostMonitorContractEntryCollection getContractEntries()
    {
        return (com.kingdee.eas.fdc.aimcost.DynamicCostMonitorContractEntryCollection)get("contractEntries");
    }
    /**
     * Object: ��̬�ɱ���� 's �ɱ���Ŀ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.DynamicCostMonitorCAEntriesCollection getCostAccountEntries()
    {
        return (com.kingdee.eas.fdc.aimcost.DynamicCostMonitorCAEntriesCollection)get("costAccountEntries");
    }
    /**
     * Object:��̬�ɱ����'s ��ͬ�ɱ���¼�������property 
     */
    public byte[] getConEntryTblContent()
    {
        return (byte[])get("conEntryTblContent");
    }
    public void setConEntryTblContent(byte[] item)
    {
        put("conEntryTblContent", item);
    }
    /**
     * Object:��̬�ɱ����'s �ɱ���Ŀ��¼�������property 
     */
    public byte[] getCaEntryTblContent()
    {
        return (byte[])get("caEntryTblContent");
    }
    public void setCaEntryTblContent(byte[] item)
    {
        put("caEntryTblContent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("272DF0A5");
    }
}