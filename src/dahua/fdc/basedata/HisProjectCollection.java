package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HisProjectCollection extends AbstractObjectCollection 
{
    public HisProjectCollection()
    {
        super(HisProjectInfo.class);
    }
    public boolean add(HisProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HisProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HisProjectInfo item)
    {
        return removeObject(item);
    }
    public HisProjectInfo get(int index)
    {
        return(HisProjectInfo)getObject(index);
    }
    public HisProjectInfo get(Object key)
    {
        return(HisProjectInfo)getObject(key);
    }
    public void set(int index, HisProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HisProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HisProjectInfo item)
    {
        return super.indexOf(item);
    }
}