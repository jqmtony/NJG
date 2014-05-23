package com.kingdee.eas.port.equipment.base;

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

public interface IEqmType extends IDataBase
{
    public EqmTypeInfo getEqmTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public EqmTypeInfo getEqmTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public EqmTypeInfo getEqmTypeInfo(String oql) throws BOSException, EASBizException;
    public EqmTypeCollection getEqmTypeCollection() throws BOSException;
    public EqmTypeCollection getEqmTypeCollection(EntityViewInfo view) throws BOSException;
    public EqmTypeCollection getEqmTypeCollection(String oql) throws BOSException;
}