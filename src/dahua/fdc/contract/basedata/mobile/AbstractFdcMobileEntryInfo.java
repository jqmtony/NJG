package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFdcMobileEntryInfo extends com.kingdee.eas.fdc.basedata.FDCBillEntryInfo implements Serializable 
{
    public AbstractFdcMobileEntryInfo()
    {
        this("id");
    }
    protected AbstractFdcMobileEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:房地产移动分录基类's 排序编码property 
     */
    public String getSeqNum()
    {
        return getString("seqNum");
    }
    public void setSeqNum(String item)
    {
        setString("seqNum", item);
    }
    /**
     * Object:房地产移动分录基类's 编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:房地产移动分录基类's 名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C4DA051F");
    }
}