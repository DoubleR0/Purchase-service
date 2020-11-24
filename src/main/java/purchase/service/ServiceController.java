package purchase.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class ServiceController {
    // List<Dataservice> dataservice = new ArrayList<>(Arrays.asList(new Dataservice("LIP001", "XOXO Lipstick", 1),
    //         new Dataservice("BRO001", "NYX Brush On Palette", 1)));

    // public void deleteData(String id) {
    //     for (int i = 0; i < dataservice.size(); i++) {
    //         if (dataservice.get(i).getId().equals(id)) {
    //             dataservice.remove(i);
    //             break;
    //         }
    //     }
    // }

    // @RequestMapping(value = "/Data/{id}", method = RequestMethod.DELETE)
    // public String deleteDataservice(@PathVariable String id) {
    //     deleteData(id);
    //     return "Delete0";
    // }

    // PurchaseService
    // send cost confirmed to Payment and waiting for response
    // set method and parameter
    @RequestMapping(value = "/confirmordered/{purchaseid}", method = RequestMethod.GET)
    @ResponseBody
    public void confirmordered(HttpServletRequest request, HttpServletResponse response, @PathVariable String purchaseid) throws IOException {
            // request sent to payment service
        String obj = "{\"order\":\"154811629\"}";
        final String url = "http://localhost:8080/payment";
        RestTemplate restTemplate = new RestTemplate();
        // set header for request sent
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // set Entity for request sent
        HttpEntity<String> entity = new HttpEntity<>(obj, headers);
        // get response request sent
        ResponseEntity<String> respon = restTemplate.postForEntity(url, entity, String.class);

        if (respon.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(respon.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(respon.getStatusCode());
        }
        response.setContentType("application/json");
        response.getWriter().print(response);
        // set responses code
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // get json Object from OrderService and call other Service for example
    // ShippingService, PaymentService, and location from CustomerService
    // set method and path
    @RequestMapping(value = "/detailorder/{orderid}", method = RequestMethod.GET)
    // @ResponseBody
    public String detailorder(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String orderid) throws IOException {
        String url = "https://order.tungmai.me/api/order/" + orderid;
        RestTemplate restTemplate = new RestTemplate();
        String dataservice = restTemplate.getForObject(url, String.class);
        JSONObject orderObject = new JSONObject(dataservice);
        // get token from header request
        // String authorization = request.getHeader("authorization");
        // // get Jsonobject from body request
        // String data = "";
        // StringBuilder builder = new StringBuilder();
        // BufferedReader reader = request.getReader();
        // String line;
        // while ((line = reader.readLine()) != null) {
        //     builder.append(line);
        // }
        // data = builder.toString();
        // // convert string to jsonobject
        // JSONObject Body = new JSONObject(data);

        // System.out.println("Body ====== " + Body);
        // System.out.println("authorization ========= " + authorization);
        // System.out.println(request);

        // request data from shiping
        // String url = "http://localhost:8080/shiping";
        // RestTemplate restTemplate = new RestTemplate();
        // String dataservice = restTemplate.getForObject(url, String.class);
        // JSONObject shipingObject = new JSONObject(dataservice);

        // request data from payment
        url = "https://sop-picnic.azurewebsites.net/payment";
        restTemplate = new RestTemplate();
        // dataservice = restTemplate.getForObject(url, String.class);
        // JSONObject paymentObject = new JSONObject(dataservice);

        // // request data from user
        url = "https://sop-picnic.azurewebsites.net/profile/" + orderObject.get("user_id");
        restTemplate = new RestTemplate();
        dataservice = restTemplate.getForObject(url, String.class);
        JSONObject userObject = new JSONObject(dataservice);

        // set responses type
        response.setContentType("application/json");
        // set responses data
        // System.out.println("shipingObject ======== " + shipingObject);
        // response.getWriter().println(shipingObject);
        // response.getWriter().println(paymentObject);
        // response.getWriter().println(userObject);
        // response.getWriter().println(orderObject);
        // response.getWriter().println(orderObject.get("product"));
        // set responses code
        System.out.println(orderObject.get("product").getClass());
        model.addAttribute("address", userObject.get("address"));

        JSONArray jsonArray = new JSONArray(orderObject.get("product").toString());
        JSONArray jsonPersonData = jsonArray;
        // System.out.print(jsonPersonData);
        Integer id = 0;
        for (int i=0; i<jsonPersonData.length(); i++) {
            JSONObject item = jsonPersonData.getJSONObject(i);
            // System.out.println(item);
            id = (Integer) item.get("id");
            model.addAttribute("amount", item.get("amount"));
            model.addAttribute("total", item.get("total"));
            // String name = item.getString("name");
            // String surname = item.getString("surname");
        }

        url = "http://stock.phwt.me/product/" + id;
        restTemplate = new RestTemplate();
        dataservice = restTemplate.getForObject(url, String.class);
        JSONObject productObject1 = new JSONObject(dataservice);

        model.addAttribute("productObject1", productObject1.get("name"));
        model.addAttribute("price1", orderObject.get(""));

        url = "https://mhee-promotion.herokuapp.com/promotions";
        restTemplate = new RestTemplate();
        dataservice = restTemplate.getForObject(url, String.class);
        JSONObject promotionsObject = new JSONObject(dataservice);
        model.addAttribute("promotions", userObject.get("promotion"));
        response.setStatus(HttpServletResponse.SC_OK);
        return "first";
    }

    // look all order in log for maintenance or other...
    // set method and path
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    @ResponseBody
    public void log(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // empty

        // set responses type
        response.setContentType("application/json");
        // set responses data
        response.getWriter().print(response);
        // set responses code
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // look One ordered for maintenance or other...
    // set method and parameter
    @RequestMapping(value = "/log/{purchaseid}", method = RequestMethod.GET)
    @ResponseBody
    public void logOne(HttpServletResponse response) throws IOException {

        // empty

        // set responses type
        response.setContentType("application/json");
        // set responses data
        response.getWriter().print(response);
        // set responses code
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // update payment from PaymentService and save to Dataservice, send data to ShipingService and send to OrderService to save history order
    // set method and parameter
    @RequestMapping(value = "/updatepaymentlog/{purchaseid}", method = RequestMethod.PATCH)
    @ResponseBody
    public void updatepaymentlog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // get token from header request
        String authorization = request.getHeader("authorization");
        // get Jsonobject from body request
        String data = "";
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        data = builder.toString();
        // convert string to jsonobject
        JSONObject Body = new JSONObject(data);

        // request sent to shiping service
        String obj = "{\"order\":\"154811629\"}";
        final String url = "http://localhost:8080/shiping";
        RestTemplate restTemplate = new RestTemplate();
        // set header for request sent
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // set Entity for request sent
        HttpEntity<String> entity = new HttpEntity<>(obj, headers);
        // get response request sent
        ResponseEntity<String> respon = restTemplate.postForEntity(url, entity, String.class);

        if (respon.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(respon.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(respon.getStatusCode());
        }

        response.setContentType("application/json");
        response.getWriter().println(Body);
        response.getWriter().println(authorization);
    }

    // add Dataservice to log for emergency or maintenance
    @RequestMapping(value = "/addlog/{purchaseid}", method = RequestMethod.PUT)
    @ResponseBody
    public void addlog(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // empty

        // set responses type
        response.setContentType("application/json");
        // set responses data
        response.getWriter().print(response);
        // set responses code
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // delete Dataservice from log when ordered finished or maintenance
    @RequestMapping(value = "/deletelog/{purchaseid}", method = RequestMethod.GET)
    @ResponseBody
    public void deletelog(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // empty

        // set responses type
        response.setContentType("application/json");
        // set responses data
        response.getWriter().print(response);
        // set responses code
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
