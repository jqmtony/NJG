package com.kingdee.eas.custom.richbase;

import java.io.Serializable;

public class OrgUnitEntryInfo extends AbstractOrgUnitEntryInfo implements Serializable 
{
    public OrgUnitEntryInfo()
    {
        super();
    }
    protected OrgUnitEntryInfo(String pkField)
    {
        super(pkField);
    }
}