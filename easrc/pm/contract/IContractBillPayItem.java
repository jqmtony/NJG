package com.kingdee.eas.port.pm.contract;

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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IContractBillPayItem extends ICoreBillEntryBase
{
    public ContractBillPayItemInfo getContractBillPayItemInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractBillPayItemInfo getContractBillPayItemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractBillPayItemInfo getContractBillPayItemInfo(String oql) throws BOSException, EASBizException;
    public ContractBillPayItemCollection getContractBillPayItemCollection() throws BOSException;
    public ContractBillPayItemCollection getContractBillPayItemCollection(EntityViewInfo view) throws BOSException;
    public ContractBillPayItemCollection getContractBillPayItemCollection(String oql) throws BOSException;
}