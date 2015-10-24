package com.kingdee.eas.fdc.contract.basedata;

import java.io.Serializable;

public class ProgrammingTempInfo extends AbstractProgrammingTempInfo implements Serializable 
{
    public ProgrammingTempInfo()
    {
        super();
    }
    protected ProgrammingTempInfo(String pkField)
    {
        super(pkField);
    }
}