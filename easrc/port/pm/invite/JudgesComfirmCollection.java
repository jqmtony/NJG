package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JudgesComfirmCollection extends AbstractObjectCollection 
{
    public JudgesComfirmCollection()
    {
        super(JudgesComfirmInfo.class);
    }
    public boolean add(JudgesComfirmInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JudgesComfirmCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JudgesComfirmInfo item)
    {
        return removeObject(item);
    }
    public JudgesComfirmInfo get(int index)
    {
        return(JudgesComfirmInfo)getObject(index);
    }
    public JudgesComfirmInfo get(Object key)
    {
        return(JudgesComfirmInfo)getObject(key);
    }
    public void set(int index, JudgesComfirmInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JudgesComfirmInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JudgesComfirmInfo item)
    {
        return super.indexOf(item);
    }
}