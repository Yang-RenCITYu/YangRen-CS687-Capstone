package com.lll.store.service;

import com.lll.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setName("lll");
        address.setPhone("12345678941");
        addressService.addNewAddress(3,"管理员",address);
    }

    @Test
    public void addNewAdress(){
        addressService.setDefault(3,11,"管理员");
    }

    @Test
    public void delete(){
        addressService.delete(8,10,"管理员");
    }
}
