package com.kingdee.eas.fdc.contract.settle;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettleDeclarationBillE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSettleDeclarationBillE1Info()
    {
        this("id");
    }
    protected AbstractSettleDeclarationBillE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第1个表体 's null property 
     */
    public com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:第1个表体's 申报资料property 
     */
    public String getSbzl()
    {
        return getString("sbzl");
    }
    public void setSbzl(String item)
    {
        setString("sbzl", item);
    }
    /**
     * Object:第1个表体's 选择property 
     */
    public boolean isXuanze()
    {
        return getBoolean("xuanze");
    }
    public void setXuanze(boolean item)
    {
        setBoolean("xuanze", item);
    }
    /**
     * Object:第1个表体's 页数property 
     */
    public int getYeshu()
    {
        return getInt("yeshu");
    }
    public void setYeshu(int item)
    {
        setInt("yeshu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("73E69340");
    }
}