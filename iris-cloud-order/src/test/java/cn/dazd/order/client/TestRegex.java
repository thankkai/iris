package cn.dazd.order.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class TestRegex {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Pattern p = Pattern.compile("^cn.dazd.iris.core.tchannel.thrift.OrderApi[$]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher("cn.dazd.iris.core.tchannel.thrift.OrderApi$Iface");
		while (m.find()) {
			System.out.println(m.group());
		}
	}

}
