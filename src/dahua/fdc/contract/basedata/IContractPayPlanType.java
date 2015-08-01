package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IContractPayPlanType extends IFDCDataBase
{
    public ContractPayPlanTypeInfo getContractPayPlanTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractPayPlanTypeInfo getContractPayPlanTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractPayPlanTypeInfo getContractPayPlanTypeInfo(String oql) throws BOSException, EASBizException;
    public ContractPayPlanTypeCollection getContractPayPlanTypeCollection() throws BOSException;
    public ContractPayPlanTypeCollection getContractPayPlanTypeCollection(EntityViewInfo view) throws BOSException;
    public ContractPayPlanTypeCollection getContractPayPlanTypeCollection(String oql) throws BOSException;
    public boolean disEnabled(IObjectPK pk, IObjectValue model) throws BOSException, EASBizException;
    public boolean enabled(IObjectPK pk, IObjectValue model) throws BOSException, EASBizException;
}