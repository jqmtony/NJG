package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JudgesComfirmEntryCollection extends AbstractObjectCollection 
{
    public JudgesComfirmEntryCollection()
    {
        super(JudgesComfirmEntryInfo.class);
    }
    public boolean add(JudgesComfirmEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JudgesComfirmEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JudgesComfirmEntryInfo item)
    {
        return removeObject(item);
    }
    public JudgesComfirmEntryInfo get(int index)
    {
        return(JudgesComfirmEntryInfo)getObject(index);
    }
    public JudgesComfirmEntryInfo get(Object key)
    {
        return(JudgesComfirmEntryInfo)getObject(key);
    }
    public void set(int index, JudgesComfirmEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JudgesComfirmEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JudgesComfirmEntryInfo item)
    {
        return super.indexOf(item);
    }
}