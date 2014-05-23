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

public interface IFundSource extends IDataBase
{
    public FundSourceInfo getFundSourceInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FundSourceInfo getFundSourceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FundSourceInfo getFundSourceInfo(String oql) throws BOSException, EASBizException;
    public FundSourceCollection getFundSourceCollection() throws BOSException;
    public FundSourceCollection getFundSourceCollection(EntityViewInfo view) throws BOSException;
    public FundSourceCollection getFundSourceCollection(String oql) throws BOSException;
}