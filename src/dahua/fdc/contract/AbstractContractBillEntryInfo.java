package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractBillEntryInfo()
    {
        this("id");
    }
    protected AbstractContractBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s ��ϸ��Ϣproperty 
     */
    public String getDetail()
    {
        return getString("detail");
    }
    public void setDetail(String item)
    {
        setString("detail", item);
    }
    /**
     * Object:��¼'s ����property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object:��¼'s ����property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:��¼'s ����Keyproperty 
     */
    public String getRowKey()
    {
        return getString("rowKey");
    }
    public void setRowKey(String item)
    {
        setString("rowKey", item);
    }
    /**
     * Object:��¼'s ��������property 
     */
    public com.kingdee.eas.fdc.basedata.DataTypeEnum getDataType()
    {
        return com.kingdee.eas.fdc.basedata.DataTypeEnum.getEnum(getInt("dataType"));
    }
    public void setDataType(com.kingdee.eas.fdc.basedata.DataTypeEnum item)
    {
		if (item != null) {
        setInt("dataType", item.getValue());
		}
    }
    /**
     * Object:��¼'s ��ϸ��Ϣ����IDproperty 
     */
    public String getDetailDefID()
    {
        return getString("detailDefID");
    }
    public void setDetailDefID(String item)
    {
        setString("detailDefID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("97EB5EDE");
    }
}