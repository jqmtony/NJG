/*jadclipse*/package com.kingdee.eas.fdc.contract.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.fdc.contract.*;
import com.kingdee.eas.fdc.contract.programming.*;
import com.kingdee.eas.fdc.finance.*;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import org.apache.log4j.Logger;
public class ContractBillReviseControllerBean extends AbstractContractBillReviseControllerBean
{
            public ContractBillReviseControllerBean()
            {
































































/*  96*/        isCanModifyNumberAndName = false;
            }
            protected boolean isCanModifyNumberAndName(Context ctx)
                throws EASBizException, BOSException
            {
/* <-MISALIGNED-> */ /*  99*/        boolean isOk = false;










/* 112*/        return isOk;
            }
            protected boolean _contractBillStore(Context ctx, IObjectValue cbInfo, String storeNumber)
                throws BOSException, EASBizException
            {/* 116*/        return false;
            }
            public boolean checkNumberDup(Context ctx, IObjectPK pk, CoreBillBaseInfo model)
                throws BOSException, EASBizException
            {

/* 122*/        isCanModifyNumberAndName = isCanModifyNumberAndName(ctx);
/* 123*/        return super.checkNumberDup(ctx, pk, model);
            }
            protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
                throws BOSException, EASBizException
            {


/* 130*/        isCanModifyNumberAndName = isCanModifyNumberAndName(ctx);
/* 131*/        super.checkNameDup(ctx, billInfo);
            }
            protected boolean isUseName()
            {/* 134*/        return isCanModifyNumberAndName;
            }
            protected boolean isUseNumber()
            {

/* 139*/        return isCanModifyNumberAndName;
            }
            protected void trimName(FDCBillInfo billInfo)
            {
/* 143*/        if(billInfo.getName() != null)
/* 144*/            billInfo.setName(billInfo.getName().trim());
            }
            protected void _unAudit(Context ctx, BOSUuid billId)
                throws BOSException, EASBizException
            {





/* 154*/        SelectorItemCollection selectors = new SelectorItemCollection();
/* 155*/        selectors.add("*");
/* 156*/        selectors.add("entrys.*");
/* 157*/        selectors.add("payItems.*");
/* 158*/        selectors.add("bail.*");
/* 159*/        selectors.add("bail.entry.*");
/* 160*/        ContractBillReviseInfo model = (ContractBillReviseInfo)_getValue(ctx, new ObjectUuidPK(billId), selectors);
/* 161*/        checkBillForUnAudit(ctx, billId, model);

/* 163*/        super._unAudit(ctx, billId);


/* 166*/        String conId = model.getContractBill().getId().toString();
/* 167*/        ContractBillReviseInfo lastModel = null;

/* 169*/        EntityViewInfo reviseView = new EntityViewInfo();
/* 170*/        reviseView.getSelector().add("*");
/* 171*/        reviseView.getSelector().add("entrys.*");
/* 172*/        reviseView.getSelector().add("payItems.*");
/* 173*/        reviseView.getSelector().add("bail.*");
/* 174*/        reviseView.getSelector().add("bail.entry.*");
/* 175*/        FilterInfo reviseFilter = new FilterInfo();
/* 176*/        reviseView.setFilter(reviseFilter);
/* 177*/        reviseFilter.getFilterItems().add(new FilterItemInfo("contractBill.id", conId));
/* 178*/        reviseFilter.getFilterItems().add(new FilterItemInfo("id", billId.toString(), CompareType.NOTEQUALS));
/* 179*/        reviseFilter.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED", CompareType.EQUALS));
/* 180*/        reviseFilter.getFilterItems().add(new FilterItemInfo("auditTime", model.getAuditTime(), CompareType.LESS));
/* 181*/        SorterItemInfo sorter = new SorterItemInfo("auditTime");
/* 182*/        sorter.setSortType(SortType.DESCEND);
/* 183*/        reviseView.getSorter().add(sorter);
/* 184*/        ContractBillReviseCollection colls = ContractBillReviseFactory.getLocalInstance(ctx).getContractBillReviseCollection(reviseView);
/* 185*/        if(colls != null && colls.size() > 0)
/* 186*/            lastModel = colls.get(0);



/* 190*/        subtractAmountToMainContract(ctx, model.getId());

/* 192*/        if(lastModel != null)
/* 193*/            addAmountToMainContract(ctx, lastModel);


/* 196*/        boolean newCostSplit = model.isIsCoseSplit();
/* 197*/        boolean oldCostSplit = model.isIsCoseSplit();

/* 199*/        if(lastModel != null)
                {/* 200*/            newCostSplit = lastModel.isIsCoseSplit();
/* 201*/            synToContractBill(ctx, lastModel);
                } else
                {/* 203*/            revertContract(ctx, model);
                }


/* 207*/        ContractExecInfosFactory.getLocalInstance(ctx).updateChange("unAudit", conId);


/* 210*/        String companyId = FDCHelper.getCurCompanyId(ctx, model.getCurProject().getId().toString());
/* 211*/        boolean isFinacial = FDCUtils.IsFinacial(ctx, companyId);
/* 212*/        boolean isSeparate = FDCUtils.getBooleanValue4FDCParamByKey(ctx, companyId, "FDC317_SEPARATEFROMPAYMENT");
                FDCSQLBuilder builder;
/* 214*/        if(isFinacial)
                {/* 215*/            if(!model.getAmount().equals(model.getRevAmount()) || oldCostSplit != newCostSplit)
                    {

/* 218*/                ClearSplitFacadeFactory.getLocalInstance(ctx).clearAllSplit(conId, false);


/* 221*/                PeriodInfo currentPeriod = model.getPeriod();
/* 222*/                if(currentPeriod != null)
/* 223*/                    CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(conId, currentPeriod.getId().toString());
                    }
                } else

/* 227*/        if(oldCostSplit != newCostSplit)
                {/* 228*/            if(oldCostSplit)
                    {
/* 230*/                FilterInfo filter = new FilterInfo();
/* 231*/                filter.appendFilterItem("contractBill.id", conId);


/* 234*/                PaymentSplitFactory.getLocalInstance(ctx).delete(filter);

/* 236*/                SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);

/* 238*/                ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);

/* 240*/                ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
                    } else
                    {/* 242*/                FilterInfo filter = new FilterInfo();
/* 243*/                filter.appendFilterItem("contractBill.id", conId);

/* 245*/                PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
/* 246*/                SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
/* 247*/                ConChangeNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
/* 248*/                ConNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
                    }
                } else
/* 251*/        if(!model.getAmount().equals(model.getRevAmount()))
/* 252*/            if(oldCostSplit)
                    {
/* 254*/                FilterInfo filter = new FilterInfo();
/* 255*/                filter.appendFilterItem("contractBill.id", conId);


/* 258*/                PaymentSplitFactory.getLocalInstance(ctx).delete(filter);

/* 260*/                SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);



/* 264*/                builder = new FDCSQLBuilder(ctx);
/* 265*/                builder.appendSql("select fid from T_CON_ContractCostSplit where fcontractbillid=? ");
/* 266*/                builder.addParam(conId);
/* 267*/                IRowSet rowSet = builder.executeQuery();

/* 269*/                try
                        {
/* <-MISALIGNED-> */ /* 269*/                    if(rowSet.next())
                            {
/* 273*/                        String costCenterId = FDCHelper.getCostCenter(model.getCurProject(), ctx).getId().toString();
/* 274*/                        boolean isSplitBaseOnProp = false;
/* 275*/                        if(model != null && oldCostSplit && costCenterId != null)
/* 276*/                            isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, costCenterId, "FDC019_SPLITBASEONPROP");


/* 279*/                        if(isSplitBaseOnProp)
                                {/* 280*/                            ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(conId));
                                } else
                                {/* 282*/                            builder.clear();
/* 283*/                            builder.appendSql("update T_Con_ContractCostSplit set fsplitState=? where fcontractBillId=?");
/* 284*/                            builder.addParam("2PARTSPLIT");
/* 285*/                            builder.addParam(conId);
/* 286*/                            builder.execute();

/* 288*/                            builder.clear();
/* 289*/                            builder.appendSql("update T_Con_Contractbill set fsplitstate=? where fid=?");
/* 290*/                            builder.addParam("2PARTSPLIT");
/* 291*/                            builder.addParam(conId);
/* 292*/                            builder.execute();
                                }
                            }
                        }/* 295*/                catch(SQLException e)
                        {/* 296*/                    throw new BOSException(e);
                        }
                    } else
                    {/* 299*/                FilterInfo filter = new FilterInfo();
/* 300*/                filter.appendFilterItem("contractBill.id", conId);

/* 302*/                PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
/* 303*/                SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);




/* 308*/                builder = new FDCSQLBuilder(ctx);
/* 309*/                builder.appendSql("select fid from T_CON_ConNoCostSplit where fcontractBillId=? ");
/* 310*/                builder.addParam(conId);
/* 311*/                IRowSet rowSet = builder.executeQuery();

