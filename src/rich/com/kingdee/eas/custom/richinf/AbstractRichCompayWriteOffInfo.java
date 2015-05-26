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
     * Object:���쵥���ڲ���Ʊ������'s �Ƿ�����ƾ֤property 
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
     * Object: ���쵥���ڲ���Ʊ������ 's Ӧ�շ�Ʊ property 
     */
    public com.kingdee.eas.custom.richinf.RichCompayWriteOffE2Collection getE2()
    {
        return (com.kingdee.eas.custom.richinf.RichCompayWriteOffE2Collection)get("E2");
    }
    /**
     * Object: ���쵥���ڲ���Ʊ������ 's ����Ա property 
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
     * Object: ���쵥���ڲ���Ʊ������ 's ��Ʊ property 
     */
    public com.kingdee.eas.custom.richinf.RichCompayWriteOffFpEntryCollection getFpEntry()
    {
        return (com.kingdee.eas.custom.richinf.RichCompayWriteOffFpEntryCollection)get("FpEntry");
    }
    /**
     * Object: ���쵥���ڲ���Ʊ������ 's ���쵥 property 
     */
    public com.kingdee.eas.custom.richinf.RichCompayWriteOffDjEntryCollection getDjEntry()
    {
        return (com.kingdee.eas.custom.richinf.RichCompayWriteOffDjEntryCollection)get("DjEntry");
    }
    /**
     * Object: ���쵥���ڲ���Ʊ������ 's ��Ʊ̧ͷ property 
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