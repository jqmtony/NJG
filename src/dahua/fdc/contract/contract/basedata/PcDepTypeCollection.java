package com.kingdee.eas.fdc.contract.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PcDepTypeCollection extends AbstractObjectCollection 
{
    public PcDepTypeCollection()
    {
        super(PcDepTypeInfo.class);
    }
    public boolean add(PcDepTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PcDepTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PcDepTypeInfo item)
    {
        return removeObject(item);
    }
    public PcDepTypeInfo get(int index)
    {
        return(PcDepTypeInfo)getObject(index);
    }
    public PcDepTypeInfo get(Object key)
    {
        return(PcDepTypeInfo)getObject(key);
    }
    public void set(int index, PcDepTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PcDepTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PcDepTypeInfo item)
    {
        return super.indexOf(item);
    }
}