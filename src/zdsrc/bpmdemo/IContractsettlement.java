package com.kingdee.eas.bpmdemo;

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

public interface IContractsettlement extends ICoreBillBase
{
    public ContractsettlementCollection getContractsettlementCollection() throws BOSException;
    public ContractsettlementCollection getContractsettlementCollection(EntityViewInfo view) throws BOSException;
    public ContractsettlementCollection getContractsettlementCollection(String oql) throws BOSException;
    public ContractsettlementInfo getContractsettlementInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractsettlementInfo getContractsettlementInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractsettlementInfo getContractsettlementInfo(String oql) throws BOSException, EASBizException;
}