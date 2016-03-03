package com.kingdee.eas.fdc.contract.app;


public class PayRequestBillControllerBeanEx extends PayRequestBillControllerBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2960263127960437009L;


//	protected void checkBill(Context ctx, IObjectValue model)
//    throws BOSException, EASBizException
//	{
//		
//	    PayRequestBillInfo info = (PayRequestBillInfo)model;
//	    checkNumberDup(ctx, info);
//	    checkPaymentType(ctx, info);
//	}
//	private void checkPaymentType(Context ctx, PayRequestBillInfo payReq)
//    throws EASBizException, BOSException
//	{
//	    if(payReq.getContractId() == null || payReq.getPaymentType() == null || payReq.getPaymentType().getPayType() == null)
//	        return;
//	    PaymentTypeInfo type = payReq.getPaymentType();
//	    FilterInfo filter = new FilterInfo();
//	    filter.appendFilterItem("paymentType.payType.id", "Ga7RLQETEADgAAC/wKgOlOwp3Sw=");
//	    filter.appendFilterItem("sourceBillId", null); // add by wp  -- 不校验从分包合同过来的付款申请
//	    filter.appendFilterItem("contractId", payReq.getContractId());
//	    if(payReq.getId() != null)
//	        filter.getFilterItems().add(new FilterItemInfo("id", payReq.getId(), CompareType.NOTEQUALS));
//	    if(type.getPayType().getId().toString().equals("Ga7RLQETEADgAADDwKgOlOwp3Sw="))
//	    {
//	        String orgUnitId = FDCUtils.findCostCenterOrgId(ctx, payReq.getCurProject().getId().toString());
//	        boolean isKeepBefore = FDCUtils.getDefaultFDCParamByKey(ctx, orgUnitId, "FDC600_KEEPBEFORESETTLEMENT");
//	        if(!_exists(ctx, filter) && !isKeepBefore)
//	            throw new PayRequestBillException(PayRequestBillException.CANTSELECTKEEPAMT);
//	        FilterInfo myfilter = new FilterInfo();
//	        myfilter.appendFilterItem("contractBill.id", payReq.getContractId());
//	        EntityViewInfo view = new EntityViewInfo();
//	        view.setFilter(myfilter);
//	        view.getSelector().add("qualityGuarante");
//	        SorterItemInfo sorterItem = new SorterItemInfo();
//	        sorterItem.setPropertyName("qualityGuarante");
//	        sorterItem.setSortType(SortType.DESCEND);
//	        view.getSorter().add(sorterItem);
//	        ContractSettlementBillCollection c = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(view);
//	        BigDecimal amount = FDCHelper.ZERO;
//	        if(c != null && c.size() != 0)
//	            amount = FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(c.get(0).getQualityGuarante()));
//	        view = new EntityViewInfo();
//	        filter = new FilterInfo();
//	        filter.appendFilterItem("contractId", payReq.getContractId());
//	        filter.appendFilterItem("sourceBillId", null); // add by wp  -- 不校验从分包合同过来的付款申请
//	        filter.appendFilterItem("paymentType.payType.id", "Ga7RLQETEADgAADDwKgOlOwp3Sw=");
//	        view.setFilter(filter);
//	        view.getSelector().add("amount");
//	        PayRequestBillCollection payRequestBillC = getPayRequestBillCollection(ctx, view);
//	        BigDecimal payReqAmount = FDCHelper.ZERO;
//	        for(int i = 0; i < payRequestBillC.size(); i++)
//	        {
//	            PayRequestBillInfo info = payRequestBillC.get(i);
//	            if(!info.getId().equals(payReq.getId()))
//	                payReqAmount = payReqAmount.add(FDCHelper.toBigDecimal(info.getAmount()));
//	        }
//	
//	        payReqAmount = payReqAmount.add(FDCHelper.toBigDecimal(payReq.getAmount()));
//	        if(FDCHelper.toBigDecimal(payReqAmount).compareTo(amount) > 0)
//	            throw new PayRequestBillException(PayRequestBillException.MORETHANQUALITYGUARANTE);
//	    }
//	    if(type.getPayType().getId().toString().equals("Ga7RLQETEADgAAC6wKgOlOwp3Sw=") && _exists(ctx, filter))
//	        throw new PayRequestBillException(PayRequestBillException.CANTSELECTPROGRESSAMT);
//	    if(type.getPayType().getId().toString().equals("Ga7RLQETEADgAAC/wKgOlOwp3Sw="))
//	    {
//	        FilterInfo myfilter = new FilterInfo();
//	        myfilter.appendFilterItem("id", payReq.getContractId());
//	        myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
//	        if(!ContractBillFactory.getLocalInstance(ctx).exists(myfilter))
//	            throw new PayRequestBillException(PayRequestBillException.MUSTSETTLE);
//	    }
//	}
//	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
//		PayRequestBillInfo payReqInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(billId));
//		BOSObjectType bosType = BOSUuid.read(payReqInfo.getContractId()).getType();
//		if(bosType != null && bosType.equals(new ContractBillInfo().getBOSType())) {
//			ContractBillInfo astContract = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(payReqInfo.getContractId()));
//			String typeName = ContractTypeFactory.getLocalInstance(ctx).getContractTypeInfo(new ObjectUuidPK(astContract.getContractType().getId())).getName();
//			//throw new EASBizException(new NumericExceptionSubItem("100", "总包合同付款申请单由系统自动生成，用户不能进行审核操作！"));
//			if("[施工]".equals(typeName) || "[材料]".equals(typeName) || "[分包]".equals(typeName)) {
//
//				super._audit(ctx, billId);
//				
//				//在总包下生成一个付款申请单
//				String zbhtId = null;
//				try {
//					zbhtId = getZbhetId(ctx, payReqInfo.getCurProject().getId().toString());
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				//main contract
//				ContractBillInfo mainContract = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(zbhtId));
//				payReqInfo.setHasClosed(false);
//				//auto code number for pay request
//				payReqInfo.setNumber(getAutoCode(ctx, payReqInfo));
//				//SupplierInfo supplier = SupplierFactory.getLocalInstance(ctx).getSupplierInfo(new ObjectUuidPK(mainContract.getPartB().getId()));
//				payReqInfo.setRealSupplier(mainContract.getPartB());
//				payReqInfo.setSupplier(mainContract.getPartB());
//				payReqInfo.setSourceBillId(payReqInfo.getId().toString());
//				String astContractNum = astContract.getNumber();
//				//分包合同中的乙方理论上是付款申请单的收款单位，但付款申请单的收款单位可以修改，会出现两者不一样的情况
//				//我的处理是按照分包合同中的乙方，然后将合同编码简写+乙方名称填写在备注里
//				//SupplierInfo supplier = SupplierFactory.getLocalInstance(ctx).getSupplierInfo(new ObjectUuidPK(astContract.getPartB().getId()));
//				if("[施工]".equals(typeName))
//					payReqInfo.setMoneyDesc(astContractNum.substring(astContractNum.length()-5)+"三材金额："+payReqInfo.getThreeCaiAmount());
//				else
//					payReqInfo.setMoneyDesc(astContractNum.substring(astContractNum.length()-5));
//				payReqInfo.setContractBase(mainContract.getContractBaseData());
//				payReqInfo.setContractId(mainContract.getId().toString());
//				payReqInfo.setContractName(mainContract.getName());
//				payReqInfo.setContractNo(mainContract.getNumber());
//				payReqInfo.setContractPrice(mainContract.getAmount());
//				payReqInfo.setState(FDCBillStateEnum.SUBMITTED);
//				payReqInfo.setId(BOSUuid.create(payReqInfo.getBOSType()));
//				PayRequestBillEntryCollection entrys = payReqInfo.getEntrys();
//				PayRequestBillEntryInfo tempEntryInfo = null;
//				for(int i=0; i<entrys.size(); i++){
//					tempEntryInfo = entrys.get(i);
//					tempEntryInfo.setId(BOSUuid.create(tempEntryInfo.getBOSType()));
//					tempEntryInfo.setPaymentBill(null);
//					tempEntryInfo.setParent(payReqInfo);
//				}
//				
//				payReqInfo.setLatestPrice(mainContract.getAmount());
//				
//				PayRequestBillFactory.getLocalInstance(ctx).addnew(payReqInfo);
//				//由生成的申请单生成付款单
//				super._audit(ctx, payReqInfo.getId());
//				
//			}
//			else {
//				super._audit(ctx, billId);
//			}
//		}else {
//			super._audit(ctx, billId);
//		}
//		
//	}
//	
//	private String getZbhetId(Context ctx, String proId) throws BOSException, SQLException{
//		String id = null;
//		FDCSQLBuilder fdc = new FDCSQLBuilder(ctx);
//		fdc.clear();
//		fdc.appendSql("select contract.fid from T_CON_ContractBill contract left outer join T_FDC_ContractType ctype on " );
//		fdc.appendSql("contract.FCONTRACTTYPEID=ctype.FID where contract.FCURPROJECTID=? and ctype.fname_l2='[建安]' ");
//		fdc.appendSql("and contract.fcontrolunitid=? and contract.FcontractPropert='DIRECT'");
//		fdc.addParam(proId);
//		fdc.addParam(ContextUtil.getCurrentCtrlUnit(ctx).getId().toString());
//		IRowSet sets = fdc.executeQuery();
//		if(sets.next()){
//			id = sets.getString(1);
//		}
//		return id;
//	}
//	
//	/** 获取编码规则生成的编码 
//     * @throws UuidException 
//     * @throws BOSException 
//     * @throws EASBizException */
//	public String getAutoCode(Context ctx ,IObjectValue objValue) throws EASBizException, BOSException, UuidException {
//		    String NumberCode = "";
//		    String companyId = "";
//			com.kingdee.eas.base.codingrule.ICodingRuleManager codeRuleMgr = null;
//			if(ctx == null){
//				companyId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
//				codeRuleMgr = CodingRuleManagerFactory.getRemoteInstance();
//			}else{
//				companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
//				codeRuleMgr = CodingRuleManagerFactory.getLocalInstance(ctx);
//			}
//
//			if (codeRuleMgr.isExist(objValue, companyId)) {
//				if (codeRuleMgr.isUseIntermitNumber(objValue, companyId)) {
//					NumberCode = codeRuleMgr.readNumber(objValue, companyId);
//				} else {
//					NumberCode = codeRuleMgr.getNumber(objValue, companyId);
//				}
//			}
//		return NumberCode;
//	}
//	
//	protected void _unAudit(Context ctx, BOSUuid uid) throws BOSException,EASBizException {
//		super._unAudit(ctx, uid);
//		
//	}

	
	
}