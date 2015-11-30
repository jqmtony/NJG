package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettleDeclarationBillEntry2Collection extends AbstractObjectCollection 
{
    public SettleDeclarationBillEntry2Collection()
    {
        super(SettleDeclarationBillEntry2Info.class);
    }
    public boolean add(SettleDeclarationBillEntry2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettleDeclarationBillEntry2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettleDeclarationBillEntry2Info item)
    {
        return removeObject(item);
    }
    public SettleDeclarationBillEntry2Info get(int index)
    {
        return(SettleDeclarationBillEntry2Info)getObject(index);
    }
    public SettleDeclarationBillEntry2Info get(Object key)
    {
        return(SettleDeclarationBillEntry2Info)getObject(key);
    }
    public void set(int index, SettleDeclarationBillEntry2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(SettleDeclarationBillEntry2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettleDeclarationBillEntry2Info item)
    {
        return super.indexOf(item);
    }
}