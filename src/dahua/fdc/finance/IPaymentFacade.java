package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;

public interface IPaymentFacade extends IBizCtrl
{
    public Map fetchInitData(Map param) throws BOSException, EASBizException;
    public Map fetchPayPlanData(Map param) throws BOSException, EASBizException;
    public ContractTypeInfo getContractType(String contractId) throws BOSException, EASBizException;
}