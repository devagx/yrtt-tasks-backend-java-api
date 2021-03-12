package com.techreturners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techreturners.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetTasksHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger LOG = LogManager.getLogger(GetTasksHandler.class);

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        LOG.info("received the handleRequest");

        String userId = request.getPathParameters().get("userId");

        List<Task> tasks = new ArrayList<>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = System.getenv("DB_HOST");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");
            connection = DriverManager.getConnection(url, user, password);

            preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE userId = ?");
            preparedStatement.setString(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task(resultSet.getString("taskId"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("completed"));

                tasks.add(task);
            }
        } catch (Exception e) {
            LOG.error(String.format("Unable to query database for tasks for user %s", userId), e);
        } finally {
            closeConnection();
        }

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String responseBody = objectMapper.writeValueAsString(tasks);
            response.setBody(responseBody);
        } catch (JsonProcessingException e) {
            LOG.error("Unable to marshall tasks array", e);
        }

        return response;
    }

    private void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            LOG.error("Unable to close connections to MySQL - {}", e.getMessage());
        }
    }
}