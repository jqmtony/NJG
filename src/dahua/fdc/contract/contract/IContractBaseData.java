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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IContractBaseData extends IDataBase
{
    public ContractBaseDataInfo getContractBaseDataInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractBaseDataInfo getContractBaseDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractBaseDataInfo getContractBaseDataInfo(String oql) throws BOSException, EASBizException;
    public ContractBaseDataCollection getContractBaseDataCollection() throws BOSException;
    public ContractBaseDataCollection getContractBaseDataCollection(EntityViewInfo view) throws BOSException;
    public ContractBaseDataCollection getContractBaseDataCollection(String oql) throws BOSException;
}