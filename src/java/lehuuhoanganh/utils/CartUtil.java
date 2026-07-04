package lehuuhoanganh.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Base64;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartUtil {
    
    public HashMap<String, CartItem> getCartFromCookie(Cookie cookieCart) {
        HashMap<String, CartItem> cart = new HashMap<>();
        if (cookieCart == null || cookieCart.getValue().isEmpty()) return cart;
        try {
            String decodedString = new String(Base64.getDecoder().decode(cookieCart.getValue().getBytes("UTF-8")));
            String[] itemsList = decodedString.split("\\|");
            for (String strItem : itemsList) {
                if (strItem.trim().isEmpty()) continue;
                String[] arrItemDetail = strItem.split(",");
                String itemId = arrItemDetail[0].trim();
                String itemName = arrItemDetail[1].trim();
                int quantity = Integer.parseInt(arrItemDetail[2].trim());
                double unitPrice = Double.parseDouble(arrItemDetail[3].trim());
                cart.put(itemId, new CartItem(itemId, itemName, quantity, unitPrice));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }

    public Cookie getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] arrCookies = request.getCookies();
        if (arrCookies != null) {
            for (Cookie cookie : arrCookies) {
                if (cookie.getName().equals(cookieName)) return cookie;
            }
        }
        return null;
    }

    public void saveCartToCookie(HttpServletRequest request, HttpServletResponse response, String strItemsInCart) {
        try {
            String encodedString = Base64.getEncoder().encodeToString(strItemsInCart.getBytes("UTF-8"));
            Cookie cookieCart = new Cookie("Cart", encodedString);
            cookieCart.setMaxAge(30 * 60); // Lưu cookie trong 30 phút giống Lab05
            cookieCart.setPath(request.getContextPath());
            response.addCookie(cookieCart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String convertCartToString(List<CartItem> itemsList) {
        StringBuilder sb = new StringBuilder();
        for (CartItem item : itemsList) {
            sb.append(item.getItemId()).append(",")
              .append(item.getItemName()).append(",")
              .append(item.getQuantity()).append(",")
              .append(item.getUnitPrice()).append("|");
        }
        return sb.toString();
    }
}