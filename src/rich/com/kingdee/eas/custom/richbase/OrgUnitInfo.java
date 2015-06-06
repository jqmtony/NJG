package com.kingdee.eas.custom.richbase;

import java.io.Serializable;

public class OrgUnitInfo extends AbstractOrgUnitInfo implements Serializable 
{
    public OrgUnitInfo()
    {
        super();
    }
    protected OrgUnitInfo(String pkField)
    {
        super(pkField);
    }
}