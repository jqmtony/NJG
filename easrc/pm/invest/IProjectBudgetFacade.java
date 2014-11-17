package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public interface IProjectBudgetFacade extends IBizCtrl
{
    public String[] subBudgetAmount(String projectNumber, String year, String budgetNumber, String amount, String stag, boolean isBack, String backAmount) throws BOSException;
    public String[] backBudgetAmount(String projectNumber, String year, String budgetNumber, String amount, String stag, boolean isBack, String backAmount) throws BOSException;
}