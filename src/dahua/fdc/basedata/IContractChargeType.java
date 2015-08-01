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
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IContractChargeType extends IFDCDataBase
{
    public ContractChargeTypeInfo getContractChargeTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractChargeTypeInfo getContractChargeTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractChargeTypeInfo getContractChargeTypeInfo(String oql) throws BOSException, EASBizException;
    public ContractChargeTypeCollection getContractChargeTypeCollection() throws BOSException;
    public ContractChargeTypeCollection getContractChargeTypeCollection(EntityViewInfo view) throws BOSException;
    public ContractChargeTypeCollection getContractChargeTypeCollection(String oql) throws BOSException;
    public boolean enabled(IObjectPK ctPK) throws BOSException, EASBizException;
    public boolean disEnabled(IObjectPK ctPK) throws BOSException, EASBizException;
}