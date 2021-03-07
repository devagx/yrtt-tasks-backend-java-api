package com.techreturners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techreturners.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		LOG.info("received the request!!!!..");

		String userId = request.getPathParameters().get("userId");

		List<Task> tasks = new ArrayList<>();

		if (userId.equals("abc123")) {
			Task t1 = new Task("abc1234", "Pickup the newspapers", false);
			tasks.add(t1);
		} else {
			Task t2 = new Task("abc4567", "Enjoy Java!", false);
			tasks.add(t2);
		}

		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		response.setStatusCode(200);

		ObjectMapper objMapper = new ObjectMapper();
		{
			try {
				String responseBody = objMapper.writeValueAsString(tasks);
				response.setBody(responseBody);
			} catch (JsonProcessingException e) {
				LOG.error("Unable to marshall tasks array", e);
			}
		}

		return response;
	}
}
