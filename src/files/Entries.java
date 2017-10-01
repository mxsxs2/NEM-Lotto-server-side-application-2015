/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

/**
 *
 * @author Mxsxs2
 */
public class Entries {
    private int TotalEntries=0;
    private int TotalValidEntries=0;
    private float TotalAmount=0;
    private float TotalValidAmount=0;
    private final float[][] EachEntries=new float[][]{{0,0},{0,0},{0,0},{0,0}};
    protected final java.util.ArrayList<String[]> list = new java.util.ArrayList<>();
    protected String Option;
    protected final MysqlAccess SQL = new MysqlAccess();
    protected String URL="";
    public java.util.ArrayList<String[]> getList(){
        return this.list;
    }
    public float[][]getEachEntries(){
        return this.EachEntries;
    }
    public Object[] getEntriesAndAmounts(){
        return new Object[]{this.TotalAmount,           //0
                            this.TotalValidAmount,      //1
                            this.TotalEntries,          //2
                            this.TotalValidEntries};    //3
    }
    protected void setAmountsAndEntries(int option, double amount, String nemaddress){
        this.TotalAmount+=amount;
        this.TotalEntries++;
        if(!nemaddress.equals("false") && amount/100000000>0){                   //if not a refund
            this.TotalValidAmount+=amount;
            this.TotalValidEntries++;
            this.EachEntries[option][0]++;
            this.EachEntries[option][1]+=amount;
        }
    }
}
