package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;

public class TestTypeInfo extends AbstractTestTypeInfo implements Serializable 
{
    public TestTypeInfo()
    {
        super();
    }
    protected TestTypeInfo(String pkField)
    {
        super(pkField);
    }
}