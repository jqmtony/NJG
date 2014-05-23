package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PreProjectTempCollection extends AbstractObjectCollection 
{
    public PreProjectTempCollection()
    {
        super(PreProjectTempInfo.class);
    }
    public boolean add(PreProjectTempInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PreProjectTempCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PreProjectTempInfo item)
    {
        return removeObject(item);
    }
    public PreProjectTempInfo get(int index)
    {
        return(PreProjectTempInfo)getObject(index);
    }
    public PreProjectTempInfo get(Object key)
    {
        return(PreProjectTempInfo)getObject(key);
    }
    public void set(int index, PreProjectTempInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PreProjectTempInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PreProjectTempInfo item)
    {
        return super.indexOf(item);
    }
}