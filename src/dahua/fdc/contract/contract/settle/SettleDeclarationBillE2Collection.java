package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettleDeclarationBillE2Collection extends AbstractObjectCollection 
{
    public SettleDeclarationBillE2Collection()
    {
        super(SettleDeclarationBillE2Info.class);
    }
    public boolean add(SettleDeclarationBillE2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettleDeclarationBillE2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettleDeclarationBillE2Info item)
    {
        return removeObject(item);
    }
    public SettleDeclarationBillE2Info get(int index)
    {
        return(SettleDeclarationBillE2Info)getObject(index);
    }
    public SettleDeclarationBillE2Info get(Object key)
    {
        return(SettleDeclarationBillE2Info)getObject(key);
    }
    public void set(int index, SettleDeclarationBillE2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(SettleDeclarationBillE2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettleDeclarationBillE2Info item)
    {
        return super.indexOf(item);
    }
}