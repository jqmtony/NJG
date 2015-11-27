package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TreeNodeCollection extends AbstractObjectCollection 
{
    public TreeNodeCollection()
    {
        super(TreeNodeInfo.class);
    }
    public boolean add(TreeNodeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TreeNodeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TreeNodeInfo item)
    {
        return removeObject(item);
    }
    public TreeNodeInfo get(int index)
    {
        return(TreeNodeInfo)getObject(index);
    }
    public TreeNodeInfo get(Object key)
    {
        return(TreeNodeInfo)getObject(key);
    }
    public void set(int index, TreeNodeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TreeNodeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TreeNodeInfo item)
    {
        return super.indexOf(item);
    }
}