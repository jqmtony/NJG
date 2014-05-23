package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierAttachListInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketSupplierAttachListInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierAttachListInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 档案附件清单 's 组别 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:档案附件清单's 是否启用property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    /**
     * Object:档案附件清单's 备注：property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    /**
     * Object: 档案附件清单 's 档案分类 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo getSupplierFileType()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo)get("supplierFileType");
    }
    public void setSupplierFileType(com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo item)
    {
        put("supplierFileType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("853A8D61");
    }
}