package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PreProjectTempE1Collection extends AbstractObjectCollection 
{
    public PreProjectTempE1Collection()
    {
        super(PreProjectTempE1Info.class);
    }
    public boolean add(PreProjectTempE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(PreProjectTempE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PreProjectTempE1Info item)
    {
        return removeObject(item);
    }
    public PreProjectTempE1Info get(int index)
    {
        return(PreProjectTempE1Info)getObject(index);
    }
    public PreProjectTempE1Info get(Object key)
    {
        return(PreProjectTempE1Info)getObject(key);
    }
    public void set(int index, PreProjectTempE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(PreProjectTempE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PreProjectTempE1Info item)
    {
        return super.indexOf(item);
    }
}