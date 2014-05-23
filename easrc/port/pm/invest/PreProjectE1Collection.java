package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PreProjectE1Collection extends AbstractObjectCollection 
{
    public PreProjectE1Collection()
    {
        super(PreProjectE1Info.class);
    }
    public boolean add(PreProjectE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(PreProjectE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PreProjectE1Info item)
    {
        return removeObject(item);
    }
    public PreProjectE1Info get(int index)
    {
        return(PreProjectE1Info)getObject(index);
    }
    public PreProjectE1Info get(Object key)
    {
        return(PreProjectE1Info)getObject(key);
    }
    public void set(int index, PreProjectE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(PreProjectE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PreProjectE1Info item)
    {
        return super.indexOf(item);
    }
}