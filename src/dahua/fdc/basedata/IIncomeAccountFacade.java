package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface IIncomeAccountFacade extends IBizCtrl
{
    public void assignOrgsIncomeAccount(IObjectPK orgPK) throws BOSException, EASBizException;
    public void disAssignOrgsIncomeAccount(IObjectPK orgPK) throws BOSException, EASBizException;
    public void disAssignSelectOrgsIncomeAccount(IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException;
    public List assignProjsIncomeAccount(IObjectPK projectPK) throws BOSException, EASBizException;
    public void disAssignProjsIncomeAccount(IObjectPK projectPK) throws BOSException, EASBizException;
    public void disAssignSelectProjsIncomeAccount(IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException;
    public List assignOrgProject(IObjectPK orgPK) throws BOSException, EASBizException;
    public void disAssignOrgProject(IObjectPK orgPK) throws BOSException, EASBizException;
    public ArrayList disAssignSelectOrgProject(IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException;
    public void handleEnterDB(IncomeAccountInfo model, boolean isEnterDB) throws BOSException, EASBizException;
    public List assignToNextLevel(IObjectPK orgPK, Set nodeIds, boolean isProjectSet) throws BOSException, EASBizException;
}