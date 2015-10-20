package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.framework.IObjectBase;

public interface IContractMoveHistory extends IObjectBase
{
    public ContractMoveHistoryInfo getContractMoveHistoryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractMoveHistoryInfo getContractMoveHistoryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractMoveHistoryInfo getContractMoveHistoryInfo(String oql) throws BOSException, EASBizException;
    public ContractMoveHistoryCollection getContractMoveHistoryCollection() throws BOSException;
    public ContractMoveHistoryCollection getContractMoveHistoryCollection(EntityViewInfo view) throws BOSException;
    public ContractMoveHistoryCollection getContractMoveHistoryCollection(String oql) throws BOSException;
}