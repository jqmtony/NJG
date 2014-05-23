package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IBuildType extends IDataBase
{
    public BuildTypeInfo getBuildTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BuildTypeInfo getBuildTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BuildTypeInfo getBuildTypeInfo(String oql) throws BOSException, EASBizException;
    public BuildTypeCollection getBuildTypeCollection() throws BOSException;
    public BuildTypeCollection getBuildTypeCollection(EntityViewInfo view) throws BOSException;
    public BuildTypeCollection getBuildTypeCollection(String oql) throws BOSException;
}