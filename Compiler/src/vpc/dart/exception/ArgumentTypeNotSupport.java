package vpc.dart.exception;


@SuppressWarnings("serial")
public class ArgumentTypeNotSupport extends DartExceptionBase {

	public ArgumentTypeNotSupport(String arg) {
		super("Argument type of entry function is not supported.", arg);
		
	}
}
