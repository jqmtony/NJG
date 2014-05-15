package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JudgesTreeCollection extends AbstractObjectCollection 
{
    public JudgesTreeCollection()
    {
        super(JudgesTreeInfo.class);
    }
    public boolean add(JudgesTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JudgesTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JudgesTreeInfo item)
    {
        return removeObject(item);
    }
    public JudgesTreeInfo get(int index)
    {
        return(JudgesTreeInfo)getObject(index);
    }
    public JudgesTreeInfo get(Object key)
    {
        return(JudgesTreeInfo)getObject(key);
    }
    public void set(int index, JudgesTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JudgesTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JudgesTreeInfo item)
    {
        return super.indexOf(item);
    }
}