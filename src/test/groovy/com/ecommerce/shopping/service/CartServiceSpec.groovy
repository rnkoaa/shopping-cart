package com.ecommerce.shopping.service

import com.ecommerce.shopping.ShoppingCartApplication
import com.ecommerce.shopping.domain.Cart
import com.ecommerce.shopping.domain.CartItem
import com.ecommerce.shopping.domain.Product
import com.ecommerce.shopping.domain.User
import com.ecommerce.shopping.util.ProductNotFoundException
import com.ecommerce.shopping.util.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * Created using Intellij IDE
 * Created by rnkoaa on 2/15/17.
 */
@ContextConfiguration(classes = ShoppingCartApplication.class)
@Transactional
class CartServiceSpec extends Specification {
    
    @Autowired
    private CartService cartService
    
    @Autowired
    private UserService userService
    
    @Autowired
    private ProductService productService
    
    def contextLoads() {
        expect:
            cartService != null
    }
    
    def "An unknown user cannot add a product to a cart"() {
        given:
            def user = User.builder().firstName("Richard").lastName("Amoako").username("richard").build() as User
        
        when:
            cartService.addItemToCart(user, 0L)
        
        then:
            thrown(UserNotFoundException.class)
    }
    
    def "An unknown product cannot be added to a cart"() {
        given:
            def user = User.builder().firstName("Richard").lastName("Amoako").username("richard").build()
        
        when:
            user = userService.save(user)
        
        then:
            user != null
            user.id > 0L
            
            def product = Product.builder().name("Product-1").serialNumber("1234").build()
        
        when:
            cartService.addItemToCart(user, product)
        
        then: "We expect a productNotFoundException"
            thrown(ProductNotFoundException.class)
    }
    
    def "For a given proper conditions, a user can add an item to a cart"() {
        given:
            def user = User.builder().firstName("Richard").lastName("Amoako").username("richard").build()
            def product = Product.builder().name("Product-1").serialNumber("1234").build()
            def cart = Cart.builder().build()
            
            cart.setUser(user)
            user.setCart(cart)
        
        when:
            user = userService.save(user)
            product = productService.save(product)
        
        
        then:
            user != null
            user.id > 0L
            user.cart != null
        
        and:
            product != null
            product.id > 0L
        
        when:
            cart = cartService.addItemToCart(user, product)
        
        then:
            cart != null
            cart.items.size() > 0 && cart.items.size() == 1
    }
    
    def "An item can be added to a cart with the number of items."() {
        given:
            def user = User.builder().firstName("Richard").lastName("Amoako").username("richard").build()
            def product = Product.builder().name("Product-1").serialNumber("1234").build()
            def cart = Cart.builder().build()
            
            cart.setUser(user)
            user.setCart(cart)
        
        when:
            user = userService.save(user)
            product = productService.save(product)
        
        
        then:
            user != null
            user.id > 0L
            user.cart != null
        
        and:
            product != null
            product.id > 0L
        
        when:
            cart = cartService.addItemToCart(user, product, 2)
        
        then:
            cart != null
            cart.items.size() > 0 && cart.items.size() == 1
        
        expect:
            CartItem item = ++cart.items.iterator() as CartItem
            
            assert item != null
            assert item.quantity == 2
    }
    
    def "Adding the same product should increase the count of the cart items"() {
        given:
            def user = User.builder().firstName("Richard").lastName("Amoako").username("richard").build()
            def product = Product.builder().name("Product-1").serialNumber("1234").build()
            def cart = Cart.builder().build()
            
            cart.setUser(user)
            user.setCart(cart)
        
        when:
            user = userService.save(user)
            product = productService.save(product)
        
        
        then:
            user != null
            user.id > 0L
            user.cart != null
        
        and:
            product != null
            product.id > 0L
        
        when:
            cart = cartService.addItemToCart(user, product)
        
        then:
            cart != null
            cart.items.size() > 0 && cart.items.size() == 1
        
        and:
            CartItem firstItem = ++cart.items.iterator() as CartItem
            
            assert firstItem != null
            assert firstItem.quantity == 1
        
        when:
            cart = cartService.addItemToCart(user, product)
        
        then:
            cart != null
            cart.items.size() > 0 && cart.items.size() == 1
        
        expect:
            CartItem item = ++cart.items.iterator() as CartItem
            
            assert item != null
            assert item.quantity == 2
    }
    
