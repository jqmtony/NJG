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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IContractDemoEntry extends ICoreBillEntryBase
{
    public ContractDemoEntryInfo getContractDemoEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractDemoEntryInfo getContractDemoEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractDemoEntryInfo getContractDemoEntryInfo(String oql) throws BOSException, EASBizException;
    public ContractDemoEntryCollection getContractDemoEntryCollection() throws BOSException;
    public ContractDemoEntryCollection getContractDemoEntryCollection(EntityViewInfo view) throws BOSException;
    public ContractDemoEntryCollection getContractDemoEntryCollection(String oql) throws BOSException;
}