/* 313*/                try
                        {
/* <-MISALIGNED-> */ /* 313*/                    if(rowSet.next())
                            {
/* <-MISALIGNED-> */ /* 315*/                        builder.clear();
/* <-MISALIGNED-> */ /* 316*/                        builder.appendSql("update T_Con_ConNoCostSplit set fsplitState=? where fcontractBillId=?");
/* <-MISALIGNED-> */ /* 317*/                        builder.addParam("2PARTSPLIT");
/* <-MISALIGNED-> */ /* 318*/                        builder.addParam(conId);
/* <-MISALIGNED-> */ /* 319*/                        builder.execute();/* 321*/                        builder.clear();
/* 322*/                        builder.appendSql("update T_Con_Contractbill set fsplitstate=? where fid=?");
/* 323*/                        builder.addParam("2PARTSPLIT");
/* 324*/                        builder.addParam(conId);
/* 325*/                        builder.execute();
                            }
                        }/* 327*/                catch(SQLException e)
                        {/* 328*/                    e.printStackTrace();
/* 329*/                    throw new BOSException(e);
                        }
                    }






/* 338*/        BigDecimal latestPrice = FDCUtils.getContractLastAmt(ctx, conId);

/* 340*/        builder = new FDCSQLBuilder(ctx);
/* 341*/        builder.appendSql("update T_Con_PayRequestBill set FContractPrice=?,FLatestPrice=? where fcontractid=? and ");
/* 342*/        builder.addParam(model.getOriginalAmount());
/* 343*/        builder.addParam(latestPrice);
/* 344*/        builder.addParam(conId);
/* 345*/        builder.appendParam("fstate", new String[] {/* 345*/            "1SAVED", "2SUBMITTED", "3AUDITTING"
                });/* 346*/        builder.execute();

/* 348*/        if(isSeparate)
                {/* 349*/            BigDecimal workload = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(conId);
/* 350*/            if(latestPrice.compareTo(FDCHelper.toBigDecimal(workload, 2)) == -1)
/* 351*/                throw new EASBizException(new NumericExceptionSubItem("111", "\u5408\u540C\u6700\u65B0\u9020\u4EF7\u5C0F\u4E8E\u5408\u540C\u7684\u5DE5\u7A0B\u786E\u8BA4\u5355\u5DF2\u5BA1\u6279\u7D2F\u8BA1\u91D1\u989D\uFF0C\u8BF7\u5148\u4FEE\u6539\u5DE5\u7A0B\u91CF\u786E\u8BA4\u5355\uFF01"));
                }




/* 357*/        try
                {
/* <-MISALIGNED-> */ /* 357*/            synContractProgAmt(ctx, model, false);
                }
/* <-MISALIGNED-> */ /* 358*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 359*/            logger.error(e);
/* <-MISALIGNED-> */ /* 360*/            throw new BOSException(e);
                }
            }
            protected void _audit(Context ctx, BOSUuid billId)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 367*/        SelectorItemCollection selectors = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 368*/        selectors.add("*");
/* <-MISALIGNED-> */ /* 369*/        selectors.add("entrys.*");
/* <-MISALIGNED-> */ /* 370*/        selectors.add("payItems.*");
/* <-MISALIGNED-> */ /* 371*/        selectors.add("bail.*");
/* <-MISALIGNED-> */ /* 372*/        selectors.add("bail.entry.*");
/* <-MISALIGNED-> */ /* 373*/        selectors.add("contractBill.originalAmount");
/* <-MISALIGNED-> */ /* 374*/        selectors.add("contractBill.amount");
/* <-MISALIGNED-> */ /* 375*/        ContractBillReviseInfo model = (ContractBillReviseInfo)_getValue(ctx, new ObjectUuidPK(billId), selectors);
/* <-MISALIGNED-> */ /* 376*/        checkBillForAudit(ctx, billId, model);
/* <-MISALIGNED-> */ /* 378*/        String conId = model.getContractBill().getId().toString();
/* <-MISALIGNED-> */ /* 379*/        ContractBillReviseInfo lastModel = null;/* 381*/        EntityViewInfo reviseView = new EntityViewInfo();
/* 382*/        reviseView.getSelector().add("*");
/* 383*/        reviseView.getSelector().add("entrys.*");
/* 384*/        reviseView.getSelector().add("payItems.*");
/* 385*/        reviseView.getSelector().add("bail.*");
/* 386*/        reviseView.getSelector().add("bail.entry.*");
/* 387*/        FilterInfo reviseFilter = new FilterInfo();
/* 388*/        reviseView.setFilter(reviseFilter);
/* 389*/        reviseFilter.getFilterItems().add(new FilterItemInfo("contractBill.id", conId));
/* 390*/        reviseFilter.getFilterItems().add(new FilterItemInfo("id", billId.toString(), CompareType.NOTEQUALS));
/* 391*/        reviseFilter.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED", CompareType.EQUALS));
/* 392*/        SorterItemInfo sorter = new SorterItemInfo("auditTime");
/* 393*/        sorter.setSortType(SortType.DESCEND);
/* 394*/        reviseView.getSorter().add(sorter);
/* 395*/        ContractBillReviseCollection colls = ContractBillReviseFactory.getLocalInstance(ctx).getContractBillReviseCollection(reviseView);
/* 396*/        if(colls != null && colls.size() > 0)
/* 397*/            lastModel = colls.get(0);


/* 400*/        super._audit(ctx, billId);



/* 404*/        if(lastModel != null)
/* 405*/            subtractAmountToMainContract(ctx, lastModel.getId());



/* 409*/        addAmountToMainContract(ctx, model);

/* 411*/        boolean newCostSplit = model.isIsCoseSplit();

/* 413*/        synToContractBill(ctx, model);
/* 414*/        boolean oldCostSplit = model.getBoolean("oldCostSplit");


/* 417*/        String companyId = FDCHelper.getCurCompanyId(ctx, model.getCurProject().getId().toString());
/* 418*/        boolean isFinacial = FDCUtils.IsFinacial(ctx, companyId);
/* 419*/        boolean isSeparate = FDCUtils.getBooleanValue4FDCParamByKey(ctx, companyId, "FDC317_SEPARATEFROMPAYMENT");

/* 421*/        if(isFinacial)
                {/* 422*/            if(!model.getAmount().equals(model.getRevAmount()) || oldCostSplit != newCostSplit)
                    {

/* 425*/                ClearSplitFacadeFactory.getLocalInstance(ctx).clearAllSplit(conId, false);


/* 428*/                PeriodInfo currentPeriod = model.getPeriod();
/* 429*/                if(currentPeriod != null)
/* 430*/                    CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(conId, currentPeriod.getId().toString());
                    }
                } else

/* 434*/        if(oldCostSplit != newCostSplit)
                {/* 435*/            if(oldCostSplit)
                    {
/* 437*/                FilterInfo filter = new FilterInfo();
/* 438*/                filter.appendFilterItem("contractBill.id", conId);


/* 441*/                PaymentSplitFactory.getLocalInstance(ctx).delete(filter);

/* 443*/                SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);

/* 445*/                ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);

/* 447*/                ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
                    } else
                    {/* 449*/                FilterInfo filter = new FilterInfo();
/* 450*/                filter.appendFilterItem("contractBill.id", conId);

/* 452*/                PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
/* 453*/                SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
/* 454*/                ConChangeNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
/* 455*/                ConNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
                    }
                } else
/* 458*/        if(!model.getAmount().equals(model.getRevAmount()))
/* 459*/            if(oldCostSplit)
                    {
/* 461*/                FilterInfo filter = new FilterInfo();
/* 462*/                filter.appendFilterItem("contractBill.id", conId);


/* 465*/                PaymentSplitFactory.getLocalInstance(ctx).delete(filter);

/* 467*/                SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);



/* 471*/                FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 472*/                builder.appendSql("select fid from T_CON_ContractCostSplit where fcontractbillid=? ");
/* 473*/                builder.addParam(conId);
/* 474*/                IRowSet rowSet = builder.executeQuery();

/* 476*/                try
                        {
/* <-MISALIGNED-> */ /* 476*/                    if(rowSet.next())
                            {
/* <-MISALIGNED-> */ /* 478*/                        boolean isSplitBaseOnProp = false;
/* <-MISALIGNED-> */ /* 479*/                        String costCenterId = FDCHelper.getCostCenter(model.getCurProject(), ctx).getId().toString();
/* <-MISALIGNED-> */ /* 480*/                        if(model != null && oldCostSplit && costCenterId != null)
/* <-MISALIGNED-> */ /* 481*/                            isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, costCenterId, "FDC019_SPLITBASEONPROP");
/* 484*/                        if(isSplitBaseOnProp)
                                {/* 485*/                            ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(conId));
/* 486*/                            builder.clear();

/* 488*/                            builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
/* 489*/                            builder.addParam("3ALLSPLIT");
/* 490*/                            builder.addParam(conId.toString());
/* 491*/                            builder.getSql();
/* 492*/                            builder.execute();
/* 493*/                            builder.clear();

/* 495*/                            builder.appendSql("update T_CON_ContractCostSplit set fsplitState=? where fcontractbillid=?");
/* 496*/                            builder.addParam("3ALLSPLIT");
/* 497*/                            builder.addParam(conId.toString());
/* 498*/                            builder.execute();
/* 499*/                            builder.clear();
                                } else
                                {/* 501*/                            builder.clear();
/* 502*/                            builder.appendSql("update T_Con_ContractCostSplit set fsplitState=? where fcontractBillId=?");
/* 503*/                            builder.addParam("2PARTSPLIT");
/* 504*/                            builder.addParam(conId);
/* 505*/                            builder.execute();

/* 507*/                            builder.clear();
/* 508*/                            builder.appendSql("update T_Con_Contractbill set fsplitstate=? where fid=?");
/* 509*/                            builder.addParam("2PARTSPLIT");
/* 510*/                            builder.addParam(conId);
/* 511*/                            builder.execute();
                                }
                            }
                        }/* 514*/                catch(SQLException e)
                        {/* 515*/                    e.printStackTrace();
/* 516*/                    throw new BOSException(e);
                        }
                    } else
                    {
/* 520*/                FilterInfo filter = new FilterInfo();
/* 521*/                filter.appendFilterItem("contractBill.id", conId);

/* 523*/                PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
/* 524*/                SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);




