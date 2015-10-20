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
     * Object:���ǩ֤ȷ�Ϸ�¼'s �������property 
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
     * Object:���ǩ֤ȷ�Ϸ�¼'s �Ƿ񷵹�property 
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
     * Object:���ǩ֤ȷ�Ϸ�¼'s ������ݱ���property 
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
     * Object: ���ǩ֤ȷ�Ϸ�¼ 's ����ͷ property 
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
     * Object:���ǩ֤ȷ�Ϸ�¼'s ȫ��ִ��property 
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
     * Object:���ǩ֤ȷ�Ϸ�¼'s ����ִ��property 
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
     * Object:���ǩ֤ȷ�Ϸ�¼'s δִ��property 
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
     * Object:���ǩ֤ȷ�Ϸ�¼'s ��עproperty 
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