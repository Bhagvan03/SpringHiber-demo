package com.decipher.usermanage.util.datatable;

import com.decipher.usermanage.entities.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by decipher19 on 4/4/17.
 */
public class DataTableDetails {
    private int iTotalRecords;

    private int iTotalDisplayRecords;

    private String sEcho;

    private String sColumns;

    private List<Account> aaData;

    public static Integer pagingCounter(List list,Integer pageDisplayLength){
        return list.size()%pageDisplayLength==0 ? list.size()/pageDisplayLength : list.size()/pageDisplayLength+1;
    }


    /***
     * searching data(according Username,emailId & role wise) from server side data table
     * @param searchParameter
     * @param accountList
     * @return
     */

    public static List<Account> getListBasedOnSearchParameter(String searchParameter,List<Account> accountList) {

        if (searchParameter != null && !searchParameter.equals("")) {
            List<Account> accountsListForSearch = new ArrayList();
            searchParameter = searchParameter.toUpperCase();
            for (Account account : accountList) {
                if (account.getUserName().toUpperCase().indexOf(searchParameter)!= -1 || account.getEmail().toUpperCase().indexOf(searchParameter)!= -1
                        || account.getRole().getRole().toUpperCase().indexOf(searchParameter)!= -1) {
                    accountsListForSearch.add(account);
                }

            }
            accountList = accountsListForSearch;
        }
        return accountList;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }

    public List<Account> getAaData() {
        return aaData;
    }

    public void setAaData(List<Account> aaData) {
        this.aaData = aaData;
    }
}
