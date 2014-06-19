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
     * Object: 合同正文 's 合同ID property 
     */
    public com.kingdee.eas.port.pm.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.port.pm.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.port.pm.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object:合同正文's 正文文件property 
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
     * Object:合同正文's 文件类型property 
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
     * Object:合同正文's 版本号property 
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
     * Object:合同正文's 父单据IDproperty 
     */
    public String getParent()
    {
        return getString("parent");
    }
    public void setParent(String item)
    {
        setString("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FE28236C");
    }
}