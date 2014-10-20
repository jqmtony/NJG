package com.kingdee.eas.bpm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractApproveBackSetEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractApproveBackSetEntryInfo()
    {
        this("id");
    }
    protected AbstractApproveBackSetEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��1������ 's null property 
     */
    public com.kingdee.eas.bpm.ApproveBackSetInfo getParent()
    {
        return (com.kingdee.eas.bpm.ApproveBackSetInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.bpm.ApproveBackSetInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��1������'s ���ݿ��ֶ�property 
     */
    public String getField()
    {
        return getString("field");
    }
    public void setField(String item)
    {
        setString("field", item);
    }
    /**
     * Object:��1������'s �ֶα���property 
     */
    public String getFieldName()
    {
        return getString("fieldName");
    }
    public void setFieldName(String item)
    {
        setString("fieldName", item);
    }
    /**
     * Object:��1������'s ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("03385A43");
    }
}