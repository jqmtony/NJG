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
     * Object: ��¼ 's ����ͷ property 
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
     * Object:��¼'s �ֶ�����property 
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
     * Object:��¼'s ����ʱ��property 
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
     * Object:��¼'s ��ǰ����property 
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
     * Object:��¼'s �Ƿ�ǿ��property 
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
     * Object: ��¼ 's �������� property 
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
     * Object:��¼'s recordSeqproperty 
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