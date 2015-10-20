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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IContractSignPlanEntry extends ICoreBillEntryBase
{
    public ContractSignPlanEntryInfo getContractSignPlanEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractSignPlanEntryInfo getContractSignPlanEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractSignPlanEntryInfo getContractSignPlanEntryInfo(String oql) throws BOSException, EASBizException;
    public ContractSignPlanEntryCollection getContractSignPlanEntryCollection() throws BOSException;
    public ContractSignPlanEntryCollection getContractSignPlanEntryCollection(EntityViewInfo view) throws BOSException;
    public ContractSignPlanEntryCollection getContractSignPlanEntryCollection(String oql) throws BOSException;
}