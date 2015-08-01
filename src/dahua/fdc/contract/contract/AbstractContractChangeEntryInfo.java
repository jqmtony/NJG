package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractChangeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractChangeEntryInfo()
    {
        this("id");
    }
    protected AbstractContractChangeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:变更签证确认分录's 变更内容property 
     */
    public String getChangeContent()
    {
        return getString("changeContent");
    }
    public void setChangeContent(String item)
    {
        setString("changeContent", item);
    }
    /**
     * Object:变更签证确认分录's 是否返工property 
     */
    public boolean isIsBack()
    {
        return getBoolean("isBack");
    }
    public void setIsBack(boolean item)
    {
        setBoolean("isBack", item);
    }
    /**
     * Object:变更签证确认分录's 变更内容编码property 
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
     * Object: 变更签证确认分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractChangeBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:变更签证确认分录's 全部执行property 
     */
    public boolean isIsAllExe()
    {
        return getBoolean("isAllExe");
    }
    public void setIsAllExe(boolean item)
    {
        setBoolean("isAllExe", item);
    }
    /**
     * Object:变更签证确认分录's 部分执行property 
     */
    public boolean isIsPartExe()
    {
        return getBoolean("isPartExe");
    }
    public void setIsPartExe(boolean item)
    {
        setBoolean("isPartExe", item);
    }
    /**
     * Object:变更签证确认分录's 未执行property 
     */
    public boolean isIsNoExe()
    {
        return getBoolean("isNoExe");
    }
    public void setIsNoExe(boolean item)
    {
        setBoolean("isNoExe", item);
    }
    /**
     * Object:变更签证确认分录's 备注property 
     */
    public String getDiscription()
    {
        return getString("discription");
    }
    public void setDiscription(String item)
    {
        setString("discription", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("509C1015");
    }
}