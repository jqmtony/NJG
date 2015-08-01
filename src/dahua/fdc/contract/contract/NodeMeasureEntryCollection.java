package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NodeMeasureEntryCollection extends AbstractObjectCollection 
{
    public NodeMeasureEntryCollection()
    {
        super(NodeMeasureEntryInfo.class);
    }
    public boolean add(NodeMeasureEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NodeMeasureEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NodeMeasureEntryInfo item)
    {
        return removeObject(item);
    }
    public NodeMeasureEntryInfo get(int index)
    {
        return(NodeMeasureEntryInfo)getObject(index);
    }
    public NodeMeasureEntryInfo get(Object key)
    {
        return(NodeMeasureEntryInfo)getObject(key);
    }
    public void set(int index, NodeMeasureEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NodeMeasureEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NodeMeasureEntryInfo item)
    {
        return super.indexOf(item);
    }
}