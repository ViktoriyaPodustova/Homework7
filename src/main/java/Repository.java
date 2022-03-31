import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    public static final String url = "jdbc:sqlite:BDhomework8.db";

    public static void createTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS weatherForecast (id integer PRIMARY KEY, localDate text NOT NULL, dayText text NOT NULL, nightText text NOT NULL, minTemperature double NOT NULL, maxTemperature double NOT NULL)";
        try (Connection sqliteConnection = DriverManager.getConnection(url);
             Statement statement = sqliteConnection.createStatement()) {
            System.out.println("Успешное подключение к БД");
            statement.execute(sqlCreateTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void putDataInDB (DailyForecast forecast){
          String sqlInsertIntoTable = "INSERT INTO weatherForecast(localDate, dayText, nightText, minTemperature , maxTemperature) VALUES(?,?,?,?,?);";
        try (Connection sqliteConnection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = sqliteConnection.prepareStatement(sqlInsertIntoTable)) {
            preparedStatement.setString(1, forecast.getDate());
            preparedStatement.setString(2, forecast.getDayTextDescription());
            preparedStatement.setString(3, forecast.getNightTextDescription());
            preparedStatement.setDouble(4, forecast.getMinimumTemperature());
            preparedStatement.setDouble(5, forecast.getMaximumTemperature());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static List<DailyForecast> unloadingDataFromDB(){
        final String sqlSelectFromTable = "SELECT * FROM weatherForecast";
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        try (Connection sqliteConnection = DriverManager.getConnection(url);
             Statement statement = sqliteConnection.createStatement()) {
           ResultSet resultSet = statement.executeQuery(sqlSelectFromTable);
           while (resultSet.next()){
               DailyForecast dailyForecast =new DailyForecast(resultSet.getString("localDate"),
                       resultSet.getDouble("minTemperature"),
                       resultSet.getDouble("maxTemperature"),
                       resultSet.getString("dayText"),
                       resultSet.getString("nightText"));
               dailyForecasts.add(dailyForecast);
           }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return dailyForecasts;
    }
}

