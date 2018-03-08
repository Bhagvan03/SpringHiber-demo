package com.decipher.usermanage.restcontroller;

import com.decipher.usermanage.entities.Account;
import com.decipher.usermanage.enums.Role;
import com.decipher.usermanage.service.AccountService;
import com.decipher.usermanage.util.datatable.DataTableDetails;
import com.decipher.usermanage.util.JsonResponse;
import com.decipher.usermanage.util.UserLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by decipher19 on 7/4/17.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/removeAccount", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse removeUserAccount(@RequestParam String emailId) {
        JsonResponse jsonResponse=new JsonResponse();
        Boolean status = accountService.removeAccountByEmail(emailId);
        String result;
        if (status) {
            result = "successfully " + emailId + " is removed from Admin Panel : ";
        }
        else {
            result = "Account " + emailId + " is not removed from Admin Panel : ";
        }
        jsonResponse.setStatus(result);
        jsonResponse.setResultStatus(status);
        return jsonResponse;
    }

    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateUserAccount(@RequestParam (value = "userName",required = true)String userName,
                                                        @RequestParam (value = "emailId",required = true)String emailId,
                                                        @RequestParam (value = "role" ,required = true) String role) {
        JsonResponse jsonResponse=new JsonResponse();

        JSONObject jsonObject=new JSONObject();
        Account account=accountService.findUserByEmailId(emailId);
        Integer roleValue=null;
        if("ROLE_ADMIN".equals(role))
        {
            roleValue=0;
        }
        else if("ROLE_USER".equals(role))
        {
            roleValue=1;
        }
        else if("ROLE_DBA".equals(role))
        {
            roleValue=2;
        }
        UserLogger.info("role : "+role);
        account.setRole(Role.getRoleByValue(roleValue));
        account.setUserName(userName);
        Boolean status = accountService.saveOrUpdateAccount(account);
        UserLogger.info("userName : "+account.getUserName()+", emailId : "+account.getEmail());
        String result;

        if (status) {
            result = "successfully " + userName + " is updated from Admin Panel : ";
            jsonObject.put("userName",account.getUserName());
            jsonObject.put("userEmailId",account.getEmail());
            jsonObject.put("userRole",account.getRole().getRole());
        }
        else {
            result = "Account " + account.getUserName() + " is not update from Admin Panel : ";
        }
        jsonResponse.setStatus(result);
        jsonResponse.setResultObject(jsonObject);
        jsonResponse.setResultStatus(status);
        return jsonResponse;
    }

    @RequestMapping(value = "/serverSidePaginationDataTables", method = RequestMethod.POST)
    @ResponseBody
    public String springPaginationDataTables(HttpServletRequest request) throws IOException {

        //Fetch the page number from client
        Integer pageNumber=0;

        //Fetch Page display length
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        if (null != request.getParameter("iDisplayStart"))
        {
            pageNumber = pageNumber+Integer.valueOf(request.getParameter("iDisplayStart"))/pageDisplayLength+1;
        }

        String orderColumn=request.getParameter("iSortCol_0");
        String orderDirection=request.getParameter("sSortDir_0");
        String orderColumnName=null;
        if(orderColumn != null && orderDirection !=null) {
            if (orderColumn.equals(String.valueOf(0))){
                orderColumnName="userName";
            }else if(orderColumn.equals(String.valueOf(1))){
                orderColumnName="email";
            }else if(orderColumn.equals(String.valueOf(2)))
            {
                orderColumnName="role";
            }
            else {
                orderColumnName=null;
            }
        }
        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");

        //Create page list data
        List<Account> accountsList = accountService.getAllAccountWithoutAdmin((pageNumber-1)*pageDisplayLength,pageDisplayLength,orderColumnName,orderDirection);

        if (searchParameter != null) {
            //Search functionality: Returns filtered list based on search parameter
            accountsList = DataTableDetails.getListBasedOnSearchParameter(searchParameter, accountsList);
        }
        UserLogger.info("pageNumber : "+pageNumber+", pageDisplayLength : "+pageDisplayLength+", accountsList Len : "+accountsList.size());
        DataTableDetails dataTableDetails = new DataTableDetails();
        //Set Total display record
        List<Account> allAccountsList = accountService.getAllAccount();
        dataTableDetails.setiTotalDisplayRecords(allAccountsList.size()-1);
        //Set Total record
        dataTableDetails.setiTotalRecords(accountsList.size());
        dataTableDetails.setAaData(accountsList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(dataTableDetails);
    }
}
