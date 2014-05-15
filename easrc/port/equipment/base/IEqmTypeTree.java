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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IEqmTypeTree extends ITreeBase
{
    public EqmTypeTreeInfo getEqmTypeTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public EqmTypeTreeInfo getEqmTypeTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public EqmTypeTreeInfo getEqmTypeTreeInfo(String oql) throws BOSException, EASBizException;
    public EqmTypeTreeCollection getEqmTypeTreeCollection() throws BOSException;
    public EqmTypeTreeCollection getEqmTypeTreeCollection(EntityViewInfo view) throws BOSException;
    public EqmTypeTreeCollection getEqmTypeTreeCollection(String oql) throws BOSException;
}