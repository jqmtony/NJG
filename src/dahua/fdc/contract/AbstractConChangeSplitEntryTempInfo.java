package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConChangeSplitEntryTempInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractConChangeSplitEntryTempInfo()
    {
        this("id");
    }
    protected AbstractConChangeSplitEntryTempInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�洢�����ַ�¼��Ϣ����ʱ��'s ���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: �洢�����ַ�¼��Ϣ����ʱ�� 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.contract.ConChangeSplitTempInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ConChangeSplitTempInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ConChangeSplitTempInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�洢�����ַ�¼��Ϣ����ʱ��'s �����ֵ���¼IDproperty 
     */
    public String getEntryID()
    {
        return getString("entryID");
    }
    public void setEntryID(String item)
    {
        setString("entryID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5F9D143");
    }
}