package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectBudgetE1Collection extends AbstractObjectCollection 
{
    public ProjectBudgetE1Collection()
    {
        super(ProjectBudgetE1Info.class);
    }
    public boolean add(ProjectBudgetE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectBudgetE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectBudgetE1Info item)
    {
        return removeObject(item);
    }
    public ProjectBudgetE1Info get(int index)
    {
        return(ProjectBudgetE1Info)getObject(index);
    }
    public ProjectBudgetE1Info get(Object key)
    {
        return(ProjectBudgetE1Info)getObject(key);
    }
    public void set(int index, ProjectBudgetE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectBudgetE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectBudgetE1Info item)
    {
        return super.indexOf(item);
    }
}