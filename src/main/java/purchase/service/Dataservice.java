package purchase.service;

public class Dataservice {
    private int purchaseidid;
    private int cartid;
    private int productid;
    private int paymentAccountid;
    private int paymentLogid;
    private String userProfileid;
    private double result;
    private String address;
    public Dataservice(int purchaseidid, int cartid, int productid,
    int paymentAccountid, int paymentLogid,
             String userProfileid, double result, String address) {
        this.purchaseidid = purchaseidid;
        this.cartid = cartid;
        this.productid = productid;
        this.paymentAccountid = paymentAccountid;
        this.paymentLogid = paymentLogid;
        this.userProfileid = userProfileid;
        this.result = result;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaseidid() {
        return purchaseid;
    }

    public void setPurchaseidid(String purchaseidid) {
        this.purchaseid = purchaseidid;
    }

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPaymentAccountid() {
        return paymentAccountid;
    }

    public void setPaymentAccountid(String paymentAccountid) {
        this.paymentAccountid = paymentAccountid;
    }

    public String getPaymentLogid() {
        return paymentLogid;
    }

    public void setPaymentLogid(String paymentLogid) {
        this.paymentLogid = paymentLogid;
    }

    public String getUserProfileid() {
        return userProfileid;
    }

    public void setUserProfileid(String userProfileid) {
        this.userProfileid = userProfileid;
    }

    public String getResultid() {
        return resultid;
    }

    public void setResultid(String resultid) {
        this.resultid = resultid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    // private String name;
    // private int    amount;

    // public Dataservice(String id, String name, int amount){
    //     this.id = id;
    //     this.name = name;
    //     this.amount = amount;
    // }

    // public String getId() {
    //     return id;
    // }

    // public void setId(String id) {
    //     this.id = id;
    // }

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public int getAmount() {
    //     return amount;
    // }

    // public void setAmount(int amount) {
    //     this.amount = amount;
    // }

    
}
