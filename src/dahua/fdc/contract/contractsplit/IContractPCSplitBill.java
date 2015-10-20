package com.kingdee.eas.fdc.contract.contractsplit;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IContractPCSplitBill extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public ContractPCSplitBillInfo getContractPCSplitBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractPCSplitBillInfo getContractPCSplitBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractPCSplitBillInfo getContractPCSplitBillInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(ContractPCSplitBillInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, ContractPCSplitBillInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, ContractPCSplitBillInfo model) throws BOSException, EASBizException;
    public void updatePartial(ContractPCSplitBillInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, ContractPCSplitBillInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public ContractPCSplitBillCollection getContractPCSplitBillCollection() throws BOSException;
    public ContractPCSplitBillCollection getContractPCSplitBillCollection(EntityViewInfo view) throws BOSException;
    public ContractPCSplitBillCollection getContractPCSplitBillCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}