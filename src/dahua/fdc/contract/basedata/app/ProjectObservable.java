package com.kingdee.eas.fdc.basedata.app;

import java.util.Observable;
import java.util.Observer;

import com.kingdee.bos.Context;

/**
 * ������Ŀ���۲��߶���, ָ�귢���仯, ֪ͨ��ֵ��ݸ��³ɱ�����
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
				// ע��۲��ߣ�����˳�򣨺�ͬ-���-����-�����ִ�е�ʱ������ӵķ�˳��ִ��
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
	 * ��̬JVMͬ������Ҫ�����ͬ��
	 * @param ctx
	 * @param isFCAll
	 *            �Ƿ����ò���ɱ�һ�廯
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
