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

public interface IBasePointTemp extends IDataBase
{
    public BasePointTempInfo getBasePointTempInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BasePointTempInfo getBasePointTempInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BasePointTempInfo getBasePointTempInfo(String oql) throws BOSException, EASBizException;
    public BasePointTempCollection getBasePointTempCollection() throws BOSException;
    public BasePointTempCollection getBasePointTempCollection(EntityViewInfo view) throws BOSException;
    public BasePointTempCollection getBasePointTempCollection(String oql) throws BOSException;
}