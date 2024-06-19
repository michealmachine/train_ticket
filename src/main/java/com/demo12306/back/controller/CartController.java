package com.demo12306.back.controller;

import com.demo12306.back.common.R;
import com.demo12306.back.entity.Ticket;
import com.demo12306.back.service.impl.CartService;
import com.demo12306.back.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    // 添加购物车项目
    @PostMapping("/add")
    public R<String> addToCart(@RequestBody Ticket ticket, HttpServletRequest request, HttpServletResponse response) {
        Integer id = JwtUtils.getIdFromToken(request.getHeader("Authorization"));
        ticket.setUserId(id);
        // 获取或创建购物车ID（保存在Cookie中）
        String cartId = getOrCreateCartId(request, response);

        // 使用Service层的逻辑将ticket添加到购物车
        cartService.addToCart(cartId, ticket);

        return R.success("Ticket added to cart");
    }

    // 从购物车中移除项目
    @DeleteMapping("/remove/{id}")
    public R<String> removeFromCart(@PathVariable Integer id, HttpServletRequest request) {
        // 获取购物车ID（从Cookie中）
        String cartId = getCartIdFromCookie(request);

        // 使用Service层的逻辑从购物车中移除ticket
        cartService.removeFromCart(cartId, id);

        return R.success("Ticket removed from cart");
    }

    // 获取购物车内容
    @GetMapping("/get")
    public R<List<Ticket>> getCart(HttpServletRequest request) {
        // 获取购物车ID（从Cookie中）
        String cartId = getCartIdFromCookie(request);
        System.out.println(cartId);
        // 使用Service层的逻辑获取购物车内容
        return R.success(cartService.getCart(cartId));
    }

    // 获取或创建购物车ID
    private String getOrCreateCartId(HttpServletRequest request, HttpServletResponse response) {
        String cartId = getCartIdFromCookie(request);
        if (cartId == null) {
            cartId = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("cartId", cartId);
            cookie.setMaxAge(60 * 60 * 24 * 7);  // 7 days
            response.addCookie(cookie);
        }
        return cartId;
    }

    // 从Cookie中获取购物车ID
    private String getCartIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cartId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
