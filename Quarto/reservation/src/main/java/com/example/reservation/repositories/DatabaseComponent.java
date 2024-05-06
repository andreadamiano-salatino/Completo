package com.example.reservation.repositories;

import com.example.reservation.models.Booking;
import com.example.reservation.models.Customer;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseComponent {

    Connection connection;

    public Connection StartConnection(){
        String url = "jdbc:mysql://localhost:3306/restaurantreservation";
        String username = "root";
        String password = "LocalDb24!";

        try{
            connection = DriverManager.getConnection(url, username, password);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return  connection;
    }


    public int GetRestaurantId(String restaurantName) throws SQLException {
        int restaurantId = -1;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            String sql = "SELECT restaurantId FROM restaurantreservation.restaurants WHERE restaurants.name = ?";
            connection = StartConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, restaurantName);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                restaurantId = resultSet.getInt("restaurantId");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            if(preparedStatement != null) {
                resultSet.close();
                preparedStatement.close();
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return restaurantId;
    }

    public int GetCustomerId(String email) throws SQLException {
        int customerId = -1;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            String sql = "SELECT customerId FROM restaurantreservation.customers WHERE customers.email = ? ";
            connection = StartConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                customerId = resultSet.getInt("customerId");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            if (preparedStatement != null){
                resultSet.close();
                preparedStatement.close();
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return customerId;
    }

    public List<LocalDateTime> GetAvailableDates(String restaurantName, LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<LocalDateTime> dates = null;
        try {
            String sql = "SELECT * " +
                    "FROM bookings " +
                    "JOIN restaurants ON bookings.restaurantId = restaurants.restaurantId " +
                    "WHERE bookings.date BETWEEN ? AND ?" +
                    "AND restaurants.name = ?";
            connection = StartConnection();
            preparedStatement = connection.prepareStatement(sql);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dataInizioStr = startDate.format(formatter);
            String dataFineStr = endDate.format(formatter);

            preparedStatement.setString(1, dataInizioStr);
            preparedStatement.setString(2, dataFineStr);
            preparedStatement.setString(3, restaurantName);

            resultSet = preparedStatement.executeQuery();

            dates = new ArrayList<>();

            List<LocalDateTime> bookedDates = new ArrayList<>();
            while (resultSet.next()) {
                String dataStr = resultSet.getString("date");
                LocalDateTime data = LocalDateTime.parse(dataStr, formatter);
                bookedDates.add(data);
            }

            LocalDateTime currentDay = startDate;
            while (!currentDay.isAfter(endDate)){
                LocalDateTime currentHour = currentDay.withHour(13).withMinute(0).withSecond(0);
                while (currentHour.getHour() >= 13 && currentHour.getHour() <=23){
                    if (!bookedDates.contains(currentHour)) {
                        dates.add(currentHour);
                    }
                    currentHour = currentHour.plusHours(2);
                }
                currentDay = currentDay.withHour(0).plusDays(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                    resultSet.close();
                    preparedStatement.close();
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return dates;
    }

    public int insertBookingAndGetId(Booking booking) throws SQLException {
        int bookingId = -1;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO restaurantreservation.bookings (note, date, seats, restaurantId, customerId) " +
                    "VALUES (?, ?, ?, ?, ?)";
            connection = StartConnection();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = booking.getDate().format(formatter);

            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, booking.getNote());
            preparedStatement.setString(2, formattedDateTime);
            preparedStatement.setInt(3, booking.getSeats());
            preparedStatement.setInt(4, booking.getRestaurantId());
            preparedStatement.setInt(5, booking.getCustomerId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating booking failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bookingId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookingId;
    }

    public int insertCustomerAndGetId(Customer customer) throws SQLException {
        int customerId = -1;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO restaurantreservation.customers (fname, lname, email, cellphone, intollerance) " +
                    "VALUES (?, ?, ?, ?, ?)";
            connection = StartConnection();

            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getFname());
            preparedStatement.setString(2, customer.getLname());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getCellphone());
            preparedStatement.setString(5, customer.getIntollerance());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customerId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close()
                ;
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return customerId;
    }
}
