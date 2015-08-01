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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IContractExecInfos extends IFDCBill
{
    public ContractExecInfosInfo getContractExecInfosInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractExecInfosInfo getContractExecInfosInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractExecInfosInfo getContractExecInfosInfo(String oql) throws BOSException, EASBizException;
    public ContractExecInfosCollection getContractExecInfosCollection() throws BOSException;
    public ContractExecInfosCollection getContractExecInfosCollection(EntityViewInfo view) throws BOSException;
    public ContractExecInfosCollection getContractExecInfosCollection(String oql) throws BOSException;
    public void updateContract(String type, String contractId) throws BOSException, EASBizException;
    public void updateChange(String type, String contractId) throws BOSException, EASBizException;
    public void updateSettle(String type, String contractId) throws BOSException, EASBizException;
    public void updatePayment(String type, String contractId) throws BOSException, EASBizException;
    public void synOldContract() throws BOSException, EASBizException;
    public void updateSuppliedContract(String type, String contractId) throws BOSException, EASBizException;
}