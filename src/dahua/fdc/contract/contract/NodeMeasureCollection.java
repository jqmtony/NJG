package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NodeMeasureCollection extends AbstractObjectCollection 
{
    public NodeMeasureCollection()
    {
        super(NodeMeasureInfo.class);
    }
    public boolean add(NodeMeasureInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NodeMeasureCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NodeMeasureInfo item)
    {
        return removeObject(item);
    }
    public NodeMeasureInfo get(int index)
    {
        return(NodeMeasureInfo)getObject(index);
    }
    public NodeMeasureInfo get(Object key)
    {
        return(NodeMeasureInfo)getObject(key);
    }
    public void set(int index, NodeMeasureInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NodeMeasureInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NodeMeasureInfo item)
    {
        return super.indexOf(item);
    }
}