/* 529*/                FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 530*/                builder.appendSql("select fid from T_CON_ConNoCostSplit where fcontractBillId=? ");
/* 531*/                builder.addParam(conId);
/* 532*/                IRowSet rowSet = builder.executeQuery();

/* 534*/                try
                        {
/* <-MISALIGNED-> */ /* 534*/                    if(rowSet.next())
                            {
/* <-MISALIGNED-> */ /* 536*/                        builder.clear();
/* <-MISALIGNED-> */ /* 537*/                        builder.appendSql("update T_Con_ConNoCostSplit set fsplitState=? where fcontractBillId=?");
/* <-MISALIGNED-> */ /* 538*/                        builder.addParam("2PARTSPLIT");
/* <-MISALIGNED-> */ /* 539*/                        builder.addParam(conId);
/* <-MISALIGNED-> */ /* 540*/                        builder.execute();/* 542*/                        builder.clear();
/* 543*/                        builder.appendSql("update T_Con_Contractbill set fsplitstate=? where fid=?");
/* 544*/                        builder.addParam("2PARTSPLIT");
/* 545*/                        builder.addParam(conId);
/* 546*/                        builder.execute();
                            }
                        }/* 548*/                catch(SQLException e)
                        {/* 549*/                    e.printStackTrace();
/* 550*/                    throw new BOSException(e);
                        }
                    }






/* 559*/        moneyCount(ctx, model);


/* 562*/        ContractExecInfosFactory.getLocalInstance(ctx).updateChange("audit", conId);
/* 563*/        ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo((new StringBuilder()).append("select amount where id='").append(conId).append("'").toString());

/* 565*/        BigDecimal latestPrice = FDCUtils.getContractLastAmt(ctx, conId);

/* 567*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 568*/        builder.appendSql("update T_Con_PayRequestBill set FContractPrice=?,FLatestPrice=? where fcontractid=? and ");
/* 569*/        builder.addParam(contractBillInfo.getAmount());
/* 570*/        builder.addParam(latestPrice);
/* 571*/        builder.addParam(conId);
/* 572*/        builder.appendParam("fstate", new String[] {/* 572*/            "1SAVED", "2SUBMITTED", "3AUDITTING"
                });/* 573*/        builder.execute();

/* 575*/        if(isSeparate)
                {/* 576*/            BigDecimal workload = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(conId);
/* 577*/            if(latestPrice.compareTo(FDCHelper.toBigDecimal(workload, 2)) == -1)
/* 578*/                throw new EASBizException(new NumericExceptionSubItem("111", "\u5408\u540C\u6700\u65B0\u9020\u4EF7\u5C0F\u4E8E\u5408\u540C\u7684\u5DE5\u7A0B\u786E\u8BA4\u5355\u5DF2\u5BA1\u6279\u7D2F\u8BA1\u91D1\u989D\uFF0C\u8BF7\u5148\u4FEE\u6539\u5DE5\u7A0B\u91CF\u786E\u8BA4\u5355\uFF01"));
                }




/* 584*/        try
                {
/* <-MISALIGNED-> */ /* 584*/            synContractProgAmt(ctx, model, true);
                }
/* <-MISALIGNED-> */ /* 585*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 586*/            logger.error(e);
/* <-MISALIGNED-> */ /* 587*/            throw new BOSException(e);
                }

/* 594*/        ContractBillReviseInfo info = model;

/* 596*/        ContractUtil.synAttachmentToContract(ctx, info);
            }
            private void moneyCount(Context ctx, ContractBillReviseInfo model)
                throws EASBizException, BOSException
            {

/* 602*/        String conId = model.getContractBill().getId().toString();
/* 603*/        String prjId = model.getCurProject().getId().toString();
/* 604*/        BigDecimal changeAmount = model.getRevLocalAmount();
/* 605*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 606*/        ContractBillInfo contractInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(conId));
/* 607*/        String contractid = contractInfo.getId().toString();

/* 609*/        String hql = (new StringBuilder()).append("where curProject='").append(prjId).append("' and mainContractNumber = '").append(contractInfo.getCodingNumber()).append("' and state = '4AUDITTED' and contractPropert = 'SUPPLY'").toString();
/* 610*/        ContractBillCollection contracts = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(hql);
/* 611*/        BigDecimal total = FDCHelper.ZERO;
/* 612*/        ContractBillInfo suppContract = null;
/* 613*/        for(int i = 0; i < contracts.size(); i++)
                {/* 614*/            suppContract = contracts.get(i);
/* 615*/            builder.clear();

/* 617*/            builder.appendSql("select fcontent from T_CON_ContractBillEntry where fparentid =? and frowkey = 'lo'");
/* 618*/            builder.addParam(suppContract.getId().toString());
/* 619*/            IRowSet rs = builder.executeQuery();

/* 621*/            try
                    {
/* <-MISALIGNED-> */ /* 621*/                if(!rs.next())
/* <-MISALIGNED-> */ /* 622*/                    continue;
/* <-MISALIGNED-> */ /* 622*/                String temp = rs.getString("fcontent");
/* <-MISALIGNED-> */ /* 623*/                if(!"\u5426".equals(temp))
/* <-MISALIGNED-> */ /* 624*/                    continue;
/* <-MISALIGNED-> */ /* 624*/                builder.clear();
/* <-MISALIGNED-> */ /* 625*/                builder.appendSql("select fcontent from T_CON_ContractBillEntry where fparentid = ? and frowkey = 'am'");
/* <-MISALIGNED-> */ /* 626*/                builder.addParam(suppContract.getId().toString());
/* <-MISALIGNED-> */ /* 627*/                IRowSet rs2 = builder.executeQuery();
/* <-MISALIGNED-> */ /* 628*/                if(rs2.next())
                        {
/* <-MISALIGNED-> */ /* 629*/                    String temp2 = rs2.getString("fcontent");
/* <-MISALIGNED-> */ /* 631*/                    total = FDCHelper.add(total, temp2);
                        }
                    }
/* <-MISALIGNED-> */ /* 635*/            catch(Exception e)
                    {
/* <-MISALIGNED-> */ /* 636*/                throw new BOSException(e);
                    }
                }
/* <-MISALIGNED-> */ /* 640*/        BigDecimal finalAmount = FDCHelper.add(changeAmount, FDCHelper.multiply(total, contractInfo.getExRate()));
/* <-MISALIGNED-> */ /* 641*/        BigDecimal fceremonybb = FDCHelper.divide(finalAmount, contractInfo.getExRate(), 4, 6);
/* <-MISALIGNED-> */ /* 643*/        builder.clear();
/* <-MISALIGNED-> */ /* 644*/        builder.appendSql("update t_con_contractbill set foriginalamount =?,famount =? where fid =?");
/* <-MISALIGNED-> */ /* 645*/        builder.addParam(fceremonybb);
/* <-MISALIGNED-> */ /* 646*/        builder.addParam(finalAmount);
/* <-MISALIGNED-> */ /* 647*/        builder.addParam(contractid);
/* <-MISALIGNED-> */ /* 648*/        builder.execute();
            }
            private void synContractProgAmt(Context ctx, ContractBillReviseInfo model, boolean flag)
                throws EASBizException, BOSException, SQLException
            {






/* 661*/        BOSUuid contractBillId = model.getContractBill().getId();
/* 662*/        SelectorItemCollection sic = new SelectorItemCollection();
/* 663*/        sic.add("*");
/* 664*/        sic.add("programmingContract.*");
/* 665*/        ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractBillId.toString()), sic);

/* 667*/        ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
/* 668*/        if(pcInfo == null)
/* 669*/            return;

/* 671*/        BigDecimal balanceAmt = pcInfo.getBalance();

/* 673*/        BigDecimal controlBalanceAmt = pcInfo.getControlBalance();

/* 675*/        BigDecimal signAmount = model.getAmount();

/* 677*/        BigDecimal revLocalAmount = model.getRevLocalAmount();

/* 679*/        BigDecimal signUpAmount = pcInfo.getSignUpAmount();

