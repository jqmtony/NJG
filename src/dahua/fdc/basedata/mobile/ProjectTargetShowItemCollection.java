package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectTargetShowItemCollection extends AbstractObjectCollection 
{
    public ProjectTargetShowItemCollection()
    {
        super(ProjectTargetShowItemInfo.class);
    }
    public boolean add(ProjectTargetShowItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectTargetShowItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectTargetShowItemInfo item)
    {
        return removeObject(item);
    }
    public ProjectTargetShowItemInfo get(int index)
    {
        return(ProjectTargetShowItemInfo)getObject(index);
    }
    public ProjectTargetShowItemInfo get(Object key)
    {
        return(ProjectTargetShowItemInfo)getObject(key);
    }
    public void set(int index, ProjectTargetShowItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectTargetShowItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectTargetShowItemInfo item)
    {
        return super.indexOf(item);
    }
}