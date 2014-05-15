package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;

public class AnnualYearDetailInfo extends AbstractAnnualYearDetailInfo implements Serializable 
{
    public AnnualYearDetailInfo()
    {
        super();
    }
    protected AnnualYearDetailInfo(String pkField)
    {
        super(pkField);
    }
}