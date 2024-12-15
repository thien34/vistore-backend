package com.example.back_end.core.admin.cart.controller;

import com.example.back_end.core.admin.cart.payload.request.CartRequest;
import com.example.back_end.core.admin.cart.payload.response.BillCountResponse;
import com.example.back_end.core.admin.cart.payload.response.CartResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.cart.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/carts")
public class CartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/{parentId}")
    public ResponseData<Void> addToCart(@RequestBody CartRequest cartRequest, @PathVariable String parentId) {
        shoppingCartService.addProduct(cartRequest, parentId);
        return new ResponseData<>(HttpStatus.OK.value(), "Thêm vào giỏ hàng thành công");
    }

    @PostMapping("/add-bill/{billId}")
    public ResponseData<Void> addBill(@PathVariable String billId) {
        shoppingCartService.addBill(billId);
        return new ResponseData<>(HttpStatus.OK.value(), "Thêm hóa đơn thành công");
    }

    @GetMapping("/{parentId}")
    public ResponseData<List<CartResponse>> getCart(@PathVariable String parentId) {
        List<CartResponse> responses = shoppingCartService.getCartById(parentId);
        return new ResponseData<>(HttpStatus.OK.value(), "Lấy giỏ hàng thành công", responses);
    }

    @DeleteMapping("/{billId}")
    public ResponseData<Void> deleteBill(@PathVariable String billId) {
        shoppingCartService.deleteBill(billId);
        return new ResponseData<>(HttpStatus.OK.value(), "Xóa hóa đơn thành công");
    }

    @DeleteMapping("/delete-item-in-bill/{billId}")
    public ResponseData<Void> deleteItemInBill(@PathVariable String billId) {
        shoppingCartService.deleteItemInBill(billId);
        return new ResponseData<>(HttpStatus.OK.value(), "Xóa sản phẩm khỏi hóa đơn thành công");
    }

    @GetMapping("/get-bills")
    public ResponseData<Map<String, BillCountResponse>> getBills() {
        return new ResponseData<>(HttpStatus.OK.value(), "Lấy danh sách hóa đơn thành công", shoppingCartService.getBillIdsCount());
    }

    @PutMapping("/updateQuantity/{id}")
    public ResponseData<Void> updateQuantity(@RequestParam Integer quantity, @PathVariable Long id) {
        shoppingCartService.updateQuantity(id, quantity);
        return new ResponseData<>(HttpStatus.OK.value(), "Cập nhật số lượng thành công");
    }

}
