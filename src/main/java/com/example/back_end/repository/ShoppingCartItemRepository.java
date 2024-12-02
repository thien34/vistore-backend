package com.example.back_end.repository;

import com.example.back_end.entity.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    ShoppingCartItem findByCartUUID(String cartUUID);

    List<ShoppingCartItem> findByParentId(String parentId);

    void deleteByCartUUID(String cartUUID);

    void deleteByParentId(String parentId);

    List<ShoppingCartItem> findAllByCustomerId(Long idCustomer);

    List<ShoppingCartItem> findAllByCustomerIdAndIdIn(Long idCustomer, List<Long> idCarts);

}
