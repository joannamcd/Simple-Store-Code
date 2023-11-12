import java.sql.*;

public class DataAdapter {
    private Connection connection;

    public DataAdapter(Connection connection) {
        this.connection = connection;
    }

    public Product loadProduct(int id) {
        try {
            String query = "SELECT * FROM Products WHERE ProductID = " + id;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Product product = new Product();
                product.setProductID(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getDouble(3));
                product.setQuantity(resultSet.getDouble(4));
                product.setSellerID(resultSet.getInt(5));
                resultSet.close();
                statement.close();

                return product;
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveProduct(Product product) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Products WHERE ProductID = ?");
            statement.setInt(1, product.getProductID());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // this product exists, update its fields
                statement = connection.prepareStatement("UPDATE Products SET Name = ?, Price = ?, Quantity = ?, SellerID = ? WHERE ProductID = ?");
                statement.setString(1, product.getName());
                statement.setDouble(2, product.getPrice());
                statement.setDouble(3, product.getQuantity());
                statement.setInt(5, product.getProductID());
                statement.setInt(4, product.getSellerID());
            }
            else { // this product does not exist, use insert into
                resultSet.close();
                statement.close();
                statement = connection.prepareStatement("INSERT INTO Products VALUES (?, ?, ?, ?, ?)");
                statement.setString(2, product.getName());
                statement.setDouble(3, product.getPrice());
                statement.setDouble(4, product.getQuantity());
                statement.setInt(1, product.getProductID());
                statement.setInt(5, product.getSellerID());
            }
            statement.execute();
            resultSet.close();
            statement.close();
            return true;        // save successfully

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return false; // cannot save!
        }
    }

    public Order loadOrder(int id) {
        try {
            Order order = null;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Orders WHERE OrderID = " + id);

            if (resultSet.next()) {
                order = new Order();
                order.setOrderID(resultSet.getInt("OrderID"));
                order.setBuyerID(resultSet.getInt("CustomerID"));
                order.setTotalCost(resultSet.getDouble("TotalCost"));
                order.setDate(resultSet.getString("OrderDate"));
                resultSet.close();
                statement.close();
            }

            // loading the order lines for this order
            resultSet = statement.executeQuery("SELECT * FROM OrderLine WHERE OrderID = " + id);

            while (resultSet.next()) {
                OrderLine line = new OrderLine();
                line.setOrderID(resultSet.getInt(1));
                line.setProductID(resultSet.getInt(2));
                line.setQuantity(resultSet.getDouble(3));
                line.setCost(resultSet.getDouble(4));
                order.addLine(line);
            }

            return order;

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveOrder(Order order) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Orders VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, order.getOrderID());
            statement.setInt(3, order.getBuyerID());
            statement.setString(2, order.getDate());
            statement.setDouble(4, order.getTotalCost());
            statement.setDouble(5, order.getTotalTax());

            statement.execute();    // commit to the database;
            statement.close();

            statement = connection.prepareStatement("INSERT INTO OrderLine VALUES (?, ?, ?, ?)");

            for (OrderLine line: order.getLines()) { // store for each order line!
                statement.setInt(1, line.getOrderID());
                statement.setInt(2, line.getProductID());
                statement.setDouble(3, line.getQuantity());
                statement.setDouble(4, line.getCost());

                statement.execute();    // commit to the database;
            }
            statement.close();
            return true; // save successfully!
        }
        catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAddress(Address address){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Address VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, address.getAddressID());
            statement.setString(2, address.getStreet());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getState());
            statement.setInt(5, address.getZip());

            statement.execute();
            statement.close();

            return true;
        }
        catch(SQLException e){
            System.out.println("Database access error!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveCard(Card card){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Card VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, card.getCardID());
            statement.setLong(2, card.getCardNum());
            statement.setString(3, card.getExpiryMonth());
            statement.setInt(4, card.getExpiryYear());
            statement.setInt(5, card.getCvv());
            statement.setString(6, card.getCardName());

            statement.execute();
            statement.close();

            return true;
        }
        catch(SQLException e){
            System.out.println("Database access error!");
            e.printStackTrace();
            return false;
        }

    }

    public boolean saveReceipt(Receipt receipt){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Receipt VALUES (?, ?)");
            statement.setInt(1, receipt.getOrderID());
            statement.setString(2, receipt.getReceiptTXT());

            statement.execute();
            statement.close();

            return true;
        }
        catch(SQLException e){
            System.out.println("Database access error!");
            e.printStackTrace();
            return false;
        }
    }


    public User loadUser(String username, String password) {
        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE UserName = ? AND Password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("UserID"));
                user.setUsername(resultSet.getString("UserName"));
                user.setPassword(resultSet.getString("Password"));
                user.setFullName(resultSet.getString("DisplayName"));
                resultSet.close();
                statement.close();

                return user;
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public int loadOrderID(){
        try{
            ResultSet rs;
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(OrderID) AS max_ID from Orders");
            rs = statement.executeQuery();
            if(rs.next()){
                int returned = rs.getInt("max_ID") + 1;
                return returned;
            }
        }
        catch (SQLException e){
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return 0;
    }
}
