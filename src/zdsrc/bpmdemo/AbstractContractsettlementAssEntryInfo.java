package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractsettlementAssEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractsettlementAssEntryInfo()
    {
        this("id");
    }
    protected AbstractContractsettlementAssEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �������Ϸ�¼ 's null property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementInfo getParent()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.bpmdemo.ContractsettlementInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�������Ϸ�¼'s ѡ��property 
     */
    public String getSelect()
    {
        return getString("select");
    }
    public void setSelect(String item)
    {
        setString("select", item);
    }
    /**
     * Object:�������Ϸ�¼'s ���ݱ���property 
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
     * Object:�������Ϸ�¼'s ��������property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:�������Ϸ�¼'s ΥԼ��property 
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
     * Object:�������Ϸ�¼'s ΥԼ����property 
     */
    public String getType()
    {
        return getString("type");
    }
    public void setType(String item)
    {
        setString("type", item);
    }
    /**
     * Object:�������Ϸ�¼'s �ۿʽproperty 
     */
    public String getDeductType()
    {
        return getString("deductType");
    }
    public void setDeductType(String item)
    {
        setString("deductType", item);
    }
    /**
     * Object:�������Ϸ�¼'s �Ƶ���property 
     */
    public String getCreator()
    {
        return getString("creator");
    }
    public void setCreator(String item)
    {
        setString("creator", item);
    }
    /**
     * Object:�������Ϸ�¼'s �Ƶ�����property 
     */
    public java.util.Date getCreateDate()
    {
        return getDate("createDate");
    }
    public void setCreateDate(java.util.Date item)
    {
        setDate("createDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9C4ABA0A");
    }
}