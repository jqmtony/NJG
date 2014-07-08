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

public interface IInitialConnection extends IDataBase
{
    public InitialConnectionInfo getInitialConnectionInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InitialConnectionInfo getInitialConnectionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InitialConnectionInfo getInitialConnectionInfo(String oql) throws BOSException, EASBizException;
    public InitialConnectionCollection getInitialConnectionCollection() throws BOSException;
    public InitialConnectionCollection getInitialConnectionCollection(EntityViewInfo view) throws BOSException;
    public InitialConnectionCollection getInitialConnectionCollection(String oql) throws BOSException;
}