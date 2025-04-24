package com.dan.shoe.perfume.controllers;

import com.dan.shoe.perfume.services.CartService;
import com.dan.shoe.perfume.dtos.requests.CartRequest;
import com.dan.shoe.perfume.dtos.requests.OrderNowCreation;
import com.dan.shoe.perfume.dtos.responses.ResponseMessage;
import com.dan.shoe.perfume.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addToCart(HttpServletRequest request, @RequestBody CartRequest cartRequest) {
        String token = getTokenFromRequest(request);
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(cartService.addToCart(username, cartRequest));
    }

    @PostMapping("/add/now")
    public ResponseEntity<ResponseMessage> addCartNow(HttpServletRequest request, @RequestBody OrderNowCreation cartRequest) {
        String token = getTokenFromRequest(request);
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(cartService.addCartNow(username, cartRequest));
    }

    @GetMapping("/view")
    public ResponseEntity<?> getMyCart(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String username = jwtService.extractUsername(token);
            return new ResponseEntity<>(cartService.getCartByUser(username), HttpStatus.OK);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/update/{cartItemId}/{quantity}")
    public ResponseEntity<ResponseMessage> updateCart(HttpServletRequest request, @PathVariable Long cartItemId, @PathVariable int quantity) {
        String token = getTokenFromRequest(request);
        String username = jwtService.extractUsername(token);
        if (quantity <= 0) {
            return ResponseEntity.ok(cartService.removeFromCart(username, cartItemId));
        }
        return ResponseEntity.ok(cartService.updateCart(username, cartItemId, quantity));
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<ResponseMessage> removeFromCart(HttpServletRequest request, @PathVariable Long cartItemId) {
        String token = getTokenFromRequest(request);
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(cartService.removeFromCart(username, cartItemId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ResponseMessage> clearCart(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(cartService.clearCart(username));
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new RuntimeException("JWT Token is missing");
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> getCartTotal(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(cartService.getCartTotal(username));
    }
}
