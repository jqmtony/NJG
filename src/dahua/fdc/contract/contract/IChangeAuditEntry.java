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

public interface IChangeAuditEntry extends ICoreBillEntryBase
{
    public ChangeAuditEntryInfo getChangeAuditEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChangeAuditEntryInfo getChangeAuditEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChangeAuditEntryInfo getChangeAuditEntryInfo(String oql) throws BOSException, EASBizException;
    public ChangeAuditEntryCollection getChangeAuditEntryCollection() throws BOSException;
    public ChangeAuditEntryCollection getChangeAuditEntryCollection(EntityViewInfo view) throws BOSException;
    public ChangeAuditEntryCollection getChangeAuditEntryCollection(String oql) throws BOSException;
}