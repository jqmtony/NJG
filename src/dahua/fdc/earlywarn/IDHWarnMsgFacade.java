package com.kingdee.eas.fdc.earlywarn;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public interface IDHWarnMsgFacade extends IBizCtrl
{
    public void programmingWarnMsg() throws BOSException;
    public void dhScheduleWarnMsg() throws BOSException;
    public void programmingGZWarnMsg() throws BOSException;
    public void settleDeclarationWarnMsg() throws BOSException;
    public void aimCostDiffWarnMsg() throws BOSException;
}