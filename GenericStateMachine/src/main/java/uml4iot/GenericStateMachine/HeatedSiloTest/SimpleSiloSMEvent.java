package uml4iot.GenericStateMachine.HeatedSiloTest;

import uml4iot.GenericStateMachine.core.SMReception;

public enum SimpleSiloSMEvent implements SMReception{
	START(0),
	STOP(1),
	FILL(2),
	EMPTY(3),
	HIGH_LEVEL_REACHED(4),
	LOW_LEVEL_REACHED(5),
	STOP_FILLING(6),
	STOP_EMPTYING(7),
	HEAT(8),
	STOP_HEATING(9)
	;
	
	private int val;
	
	private SimpleSiloSMEvent(int v){
		val = v;
	}
}
