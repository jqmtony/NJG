package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialIndexValueCollection extends AbstractObjectCollection 
{
    public MaterialIndexValueCollection()
    {
        super(MaterialIndexValueInfo.class);
    }
    public boolean add(MaterialIndexValueInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialIndexValueCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialIndexValueInfo item)
    {
        return removeObject(item);
    }
    public MaterialIndexValueInfo get(int index)
    {
        return(MaterialIndexValueInfo)getObject(index);
    }
    public MaterialIndexValueInfo get(Object key)
    {
        return(MaterialIndexValueInfo)getObject(key);
    }
    public void set(int index, MaterialIndexValueInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialIndexValueInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialIndexValueInfo item)
    {
        return super.indexOf(item);
    }
}