/* 681*/        BigDecimal otherSignUpAmount = FDCHelper.ZERO;
/* 682*/        if(flag)
                {/* 683*/            pcInfo.setBalance(FDCHelper.subtract(FDCHelper.add(balanceAmt, signAmount), revLocalAmount));
/* 684*/            pcInfo.setControlBalance(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, signAmount), revLocalAmount));
/* 685*/            pcInfo.setSignUpAmount(FDCHelper.add(FDCHelper.subtract(signUpAmount, signAmount), revLocalAmount));
/* 686*/            otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(FDCHelper.subtract(signUpAmount, signAmount), revLocalAmount), signUpAmount);
                } else
                {/* 688*/            pcInfo.setBalance(FDCHelper.subtract(FDCHelper.add(balanceAmt, revLocalAmount), signAmount));
/* 689*/            pcInfo.setControlBalance(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, revLocalAmount), signAmount));
/* 690*/            pcInfo.setSignUpAmount(FDCHelper.add(FDCHelper.subtract(signUpAmount, revLocalAmount), signAmount));
/* 691*/            otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(FDCHelper.subtract(signUpAmount, revLocalAmount), signAmount), signUpAmount);
                }
/* 693*/        SelectorItemCollection sict = new SelectorItemCollection();
/* 694*/        sict.add("balance");
/* 695*/        sict.add("controlBalance");
/* 696*/        sict.add("signUpAmount");
/* 697*/        sict.add("changeAmount");
/* 698*/        sict.add("settleAmount");
/* 699*/        sict.add("srcId");
/* 700*/        IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
/* 701*/        service.updatePartial(pcInfo, sict);

/* 703*/        for(String progId = pcInfo.getId().toString(); progId != null;)
                {
/* 705*/            String nextVersionProgId = getNextVersionProg(ctx, progId);
/* 706*/            if(nextVersionProgId != null)
                    {/* 707*/                pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
/* 708*/                pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), otherSignUpAmount));
/* 709*/                pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(), otherSignUpAmount));
/* 710*/                pcInfo.setSignUpAmount(FDCHelper.add(pcInfo.getSignUpAmount(), otherSignUpAmount));
/* 711*/                service.updatePartial(pcInfo, sict);
/* 712*/                progId = pcInfo.getId().toString();
                    } else
                    {/* 714*/                progId = null;
                    }
                }
            }
            private String getNextVersionProg(Context ctx, String nextProgId)
                throws BOSException, SQLException
            {/* 720*/        String tempId = null;
/* 721*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 722*/        IRowSet rowSet = null;
/* 723*/        builder.appendSql(" select fid from t_con_programmingContract where  ");
/* 724*/        builder.appendParam("FSrcId", nextProgId);
/* 725*/        for(rowSet = builder.executeQuery(); rowSet.next();)

/* 727*/            tempId = rowSet.getString("fid");

/* 729*/        return tempId;
            }
            private SelectorItemCollection getSic()
            {

/* 734*/        SelectorItemCollection sic = new SelectorItemCollection();
/* 735*/        sic.add(new SelectorItemInfo("id"));
/* 736*/        sic.add(new SelectorItemInfo("number"));
/* 737*/        sic.add(new SelectorItemInfo("period.id"));
/* 738*/        sic.add(new SelectorItemInfo("period.beginDate"));
/* 739*/        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
/* 740*/        sic.add(new SelectorItemInfo("curProject.id"));
/* 741*/        sic.add(new SelectorItemInfo("curProject.displayName"));
/* 742*/        sic.add(new SelectorItemInfo("contractBill.id"));
/* 743*/        sic.add(new SelectorItemInfo("revLocalAmount"));
/* 744*/        return sic;
            }
            protected void checkBillForSubmit(Context ctx, IObjectValue model)
                throws BOSException, EASBizException
            {


/* 751*/        ContractBillReviseInfo contractBill = (ContractBillReviseInfo)model;


/* 754*/        String comId = null;
/* 755*/        if(contractBill.getCurProject().getFullOrgUnit() == null)
                {
/* 757*/            SelectorItemCollection sic = new SelectorItemCollection();
/* 758*/            sic.add("fullOrgUnit.id");
/* 759*/            CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(contractBill.getCurProject().getId().toString()), sic);


/* 762*/            comId = curProject.getFullOrgUnit().getId().toString();
                } else
                {/* 764*/            comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
                }
/* 766*/        boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
/* 767*/        if(isInCore)
                {/* 768*/            PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx, contractBill.getCurProject().getId().toString(), true);
/* 769*/            if(contractBill.getPeriod() != null && contractBill.getPeriod().getEndDate().before(bookedPeriod.getBeginDate()))

/* 771*/                throw new ContractException(ContractException.CNTPERIODBEFORE);
                }
            }
            private void checkBillForAudit(Context ctx, BOSUuid billId, FDCBillInfo billInfo)
                throws BOSException, EASBizException
            {


/* 779*/        ContractBillReviseInfo model = (ContractBillReviseInfo)billInfo;

/* 781*/        if(model == null || model.getCurProject() == null || model.getCurProject().getFullOrgUnit() == null)
/* 782*/            model = getContractBillReviseInfo(ctx, new ObjectUuidPK(billId.toString()), getSic());



/* 786*/        String comId = model.getCurProject().getFullOrgUnit().getId().toString();


/* 789*/        boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
/* 790*/        if(isInCore)
                {/* 791*/            String curProject = model.getCurProject().getId().toString();

/* 793*/            PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx, curProject, true);
/* 794*/            if(model.getPeriod().getBeginDate().after(bookedPeriod.getEndDate()))
/* 795*/                throw new ContractException(ContractException.AUD_AFTERPERIOD, new Object[] {/* 795*/                    model.getNumber()
                        });
                }
            }
            private void checkBillForUnAudit(Context ctx, BOSUuid billId, FDCBillInfo billInfo)
                throws BOSException, EASBizException
            {










/* 812*/        ContractBillReviseInfo model = (ContractBillReviseInfo)billInfo;

/* 814*/        SelectorItemCollection sic = new SelectorItemCollection();
/* 815*/        sic.add(new SelectorItemInfo("id"));
/* 816*/        sic.add(new SelectorItemInfo("number"));
/* 817*/        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
/* 818*/        sic.add(new SelectorItemInfo("curProject.id"));
/* 819*/        sic.add(new SelectorItemInfo("curProject.displayName"));
/* 820*/        sic.add(new SelectorItemInfo("period.beginDate"));

/* 822*/        if(model == null || model.getCurProject() == null || model.getCurProject().getFullOrgUnit() == null)
/* 823*/            model = getContractBillReviseInfo(ctx, new ObjectUuidPK(billId.toString()), getSic());


/* 826*/        String comId = model.getCurProject().getFullOrgUnit().getId().toString();


/* 829*/        boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
/* 830*/        if(isInCore)
                {/* 831*/            String curProject = model.getCurProject().getId().toString();


/* 834*/            PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx, curProject, true);
/* 835*/            if(model.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate()))
/* 836*/                throw new ContractException(ContractException.CNTPERIODBEFORE);
                }
            }
            protected IObjectPK _submit(Context ctx, IObjectValue model)
                throws BOSException, EASBizException
            {









/* 851*/        ContractBillReviseInfo info = (ContractBillReviseInfo)model;
/* 852*/        if(info.getName() != null)
                {/* 853*/            String string = info.getName();
/* 854*/            info.setName(string.trim());
                }
/* 856*/        if(info.getNumber() != null)
                {/* 857*/            String string = info.getNumber();
/* 858*/            info.setNumber(string.trim());
                }
/* 860*/        checkContractN(ctx, info);
/* 861*/        saveBail(ctx, info);
/* 862*/        return super._submit(ctx, info);
            }
            private void checkContractN(Context ctx, ContractBillReviseInfo info)
                throws ContractException, EASBizException, BOSException
            {



/* 870*/        boolean isOk = FDCUtils.getDefaultFDCParamByKey(ctx, null, "FDC601_CANMODIFYNUMBER");
/* 871*/        if(isOk)
                {/* 872*/            String name = info.getName();
/* 873*/            String number = info.getNumber();

/* 875*/            FilterInfo filter = new FilterInfo();
/* 876*/            filter.getFilterItems().add(new FilterItemInfo("number", number));
/* 877*/            filter.getFilterItems().add(new FilterItemInfo("curProject.id", info.getCurProject().getId()));
/* 878*/            filter.getFilterItems().add(new FilterItemInfo("id", info.getContractBill().getId().toString(), CompareType.NOTEQUALS));

/* 880*/            if(ContractBillFactory.getLocalInstance(ctx).exists(filter))
/* 881*/                throw new ContractException(ContractException.NUMBER_DUP);

/* 883*/            filter = new FilterInfo();
/* 884*/            filter.getFilterItems().add(new FilterItemInfo("name", name));
/* 885*/            filter.getFilterItems().add(new FilterItemInfo("curProject.id", info.getCurProject().getId()));
/* 886*/            filter.getFilterItems().add(new FilterItemInfo("id", info.getContractBill().getId().toString(), CompareType.NOTEQUALS));

/* 888*/            if(ContractBillFactory.getLocalInstance(ctx).exists(filter))
/* 889*/                throw new ContractException(ContractException.NAME_DUP);
                }
            }
            protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
                throws BOSException, EASBizException
            {



/* 898*/        ContractBillReviseInfo info = (ContractBillReviseInfo)model;

/* 900*/        if(info.getName() != null)
                {/* 901*/            String string = info.getName();
/* 902*/            info.setName(string.trim());
                }
/* 904*/        if(info.getNumber() != null)
                {/* 905*/            String string = info.getNumber();
/* 906*/            info.setNumber(string.trim());
                }
/* 908*/        checkContractN(ctx, info);
/* 909*/        saveBail(ctx, model);
/* 910*/        super._submit(ctx, pk, model);
            }
            protected IObjectPK _save(Context ctx, IObjectValue model)
                throws BOSException, EASBizException
            {/* 914*/        ContractBillReviseInfo info = (ContractBillReviseInfo)model;
/* 915*/        if(info.getName() != null)
                {/* 916*/            String string = info.getName();
/* 917*/            info.setName(string.trim());
                }
/* 919*/        if(info.getNumber() != null)
                {/* 920*/            String string = info.getNumber();
/* 921*/            info.setNumber(string.trim());
                }
/* 923*/        saveBail(ctx, info);
/* 924*/        return super._save(ctx, info);
            }
            protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
                throws BOSException, EASBizException
            {/* 928*/        ContractBillReviseInfo info = (ContractBillReviseInfo)model;
/* 929*/        if(info.getName() != null)
                {/* 930*/            String string = info.getName();
/* 931*/            info.setName(string.trim());
                }
/* 933*/        if(info.getNumber() != null)
                {/* 934*/            String string = info.getNumber();
/* 935*/            info.setNumber(string.trim());
                }
/* 937*/        saveBail(ctx, info);
/* 938*/        super._save(ctx, pk, info);
            }
            private void saveBail(Context ctx, IObjectValue model)
                throws BOSException, EASBizException
            {







/* 950*/        ContractBillReviseInfo info = (ContractBillReviseInfo)model;
/* 951*/        if(info.getBail() != null)
/* 952*/            ContractReviseBailFactory.getLocalInstance(ctx).save(info.getBail());
            }
            private BigDecimal multiply(BigDecimal b1, BigDecimal b2)
            {

/* 957*/        if(b1 == null || b2 == null)
/* 958*/            return FDCHelper.ZERO;

/* 960*/        else/* 960*/            return FDCHelper.divide(FDCHelper.multiply(b1, b2), FDCHelper.ONE_HUNDRED);
            }
            private void revertContract(Context ctx, ContractBillReviseInfo reviseBill)
                throws EASBizException, BOSException
            {/* 964*/        BOSUuid contractId = reviseBill.getContractBill().getId();
/* 965*/        BigDecimal amount = reviseBill.getAmount();
/* 966*/        SelectorItemCollection selector = new SelectorItemCollection();
/* 967*/        selector.add("id");
/* 968*/        selector.add("stampTaxRate");
/* 969*/        selector.add("grtRate");
/* 970*/        selector.add("bail");
/* 971*/        selector.add("bail.id");
/* 972*/        selector.add("bail.amount");
/* 973*/        selector.add("bail.prop");
/* 974*/        selector.add("bail.entry.id");
/* 975*/        selector.add("bail.entry.amount");
/* 976*/        selector.add("bail.entry.prop");
/* 977*/        selector.add("payItems.*");

/* 979*/        ContractBillInfo contract = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId), selector);
/* 980*/        selector.clear();

