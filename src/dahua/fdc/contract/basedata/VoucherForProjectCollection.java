package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class VoucherForProjectCollection extends AbstractObjectCollection 
{
    public VoucherForProjectCollection()
    {
        super(VoucherForProjectInfo.class);
    }
    public boolean add(VoucherForProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(VoucherForProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(VoucherForProjectInfo item)
    {
        return removeObject(item);
    }
    public VoucherForProjectInfo get(int index)
    {
        return(VoucherForProjectInfo)getObject(index);
    }
    public VoucherForProjectInfo get(Object key)
    {
        return(VoucherForProjectInfo)getObject(key);
    }
    public void set(int index, VoucherForProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(VoucherForProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(VoucherForProjectInfo item)
    {
        return super.indexOf(item);
    }
}