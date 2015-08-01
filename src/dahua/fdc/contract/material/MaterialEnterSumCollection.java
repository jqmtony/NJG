package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialEnterSumCollection extends AbstractObjectCollection 
{
    public MaterialEnterSumCollection()
    {
        super(MaterialEnterSumInfo.class);
    }
    public boolean add(MaterialEnterSumInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialEnterSumCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialEnterSumInfo item)
    {
        return removeObject(item);
    }
    public MaterialEnterSumInfo get(int index)
    {
        return(MaterialEnterSumInfo)getObject(index);
    }
    public MaterialEnterSumInfo get(Object key)
    {
        return(MaterialEnterSumInfo)getObject(key);
    }
    public void set(int index, MaterialEnterSumInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialEnterSumInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialEnterSumInfo item)
    {
        return super.indexOf(item);
    }
}