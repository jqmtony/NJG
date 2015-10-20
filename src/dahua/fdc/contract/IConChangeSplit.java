package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.fdc.basedata.IFDCSplitBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IConChangeSplit extends IFDCSplitBill
{
    public ConChangeSplitInfo getConChangeSplitInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ConChangeSplitInfo getConChangeSplitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ConChangeSplitInfo getConChangeSplitInfo(String oql) throws BOSException, EASBizException;
    public ConChangeSplitCollection getConChangeSplitCollection() throws BOSException;
    public ConChangeSplitCollection getConChangeSplitCollection(EntityViewInfo view) throws BOSException;
    public ConChangeSplitCollection getConChangeSplitCollection(String oql) throws BOSException;
    public void autoSplit(IObjectPK changePK) throws BOSException, EASBizException;
    public void changeSettle(ContractChangeBillInfo changeInfo) throws BOSException, EASBizException;
    public void autoSplit4(BOSUuid billID) throws BOSException, EASBizException;
}