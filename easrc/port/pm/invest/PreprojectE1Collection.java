package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PreprojectE1Collection extends AbstractObjectCollection 
{
    public PreprojectE1Collection()
    {
        super(PreprojectE1Info.class);
    }
    public boolean add(PreprojectE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(PreprojectE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PreprojectE1Info item)
    {
        return removeObject(item);
    }
    public PreprojectE1Info get(int index)
    {
        return(PreprojectE1Info)getObject(index);
    }
    public PreprojectE1Info get(Object key)
    {
        return(PreprojectE1Info)getObject(key);
    }
    public void set(int index, PreprojectE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(PreprojectE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PreprojectE1Info item)
    {
        return super.indexOf(item);
    }
}