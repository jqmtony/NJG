package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class SettlementCostSplitEntryInfo extends AbstractSettlementCostSplitEntryInfo implements Serializable 
{
    public SettlementCostSplitEntryInfo()
    {
        super();
    }
    protected SettlementCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}