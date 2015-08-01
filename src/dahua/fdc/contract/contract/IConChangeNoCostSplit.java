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
import com.kingdee.eas.fdc.basedata.IFDCNoCostSplitBill;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IConChangeNoCostSplit extends IFDCNoCostSplitBill
{
    public ConChangeNoCostSplitInfo getConChangeNoCostSplitInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ConChangeNoCostSplitInfo getConChangeNoCostSplitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ConChangeNoCostSplitInfo getConChangeNoCostSplitInfo(String oql) throws BOSException, EASBizException;
    public ConChangeNoCostSplitCollection getConChangeNoCostSplitCollection() throws BOSException;
    public ConChangeNoCostSplitCollection getConChangeNoCostSplitCollection(EntityViewInfo view) throws BOSException;
    public ConChangeNoCostSplitCollection getConChangeNoCostSplitCollection(String oql) throws BOSException;
}