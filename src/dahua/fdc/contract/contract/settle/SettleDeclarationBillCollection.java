package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettleDeclarationBillCollection extends AbstractObjectCollection 
{
    public SettleDeclarationBillCollection()
    {
        super(SettleDeclarationBillInfo.class);
    }
    public boolean add(SettleDeclarationBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettleDeclarationBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettleDeclarationBillInfo item)
    {
        return removeObject(item);
    }
    public SettleDeclarationBillInfo get(int index)
    {
        return(SettleDeclarationBillInfo)getObject(index);
    }
    public SettleDeclarationBillInfo get(Object key)
    {
        return(SettleDeclarationBillInfo)getObject(key);
    }
    public void set(int index, SettleDeclarationBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettleDeclarationBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettleDeclarationBillInfo item)
    {
        return super.indexOf(item);
    }
}