/* 982*/        selector.add("originalAmount");
/* 983*/        selector.add("amount");
/* 984*/        selector.add("stampTaxAmt");
/* 985*/        selector.add("grtAmount");
/* 986*/        selector.add("grtLocalAmount");
/* 987*/        contract.setOriginalAmount(reviseBill.getOriginalAmount());
/* 988*/        contract.setAmount(amount);
/* 989*/        contract.setStampTaxAmt(multiply(amount, contract.getStampTaxRate()));
/* 990*/        contract.setGrtLocalAmount(multiply(amount, contract.getGrtRate()));
/* 991*/        contract.setGrtAmount(multiply(contract.getOriginalAmount(), contract.getGrtRate()));
/* 992*/        ContractBillFactory.getLocalInstance(ctx).updatePartial(contract, selector);
/* 993*/        selector.clear();

/* 995*/        EntityViewInfo payItemsView = new EntityViewInfo();
/* 996*/        payItemsView.setSelector(selector);
/* 997*/        selector.add("id");
/* 998*/        selector.add("prop");
/* 999*/        selector.add("amount");
/*1000*/        FilterInfo filter = new FilterInfo();
/*1001*/        payItemsView.setFilter(filter);
/*1002*/        filter.getFilterItems().add(new FilterItemInfo("contractbill", contractId.toString()));

/*1004*/        ContractPayItemCollection payItems = ContractPayItemFactory.getLocalInstance(ctx).getContractPayItemCollection(payItemsView);
/*1005*/        if(payItems != null)
                {/*1006*/            for(int i = 0; i < payItems.size(); i++)
                    {/*1007*/                ContractPayItemInfo item = payItems.get(i);
/*1008*/                item.setAmount(multiply(amount, item.getProp()));
/*1009*/                ContractPayItemFactory.getLocalInstance(ctx).updatePartial(item, selector);
                    }
                }
/*1012*/        selector.clear();

/*1014*/        ContractBailInfo bail = contract.getBail();
/*1015*/        if(bail != null)
                {/*1016*/            selector.add("prop");
/*1017*/            selector.add("amount");
/*1018*/            bail.setAmount(multiply(amount, bail.getProp()));
/*1019*/            ContractBailFactory.getLocalInstance(ctx).updatePartial(bail, selector);
/*1020*/            for(int i = 0; i < bail.getEntry().size(); i++)
                    {/*1021*/                ContractBailEntryInfo bailEntry = bail.getEntry().get(i);
/*1022*/                bailEntry.setAmount(multiply(bail.getAmount(), bailEntry.getProp()));
/*1023*/                ContractBailEntryFactory.getLocalInstance(ctx).updatePartial(bailEntry, selector);
                    }
                }
            }
            private void synToContractBill(Context ctx, ContractBillReviseInfo info)
                throws BOSException, EASBizException
            {









/*1039*/        BOSUuid id = info.getContractBill().getId();




/*1044*/        SelectorItemCollection selector = new SelectorItemCollection();
/*1045*/        selector.add("*");
/*1046*/        selector.add("entries.*");
/*1047*/        selector.add("payItems.*");
/*1048*/        selector.add("bail.id");
/*1049*/        selector.add("bail.amount");
/*1050*/        selector.add("bail.prop");
/*1051*/        selector.add("bail.entry.id");
/*1052*/        selector.add("bail.entry.amount");
/*1053*/        selector.add("bail.entry.prop");
/*1054*/        selector.add("bail.entry.bailDate");
/*1055*/        selector.add("bail.entry.bailConditon");
/*1056*/        selector.add("bail.entry.desc");
/*1057*/        selector.add("period.*");
/*1058*/        selector.add("period.id");
/*1059*/        selector.add("contractPropert");

/*1061*/        ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(id), selector);
/*1062*/        info.setBoolean("oldCostSplit", contractBillInfo.isIsCoseSplit());

/*1064*/        ContractBillInfo newConInfo = new ContractBillInfo();


/*1067*/        ContractBillEntryCollection conCol = new ContractBillEntryCollection();
/*1068*/        ContractBillEntryInfo entryInfo = null;

/*1070*/        ContractBillReviseEntryCollection entrys = info.getEntrys();

/*1072*/        if(entrys != null && entrys.size() > 0)
                {/*1073*/            for(Iterator iter = entrys.iterator(); iter.hasNext(); conCol.add(entryInfo))
                    {/*1074*/                ContractBillReviseEntryInfo element = (ContractBillReviseEntryInfo)iter.next();
/*1075*/                entryInfo = new ContractBillEntryInfo();
/*1076*/                entryInfo.putAll(element);
/*1077*/                entryInfo.setParent(contractBillInfo);
                    }
                }



/*1083*/        info.put("entrys", null);


/*1086*/        ContractRevisePayItemCollection rePayCol = info.getPayItems();
/*1087*/        ContractPayItemCollection payCol = new ContractPayItemCollection();
/*1088*/        ContractPayItemInfo payInfo = null;
/*1089*/        if(rePayCol != null && rePayCol.size() > 0)
                {/*1090*/            for(Iterator iter = rePayCol.iterator(); iter.hasNext(); payCol.add(payInfo))
                    {/*1091*/                ContractRevisePayItemInfo element = (ContractRevisePayItemInfo)iter.next();
/*1092*/                payInfo = new ContractPayItemInfo();
/*1093*/                payInfo.putAll(element);
/*1094*/                payInfo.setId(null);
/*1095*/                payInfo.setContractbill(newConInfo);
                    }
                }

/*1099*/        info.put("payItems", null);


/*1102*/        newConInfo.putAll(info);
/*1103*/        newConInfo.put("entrys", conCol);
/*1104*/        newConInfo.put("payItems", payCol);



