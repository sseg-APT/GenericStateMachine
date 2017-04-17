package uml4iot.GenericStateMachine.HeatedSiloTest;

import uml4iot.GenericStateMachine.core.MessageQueue;

public class SignalGenerator implements Runnable {
	private MessageQueue msgQ;
	
	public SignalGenerator(MessageQueue msgQ){
		this.msgQ=msgQ;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<1;i++){
			msgQ.add(SimpleSiloSMEvent.FILL);
			msgQ.add(SimpleSiloSMEvent.HIGH_LEVEL_REACHED);
			msgQ.add(SimpleSiloSMEvent.EMPTY );
			msgQ.add(SimpleSiloSMEvent.STOP_HEATING );
			msgQ.add(SimpleSiloSMEvent.LOW_LEVEL_REACHED );
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}/*
			msgQ.add(SimpleSiloSMEvent.FILL);
			msgQ.add(SimpleSiloSMEvent.HIGH_LEVEL_REACHED);
			msgQ.add(SimpleSiloSMEvent.EMPTY );
			msgQ.add(SimpleSiloSMEvent.STOP_HEATING );
			msgQ.add(SimpleSiloSMEvent.LOW_LEVEL_REACHED );
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			//msgQ.add(SimpleSiloSMEvent.FILL);
			//msgQ.add(SimpleSiloSMEvent.STOP_FILLING);
			//msgQ.add(SimpleSiloSMEvent.EMPTY );
			//msgQ.add(SimpleSiloSMEvent.LOW_LEVEL_REACHED );
			
		}
	}

}
