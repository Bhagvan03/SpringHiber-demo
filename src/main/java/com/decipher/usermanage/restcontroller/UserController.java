package com.decipher.usermanage.restcontroller;

import com.decipher.usermanage.entities.Account;
import com.decipher.usermanage.entities.CoordinateDetails;
import com.decipher.usermanage.service.AccountService;
import com.decipher.usermanage.service.CoordinateService;
import com.decipher.usermanage.util.JsonResponse;
import com.decipher.usermanage.util.UserLogger;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by decipher16 on 3/3/17.
 */
@Controller
//@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CoordinateService coordinateService;

    public static final String MESSAGE = "message";

    @RequestMapping(value = "/createnewuser", method = RequestMethod.POST)
    public String newUserPage(@ModelAttribute Account account) {
        ModelAndView model = new ModelAndView();
        model.addObject(MESSAGE, "Please " + account.getUserName() + " check your Email ...");
        account.setStatus(false);
        UserLogger.info("role-->>> " + account.getRole()+", address : "+account.getAddress());
        if (account.getEmail() != null && account.getRole() != null) {
            account.setPassword(RandomStringUtils.randomAlphanumeric(8));
            Boolean checkAvailabilityStatus = accountService.checkUserAvailability(account.getEmail());
            UserLogger.info("email-->>>" + account.getEmail());
            if (!checkAvailabilityStatus) {
//            status = EmailSenderService.mailingService(account);
//                status=true;
                accountService.saveOrUpdateAccount(account);
                return "redirect:/success";
            }
             else {
                return "redirect:/registration";
            }
        }
        else {
            return "redirect:/registration";
        }
    }

    @RequestMapping(value = "/position" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse draggerPositionDetails(@RequestParam Integer coordinateDetailsId,
                                               @RequestParam String leftPosition,
                                               @RequestParam String topPosition) {
        JsonResponse jsonResponse=new JsonResponse();
        CoordinateDetails coordinateDetails=new CoordinateDetails();
        coordinateDetails.setCoordinateDetailsId(1);
        coordinateDetails.setLeftPosition(leftPosition);
        coordinateDetails.setTopPosition(topPosition);
//        UserLogger.info("coordinateDetails leftPosition : "+coordinateDetails.getLeftPosition()+", top Position : "+coordinateDetails.getTopPosition());
        Boolean status=coordinateService.saveOrUpdatePosition(coordinateDetails);
        UserLogger.info("dragger Position : "+status);
        jsonResponse.setResultStatus(status);
        return jsonResponse;
    }


    @RequestMapping(value = "/getPosition" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse getPosition(@RequestParam Integer coordinateDetailsId) {
        JsonResponse jsonResponse=new JsonResponse();
        JSONObject jsonObject=new JSONObject();
//        UserLogger.info("coordinateDetails leftPosition : "+coordinateDetails.getLeftPosition()+", top Position : "+coordinateDetails.getTopPosition());
        CoordinateDetails coordinateDetails=coordinateService.getCoordinateDetailsById(coordinateDetailsId);
        jsonObject.put("leftPosition",coordinateDetails.getLeftPosition());
        jsonObject.put("topPosition",coordinateDetails.getTopPosition());
        UserLogger.info("get dragger Position details...");
        jsonResponse.setResultObject(jsonObject);
        return jsonResponse;
    }

}
