package vpc.dart.exception;

import cck.util.Util.Error;

@SuppressWarnings("serial")
public class DartExceptionBase extends Error {

	public DartExceptionBase(String p) {
		super(p);
	}
	public DartExceptionBase(String p, String a) {
		super(p, a);
	}
}


