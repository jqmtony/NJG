package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettleDeclarationBillEntry3Collection extends AbstractObjectCollection 
{
    public SettleDeclarationBillEntry3Collection()
    {
        super(SettleDeclarationBillEntry3Info.class);
    }
    public boolean add(SettleDeclarationBillEntry3Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettleDeclarationBillEntry3Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettleDeclarationBillEntry3Info item)
    {
        return removeObject(item);
    }
    public SettleDeclarationBillEntry3Info get(int index)
    {
        return(SettleDeclarationBillEntry3Info)getObject(index);
    }
    public SettleDeclarationBillEntry3Info get(Object key)
    {
        return(SettleDeclarationBillEntry3Info)getObject(key);
    }
    public void set(int index, SettleDeclarationBillEntry3Info item)
    {
        setObject(index, item);
    }
    public boolean contains(SettleDeclarationBillEntry3Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettleDeclarationBillEntry3Info item)
    {
        return super.indexOf(item);
    }
}