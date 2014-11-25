package vpc.util;

import java.io.PrintStream;

public class SimpleLogger {
	protected static SimpleLogger s_ins = new SimpleLogger();
	
	protected PrintStream getStream() {
		return System.err;
	}
	
	public static SimpleLogger getLogger() {
		return s_ins;
	}
	public static void setLogger(boolean isopen) {
		if(isopen)
			s_ins = new SimpleLogger();
		else
			s_ins = new EmptyLogger();
	}
	
	public SimpleLogger info(String str) {
		getStream().println("[info]  " + str);
		return this;
	}
	public SimpleLogger infoText(String str, int len) {
		if(len <= 0)
			len = str.length();
		int index = 0;
		getStream().println("[begin info area]");
		while(index + len < str.length()) {
			getStream().print(str.substring(index, index + len));
			getStream().print('-');
			getStream().println();
			index += len;
		}
		getStream().println(str.substring(index));
		getStream().println("[end info area]");
		return this;
	}
	public SimpleLogger newline() {
		getStream().println();
		return this;
	}
	public SimpleLogger error(String str) {
		getStream().println("[error]  " + str);
		return this;
	}
	public SimpleLogger message(String str) {
		getStream().println(str);
		return this;
	}
	public SimpleLogger logValue(Object val) {
		return logValue("", val);
	}
	public SimpleLogger logValue(String inf, Object val) {
		getStream().println(String.format("[value|%s]  %s", inf, val != null ? val.toString() : ""));
		return this;
	}
	public SimpleLogger logValue(Object[] vals) {
		return logValue("", vals);
	}
	
	public SimpleLogger logValue(String inf, Object[] vals) {
		getStream().println(String.format("[begin group %s]", inf));
		if(vals != null) {
			for(int i = 0; i < vals.length; i++)
				getStream().println(String.format("[value %d]  %s)", i, vals[i] != null ? vals[i].toString() : ""));
		}
		getStream().println(String.format("[end group %s]", inf));
		return this;
	}
	
	
}

class EmptyLogger extends SimpleLogger {
	public SimpleLogger info(String str) {
		return this;
	}
	public SimpleLogger newline() {
		getStream().println();
		return this;
	}
	public SimpleLogger error(String str) {
		return this;
	}
	public SimpleLogger message(String str) {
		return this;
	}
	public SimpleLogger logValue(Object val) {
		return this;
	}
	public SimpleLogger logValue(String inf, Object val) {
		return this;
	}
	public SimpleLogger logValue(Object[] vals) {
		return this;
	}
	public SimpleLogger logValue(String inf, Object[] vals) {
		return this;
	}
	
}