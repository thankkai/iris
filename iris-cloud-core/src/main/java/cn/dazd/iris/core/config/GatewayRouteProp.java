package cn.dazd.iris.core.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.uber.tchannel.api.TChannel;

import cn.dazd.iris.core.kit.PropertiesUtil;
import cn.dazd.iris.core.tchannel.handler.GatewayRouteHandlerImpl;

public class GatewayRouteProp {

	final static Map<String, GatewayRoute> ROUTES_MAP = new LinkedHashMap<>();
	final String ROUTE_CONFIG_FILE = "routeconfig.yml";
	final String routesNode = "routes";
	final Logger l = Logger.getLogger(getClass().getName());

	public static void init(TChannel tChannel) {
		new GatewayRouteProp(tChannel);
	}

	public static GatewayRoute getGatewayEndpoint(String service, String endpoint) {

		GatewayRoute gr = ROUTES_MAP.get(service);
		if (null != gr) {
			Pattern p = Pattern.compile(gr.getMethodName(), Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(endpoint);
			if (matcher.matches()) {
				endpoint = gr.getMethodName();
			}
		}
		return gr;
	}

	private GatewayRouteProp(TChannel tChannel) {

		try {
			String yamlStr = PropertiesUtil.loadYaml(ROUTE_CONFIG_FILE);

			Gson gson = new Gson();
			JsonObject jobj = gson.fromJson(yamlStr, JsonObject.class);
			JsonObject routes = jobj.getAsJsonObject(routesNode);

			for (Entry<String, JsonElement> entry : routes.entrySet()) {
				GatewayRoute gr = gson.fromJson(entry.getValue(), GatewayRoute.class);
				ROUTES_MAP.put(entry.getKey(), gr);
				tChannel.makeSubChannel(entry.getKey()).register(gr.getMethodName(), new GatewayRouteHandlerImpl());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
