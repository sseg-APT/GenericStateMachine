package SimpleSiloTest;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleSiloCtr ss = new SimpleSiloCtr();
		SignalGenerator sg = new SignalGenerator(ss.itsMsgQ);
		new Thread(sg).start();
		/*ss.itsMsgQ.add(SimpleSiloSMEvent.FILL);
		ss.itsMsgQ.add(SimpleSiloSMEvent.HIGH_LEVEL_REACHED);
		ss.itsMsgQ.add(SimpleSiloSMEvent.EMPTY );
		ss.itsMsgQ.add(SimpleSiloSMEvent.LOW_LEVEL_REACHED );
		//ss.execute();
		ss.itsMsgQ.add(SimpleSiloSMEvent.FILL);
		ss.itsMsgQ.add(SimpleSiloSMEvent.STOP_FILLING);
		ss.itsMsgQ.add(SimpleSiloSMEvent.EMPTY );
		ss.itsMsgQ.add(SimpleSiloSMEvent.STOP_EMPTYING );*/
		ss.execute();
		System.out.println("end");
	}

}
