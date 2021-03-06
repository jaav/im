// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import be.mobila.im.models.Response;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect ResponseDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ResponseDataOnDemand: @Component;
    
    private Random ResponseDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Response> ResponseDataOnDemand.data;
    
    public Response ResponseDataOnDemand.getNewTransientResponse(int index) {
        be.mobila.im.models.Response obj = new be.mobila.im.models.Response();
        obj.setAddress("address_" + index);
        obj.setImRequest(null);
        obj.setImresult(null);
        obj.setResponseDate(new java.util.Date());
        obj.setResponseMode(null);
        obj.setStatus(null);
        return obj;
    }
    
    public Response ResponseDataOnDemand.getSpecificResponse(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Response obj = data.get(index);
        return Response.findResponse(obj.getId());
    }
    
    public Response ResponseDataOnDemand.getRandomResponse() {
        init();
        Response obj = data.get(rnd.nextInt(data.size()));
        return Response.findResponse(obj.getId());
    }
    
    public boolean ResponseDataOnDemand.modifyResponse(Response obj) {
        return false;
    }
    
    public void ResponseDataOnDemand.init() {
        data = be.mobila.im.models.Response.findResponseEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Response' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<be.mobila.im.models.Response>();
        for (int i = 0; i < 10; i++) {
            be.mobila.im.models.Response obj = getNewTransientResponse(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
