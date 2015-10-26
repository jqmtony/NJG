package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildPriceIndexEindexDataEnumberDataCollection extends AbstractObjectCollection 
{
    public BuildPriceIndexEindexDataEnumberDataCollection()
    {
        super(BuildPriceIndexEindexDataEnumberDataInfo.class);
    }
    public boolean add(BuildPriceIndexEindexDataEnumberDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildPriceIndexEindexDataEnumberDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildPriceIndexEindexDataEnumberDataInfo item)
    {
        return removeObject(item);
    }
    public BuildPriceIndexEindexDataEnumberDataInfo get(int index)
    {
        return(BuildPriceIndexEindexDataEnumberDataInfo)getObject(index);
    }
    public BuildPriceIndexEindexDataEnumberDataInfo get(Object key)
    {
        return(BuildPriceIndexEindexDataEnumberDataInfo)getObject(key);
    }
    public void set(int index, BuildPriceIndexEindexDataEnumberDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildPriceIndexEindexDataEnumberDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildPriceIndexEindexDataEnumberDataInfo item)
    {
        return super.indexOf(item);
    }
}