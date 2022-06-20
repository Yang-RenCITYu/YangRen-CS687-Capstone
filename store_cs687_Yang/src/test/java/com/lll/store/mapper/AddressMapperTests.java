package com.lll.store.mapper;

import com.lll.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(3);
        address.setPhone("12345678901");
        address.setName("小武");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid(){
        Integer count = addressMapper.countByUid(3);
        System.out.println(count);
    }

    @Test
    public void findeByUid(){
        List<Address> list = addressMapper.findByUid(3);
        System.out.println(list);
    }

    @Test
    public void findByAid(){
        System.out.println(addressMapper.findByAid(3));
    }

    @Test
    public void updateNonDefaultByUid(){
        addressMapper.updateNonDefaultByUid(11);
    }

    @Test
    public void updateDefaultByAid(){
        addressMapper.updateDefaultByAid(3,"梦洁",new Date());
    }

    @Test
    public void deleteByAid(){
        addressMapper.deleteByAid(10);
    }

    @Test
    public void findLastModified(){
        System.out.println(addressMapper.findLastModified(3));
    }
}
