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

public interface IProjectType extends IDataBase
{
    public ProjectTypeInfo getProjectTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectTypeInfo getProjectTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectTypeInfo getProjectTypeInfo(String oql) throws BOSException, EASBizException;
    public ProjectTypeCollection getProjectTypeCollection() throws BOSException;
    public ProjectTypeCollection getProjectTypeCollection(EntityViewInfo view) throws BOSException;
    public ProjectTypeCollection getProjectTypeCollection(String oql) throws BOSException;
}