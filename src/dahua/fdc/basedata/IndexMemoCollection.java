package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IndexMemoCollection extends AbstractObjectCollection 
{
    public IndexMemoCollection()
    {
        super(IndexMemoInfo.class);
    }
    public boolean add(IndexMemoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IndexMemoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IndexMemoInfo item)
    {
        return removeObject(item);
    }
    public IndexMemoInfo get(int index)
    {
        return(IndexMemoInfo)getObject(index);
    }
    public IndexMemoInfo get(Object key)
    {
        return(IndexMemoInfo)getObject(key);
    }
    public void set(int index, IndexMemoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IndexMemoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IndexMemoInfo item)
    {
        return super.indexOf(item);
    }
}