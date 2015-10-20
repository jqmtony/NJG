package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

public interface IContractExecFacade extends IBizCtrl
{
    public Map _getCompleteAmt(String fullOrgUnitID, Set idSet) throws BOSException, EASBizException;
    public Map _getCompletePrjAmtForNoTextContract(Set idSet) throws BOSException, EASBizException;
    public Map getCompleteAmt(Map orgId2ContractIdSet) throws BOSException, EASBizException;
    public Map getAllInvoiceAmt(Set idSet) throws BOSException, EASBizException;
    public Map getAllInvoiceOriAmt(Set idSet) throws BOSException, EASBizException;
    public Map getAdjustSumMap(Set idSet) throws BOSException, EASBizException;
    public Map getInvoiceAmt(Set idSet) throws BOSException, EASBizException;
    public Map getInvoiceOriAmt(Set idSet) throws BOSException, EASBizException;
}