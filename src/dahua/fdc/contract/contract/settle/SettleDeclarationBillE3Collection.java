package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettleDeclarationBillE3Collection extends AbstractObjectCollection 
{
    public SettleDeclarationBillE3Collection()
    {
        super(SettleDeclarationBillE3Info.class);
    }
    public boolean add(SettleDeclarationBillE3Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettleDeclarationBillE3Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettleDeclarationBillE3Info item)
    {
        return removeObject(item);
    }
    public SettleDeclarationBillE3Info get(int index)
    {
        return(SettleDeclarationBillE3Info)getObject(index);
    }
    public SettleDeclarationBillE3Info get(Object key)
    {
        return(SettleDeclarationBillE3Info)getObject(key);
    }
    public void set(int index, SettleDeclarationBillE3Info item)
    {
        setObject(index, item);
    }
    public boolean contains(SettleDeclarationBillE3Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettleDeclarationBillE3Info item)
    {
        return super.indexOf(item);
    }
}