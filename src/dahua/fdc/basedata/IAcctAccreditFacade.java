package com.kingdee.eas.fdc.basedata;

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

public interface IAcctAccreditFacade extends IBizCtrl
{
    public void saveAcctAccreditUsers(AcctAccreditUserCollection accreditUsers) throws BOSException, EASBizException;
    public Set getPrjAccreditAcctSet(String prjId) throws BOSException;
    public Set getOrgAccreditAcctSet(String orgUnitId) throws BOSException;
    public Map fetchData(Map param) throws BOSException, EASBizException;
}