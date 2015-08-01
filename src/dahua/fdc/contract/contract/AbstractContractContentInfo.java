package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractContentInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractContractContentInfo()
    {
        this("id");
    }
    protected AbstractContractContentInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ͬ���� 's ��ͬID property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object:��ͬ����'s �����ļ�property 
     */
    public byte[] getContentFile()
    {
        return (byte[])get("contentFile");
    }
    public void setContentFile(byte[] item)
    {
        put("contentFile", item);
    }
    /**
     * Object:��ͬ����'s �ļ�����property 
     */
    public String getFileType()
    {
        return getString("fileType");
    }
    public void setFileType(String item)
    {
        setString("fileType", item);
    }
    /**
     * Object:��ͬ����'s �汾��property 
     */
    public java.math.BigDecimal getVersion()
    {
        return getBigDecimal("version");
    }
    public void setVersion(java.math.BigDecimal item)
    {
        setBigDecimal("version", item);
    }
    /**
     * Object:��ͬ����'s ������IDproperty 
     */
    public String getParent()
    {
        return getString("parent");
    }
    public void setParent(String item)
    {
        setString("parent", item);
    }
    /**
     * Object:��ͬ����'s ��ͬ����property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object:��ͬ����'s �༭��property 
     */
    public String getEditer()
    {
        return getString("editer");
    }
    public void setEditer(String item)
    {
        setString("editer", item);
    }
    /**
     * Object:��ͬ����'s �༭��ʼʱ��property 
     */
    public java.sql.Timestamp getEditTime()
    {
        return getTimestamp("editTime");
    }
    public void setEditTime(java.sql.Timestamp item)
    {
        setTimestamp("editTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FE28236C");
    }
}