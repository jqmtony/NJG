package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCTreeBaseDataInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractFDCTreeBaseDataInfo()
    {
        this("id");
    }
    protected AbstractFDCTreeBaseDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:房地产树形基础资料's 启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6CCBC6AC");
    }
}