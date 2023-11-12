import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ProductView extends JFrame implements ActionListener{
    private JTextField txtProductID  = new JTextField(10);
    private JTextField txtProductName  = new JTextField(30);
    private JTextField txtProductPrice  = new JTextField(10);
    private JTextField txtProductQuantity  = new JTextField(10);

    private JButton btnLoad = new JButton("Load Product");
    
    private JButton btnSave = new JButton("Save Product");

    public ProductView() {
        this.setTitle("Manage Products");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(500, 200);

        JPanel panelButton = new JPanel();
        panelButton.add(btnLoad);
        panelButton.add(btnSave);
        this.getContentPane().add(panelButton);

        JPanel panelProductID = new JPanel();
        panelProductID.add(new JLabel("Product ID: "));
        panelProductID.add(txtProductID);
        txtProductID.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelProductID);

        JPanel panelProductName = new JPanel();
        panelProductName.add(new JLabel("Product Name: "));
        panelProductName.add(txtProductName);
        this.getContentPane().add(panelProductName);

        JPanel panelProductInfo = new JPanel();
        panelProductInfo.add(new JLabel("Price: "));
        panelProductInfo.add(txtProductPrice);
        txtProductPrice.setHorizontalAlignment(JTextField.RIGHT);

        panelProductInfo.add(new JLabel("Quantity: "));
        panelProductInfo.add(txtProductQuantity);
        txtProductQuantity.setHorizontalAlignment(JTextField.RIGHT);

        this.getContentPane().add(panelProductInfo);


        ProductController();
    }

    public void ProductController(){
        this.getBtnLoad().addActionListener(this);
        this.getBtnSave().addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()== this.getBtnLoad())
            loadProduct();
        else
        if (e.getSource() == this.getBtnSave())
            saveProduct();
    }


    private void loadProduct() {
        int productID = 0;
        try {
            productID = Integer.parseInt(this.getTxtProductID().getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product ID! Please provide a valid product ID!");
            return;
        }

        Product product = Application.getInstance().getDataAdapter().loadProduct(productID);

        if (product == null) {
            JOptionPane.showMessageDialog(null, "This product ID does not exist in the database!");
            return;
        }

        this.getTxtProductPrice().setText(String.valueOf(product.getPrice()));
        this.getTxtProductName().setText(String.valueOf(product.getName()));
        this.getTxtProductQuantity().setText(String.valueOf(product.getQuantity()));
    }




    public JButton getBtnLoad() {
        return btnLoad;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JTextField getTxtProductID() {
        return txtProductID;
    }

    public JTextField getTxtProductName() {
        return txtProductName;
    }

    public JTextField getTxtProductPrice() {
        return txtProductPrice;
    }

    public JTextField getTxtProductQuantity() {
        return txtProductQuantity;
    }



private void saveProduct() {
        int productID;
        try {
            productID = Integer.parseInt(this.getTxtProductID().getText());

            if(Integer.parseInt(this.getTxtProductID().getText())<0){
                throw new IllegalArgumentException("Invalid product ID! Product ID must be greater than 0. Please provide a valid product ID!");  
            }            


        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product ID! Please provide a valid product ID!");
            return;
        }

        catch(IllegalArgumentException i){
            JOptionPane.showMessageDialog(null, "Invalid product ID! Please provide a valid product ID greater than 0!");
            return;
        }


        double productPrice;
        try {
            productPrice = Double.parseDouble(this.getTxtProductPrice().getText());

            if(productPrice<0){
                throw new IllegalArgumentException("Invalid product price! Price must be greater than 0. Please provide a valid product price!");  
            } 

        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product price! Please provide a valid product price!");
            return;
        }

        catch(IllegalArgumentException i){
            JOptionPane.showMessageDialog(null, "Invalid product price! Price must be greater than 0. Please provide a valid product price!");  
            return;
        }


        double productQuantity;
        try {
            productQuantity = Double.parseDouble(this.getTxtProductQuantity().getText());

            if(productPrice<0){
                throw new IllegalArgumentException("Invalid product quantity! Quantity must be greater than 0. Please provide a valid product quantity!");  
            } 

        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product quantity! Please provide a valid product quantity!");
            return;
        }

        catch(IllegalArgumentException i){
            JOptionPane.showMessageDialog(null, "Invalid product quantity! Quantity must be greater than 0. Please provide a valid product quantity!");  
            return;
        }


        String productName = this.getTxtProductName().getText().trim();

        if (productName.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid product name! Please provide a non-empty product name!");
            return;
        }

        // Done all validations! Make an object for this product!

        Product product = new Product();
        product.setProductID(productID);
        product.setSellerID(Application.getInstance().getCurrentUser().getUserID());
        product.setName(productName);
        product.setPrice(productPrice);
        product.setQuantity(productQuantity);

        // Store the product to the database

        Application.getInstance().getDataAdapter().saveProduct(product);
    }
}

