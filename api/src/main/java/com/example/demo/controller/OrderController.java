package com.example.demo.controller;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Address;
import com.example.demo.models.OrderList;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.service.AddressService;
import com.example.demo.service.DeliveryService;
import com.example.demo.service.OrderListService;
import com.example.demo.service.StoreService;
import com.example.demo.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderListService orderListService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private UserService userService;
    
    @GetMapping("")
    public Page<OrderList> getOrders() {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return Page.empty();
        User user = user_opt.get();
        return orderListService.findAll(user, Pageable.unpaged());
    }

    
    @PostMapping("")
    public ResponseEntity<OrderList> postOrders(@RequestParam int store
        ,@RequestParam int address ,@RequestParam String deliveryTimestamp) throws IOException, InterruptedException, ParseException {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        Address getAddress = addressService.getById(address);
        if (getAddress == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Store getStore = storeService.getById(store);
        if (getStore == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //NEED HELP NESTE ENDPOINT
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime deliveryTs = LocalDateTime.parse(deliveryTimestamp, formatter);

        Map<String,Object> body = new HashMap<>();
        Map<String,String> address_send = new HashMap<>();
        Map<String,String> store_address = new HashMap<>();

        store_address.put("address", getStore.getAddress().getAddress());
        store_address.put("city", getStore.getAddress().getCity());
        store_address.put("country", getStore.getAddress().getCountry());
        store_address.put("zipcode", getStore.getAddress().getZipcode());

        address_send.put("address", getAddress.getAddress());
        address_send.put("city", getAddress.getCity());
        address_send.put("country", getAddress.getCountry());
        address_send.put("zipcode", getAddress.getZipcode());

        body.put("shopAddress", store_address);
        body.put("deliveryTimestamp", deliveryTimestamp);
        body.put("clientName", user.getName());
        body.put("clientPhoneNumber", user.getPhoneNumber());
        body.put("address", address_send);




        Long id_delivery= deliveryService.postOrder(body);

        //IR BUSCAR O ID DA DELIVERY E A PARTIR DAQUI FAZER QUERIES POR AQUI

        OrderList x = orderListService.createFromCart(user, getAddress, getStore, id_delivery, deliveryTs);
        return new ResponseEntity<>(x,HttpStatus.CREATED);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Map<String,Object>> getByID( @PathVariable long id) throws IOException, InterruptedException, ParseException {
        Optional<OrderList> ret = orderListService.findById(id);
        //TENHO DE USAR O ID DA DELIVERY , VOU TER QUE ALTERAR O TIPO DE RESPONSE PARA MAP ṔARA SER UMA ORDER + O ESTADO DELA
        // 
        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OrderList ret_final = ret.get();

        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();

        if (user.getId() == ret_final.getProductList().getUser().getId()|| user.getAdmin() || user.getStaff()){
            Map<String,Object> map = new HashMap();
            map.put("order", ret_final);
            
            HashMap<String, Object> ret_delivery = deliveryService.getDelivery(ret_final.getDeliveryId());

            map.put("status", ret_delivery.get("status"));

            return new ResponseEntity<>(map,HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
       
    }

    
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String,Object>> deleteByID( @PathVariable long id) throws IOException, InterruptedException, ParseException {

        Optional<OrderList> ret = orderListService.findById(id);

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OrderList ret_final = ret.get();
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();

        if (user.getId() == ret_final.getProductList().getUser().getId()){
            Map<String,Object> map = new HashMap();
            map.put("order", ret_final);
            
            HashMap<String, Object> ret_delivery = deliveryService.cancelDelivery(ret_final.getDeliveryId());

            map.put("status", ret_delivery.get("status"));

            return new ResponseEntity<>(map,HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

       
    }

    @GetMapping("{id}/fee")
    public HashMap<String,Double> getFee(@PathVariable long id) throws IOException, InterruptedException, ParseException {
        return deliveryService.getFee(id);
    }

}
