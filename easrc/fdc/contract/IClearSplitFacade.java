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
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IClearSplitFacade extends IBizCtrl
{
    public void clearAllSplit(String contractID, boolean isContract) throws BOSException, EASBizException;
    public void clearSplitBill(String contractID) throws BOSException, EASBizException;
    public void clearNoTextSplit(String id) throws BOSException, EASBizException;
    public void clearSettle(String contractId) throws BOSException, EASBizException;
    public void clearWithoutTextSplit(String id) throws BOSException, EASBizException;
    public void clearPaymentSplit(List idList) throws BOSException, EASBizException;
    public String clearPeriodConSplit(String contractID, String type) throws BOSException, EASBizException;
    public void clearChangeSplit(String changeId) throws BOSException, EASBizException;
    public List getToInvalidContract(List idList) throws BOSException, EASBizException;
}