/*1108*/        newConInfo.setId(contractBillInfo.getId());
/*1109*/        newConInfo.setCreateTime(contractBillInfo.getCreateTime());
/*1110*/        newConInfo.setLastUpdateTime(contractBillInfo.getLastUpdateTime());
/*1111*/        newConInfo.setCreator(contractBillInfo.getCreator());
/*1112*/        newConInfo.setAuditor(contractBillInfo.getAuditor());
/*1113*/        newConInfo.setAuditTime(contractBillInfo.getAuditTime());
/*1114*/        newConInfo.setLastUpdateUser(contractBillInfo.getLastUpdateUser());
/*1115*/        newConInfo.setState(contractBillInfo.getState());
/*1116*/        newConInfo.setIsPartAMaterialCon(contractBillInfo.isIsPartAMaterialCon());
/*1117*/        newConInfo.setPeriod(contractBillInfo.getPeriod());

/*1119*/        newConInfo.setPrjPriceInConPaid(contractBillInfo.getPrjPriceInConPaid());
/*1120*/        newConInfo.setPaidPartAMatlAmt(contractBillInfo.getPaidPartAMatlAmt());
/*1121*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);


/*1124*/        boolean isOk = FDCUtils.getDefaultFDCParamByKey(ctx, null, "FDC601_CANMODIFYNUMBER");
/*1125*/        if(isOk)
                {/*1126*/            newConInfo.setMainContractNumber(info.getMainContractNumber());

/*1128*/            newConInfo.setNumber(info.getNumber());
/*1129*/            newConInfo.setCodingNumber(info.getNumber());


/*1132*/            builder.appendSql("UPDATE T_CON_PAYREQUESTBILL SET FCONTRACTNO = ?,FCONTRACTNAME = ? WHERE FCONTRACTID = ?");
/*1133*/            builder.addParam(newConInfo.getNumber());
/*1134*/            builder.addParam(newConInfo.getName());
/*1135*/            builder.addParam(newConInfo.getId().toString());
/*1136*/            builder.execute();


/*1139*/            builder.clear();
/*1140*/            builder.appendSql("update t_con_contractbillentry set fcontent = ? where  frowkey = 'na' and fparentid in (select fparentid from t_con_contractbillentry where fcontent = ? and frowkey ='nu')");
/*1141*/            builder.addParam(newConInfo.getName());
/*1142*/            builder.addParam(contractBillInfo.getId().toString());
/*1143*/            builder.execute();
/*1144*/            builder.clear();

/*1146*/            if(!ContractPropertyEnum.SUPPLY.equals(contractBillInfo.getContractPropert()))
                    {/*1147*/                builder.clear();
/*1148*/                builder.appendSql("update t_con_contractbill set fmainContractNumber = ? where  fid in (select DISTINCT fparentid from t_con_contractbillentry where fcontent = ? )");
/*1149*/                builder.addParam(newConInfo.getNumber());
/*1150*/                builder.addParam(contractBillInfo.getId().toString());
/*1151*/                builder.execute();
/*1152*/                builder.clear();
                    }



/*1157*/            builder.clear();
/*1158*/            builder.appendSql("UPDATE T_CAS_PAYMENTBILL SET fcontractno = ?  WHERE fcontractbillid = ?");
/*1159*/            builder.addParam(newConInfo.getNumber());
/*1160*/            builder.addParam(newConInfo.getId().toString());
/*1161*/            builder.execute();
/*1162*/            builder.clear();
                }






/*1170*/        newConInfo.setCeremonyb(info.getRevAmount());
/*1171*/        newConInfo.setCeremonybb(info.getRevLocalAmount());



/*1175*/        newConInfo.setOriginalAmount(info.getRevAmount());
/*1176*/        newConInfo.setAmount(info.getRevLocalAmount());



/*1180*/        ContractBailInfo bailInfo = null;
/*1181*/        if(info.getBail() != null)
                {/*1182*/            bailInfo = new ContractBailInfo();
/*1183*/            bailInfo.setProp(info.getBail().getProp());
/*1184*/            bailInfo.setAmount(info.getBail().getAmount());

/*1186*/            ContractReviseBailEntryCollection reBailEntryCol = info.getBail().getEntry();
/*1187*/            ContractBailEntryInfo bailEntryInfo = null;
/*1188*/            if(reBailEntryCol != null && reBailEntryCol.size() > 0)
                    {/*1189*/                for(Iterator iter = reBailEntryCol.iterator(); iter.hasNext(); bailInfo.getEntry().add(bailEntryInfo))
                        {/*1190*/                    ContractReviseBailEntryInfo element = (ContractReviseBailEntryInfo)iter.next();
/*1191*/                    bailEntryInfo = new ContractBailEntryInfo();
/*1192*/                    bailEntryInfo.putAll(element);
/*1193*/                    bailEntryInfo.setId(null);
/*1194*/                    bailEntryInfo.setParent(bailInfo);
                        }
                    }
                }

/*1199*/        newConInfo.setBail(bailInfo);
/*1200*/        if(contractBillInfo.getBail() != null)
                {/*1201*/            BOSUuid bailID = contractBillInfo.getBail().getId();
/*1202*/            ContractBailFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(bailID));
/*1203*/            if(bailInfo != null)
/*1204*/                bailInfo.setId(bailID);
                }

/*1207*/        if(bailInfo != null)
/*1208*/            ContractBailFactory.getLocalInstance(ctx).addnew(bailInfo);











































































