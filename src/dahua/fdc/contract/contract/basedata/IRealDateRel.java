package com.kingdee.eas.fdc.contract.basedata;

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

public interface IRealDateRel extends IDataBase
{
    public RealDateRelInfo getRealDateRelInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RealDateRelInfo getRealDateRelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RealDateRelInfo getRealDateRelInfo(String oql) throws BOSException, EASBizException;
    public RealDateRelCollection getRealDateRelCollection() throws BOSException;
    public RealDateRelCollection getRealDateRelCollection(EntityViewInfo view) throws BOSException;
    public RealDateRelCollection getRealDateRelCollection(String oql) throws BOSException;
    public void importTemp(RealDateRelInfo model) throws BOSException;
    public void importGroup(RealDateRelInfo model) throws BOSException;
}