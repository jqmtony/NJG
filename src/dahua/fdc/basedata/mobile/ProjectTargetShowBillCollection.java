package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectTargetShowBillCollection extends AbstractObjectCollection 
{
    public ProjectTargetShowBillCollection()
    {
        super(ProjectTargetShowBillInfo.class);
    }
    public boolean add(ProjectTargetShowBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectTargetShowBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectTargetShowBillInfo item)
    {
        return removeObject(item);
    }
    public ProjectTargetShowBillInfo get(int index)
    {
        return(ProjectTargetShowBillInfo)getObject(index);
    }
    public ProjectTargetShowBillInfo get(Object key)
    {
        return(ProjectTargetShowBillInfo)getObject(key);
    }
    public void set(int index, ProjectTargetShowBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectTargetShowBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectTargetShowBillInfo item)
    {
        return super.indexOf(item);
    }
}