package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichCustomWriteOffInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractRichCustomWriteOffInfo()
    {
        this("id");
    }
    protected AbstractRichCustomWriteOffInfo(String pkField)
    {
        super(pkField);
        put("E2", new com.kingdee.eas.custom.richinf.RichCustomWriteOffE2Collection());
        put("DjEntry", new com.kingdee.eas.custom.richinf.RichCustomWriteOffDjEntryCollection());
        put("FpEntry", new com.kingdee.eas.custom.richinf.RichCustomWriteOffFpEntryCollection());
    }
    /**
     * Object:到检单与客户发票核销单's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: 到检单与客户发票核销单 's 应收发票 property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffE2Collection getE2()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffE2Collection)get("E2");
    }
    /**
     * Object: 到检单与客户发票核销单 's 销售员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getSales()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("sales");
    }
    public void setSales(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("sales", item);
    }
    /**
     * Object: 到检单与客户发票核销单 's 发票 property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffFpEntryCollection getFpEntry()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffFpEntryCollection)get("FpEntry");
    }
    /**
     * Object: 到检单与客户发票核销单 's 到检单 property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffDjEntryCollection getDjEntry()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffDjEntryCollection)get("DjEntry");
    }
    /**
     * Object: 到检单与客户发票核销单 's 开票抬头 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getKpCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("kpCustomer");
    }
    public void setKpCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("kpCustomer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C6D7BD2B");
    }
}