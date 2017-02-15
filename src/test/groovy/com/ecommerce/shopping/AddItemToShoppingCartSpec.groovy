package com.ecommerce.shopping

import com.ecommerce.shopping.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Created using Intellij IDE
 * Created by rnkoaa on 2/15/17.
 */
@ActiveProfiles("local")
@ContextConfiguration(classes = ShoppingCartApplication.class)
class AddItemToShoppingCartSpec extends Specification {
    
    @Autowired
    CartService cartService;
    
    def contextLoads() {
        expect:
            cartService != null
    }
}
