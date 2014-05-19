package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;

public class ComproductionInfo extends AbstractComproductionInfo implements Serializable 
{
    public ComproductionInfo()
    {
        super();
    }
    protected ComproductionInfo(String pkField)
    {
        super(pkField);
    }
}