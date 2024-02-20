package telran.probes.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GatewayServiceImpl implements GatewayService {
	@Value("#{${app.map.hosts.ports}}")
	Map<String, String> hostsPortsMap;

	@Override
	public ResponseEntity<byte[]> proxyRouting(ProxyExchange<byte[]> exchange, HttpServletRequest request,
			String httpMethod) {
		String url = getURL(request);
		ResponseEntity<byte[]> res = switch(httpMethod) {
		case "GET" -> exchange.uri(url).get();
		case "PUT" -> exchange.uri(url).put();
		case "POST" -> exchange.uri(url).post();
		case "DELETE" -> exchange.uri(url).delete();
	    default -> throw new IllegalArgumentException("wrongHttpMethod");
		};
		return res;
	}

	private String getURL(HttpServletRequest request) {
		String uri = request.getRequestURI(); // /emails/sensor/123
		log.debug("received URI: {}", uri);
		String firstUrn = uri.split("/+")[1];
		log.debug("first URN {}", firstUrn);
		String hostPort = hostsPortsMap.get(firstUrn);		
		String res= String.format("%s%s", hostPort, uri);
		log.debug("result URL is {}", res);
		return res;
	}

}
