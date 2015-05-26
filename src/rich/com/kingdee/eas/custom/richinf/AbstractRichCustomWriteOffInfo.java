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
     * Object:���쵥��ͻ���Ʊ������'s �Ƿ�����ƾ֤property 
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
     * Object: ���쵥��ͻ���Ʊ������ 's Ӧ�շ�Ʊ property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffE2Collection getE2()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffE2Collection)get("E2");
    }
    /**
     * Object: ���쵥��ͻ���Ʊ������ 's ����Ա property 
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
     * Object: ���쵥��ͻ���Ʊ������ 's ��Ʊ property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffFpEntryCollection getFpEntry()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffFpEntryCollection)get("FpEntry");
    }
    /**
     * Object: ���쵥��ͻ���Ʊ������ 's ���쵥 property 
     */
    public com.kingdee.eas.custom.richinf.RichCustomWriteOffDjEntryCollection getDjEntry()
    {
        return (com.kingdee.eas.custom.richinf.RichCustomWriteOffDjEntryCollection)get("DjEntry");
    }
    /**
     * Object: ���쵥��ͻ���Ʊ������ 's ��Ʊ̧ͷ property 
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