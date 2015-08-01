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
     * Object: 动态成本监控 's 工程项目 property 
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
     * Object: 动态成本监控 's 合约框架 property 
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
     * Object: 动态成本监控 's 合同分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.DynamicCostMonitorContractEntryCollection getContractEntries()
    {
        return (com.kingdee.eas.fdc.aimcost.DynamicCostMonitorContractEntryCollection)get("contractEntries");
    }
    /**
     * Object: 动态成本监控 's 成本科目分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.DynamicCostMonitorCAEntriesCollection getCostAccountEntries()
    {
        return (com.kingdee.eas.fdc.aimcost.DynamicCostMonitorCAEntriesCollection)get("costAccountEntries");
    }
    /**
     * Object:动态成本监控's 合同成本分录表格内容property 
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
     * Object:动态成本监控's 成本科目分录表格内容property 
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