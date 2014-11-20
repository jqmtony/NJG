package com.kingdee.eas.bpm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBPMServerConfigInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBPMServerConfigInfo()
    {
        this("id");
    }
    protected AbstractBPMServerConfigInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F3297283");
    }
}