package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectStartRequestEntryCollection extends AbstractObjectCollection 
{
    public ProjectStartRequestEntryCollection()
    {
        super(ProjectStartRequestEntryInfo.class);
    }
    public boolean add(ProjectStartRequestEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectStartRequestEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectStartRequestEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectStartRequestEntryInfo get(int index)
    {
        return(ProjectStartRequestEntryInfo)getObject(index);
    }
    public ProjectStartRequestEntryInfo get(Object key)
    {
        return(ProjectStartRequestEntryInfo)getObject(key);
    }
    public void set(int index, ProjectStartRequestEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectStartRequestEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectStartRequestEntryInfo item)
    {
        return super.indexOf(item);
    }
}