/*1284*/        ContractBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(id), newConInfo);
            }
            private void refreshContractSplitState(Context ctx, String contrcatId)
                throws BOSException
            {















/*1304*/        String splitState = null;

/*1306*/        try
                {
/* <-MISALIGNED-> */ /*1306*/            FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* <-MISALIGNED-> */ /*1307*/            builder.appendSql("select C.FAmount,CC.FAmount AS FSplitAmount from T_CON_ContractBill C ");
/* <-MISALIGNED-> */ /*1308*/            builder.appendSql(" LEFT OUTER JOIN T_CON_CONTRACTCOSTSPLIT CC ON CC.FContractBillId=C.FID ");
/* <-MISALIGNED-> */ /*1309*/            builder.appendSql(" where CC.FIsInvalid=0 AND C.FId=? ");
/* <-MISALIGNED-> */ /*1310*/            builder.addParam(contrcatId);
/* <-MISALIGNED-> */ /*1311*/            IRowSet rowSet = builder.executeQuery();
/* <-MISALIGNED-> */ /*1312*/            builder.clear();
/* <-MISALIGNED-> */ /*1313*/            if(rowSet == null || rowSet.size() < 1)
/* <-MISALIGNED-> */ /*1314*/                splitState = "1NOSPLIT";/*1316*/            else/*1316*/            if(rowSet.next())
/*1317*/                if(rowSet.getBigDecimal("FAmount").equals(rowSet.getBigDecimal("FSplitAmount")))
/*1318*/                    splitState = "3ALLSPLIT";

/*1320*/                else/*1320*/                    splitState = "2PARTSPLIT";




/*1325*/            builder.clear();
/*1326*/            builder.appendSql("update T_CON_ContractBill set FSplitState=? where fid=?");
/*1327*/            builder.addParam(splitState);
/*1328*/            builder.addParam(contrcatId);
/*1329*/            builder.executeUpdate(ctx);

/*1331*/            builder.clear();
/*1332*/            builder.appendSql("update T_CON_ContractCostSplit set FSplitState=? where FContractBillID=?");
/*1333*/            builder.addParam(splitState);
/*1334*/            builder.addParam(contrcatId);
/*1335*/            builder.executeUpdate(ctx);
                }/*1336*/        catch(SQLException e)
                {/*1337*/            e.printStackTrace();
/*1338*/            throw new BOSException(e);
                }/*1339*/        catch(Exception e)
                {/*1340*/            e.printStackTrace();
/*1341*/            throw new BOSException(e);
                }
            }
            protected Map _fetchInitData(Context ctx, Map paramMap)
                throws BOSException, EASBizException
            {

/*1348*/        Map initMap = super._fetchInitData(ctx, paramMap);

/*1350*/        CurProjectInfo curProject = (CurProjectInfo)initMap.get("curProject");
/*1351*/        if(curProject != null)
                {/*1352*/            PeriodInfo costperiod = FDCUtils.getCurrentPeriod(ctx, curProject.getId().toString(), true);
/*1353*/            PeriodInfo finaperiod = FDCUtils.getCurrentPeriod(ctx, curProject.getId().toString(), true);
/*1354*/            if(costperiod != null && finaperiod != null && !costperiod.getId().toString().equals(finaperiod.getId().toString()))
/*1355*/                throw new ContractException(ContractException.CANTXIUDING, new Object[] {/*1355*/                    new Integer(costperiod.getNumber()), new Integer(finaperiod.getNumber())
                        });
                }

/*1359*/        return initMap;
            }
            private void subtractAmountToMainContract(Context ctx, BOSUuid billId)
                throws BOSException, ContractException, EASBizException, IllegalConversionException, UuidException
            {











/*1375*/        String sql = " select entry.fcontent as amount,parent.fsplitState splitState,parent.fid mainId,parent.FAmount mainAmount,parent.ForiginalAmount oriAmount \t\t\t\r\n,parent.FexRate mainRate,parent.FgrtRate grtRate,parent.FGrtAmount grtAmount,parent.FStampTaxRate stampRate,parent.FStampTaxAmt stampAmt    \r\n ,parent.FHasSettled    from T_CON_ContractBillReviseEntry entry \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\ninner join T_CON_ContractBillRevise con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' \t\r\ninner join T_CON_ContractBill parent on parent.fnumber = con.fmainContractNumber \tand parent.fcurprojectid=con.fcurprojectid\t\t\t\t\t\r\nwhere   con.fid=? \tand  entry.FRowkey='am' \t \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n";





/*1381*/        IRowSet rs = DbUtil.executeQuery(ctx, sql, new Object[] {/*1381*/            billId.toString()
                });
/*1383*/        try
                {
/* <-MISALIGNED-> */ /*1383*/            if(rs != null && rs.next())
                    {
/* <-MISALIGNED-> */ /*1385*/                boolean hasSettle = rs.getBoolean("FHasSettled");
/* <-MISALIGNED-> */ /*1386*/                if(hasSettle)
/* <-MISALIGNED-> */ /*1387*/                    throw new ContractException(ContractException.MAINCONHASSETTLED);/*1389*/                String splitState = rs.getString("splitState");

/*1391*/                if(splitState != null && splitState.equals("3ALLSPLIT"))
/*1392*/                    throw new ContractException(ContractException.HASALLAPLIT);


/*1395*/                String mainId = rs.getString("mainId");

/*1397*/                BigDecimal supAmount = rs.getBigDecimal("amount");
/*1398*/                BigDecimal mainAmount = rs.getBigDecimal("mainAmount");


/*1401*/                BigDecimal oriAmount = rs.getBigDecimal("oriAmount");

/*1403*/                BigDecimal mainRate = rs.getBigDecimal("mainRate");

/*1405*/                BigDecimal grtRate = rs.getBigDecimal("grtRate");

/*1407*/                BigDecimal grtAmount = rs.getBigDecimal("grtAmount");

/*1409*/                BigDecimal stampRate = rs.getBigDecimal("stampRate");

/*1411*/                BigDecimal stampAmt = rs.getBigDecimal("stampAmt");

/*1413*/                String updateSql = "update T_CON_Contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";





/*1419*/                if(supAmount == null)
/*1420*/                    supAmount = FDCConstants.ZERO;

/*1422*/                BigDecimal revAmount = supAmount.multiply(mainRate);

/*1424*/                String splitSql = "select famount from T_CON_ContractCostSplit where fcontractBillId=?";
/*1425*/                IRowSet ir = DbUtil.executeQuery(ctx, splitSql, new Object[] {/*1425*/                    mainId.toString()
                        });/*1426*/                if(ir != null && ir.next())
                        {/*1427*/                    BigDecimal splitAmount = ir.getBigDecimal("famount");
/*1428*/                    if(splitAmount.compareTo(mainAmount.subtract(revAmount)) == 1)
/*1429*/                        throw new ContractException(ContractException.HASSPLIT);
                        }


/*1433*/                if(mainAmount == null)
/*1434*/                    mainAmount = FDCConstants.ZERO;

/*1436*/                if(oriAmount == null)
/*1437*/                    oriAmount = FDCConstants.ZERO;

/*1439*/                BigDecimal temp = FDCConstants.ZERO;
/*1440*/                if(mainRate.compareTo(FDCConstants.ZERO) == 1)
/*1441*/                    temp = supAmount.multiply(mainRate);


/*1444*/                BigDecimal newGrtAmount = FDCConstants.ZERO;
/*1445*/                if(grtRate.compareTo(FDCConstants.ZERO) == 1)
/*1446*/                    newGrtAmount = oriAmount.subtract(supAmount).multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED, 4, 2);


/*1449*/                BigDecimal newStampAmt = FDCConstants.ZERO;
/*1450*/                if(stampRate.compareTo(FDCConstants.ZERO) == 1)
/*1451*/                    newStampAmt = oriAmount.subtract(supAmount).multiply(mainRate).multiply(stampRate).divide(FDCConstants.ONE_HUNDRED, 4, 2);

/*1453*/                DbUtil.execute(ctx, updateSql, new Object[] {/*1453*/                    oriAmount.subtract(supAmount), mainAmount.subtract(temp), newGrtAmount, newStampAmt, mainId
                        });/*1454*/                EntityViewInfo viewInfo = new EntityViewInfo();
/*1455*/                viewInfo.getSelector().add("*");
/*1456*/                viewInfo.getSelector().add("curProject.costCenter");
/*1457*/                viewInfo.getSelector().add("contractBill.isCoseSplit");
/*1458*/                FilterInfo filterInfo = new FilterInfo();
/*1459*/                viewInfo.setFilter(filterInfo);
/*1460*/                filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id", mainId));


/*1463*/                ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
/*1464*/                if(contractbill.isIsCoseSplit())
                        {

/*1467*/                    ContractCostSplitCollection coll = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(viewInfo);

/*1469*/                    boolean isSplitBaseOnProp = false;
/*1470*/                    if(coll != null && coll.size() > 0)
                            {/*1471*/                        ContractCostSplitInfo info = coll.get(0);
/*1472*/                        if(info != null && info.getContractBill() != null && info.getContractBill().isIsCoseSplit() && info.getCurProject() != null && info.getCurProject().getCostCenter() != null && info.getCurProject().getCostCenter().getId() != null)

/*1474*/                            isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, info.getCurProject().getCostCenter().getId().toString(), "FDC019_SPLITBASEONPROP");
                            }

/*1477*/                    FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/*1478*/                    for(int j = 0; j < coll.size(); j++)
                            {/*1479*/                        ContractCostSplitInfo splitInfo = coll.get(j);
/*1480*/                        if(splitInfo != null && !isSplitBaseOnProp && mainAmount.subtract(revAmount).compareTo(splitInfo.getBigDecimal("amount")) == 0 && splitState.equals("2PARTSPLIT"))
                                {



/*1485*/                            builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
/*1486*/                            builder.addParam("3ALLSPLIT");
/*1487*/                            builder.addParam(mainId.toString());
/*1488*/                            builder.getSql();
/*1489*/                            builder.execute();


/*1492*/                            builder.clear();
/*1493*/                            builder.appendSql("update T_CON_ContractCostSplit set fsplitState=? where fcontractbillid=?");
/*1494*/                            builder.addParam("3ALLSPLIT");
/*1495*/                            builder.addParam(mainId.toString());
/*1496*/                            builder.execute();
/*1497*/                            builder.clear();
                                }
                            }



/*1503*/                    if(isSplitBaseOnProp)
/*1504*/                        ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(mainId));
                        } else
                        {


/*1509*/                    ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
/*1510*/                    FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/*1511*/                    for(int i = 0; i < col.size(); i++)
                            {/*1512*/                        ConNoCostSplitInfo info = col.get(i);
/*1513*/                        if(info != null && mainAmount.subtract(revAmount).compareTo(info.getBigDecimal("amount")) == 0 && splitState.equals("2PARTSPLIT"))
                                {


/*1517*/                            builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
/*1518*/                            builder.addParam("3ALLSPLIT");
/*1519*/                            builder.addParam(mainId.toString());
/*1520*/                            builder.getSql();
/*1521*/                            builder.execute();
/*1522*/                            builder.clear();
/*1523*/                            builder.appendSql("update T_CON_ConNoCostSplit set fsplitState=? where fcontractbillid=?");
/*1524*/                            builder.addParam("3ALLSPLIT");
/*1525*/                            builder.addParam(mainId.toString());
/*1526*/                            builder.execute();
/*1527*/                            builder.clear();
                                }
                            }
                        }



/*1534*/                revAmount = revAmount.add(FDCUtils.getContractLastPrice(ctx, mainId, false));

/*1536*/                EntityViewInfo view = new EntityViewInfo();
/*1537*/                view.getSelector().add(new SelectorItemInfo("payProportion"));
/*1538*/                FilterInfo filter = new FilterInfo();
/*1539*/                view.setFilter(filter);
/*1540*/                filter.getFilterItems().add(new FilterItemInfo("contractId.id", mainId));

/*1542*/                IContractPayPlan iContractPayPlan = ContractPayPlanFactory.getLocalInstance(ctx);
/*1543*/                ContractPayPlanCollection payPlans = iContractPayPlan.getContractPayPlanCollection(view);
/*1544*/                for(int i = 0; i < payPlans.size(); i++)
                        {/*1545*/                    ContractPayPlanInfo info = payPlans.get(i);

/*1547*/                    info.setPayAmount(revAmount.multiply(info.getPayProportion()).divide(new BigDecimal(100), 2, 4));

/*1549*/                    iContractPayPlan.update(new ObjectUuidPK(info.getId().toString()), info);
                        }

/*1552*/                ContractExecInfosFactory.getLocalInstance(ctx).updateSuppliedContract("audit", mainId);
                    }
                }
