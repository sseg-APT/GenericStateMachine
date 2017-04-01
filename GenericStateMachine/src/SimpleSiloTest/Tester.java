package SimpleSiloTest;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleSiloCtr ss = new SimpleSiloCtr();
		ss.itsMsgQ.add(SimpleSiloSMEvent.FILL);
		ss.itsMsgQ.add(SimpleSiloSMEvent.HIGH_LEVEL_REACHED);
		ss.itsMsgQ.add(SimpleSiloSMEvent.EMPTY );
		ss.itsMsgQ.add(SimpleSiloSMEvent.LOW_LEVEL_REACHED );
		ss.execute();
		System.out.println("end");
	}

}
