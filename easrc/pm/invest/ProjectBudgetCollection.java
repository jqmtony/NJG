package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectBudgetCollection extends AbstractObjectCollection 
{
    public ProjectBudgetCollection()
    {
        super(ProjectBudgetInfo.class);
    }
    public boolean add(ProjectBudgetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectBudgetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectBudgetInfo item)
    {
        return removeObject(item);
    }
    public ProjectBudgetInfo get(int index)
    {
        return(ProjectBudgetInfo)getObject(index);
    }
    public ProjectBudgetInfo get(Object key)
    {
        return(ProjectBudgetInfo)getObject(key);
    }
    public void set(int index, ProjectBudgetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectBudgetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectBudgetInfo item)
    {
        return super.indexOf(item);
    }
}