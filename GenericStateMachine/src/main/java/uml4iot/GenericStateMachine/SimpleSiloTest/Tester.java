package uml4iot.GenericStateMachine.SimpleSiloTest;

public class Tester {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		SimpleSiloCtr ss = new SimpleSiloCtr();
		SignalGenerator sg = new SignalGenerator(ss.itsMsgQ);
		new Thread(sg).start();
		Thread smt=new Thread(ss);
		smt.setName("mainSmT");
		smt.start();
		smt.join();
		System.out.println("end");
	}

}
