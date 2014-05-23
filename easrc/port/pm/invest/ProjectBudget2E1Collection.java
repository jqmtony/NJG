package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectBudget2E1Collection extends AbstractObjectCollection 
{
    public ProjectBudget2E1Collection()
    {
        super(ProjectBudget2E1Info.class);
    }
    public boolean add(ProjectBudget2E1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectBudget2E1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectBudget2E1Info item)
    {
        return removeObject(item);
    }
    public ProjectBudget2E1Info get(int index)
    {
        return(ProjectBudget2E1Info)getObject(index);
    }
    public ProjectBudget2E1Info get(Object key)
    {
        return(ProjectBudget2E1Info)getObject(key);
    }
    public void set(int index, ProjectBudget2E1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectBudget2E1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectBudget2E1Info item)
    {
        return super.indexOf(item);
    }
}