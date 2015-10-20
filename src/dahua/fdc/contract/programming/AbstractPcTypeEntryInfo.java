package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPcTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPcTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractPcTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.programming.PcTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.programming.PcTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.programming.PcTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 字段名称property 
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
     * Object:分录's 参照时间property 
     */
    public com.kingdee.eas.fdc.contract.programming.CKDate getCkDate()
    {
        return com.kingdee.eas.fdc.contract.programming.CKDate.getEnum(getString("ckDate"));
    }
    public void setCkDate(com.kingdee.eas.fdc.contract.programming.CKDate item)
    {
		if (item != null) {
        setString("ckDate", item.getValue());
		}
    }
    /**
     * Object:分录's 提前天数property 
     */
    public int getTqDays()
    {
        return getInt("tqDays");
    }
    public void setTqDays(int item)
    {
        setInt("tqDays", item);
    }
    /**
     * Object:分录's 是否强控property 
     */
    public boolean isStrongControl()
    {
        return getBoolean("strongControl");
    }
    public void setStrongControl(boolean item)
    {
        setBoolean("strongControl", item);
    }
    /**
     * Object: 分录 's 部门类型 property 
     */
    public com.kingdee.eas.fdc.contract.basedata.PcDepTypeInfo getDepType()
    {
        return (com.kingdee.eas.fdc.contract.basedata.PcDepTypeInfo)get("depType");
    }
    public void setDepType(com.kingdee.eas.fdc.contract.basedata.PcDepTypeInfo item)
    {
        put("depType", item);
    }
    /**
     * Object:分录's recordSeqproperty 
     */
    public String getRecordSeq()
    {
        return getString("recordSeq");
    }
    public void setRecordSeq(String item)
    {
        setString("recordSeq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("48D46D75");
    }
}