    def "Find Cart for users with Items"() {
        given:
            def user = User.builder().firstName("Richard").lastName("Amoako").username("richard").build()
            def product = Product.builder().name("Product-1").serialNumber("1234").build()
            def cart = Cart.builder().build()
            
            cart.setUser(user)
            user.setCart(cart)
        
        when:
            user = userService.save(user)
            product = productService.save(product)
        
        
        then:
            user != null
            user.id > 0L
            user.cart != null
        
        and:
            product != null
            product.id > 0L
        
        when:
            cart = cartService.addItemToCart(user, product)
        
        then:
            cart != null
            cart.items.size() > 0 && cart.items.size() == 1
        
        when:
            def optionalCart = cartService.findByUser(user)
        
        then:
            optionalCart.isPresent()
        
        and:
            def newCart = optionalCart.get()
            newCart != null
            newCart.getId() == cart.getId()
            newCart.items.size() > 0 && newCart.items.size() == 1
        
        expect:
            CartItem item = ++newCart.items.iterator() as CartItem
            
            assert item != null
            assert item.quantity == 1
    }
    
    def "removing an item from the cart ensures the size of the cart reduces"() {
        given:
            def user = User.builder().firstName("Richard").lastName("Amoako").username("richard").build()
            def product = Product.builder().name("Product-1").serialNumber("1234").build()
            def cart = Cart.builder().build()
            
            cart.setUser(user)
            user.setCart(cart)
        
        when:
            user = userService.save(user)
            product = productService.save(product)
        
        
        then:
            user != null
            user.id > 0L
            user.cart != null
        
        and:
            product != null
            product.id > 0L
        
        when:
            cart = cartService.addItemToCart(user, product)
        
        then:
            cart != null
            cart.items.size() > 0 && cart.items.size() == 1
        
        when:
            cart = cartService.removeItem(user, product)
        
        then:
            cart != null
            cart.items.size() == 0
    }
    
    def "clearing a cart ensures all items are removed"() {
        given:
            def user = User.builder().firstName("Richard").lastName("Amoako").username("richard").build()
            def product = Product.builder().name("Product-1").serialNumber("1234").build()
            def cart = Cart.builder().build()
            
            cart.setUser(user)
            user.setCart(cart)
        
        when:
            user = userService.save(user)
            product = productService.save(product)
        
        
        then:
            user != null
            user.id > 0L
            user.cart != null
        
        and:
            product != null
            product.id > 0L
        
        when:
            cart = cartService.addItemToCart(user, product)
        
        then:
            cart != null
            cart.items.size() > 0 && cart.items.size() == 1
        
        when:
            cart = cartService.clear(user)
        
        then:
            cart != null
            cart.isEmpty()
        
        when:
            def optionalCart = cartService.findByUser(user)
        
        then:
            optionalCart != null && optionalCart.isPresent()
        
        when:
            cart = optionalCart.get()
        
        then:
            cart.isEmpty()
        
        
    }
    
    def "updating the count of an item succeeds"() {
        given:
            def user = User.builder().firstName("Richard").lastName("Amoako").username("richard").build()
            def product = Product.builder().name("Product-1").serialNumber("1234").build()
            def cart = Cart.builder().build()
            
            cart.setUser(user)
            user.setCart(cart)
        
        when:
            user = userService.save(user)
            product = productService.save(product)
        
        
        then:
            user != null
            user.id > 0L
            user.cart != null
        
        and:
            product != null
            product.id > 0L
        
        when:
            cart = cartService.addItemToCart(user, product)
        
        then:
            cart != null
            cart.items.size() > 0 && cart.items.size() == 1
        
        when:
            cart = cartService.updateItem(user, product, 2)
        
        then:
            cart != null
            cart.items.size() > 0 && cart.items.size() == 1
        
        and:
            CartItem item = ++cart.items.iterator() as CartItem;
            
            assert item != null
            assert item.quantity == 3
    }
}
