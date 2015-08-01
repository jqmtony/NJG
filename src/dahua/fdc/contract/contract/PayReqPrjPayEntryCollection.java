package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayReqPrjPayEntryCollection extends AbstractObjectCollection 
{
    public PayReqPrjPayEntryCollection()
    {
        super(PayReqPrjPayEntryInfo.class);
    }
    public boolean add(PayReqPrjPayEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayReqPrjPayEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayReqPrjPayEntryInfo item)
    {
        return removeObject(item);
    }
    public PayReqPrjPayEntryInfo get(int index)
    {
        return(PayReqPrjPayEntryInfo)getObject(index);
    }
    public PayReqPrjPayEntryInfo get(Object key)
    {
        return(PayReqPrjPayEntryInfo)getObject(key);
    }
    public void set(int index, PayReqPrjPayEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayReqPrjPayEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayReqPrjPayEntryInfo item)
    {
        return super.indexOf(item);
    }
}