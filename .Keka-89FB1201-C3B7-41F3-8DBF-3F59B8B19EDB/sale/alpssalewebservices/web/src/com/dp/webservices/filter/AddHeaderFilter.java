package com.dp.webservices.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AddHeaderFilter implements Filter
{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.addHeader("Access-Control-Allow-Origin","*");
		/*httpResponse.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept,X-Csrf-Token,Authorization");
		httpResponse.addHeader("Access-Control-Request-Headers","Origin, X-Requested-With, Content-Type, Accept,X-Csrf-Token,Authorization");
		httpResponse.addHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE,PUT");*/
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
