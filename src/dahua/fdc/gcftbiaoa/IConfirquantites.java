package com.kingdee.eas.fdc.gcftbiaoa;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillBase;

public interface IConfirquantites extends ICoreBillBase
{
    public ConfirquantitesCollection getConfirquantitesCollection() throws BOSException;
    public ConfirquantitesCollection getConfirquantitesCollection(EntityViewInfo view) throws BOSException;
    public ConfirquantitesCollection getConfirquantitesCollection(String oql) throws BOSException;
    public ConfirquantitesInfo getConfirquantitesInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ConfirquantitesInfo getConfirquantitesInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ConfirquantitesInfo getConfirquantitesInfo(String oql) throws BOSException, EASBizException;
    public void actionAudit(ConfirquantitesInfo model) throws BOSException;
    public void actionUnAudit(ConfirquantitesInfo model) throws BOSException;
}