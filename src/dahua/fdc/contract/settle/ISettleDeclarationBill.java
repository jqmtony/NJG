package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public interface ISettleDeclarationBill extends ICoreBillBase
{
    public SettleDeclarationBillCollection getSettleDeclarationBillCollection() throws BOSException;
    public SettleDeclarationBillCollection getSettleDeclarationBillCollection(EntityViewInfo view) throws BOSException;
    public SettleDeclarationBillCollection getSettleDeclarationBillCollection(String oql) throws BOSException;
    public SettleDeclarationBillInfo getSettleDeclarationBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SettleDeclarationBillInfo getSettleDeclarationBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SettleDeclarationBillInfo getSettleDeclarationBillInfo(String oql) throws BOSException, EASBizException;
    public void InTrial(IObjectValue model) throws BOSException;
    public void Approved(IObjectValue model) throws BOSException;
    public void Audit(IObjectValue model) throws BOSException;
    public void UnAudit(IObjectValue model) throws BOSException;
}