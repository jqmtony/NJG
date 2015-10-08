package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettleDeclarationBillE1Collection extends AbstractObjectCollection 
{
    public SettleDeclarationBillE1Collection()
    {
        super(SettleDeclarationBillE1Info.class);
    }
    public boolean add(SettleDeclarationBillE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettleDeclarationBillE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettleDeclarationBillE1Info item)
    {
        return removeObject(item);
    }
    public SettleDeclarationBillE1Info get(int index)
    {
        return(SettleDeclarationBillE1Info)getObject(index);
    }
    public SettleDeclarationBillE1Info get(Object key)
    {
        return(SettleDeclarationBillE1Info)getObject(key);
    }
    public void set(int index, SettleDeclarationBillE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(SettleDeclarationBillE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettleDeclarationBillE1Info item)
    {
        return super.indexOf(item);
    }
}