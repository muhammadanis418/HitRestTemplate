package com.example.hitrestcontroller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class HitController {
    //@Autowired
    private final RestTemplate restTemplate;
    private String url = "http://localhost:8080/create/user";
    private String url1 = "http://localhost:8080/user/";

    private String url2 = "http://localhost:8080/createUser";

    private String url3 = "http://localhost:8080/user/1?name=anis";
    private String url4 = "http://localhost:8080/users/1?name=anis";

    private String url5 = "http://localhost:8080/users";

    private String finalUrl = "http://localhost:8080/check";
    //   finalUrl = "http://localhost:8080/check/1?name=ozair"


    public HitController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hit")
    public String show() {
        String response = restTemplate.getForObject(url, String.class);
        return response + "Success";
    }

    @GetMapping("/hitagain/{id}")
    public String ping(@PathVariable int id) {
       /*
        String response=restTemplate.getForObject(url1,String.class, Map.of("id",1));
           //---PostMan data Show in this way
        TestModel(id=2, name=an, isPresent=false, age=43, rollNo=10)user id is:Pong
        */
        String response = restTemplate.getForObject(url1 + id, String.class);
        return response + "Pong" + id;
    }

    @PostMapping("/hit")
    //public String ping(@RequestBody TestModel testModel)
    public String ping() {
        TestModel testModel = new TestModel(8, "Ozair", false, 25, 32);
        String createTestModel = restTemplate.postForObject(url2, testModel, String.class);
        return "API is Called----!" + createTestModel;
    }

    @GetMapping("/hits")
    public String pong() {
        //  HttpHeaders httpHeaders =new HttpHeaders();
        // httpHeaders.set("name","Anis");
        // HttpEntity<String> req= new HttpEntity<>(httpHeaders);
        // int id=23;
        String response = restTemplate.getForObject(url3, String.class);
        return "Api is Called----" + response;

    }

    @PostMapping("/hits")
    public String pongOne() {
        TestModel testModel = new TestModel(9, "Waqas", true, 24, 31);
        String response = restTemplate.postForObject(url4, testModel, String.class);
        return "PAthVariable,RequestParams,RequestBody" + response;
    }

    @GetMapping("/hitUrl")
    public String pingOne() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("id", "1");
        headers.set("name", "Anis");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<String> testmodel = restTemplate.exchange(url5, HttpMethod.GET, request, String.class);

        // String response= restTemplate.getForObject(url,String.class);

        if (testmodel.getStatusCode() == HttpStatus.OK) return "Request Successful..." + testmodel.getBody();
        else

            return ("Request Failed" + testmodel.getStatusCode());

        // return "Ok";
    }

    @PostMapping("/check/{id}")
    public String checkAll(@PathVariable(name = "id") int id, @RequestParam(name = "name") String name) {

        id = 1;
        name = "?name=jahanzaib";
        TestModel testModel = new TestModel(2, "Ozair", false, 12, 01);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("isPresent", String.valueOf(true));

        HttpEntity<TestModel> request = new HttpEntity<>(testModel, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(finalUrl + id + name, HttpMethod.POST, request, String.class);
        // String respo=restTemplate.postForEntity(finalUrl,HttpMethod.POST,request,testModel,String.class);
        if (response.getStatusCode() == HttpStatus.OK)
            return "ok---" + "-----" + id + "-----" + name + "-----" + response.getBody();
        else return "Request FAiled";
    }
}
