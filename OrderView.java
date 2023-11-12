import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class OrderView extends JFrame implements ActionListener {

    private JButton btnAdd = new JButton("Add a new item");
    private JButton btnPay = new JButton("Finish and pay");
    /*private JButton btnCard = new JButton("Add credit card info");
    private JButton btnAddress = new JButton("Add shipping address");*/


    private DefaultTableModel items = new DefaultTableModel(); // store information for the table!

    private JTable tblItems = new JTable(items);
    private JLabel labTotal = new JLabel("Total: ");

    Order order = null;
    //order.setOrderID(Application.getInstance().getDataAdapter().loadOrderID());


    public OrderView() {

        this.setTitle("Order View");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(400, 600);


        items.addColumn("Product ID");
        items.addColumn("Name");
        items.addColumn("Price");
        items.addColumn("Quantity");
        items.addColumn("Cost");

        JPanel panelOrder = new JPanel();
        panelOrder.setPreferredSize(new Dimension(400, 450));
        panelOrder.setLayout(new BoxLayout(panelOrder, BoxLayout.PAGE_AXIS));
        tblItems.setBounds(0, 0, 400, 350);
        panelOrder.add(tblItems.getTableHeader());
        panelOrder.add(tblItems);
        panelOrder.add(labTotal);
        tblItems.setFillsViewportHeight(true);
        this.getContentPane().add(panelOrder);

        JPanel panelButton = new JPanel();
        panelButton.setPreferredSize(new Dimension(400, 100));
        panelButton.add(btnAdd);
        panelButton.add(btnPay);
        /*panelButton.add(btnCard);
        panelButton.add(btnAddress);*/
        this.getContentPane().add(panelButton);

        OrderController();
    }

    public void OrderController(){
        this.getBtnAdd().addActionListener(this);
        this.getBtnPay().addActionListener(this);
        /*this.getBtnCard().addActionListener(this);
        this.getBtnAddress().addActionListener(this);*/

        order = new Order();
        //int currOrder = Application.getInstance().getDataAdapter().loadOrderID();
        //order.setOrderIDCurr(currOrder + 1);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == this.getBtnAdd())
            addProduct();
        else
        if (e.getSource() == this.getBtnPay())
            makeOrder();
        /*else
        if  (e.getSource() == this.getBtnCard())
            addAddress();
        else
        if  (e.getSource() == this.getBtnAddress())
            addAddress();*/
    }

    //do when you hit pay button
    /*private void makeOrder(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "This function is being implemented!");
    
         Remember to update new quantity of products!
        product.setQuantity(product.getQuantity() - quantity); // update new quantity!!
        dataAdapter.saveProduct(product); // and save this product back 
        
    
    }*/

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnPay() {
        return btnPay;
    }
    /*public JButton getBtnCard() {
        return btnCard;
    }
    public JButton getBtnAddress() {
        return btnAddress;
    }*/

    public JLabel getLabTotal() {
        return labTotal;
    }

    public void addRow(Object[] row) {
        items.addRow(row);
    }
    
    private Address addAddress(){
        JTextField myAddress = new JTextField(5);
        JTextField city = new JTextField(5);
        JTextField state = new JTextField(5);
        JTextField myZip = new JTextField(5);

        JPanel addressPanel = new JPanel();
        addressPanel.add(new JLabel("Street Address: "));
        addressPanel.add(myAddress);
        addressPanel.add(Box.createHorizontalStrut(15));
        addressPanel.add(new JLabel("City: "));
        addressPanel.add(city);
        addressPanel.add(Box.createHorizontalStrut(15));
        addressPanel.add(new JLabel("State: "));
        addressPanel.add(state);
        addressPanel.add(Box.createHorizontalStrut(15));
        addressPanel.add(new JLabel("Zipcode: "));
        addressPanel.add(myZip);
        /*int result =*/ JOptionPane.showConfirmDialog(null, addressPanel, "Enter Address Information", JOptionPane.OK_CANCEL_OPTION);
        String userAddress = myAddress.getText();
        String userCity = city.getText();
        String userState = state.getText();
        int userZip = Integer.parseInt(myZip.getText());
        

        Address address = new Address();
        address.setAddressID(Application.getInstance().getCurrentUser().getUserID());
        address.setStreet(userAddress);
        address.setCity(userCity);
        address.setState(userState);
        address.setZip(userZip);

        Application.getInstance().getDataAdapter().saveAddress(address);

        return address;
    }

    private Card addCard(){
        JTextField cardNum = new JTextField(5);
        JTextField expiryMonth = new JTextField(5);
        JTextField expiryYear = new JTextField(5);
        JTextField cvv = new JTextField(5);
        JTextField cardName = new JTextField(5);

        JPanel cardPanel = new JPanel();
        cardPanel.add(new JLabel("Card number: "));
        cardPanel.add(cardNum);
        cardPanel.add(Box.createHorizontalStrut(15));
        cardPanel.add(new JLabel("Expiration month: "));
        cardPanel.add(expiryMonth);
        cardPanel.add(Box.createHorizontalStrut(15));
        cardPanel.add(new JLabel("Expiration year: "));
        cardPanel.add(expiryYear);
        cardPanel.add(Box.createHorizontalStrut(15));
        cardPanel.add(new JLabel("CVV: "));
        cardPanel.add(cvv);
        cardPanel.add(Box.createHorizontalStrut(15));
        cardPanel.add(new JLabel("Name on card: "));
        cardPanel.add(cardName);
        cardPanel.add(Box.createHorizontalStrut(15));
        
        JOptionPane.showConfirmDialog(null, cardPanel, "Enter Card Information", JOptionPane.OK_CANCEL_OPTION);
        long cNum = Long.parseLong(cardNum.getText());
        //BigInteger cNum = new BigInteger(cNumStr);
        String eMonth = expiryMonth.getText();
        int eYear = Integer.parseInt(expiryYear.getText());
        int CVV = Integer.parseInt(cvv.getText());
        String cName = cardName.getText();

        Card card = new Card();
        card.setCardID(Application.getInstance().getCurrentUser().getUserID());
        card.setCardNum(cNum);
        card.setExpiryMonth(eMonth);
        card.setExpiryYear(eYear);
        card.setCvv(CVV);
        card.setCardName(cName);
        
        Application.getInstance().getDataAdapter().saveCard(card);

        return card;
    }

    private void addProduct() {
        System.out.println(order.getOrderID());
        //int currOrderID = Application.getInstance().getDataAdapter().loadOrderID();
        System.out.println(Application.getInstance().getDataAdapter().loadOrderID() + 1);
        //order.setOrderIDCurr(currOrderID + 1);
        //System.out.println(order.getOrderID());
        String id = JOptionPane.showInputDialog("Enter ProductID: ");
        Product product = Application.getInstance().getDataAdapter().loadProduct(Integer.parseInt(id));
        if (product == null) {
            JOptionPane.showMessageDialog(null, "This product does not exist!");
            return;
        }

        double quantity = Double.parseDouble(JOptionPane.showInputDialog(null,"Enter quantity: "));

        if (quantity < 0 || quantity > product.getQuantity()) {
            JOptionPane.showMessageDialog(null, "This quantity is not valid!");
            return;
        }
        int currOrder = Application.getInstance().getDataAdapter().loadOrderID();
        order.setOrderIDCurr(currOrder);

        OrderLine line = new OrderLine();
        line.setOrderID(order.getOrderID());
        //line.setOrderID(this.order.getOrderID());
        //System.out.println(this.order.getOrderID());
        line.setProductID(product.getProductID());
        line.setQuantity(quantity);
        //rounding the cost
        double val = quantity * product.getPrice();
        val = val*100;
        val = Math.round(val);
        val = val/100;
        line.setCost(val);
        order.getLines().add(line);
        //rounding total cost
        double val2 = order.getTotalCost() + line.getCost();
        val2 = val2*100;
        val2 = Math.round(val2);
        val2 = val2/100;
        order.setTotalCost(val2);



        Object[] row = new Object[5];
        row[0] = line.getProductID();
        row[1] = product.getName();
        row[2] = product.getPrice();
        row[3] = line.getQuantity();
        row[4] = line.getCost();

        this.addRow(row);
        this.getLabTotal().setText("Total: $" + order.getTotalCost());
        //order.addLine(line);
        this.invalidate();
    }

    private void makeOrder() {
        Address myAddress = addAddress();
        Card myCard = addCard();
        String date = (java.time.LocalDate.now()).toString();
        order.setDate(date);
        System.out.println("Order Date " + order.getDate());
        order.setBuyerID(Application.getInstance().getCurrentUser().getUserID());
        //System.out.println("BuyerID" + order.getBuyerID() );
        double currCost = order.getTotalCost();
        double currTax = currCost *  .0625;
        order.setTotalTax(currTax);
        System.out.println("orderID " + order.getOrderID() + "BuyerID " + order.getBuyerID() + "totalCost " + currCost + "currTax " + currTax + "orderTotalTax " + order.getTotalTax() );
        //JOptionPane.showMessageDialog(null, "This function is being implemented!");
        for(int i=0; i< order.getLines().size() ; i++ ){
            OrderLine currLine = order.getLines().get(i);
            System.out.println("currLine at "+ i + currLine);
            int currID = currLine.getProductID();
            System.out.println("currLine ID at "+ i + currID);
            //System.out.println("currProd at "+ i + currLine.getProductID());
            Product currProduct = Application.getInstance().getDataAdapter().loadProduct(currID);
            System.out.println("Product name: " + currProduct.getName());
            double currQuant = currLine.getQuantity();
            System.out.println("current Quantity: " + currQuant);
            System.out.println("Curr Prod Quantity: " + currProduct.getQuantity());
            System.out.println("new Quant Estimate: " + (currProduct.getQuantity() - currQuant));
            currProduct.setQuantity(currProduct.getQuantity() - currQuant);
            System.out.println("new Quant: " + currProduct.getQuantity());
            Application.getInstance().getDataAdapter().saveProduct(currProduct);
        }
        System.out.println("outside for loop");
        Application.getInstance().getDataAdapter().saveOrder(order);
        System.out.println("after save order");
        /* Remember to update new quantity of products!
        product.setQuantity(product.getQuantity() - quantity); // update new quantity!!
        dataAdapter.saveProduct(product); // and save this product back */
        //Application.getInstance().getDataAdapter().saveProduct(product);
        makeReceipt(order, myAddress, myCard);
        System.out.println("makeReceipt ran in makeOrder");
    }

    private void makeReceipt(Order order, Address address, Card card){
        System.out.println("in makeReceipt");
        Receipt receipt = new Receipt();
        int rID = order.getOrderID();
        receipt.setOrderID(rID);
        String uName = Application.getInstance().getCurrentUser().getFullName();
        String dateTime = order.getDate();
        double oCost = order.getTotalCost();

        String cardSub = "**** **** **** ";
        String cardN = String.valueOf(card.getCardNum());
        String cardNSub = cardN.substring(cardN.length() - 4);
        // ***
        String cardNumber = cardSub + cardNSub;

        String adStreet = address.getStreet();
        String adCity = address.getCity();
        String adState = address.getState();
        String adZip = String.valueOf(address.getZip());


        String rText = "Order Receipt\n Customer: " + uName + " Date: " + dateTime + " Order ID: " + rID
        + "\n Shipping Address: " + adStreet + ", " + adCity + ", " + adState + " " + adZip
        + "\n........................\n Credit Card: " + cardNumber + "\n ........................\n";

        System.out.println("before for" + rText);
        for(int i=0; i< order.getLines().size() ; i++ ){
            OrderLine currLine = order.getLines().get(i);
            System.out.println(currLine);
            //int currID = currLine.getOrderID();
            int currProdID = currLine.getProductID();
            Product currProduct = Application.getInstance().getDataAdapter().loadProduct(currProdID);
            String pName = currProduct.getName();
            int pID = currProduct.getProductID();
            double prodPrice = currProduct.getPrice();
            double price = currLine.getCost();
            double currQuant = currLine.getQuantity();

            rText += "ID " + pID + " " + pName + "  $" + prodPrice + "  *  " + currQuant + " ..... $" + price + "\n";
            System.out.println("in for at " + i + rText);
            //currProduct.setQuantity(currProduct.getQuantity() - currQuant);
            //Application.getInstance().getDataAdapter().saveProduct(currProduct);
        }
        rText += "...............total: $" + oCost;
        System.out.println("after for loop rText " + rText);
        receipt.setReceiptTXT(rText);

        Application.getInstance().getDataAdapter().saveReceipt(receipt);
        System.out.println("saved rText");
    }
}