/*1555*/        catch(SQLException e)
                {/*1556*/            e.printStackTrace();
/*1557*/            throw new BOSException(e);
                }
            }
            private void addAmountToMainContract(Context ctx, ContractBillReviseInfo model)
                throws BOSException, EASBizException, IllegalConversionException, UuidException
            {










/*1573*/        String sql1 = " select entry.fcontent as amount,parent.fsplitState splitState,parent.fid mainId,parent.fceremonyb ceremonyb,parent.fceremonybb ceremonybb,parent.FAmount mainAmount,parent.ForiginalAmount oriAmount \t\t\t\r\n,parent.FexRate mainRate,parent.FgrtRate grtRate,parent.FGrtAmount grtAmount,parent.FStampTaxRate stampRate,parent.FStampTaxAmt stampAmt                             \r\nfrom T_CON_ContractBillReviseEntry entry \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\ninner join T_CON_ContractBillRevise con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' \t\r\ninner join T_CON_ContractBill parent on parent.fnumber = con.fmainContractNumber  and parent.fcurprojectid=con.fcurprojectid\t\t\t\t\t\t\t\t\t\t\t\t\r\nwhere con.fid=? and entry.FRowkey='am' \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n";






/*1580*/        IRowSet rs1 = DbUtil.executeQuery(ctx, sql1, new Object[] {/*1580*/            model.getId().toString()
                });

/*1583*/        try
                {
/* <-MISALIGNED-> */ /*1583*/            if(rs1 != null && rs1.next())
                    {
/* <-MISALIGNED-> */ /*1585*/                String splitState = rs1.getString("splitState");
/* <-MISALIGNED-> */ /*1586*/                String mainId = rs1.getString("mainId");/*1588*/                BigDecimal supAmount = rs1.getBigDecimal("amount");

/*1590*/                BigDecimal ceremonyb = rs1.getBigDecimal("ceremonyb");

/*1592*/                BigDecimal ceremonybb = rs1.getBigDecimal("ceremonybb");

/*1594*/                BigDecimal mainAmount = rs1.getBigDecimal("mainAmount");


/*1597*/                BigDecimal oriAmount = rs1.getBigDecimal("oriAmount");

/*1599*/                BigDecimal mainRate = rs1.getBigDecimal("mainRate");

/*1601*/                BigDecimal grtRate = rs1.getBigDecimal("grtRate");

/*1603*/                BigDecimal grtAmount = rs1.getBigDecimal("grtAmount");

/*1605*/                BigDecimal stampRate = rs1.getBigDecimal("stampRate");

/*1607*/                BigDecimal stampAmt = rs1.getBigDecimal("stampAmt");

/*1609*/                String updateSql = "update T_CON_Contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";


/*1612*/                if(supAmount == null)
/*1613*/                    supAmount = FDCConstants.ZERO;

/*1615*/                if(mainAmount == null)
/*1616*/                    mainAmount = FDCConstants.ZERO;

/*1618*/                if(oriAmount == null)
/*1619*/                    oriAmount = FDCConstants.ZERO;

/*1621*/                BigDecimal revAmount = FDCConstants.ZERO;
/*1622*/                if(mainRate.compareTo(FDCConstants.ZERO) == 1)
/*1623*/                    revAmount = supAmount.multiply(mainRate);


/*1626*/                if(FDCHelper.compareTo(model.getRevAmount(), model.getContractBill().getOriginalAmount()) != 0)
                        {
/*1628*/                    oriAmount = FDCHelper.subtract(oriAmount, model.getContractBill().getOriginalAmount()).add(revAmount);
/*1629*/                    mainAmount = FDCHelper.subtract(mainAmount, model.getContractBill().getAmount()).add(supAmount);
                        }

/*1632*/                ceremonyb = revAmount;
/*1633*/                ceremonybb = supAmount;


/*1636*/                BigDecimal newGrtAmount = FDCConstants.ZERO;
/*1637*/                if(grtRate.compareTo(FDCConstants.ZERO) == 1)
/*1638*/                    newGrtAmount = oriAmount.multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED, 4, 2);


/*1641*/                BigDecimal newStampAmt = FDCConstants.ZERO;
/*1642*/                if(stampRate.compareTo(FDCConstants.ZERO) == 1)
/*1643*/                    newStampAmt = oriAmount.multiply(mainRate).multiply(stampRate).divide(FDCConstants.ONE_HUNDRED, 4, 2);


/*1646*/                DbUtil.execute(ctx, updateSql, new Object[] {/*1646*/                    oriAmount, mainAmount, newGrtAmount, newStampAmt, mainId
                        });



/*1651*/                EntityViewInfo viewInfo = new EntityViewInfo();


/*1654*/                viewInfo.getSelector().add("*");
/*1655*/                viewInfo.getSelector().add("curProject.costCenter");
/*1656*/                viewInfo.getSelector().add("contractBill.isCoseSplit");
/*1657*/                FilterInfo filterInfo = new FilterInfo();
/*1658*/                viewInfo.setFilter(filterInfo);
/*1659*/                filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id", mainId));


/*1662*/                ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
/*1663*/                if(contractbill.isIsCoseSplit())
                        {

/*1666*/                    ContractCostSplitCollection col = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(viewInfo);
/*1667*/                    boolean isSplitBaseOnProp = false;
/*1668*/                    if(col != null && col.size() > 0)
                            {/*1669*/                        ContractCostSplitInfo info = col.get(0);
/*1670*/                        if(info != null && info.getContractBill() != null && info.getContractBill().isIsCoseSplit() && info.getCurProject() != null && info.getCurProject().getCostCenter() != null && info.getCurProject().getCostCenter().getId() != null)

/*1672*/                            isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, info.getCurProject().getCostCenter().getId().toString(), "FDC019_SPLITBASEONPROP");
                            }

/*1675*/                    FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/*1676*/                    for(int i = 0; i < col.size(); i++)
                            {/*1677*/                        ContractCostSplitInfo info = col.get(i);
/*1678*/                        if(info != null && !isSplitBaseOnProp && mainAmount.add(revAmount).compareTo(info.getBigDecimal("amount")) == 1 && splitState.equals("3ALLSPLIT"))
                                {



/*1683*/                            builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
/*1684*/                            builder.addParam("2PARTSPLIT");
/*1685*/                            builder.addParam(mainId.toString());
/*1686*/                            builder.getSql();
/*1687*/                            builder.execute();
/*1688*/                            builder.clear();
/*1689*/                            builder.appendSql("update T_CON_ContractCostSplit set fsplitState=? where fcontractbillid=?");
/*1690*/                            builder.addParam("2PARTSPLIT");
/*1691*/                            builder.addParam(mainId.toString());
/*1692*/                            builder.execute();
/*1693*/                            builder.clear();
                                }
                            }



/*1699*/                    if(isSplitBaseOnProp)
/*1700*/                        ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(mainId));
                        } else
                        {


/*1705*/                    ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
/*1706*/                    FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/*1707*/                    for(int i = 0; i < col.size(); i++)
                            {/*1708*/                        ConNoCostSplitInfo info = col.get(i);
/*1709*/                        if(info != null && mainAmount.add(revAmount).compareTo(info.getBigDecimal("amount")) == 1 && splitState.equals("3ALLSPLIT"))
                                {


/*1713*/                            builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
/*1714*/                            builder.addParam("2PARTSPLIT");
/*1715*/                            builder.addParam(mainId.toString());
/*1716*/                            builder.getSql();
/*1717*/                            builder.execute();
/*1718*/                            builder.clear();
/*1719*/                            builder.appendSql("update T_CON_ConNoCostSplit set fsplitState=? where fcontractbillid=?");
/*1720*/                            builder.addParam("2PARTSPLIT");
/*1721*/                            builder.addParam(mainId.toString());
/*1722*/                            builder.execute();
/*1723*/                            builder.clear();
                                }
                            }
                        }



/*1730*/                revAmount = revAmount.add(FDCUtils.getContractLastPrice(ctx, mainId, false));

/*1732*/                EntityViewInfo view = new EntityViewInfo();
/*1733*/                view.getSelector().add(new SelectorItemInfo("payProportion"));
/*1734*/                FilterInfo filter = new FilterInfo();
/*1735*/                view.setFilter(filter);
/*1736*/                filter.getFilterItems().add(new FilterItemInfo("contractId.id", mainId));

/*1738*/                IContractPayPlan iContractPayPlan = ContractPayPlanFactory.getLocalInstance(ctx);
/*1739*/                ContractPayPlanCollection payPlans = iContractPayPlan.getContractPayPlanCollection(view);
/*1740*/                for(int i = 0; i < payPlans.size(); i++)
                        {/*1741*/                    ContractPayPlanInfo info = payPlans.get(i);

/*1743*/                    info.setPayAmount(revAmount.multiply(info.getPayProportion()).divide(new BigDecimal(100), 2, 4));

/*1745*/                    iContractPayPlan.update(new ObjectUuidPK(info.getId().toString()), info);
                        }

/*1748*/                ContractExecInfosFactory.getLocalInstance(ctx).updateSuppliedContract("audit", mainId);
                    }
                }



/*1754*/        catch(SQLException e)
                {/*1755*/            e.printStackTrace();
/*1756*/            throw new BOSException(e);
                }
            }
            protected IObjectPK _addnew(Context ctx, IObjectValue model)
                throws BOSException, EASBizException
            {/*1761*/        IObjectPK objectPK = super._addnew(ctx, model);

/*1763*/        ContractBillReviseInfo info = (ContractBillReviseInfo)model;

/*1765*/        ContractUtil.synAttachmentFromContract(ctx, info);

/*1767*/        return objectPK;
            }
            private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillReviseControllerBean");
            private boolean isCanModifyNumberAndName;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws7\dh7\lib\server\eas\fdc_contract-server.jar
	Total time: 624 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/