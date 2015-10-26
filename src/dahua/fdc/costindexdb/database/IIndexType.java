package com.kingdee.eas.fdc.costindexdb.database;

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

public interface IIndexType extends IDataBase
{
    public IndexTypeInfo getIndexTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public IndexTypeInfo getIndexTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public IndexTypeInfo getIndexTypeInfo(String oql) throws BOSException, EASBizException;
    public IndexTypeCollection getIndexTypeCollection() throws BOSException;
    public IndexTypeCollection getIndexTypeCollection(EntityViewInfo view) throws BOSException;
    public IndexTypeCollection getIndexTypeCollection(String oql) throws BOSException;
}