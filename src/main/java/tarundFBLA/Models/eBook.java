package tarundFBLA.Models;

public class eBook {
    private int id;
    private String issueStatus;
    private String ownerFirstName, ownerLastName, Author, bookName, RedemptionCode;

    public eBook(int id, String bookName, String author, String redemptionCode, String issuedState){
        this.id = id;
        this.bookName = bookName;
        this.Author = author;
        this.RedemptionCode = redemptionCode;
        this.issueStatus = issuedState;
    }

    public String getID(){return String.valueOf(id);}
    public void setID(String id){this.id = Integer.parseInt(id);}
    public String getAuthor(){return Author;}
    public void setAuthor(String author){this.Author = author;}
    public String getBookName(){return bookName;}
    public void setBookName(String bookName){this.bookName = bookName;}
    //public boolean getIssueStatus(){return issueStatus;}
    public String getIssueStatus(){return String.valueOf(issueStatus);}
    public void setIssueStatus(String IssueStatus){this.issueStatus=String.valueOf(IssueStatus);}
    public String getRedemptionCode(){return RedemptionCode;}
    public void setRedemptionCode(String redemptionCode){this.RedemptionCode=redemptionCode;}
    public String getOwnerFirstName(){return ownerFirstName;}
    public void setOwnerFirstName(String ownerFirstName){this.ownerFirstName = ownerFirstName;}
    public String getOwnerLastName(){return ownerLastName;}
    public void setOwnerLastName(String ownerLastName){this.ownerLastName = ownerLastName;}
}
