package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichCompayWriteOffInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractRichCompayWriteOffInfo()
    {
        this("id");
    }
    protected AbstractRichCompayWriteOffInfo(String pkField)
    {
        super(pkField);
        put("E2", new com.kingdee.eas.custom.richinf.RichCompayWriteOffE2Collection());
        put("DjEntry", new com.kingdee.eas.custom.richinf.RichCompayWriteOffDjEntryCollection());
        put("FpEntry", new com.kingdee.eas.custom.richinf.RichCompayWriteOffFpEntryCollection());
    }
    /**
     * Object:到检单与内部发票核销单's 是否生成凭证property 
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
     * Object: 到检单与内部发票核销单 's 应收发票 property 
     */
    public com.kingdee.eas.custom.richinf.RichCompayWriteOffE2Collection getE2()
    {
        return (com.kingdee.eas.custom.richinf.RichCompayWriteOffE2Collection)get("E2");
    }
    /**
     * Object: 到检单与内部发票核销单 's 销售员 property 
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
     * Object: 到检单与内部发票核销单 's 发票 property 
     */
    public com.kingdee.eas.custom.richinf.RichCompayWriteOffFpEntryCollection getFpEntry()
    {
        return (com.kingdee.eas.custom.richinf.RichCompayWriteOffFpEntryCollection)get("FpEntry");
    }
    /**
     * Object: 到检单与内部发票核销单 's 到检单 property 
     */
    public com.kingdee.eas.custom.richinf.RichCompayWriteOffDjEntryCollection getDjEntry()
    {
        return (com.kingdee.eas.custom.richinf.RichCompayWriteOffDjEntryCollection)get("DjEntry");
    }
    /**
     * Object: 到检单与内部发票核销单 's 开票抬头 property 
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
        return new BOSObjectType("4539EF41");
    }
}