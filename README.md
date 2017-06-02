# GenericStateMachine
Γιαννη Ρίξε το ματιά. Μου φαίνεται καλύτερο από το Enum. Ελπίζω να έχω καταγραψει σωστά το basic flow. Αν δεις κατι πέσμου.τΟ αφήνω σε σένα να το προχωρήσεις
[2Jun2017] Γιάννη δες την παρακάτω αλλαγή στην δήλωση των states.
Instead of 
e2ft = new Empty2FillingTrans(filling);
and the related
empty.addTransition(e2ft); 
it is preferable to have the following constratuctor for states 
e2ft = new Empty2FillingTrans(empty, filling);

[24Apr2017]
Γιάννη, σημειώνω παρακάτω την λίστα με τα θέματα που συζητήσαμε σήμερα σχετικά με το state machine. Αν ξέχασα κάτι σε παρακαλώ πρόσθεσε το
1.event Dispatcher (add event dispatcher that will forward the incoming events to every state machine)
2. add state entry()  in state machine //is  missing
3. handle completion transition by appending  an attribute  (completion=false) in Transition.
4. move fork code after state exit() and transition effect()
5. Handling of deferred events on branching (deferred)
6. message structure (to be defined later based on requirements of paper)
7. Complete the lgpa by adding decision pseudo states for handling
    a. decision on more2Produce and
    b. STOP (deferred event) 
