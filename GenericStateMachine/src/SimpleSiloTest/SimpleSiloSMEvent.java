package SimpleSiloTest;

import core.StateMachineReception;

public enum SimpleSiloSMEvent implements StateMachineReception{
	START(0),
	STOP(1),
	FILL(2),
	EMPTY(3),
	HIGH_LEVEL_REACHED(4),
	LOW_LEVEL_REACHED(5),
	STOP_FILLING(6),
	STOP_EMPTYING(7)
	;
	
	private int val;
	
	private SimpleSiloSMEvent(int v){
		val = v;
	}
}