package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildPriceIndexEindexDataEbuildNumberCollection extends AbstractObjectCollection 
{
    public BuildPriceIndexEindexDataEbuildNumberCollection()
    {
        super(BuildPriceIndexEindexDataEbuildNumberInfo.class);
    }
    public boolean add(BuildPriceIndexEindexDataEbuildNumberInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildPriceIndexEindexDataEbuildNumberCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildPriceIndexEindexDataEbuildNumberInfo item)
    {
        return removeObject(item);
    }
    public BuildPriceIndexEindexDataEbuildNumberInfo get(int index)
    {
        return(BuildPriceIndexEindexDataEbuildNumberInfo)getObject(index);
    }
    public BuildPriceIndexEindexDataEbuildNumberInfo get(Object key)
    {
        return(BuildPriceIndexEindexDataEbuildNumberInfo)getObject(key);
    }
    public void set(int index, BuildPriceIndexEindexDataEbuildNumberInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildPriceIndexEindexDataEbuildNumberInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildPriceIndexEindexDataEbuildNumberInfo item)
    {
        return super.indexOf(item);
    }
}