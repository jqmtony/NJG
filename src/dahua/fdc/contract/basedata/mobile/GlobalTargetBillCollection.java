package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class GlobalTargetBillCollection extends AbstractObjectCollection 
{
    public GlobalTargetBillCollection()
    {
        super(GlobalTargetBillInfo.class);
    }
    public boolean add(GlobalTargetBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(GlobalTargetBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(GlobalTargetBillInfo item)
    {
        return removeObject(item);
    }
    public GlobalTargetBillInfo get(int index)
    {
        return(GlobalTargetBillInfo)getObject(index);
    }
    public GlobalTargetBillInfo get(Object key)
    {
        return(GlobalTargetBillInfo)getObject(key);
    }
    public void set(int index, GlobalTargetBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(GlobalTargetBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(GlobalTargetBillInfo item)
    {
        return super.indexOf(item);
    }
}