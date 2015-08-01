package com.kingdee.eas.fdc.basedata.app;

import java.util.Observable;
import java.util.Observer;

import com.kingdee.bos.Context;

/**
 * 工程项目被观察者对象, 指标发生变化, 通知拆分单据更新成本数据
 * 
 * @author liupd
 * 
 */
public class ProjectObservable extends Observable {
	private Context ctx;

	public ProjectObservable(Context ctx) {
		this.ctx = ctx;
		setChanged();
	}

	private static class ProjectObservableHandler {
		private static ProjectObservable projectObservable = new ProjectObservable(null);
		private static ProjectObservable clearProjectObservable = new ProjectObservable(null);
		static{
			try {
				Observer clearConSplitObserver = SplitObserverFactory.factory(SplitObserverFactory.CLEAR_CON_SPLIT);
				Observer clearPaymentSplitObserver = SplitObserverFactory.factory(SplitObserverFactory.CLEAR_PAYMENT_SPLIT);
				// 注册观察者，按照顺序（合同-变更-结算-付款），执行的时候按照添加的反顺序执行
				clearProjectObservable.addObserver(clearPaymentSplitObserver);
				clearProjectObservable.addObserver(clearConSplitObserver);

				Observer contractSplitObserver = SplitObserverFactory.factory(SplitObserverFactory.CONTRACT_SPLIT);
				Observer conChangeSplitObserver = SplitObserverFactory.factory(SplitObserverFactory.CON_CHANGE_SPLIT);
				Observer conSettleSplitObserver = SplitObserverFactory.factory(SplitObserverFactory.CON_SETTLE_SPLIT);
				Observer paymentSplitObserver = SplitObserverFactory.factory(SplitObserverFactory.PAYMENT_SPLIT);
										
				projectObservable.addObserver(paymentSplitObserver);
				projectObservable.addObserver(conSettleSplitObserver);
				projectObservable.addObserver(conChangeSplitObserver);
				projectObservable.addObserver(contractSplitObserver);

			} catch (BadSplitObserverException e) {
				// @AbortException
				e.printStackTrace();
			}
		}

	}

	/**
	 * 静态JVM同步不需要额外的同步
	 * @param ctx
	 * @param isFCAll
	 *            是否启用财务成本一体化
	 * @return
	 */
	public static ProjectObservable getInstance(Context ctx, boolean isFCAll) {
		if (isFCAll) {
			ProjectObservableHandler.clearProjectObservable.ctx = ctx;
			ProjectObservableHandler.clearProjectObservable.setChanged();
			return ProjectObservableHandler.clearProjectObservable;
		} else {
			ProjectObservableHandler.projectObservable.ctx = ctx;
			ProjectObservableHandler.projectObservable.setChanged();
			return ProjectObservableHandler.projectObservable;
		}
	}

	public Context getCtx() {
		return ctx;
	}

}
