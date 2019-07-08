package es.macero.dev.restexample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookResponse;

@RestController
@RequestMapping("/american-greetings")
public class AmericanGreetingController {

    private List<AmericanGreeting> americanGreetings;

    public AmericanGreetingController() {
    	americanGreetings = new ArrayList<>();
    	americanGreetings.add(new AmericanGreeting("Hola!"));
        americanGreetings.add(new AmericanGreeting("Qué tal?!"));
        americanGreetings.add(new AmericanGreeting("Buenas!"));
    }

    @GetMapping("/{id}")
    public AmericanGreeting getAmericanGreetingById(@PathVariable("id") final int id) {
        return americanGreetings.get(id - 1); // list index starts with 0 but we prefer to start on 1
    }

    @GetMapping("/random")
    public AmericanGreeting getRandom() {
        return americanGreetings.get(new Random().nextInt(americanGreetings.size()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createAmericanGreeting(@RequestBody AmericanGreeting americanGreeting) {
    	americanGreetings.add(americanGreeting);
    }
    @GetMapping(path = "/EnableAutoDeployments" , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<String> enableAutoDeployments(@RequestParam String flag){
    	List<String> list = new ArrayList<>();
    	if(flag.equalsIgnoreCase("true")) {
    	list.add("AutomaticDeploymentsEnabled - In Heroku - Click on EnableAutomaticDeployents");
    	list.add("If you won't select EnableAutomaticDeployments in Heroku - you need to build and deploy");
    	return list;
    	}
    	return Arrays.asList("Please enable the flag in heroku ...");
    	}
    
    @RequestMapping(path="/webhookdialogflow",method=RequestMethod.POST)
     public ResponseEntity<?> getDialogFlowHook(@RequestParam String requestStr){
    	 JacksonFactory jacksonFactory = new JacksonFactory();
    	 GoogleCloudDialogflowV2WebhookResponse webHookRes = new GoogleCloudDialogflowV2WebhookResponse();
    	 GoogleCloudDialogflowV2WebhookRequest webHookReq = null;
		try {
			webHookReq = jacksonFactory.createJsonParser(requestStr).parse(GoogleCloudDialogflowV2WebhookRequest.class);
			Map<String,Object> jsonObj = webHookReq.getQueryResult().getParameters();
	    	 System.out.println("jsobObj : " + jsonObj);
	    	 
	    	 Map<String,Object> payloadWebhook = webHookReq.getQueryResult();
	    	 System.out.println("payloadWebhook : " + payloadWebhook);

	    	 if(jsonObj.size()>0) {
	    		 
	    		 webHookRes.setFulfillmentText(webHookReq.getQueryResult().getFulfillmentText());
	    	 }else {
	    		 webHookRes.setFulfillmentText("Fill the dialogflow properly.....");
	    	 }
	    	
		} catch (IOException e) {
			return new ResponseEntity<GoogleCloudDialogflowV2WebhookResponse>(webHookRes,HttpStatus.BAD_REQUEST);
		}
    	 
    	 return new ResponseEntity<GoogleCloudDialogflowV2WebhookResponse>(webHookRes,HttpStatus.OK);
     }
    
}
