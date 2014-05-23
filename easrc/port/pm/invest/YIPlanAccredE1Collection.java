package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class YIPlanAccredE1Collection extends AbstractObjectCollection 
{
    public YIPlanAccredE1Collection()
    {
        super(YIPlanAccredE1Info.class);
    }
    public boolean add(YIPlanAccredE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(YIPlanAccredE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(YIPlanAccredE1Info item)
    {
        return removeObject(item);
    }
    public YIPlanAccredE1Info get(int index)
    {
        return(YIPlanAccredE1Info)getObject(index);
    }
    public YIPlanAccredE1Info get(Object key)
    {
        return(YIPlanAccredE1Info)getObject(key);
    }
    public void set(int index, YIPlanAccredE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(YIPlanAccredE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(YIPlanAccredE1Info item)
    {
        return super.indexOf(item);
    }
}