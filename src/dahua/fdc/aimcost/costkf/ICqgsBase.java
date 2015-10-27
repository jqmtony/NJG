package com.kingdee.eas.fdc.aimcost.costkf;

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

public interface ICqgsBase extends IDataBase
{
    public CqgsBaseInfo getCqgsBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CqgsBaseInfo getCqgsBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CqgsBaseInfo getCqgsBaseInfo(String oql) throws BOSException, EASBizException;
    public CqgsBaseCollection getCqgsBaseCollection() throws BOSException;
    public CqgsBaseCollection getCqgsBaseCollection(EntityViewInfo view) throws BOSException;
    public CqgsBaseCollection getCqgsBaseCollection(String oql) throws BOSException;
}