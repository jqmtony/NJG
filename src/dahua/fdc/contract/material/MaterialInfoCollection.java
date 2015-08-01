package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialInfoCollection extends AbstractObjectCollection 
{
    public MaterialInfoCollection()
    {
        super(MaterialInfoInfo.class);
    }
    public boolean add(MaterialInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialInfoInfo item)
    {
        return removeObject(item);
    }
    public MaterialInfoInfo get(int index)
    {
        return(MaterialInfoInfo)getObject(index);
    }
    public MaterialInfoInfo get(Object key)
    {
        return(MaterialInfoInfo)getObject(key);
    }
    public void set(int index, MaterialInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialInfoInfo item)
    {
        return super.indexOf(item);
    }
}