package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectTargetEntryCollection extends AbstractObjectCollection 
{
    public ProjectTargetEntryCollection()
    {
        super(ProjectTargetEntryInfo.class);
    }
    public boolean add(ProjectTargetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectTargetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectTargetEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectTargetEntryInfo get(int index)
    {
        return(ProjectTargetEntryInfo)getObject(index);
    }
    public ProjectTargetEntryInfo get(Object key)
    {
        return(ProjectTargetEntryInfo)getObject(key);
    }
    public void set(int index, ProjectTargetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectTargetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectTargetEntryInfo item)
    {
        return super.indexOf(item);
    }
}