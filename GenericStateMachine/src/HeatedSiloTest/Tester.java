//implemented to check SM branch support
package HeatedSiloTest;

public class Tester {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		HeatedSiloCtr ss = new HeatedSiloCtr();
		SignalGenerator sg = new SignalGenerator(ss.itsMsgQ);
		new Thread(sg).start();
		Thread smt=new Thread(ss);
		smt.setName("mainSmT");
		smt.start();
		smt.join();
		System.out.println("end");
	}

}
