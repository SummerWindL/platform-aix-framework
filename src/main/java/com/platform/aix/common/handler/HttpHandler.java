package com.platform.aix.common.handler;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;

public class HttpHandler extends AbstractHandler {

    private final ExecutorService executorService;

    public HttpHandler(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void handle(String context, Request req, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        String origin = request.getHeader("Origin");

        response.setHeader("Access-Control-Allow-Origin", origin);
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With,Content-Type,Accept,AUTH,SIGN,TS,PARAMS");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        PrintWriter out = response.getWriter();

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpStatus.OK_200);
//			response.setStatus(HttpStatus.NOT_FOUND_404);
//            response.setContentLength(0);
            response.getWriter().write("OPTIONS returns OK");
            out.flush();
            return;
        }
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpStatus.OK_200);
//			response.setStatus(HttpStatus.NOT_FOUND_404);
            response.setContentLength(0);
            out.flush();
            return;
        }

        Continuation continuation = ContinuationSupport.getContinuation(request);
        if (continuation.isExpired()) {
            response.setStatus(HttpStatus.REQUEST_TIMEOUT_408);
            response.setContentLength(0);
            out.flush();
            return;
        }

        if (continuation.isResumed()) {
            String resp = (String) continuation.getAttribute("ret");
            if (!StringUtils.isEmpty(resp)) {
                out.write(resp);
                // 设置请求响应结果长度
                response.setContentLength(resp.getBytes().length);
            } else {
                response.setContentLength(0);
            }
//            out.flush();
        } else {
            continuation.suspend();
//			bean.setAttribute("executorService", executorService.toString());
            executorService.execute(new HandlerRequestRunnable(continuation, request));
        }
    }
}
