package com.kingdee.eas.fdc.contract.settle;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettleDeclarationBillE2Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSettleDeclarationBillE2Info()
    {
        this("id");
    }
    protected AbstractSettleDeclarationBillE2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第2个表体 's null property 
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
     * Object:第2个表体's 申报资料property 
     */
    public String getSbzl2()
    {
        return getString("sbzl2");
    }
    public void setSbzl2(String item)
    {
        setString("sbzl2", item);
    }
    /**
     * Object:第2个表体's 选择property 
     */
    public boolean isXuanze2()
    {
        return getBoolean("xuanze2");
    }
    public void setXuanze2(boolean item)
    {
        setBoolean("xuanze2", item);
    }
    /**
     * Object:第2个表体's 页数property 
     */
    public int getYeshu2()
    {
        return getInt("yeshu2");
    }
    public void setYeshu2(int item)
    {
        setInt("yeshu2", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("73E69341");
    }
}