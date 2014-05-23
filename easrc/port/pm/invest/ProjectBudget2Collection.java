package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectBudget2Collection extends AbstractObjectCollection 
{
    public ProjectBudget2Collection()
    {
        super(ProjectBudget2Info.class);
    }
    public boolean add(ProjectBudget2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectBudget2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectBudget2Info item)
    {
        return removeObject(item);
    }
    public ProjectBudget2Info get(int index)
    {
        return(ProjectBudget2Info)getObject(index);
    }
    public ProjectBudget2Info get(Object key)
    {
        return(ProjectBudget2Info)getObject(key);
    }
    public void set(int index, ProjectBudget2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectBudget2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectBudget2Info item)
    {
        return super.indexOf(item);
    }
}