package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildPriceIndexEindexDataEproductTypeCollection extends AbstractObjectCollection 
{
    public BuildPriceIndexEindexDataEproductTypeCollection()
    {
        super(BuildPriceIndexEindexDataEproductTypeInfo.class);
    }
    public boolean add(BuildPriceIndexEindexDataEproductTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildPriceIndexEindexDataEproductTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildPriceIndexEindexDataEproductTypeInfo item)
    {
        return removeObject(item);
    }
    public BuildPriceIndexEindexDataEproductTypeInfo get(int index)
    {
        return(BuildPriceIndexEindexDataEproductTypeInfo)getObject(index);
    }
    public BuildPriceIndexEindexDataEproductTypeInfo get(Object key)
    {
        return(BuildPriceIndexEindexDataEproductTypeInfo)getObject(key);
    }
    public void set(int index, BuildPriceIndexEindexDataEproductTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildPriceIndexEindexDataEproductTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildPriceIndexEindexDataEproductTypeInfo item)
    {
        return super.indexOf(item);
    }
}