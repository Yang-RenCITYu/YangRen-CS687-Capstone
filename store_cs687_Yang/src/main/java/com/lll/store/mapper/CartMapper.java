package com.lll.store.mapper;

import com.lll.store.entity.Cart;
import com.lll.store.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/** persistence layer interface for handling shopping cart data */
public interface CartMapper {
    /**
     * Insert shopping cart data
     * @param cart cart data
     * @return the number of rows affected
     */
    Integer insert(Cart cart);

    /**
     * Modify the number of items in the cart data
     * @param cid the id of the cart data
     * @param num new quantity
     * @param modifiedUser Modify the executor
     * @param modifiedTime The time of modification
     * @return the number of rows affected
     */
    Integer updateNumByCid(
            @Param("cid") Integer cid,
            @Param("num") Integer num,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * Query the data in the shopping cart based on user id and product id
     * @param uid user id
     * @param pid product id
     * @return the matching cart data, or null if the product is not in the user's cart
     */
    Cart findByUidAndPid(
            @Param("uid") Integer uid,
            @Param("pid") Integer pid);

    /**
     * Query a user's shopping cart data
     * @param uid user id
     * @return the list of shopping cart data for this user
     */
    List<CartVO> findVOByUid(Integer uid);

    /**
     * Find the cart data details based on the cart data id
     * @param cid shopping cart data id
     * @return the matching cart data details, or null if there is no matching data
     */
    Cart findByCid(Integer cid);

    /**
     * Find a list of details based on several cart data ids
     * @param cids several shopping cart data ids
     * @return A list of matching cart data details
     */
    List<CartVO> findVOByCids(Integer[] cids);
}
