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

public interface IContractSource extends IFDCDataBase
{
    public ContractSourceInfo getContractSourceInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractSourceInfo getContractSourceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractSourceInfo getContractSourceInfo(String oql) throws BOSException, EASBizException;
    public ContractSourceCollection getContractSourceCollection() throws BOSException;
    public ContractSourceCollection getContractSourceCollection(EntityViewInfo view) throws BOSException;
    public ContractSourceCollection getContractSourceCollection(String oql) throws BOSException;
}