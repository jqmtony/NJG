package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class YIPlanAccredE1E2Collection extends AbstractObjectCollection 
{
    public YIPlanAccredE1E2Collection()
    {
        super(YIPlanAccredE1E2Info.class);
    }
    public boolean add(YIPlanAccredE1E2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(YIPlanAccredE1E2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(YIPlanAccredE1E2Info item)
    {
        return removeObject(item);
    }
    public YIPlanAccredE1E2Info get(int index)
    {
        return(YIPlanAccredE1E2Info)getObject(index);
    }
    public YIPlanAccredE1E2Info get(Object key)
    {
        return(YIPlanAccredE1E2Info)getObject(key);
    }
    public void set(int index, YIPlanAccredE1E2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(YIPlanAccredE1E2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(YIPlanAccredE1E2Info item)
    {
        return super.indexOf(item);
    }
}