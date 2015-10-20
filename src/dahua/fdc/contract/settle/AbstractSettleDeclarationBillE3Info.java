package com.kingdee.eas.fdc.contract.settle;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettleDeclarationBillE3Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSettleDeclarationBillE3Info()
    {
        this("id");
    }
    protected AbstractSettleDeclarationBillE3Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第3个表体 's null property 
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
     * Object: 第3个表体 's 预警人员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getYujingrenyuan()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("yujingrenyuan");
    }
    public void setYujingrenyuan(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("yujingrenyuan", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("73E69342");
    }
}