package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettleDeclarationBillEntryCollection extends AbstractObjectCollection 
{
    public SettleDeclarationBillEntryCollection()
    {
        super(SettleDeclarationBillEntryInfo.class);
    }
    public boolean add(SettleDeclarationBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettleDeclarationBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettleDeclarationBillEntryInfo item)
    {
        return removeObject(item);
    }
    public SettleDeclarationBillEntryInfo get(int index)
    {
        return(SettleDeclarationBillEntryInfo)getObject(index);
    }
    public SettleDeclarationBillEntryInfo get(Object key)
    {
        return(SettleDeclarationBillEntryInfo)getObject(key);
    }
    public void set(int index, SettleDeclarationBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettleDeclarationBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettleDeclarationBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}