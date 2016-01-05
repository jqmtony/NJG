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

public interface IUses extends IDataBase
{
    public UsesInfo getUsesInfo(IObjectPK pk) throws BOSException, EASBizException;
    public UsesInfo getUsesInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public UsesInfo getUsesInfo(String oql) throws BOSException, EASBizException;
    public UsesCollection getUsesCollection() throws BOSException;
    public UsesCollection getUsesCollection(EntityViewInfo view) throws BOSException;
    public UsesCollection getUsesCollection(String oql) throws BOSException;
}