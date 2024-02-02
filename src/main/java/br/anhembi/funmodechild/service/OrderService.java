package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Customer;
import br.anhembi.funmodechild.entity.Order;
import br.anhembi.funmodechild.entity.Order.OrderDetail;
import br.anhembi.funmodechild.entity.Product;
import br.anhembi.funmodechild.model.common.OrderNotFoundException;
import br.anhembi.funmodechild.model.request.OrderRequest;
import br.anhembi.funmodechild.model.response.OrderResponse;
import br.anhembi.funmodechild.repository.CustomerRepository;
import br.anhembi.funmodechild.repository.OrderRepository;
import br.anhembi.funmodechild.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public OrderResponse getOrderById(String id, String customerId) {
        Order order = orderRepository.findByIdAndCustomerId(id, customerId);

        if (order != null) {
            return order.toApiResponse();
        }

        throw new OrderNotFoundException("Order not found");
    }

    public void updateStatus(String id, boolean status, String customerId) {
        Order order = orderRepository.findByIdAndCustomerId(id, customerId);

        if (order == null) {
            throw new OrderNotFoundException("Order not found");
        }

        order.setActive(status);
        orderRepository.save(order);
    }

    public List<OrderResponse> getOrdersByUserId(String customerId) {
        return orderRepository.findByCustomerId(customerId)
            .stream()
            .map(Order::toApiResponse)
            .toList();
    }

    public OrderResponse createOrder(OrderRequest request, String id) {
        List<OrderDetail> orderDetails = request.details
            .stream()
            .map(detail -> {
                Product product = productRepository.findById(detail.productId).orElseThrow();
                return new OrderDetail(product, product.getPrice(), detail.quantity);
            })
            .toList();

        Customer customer = customerRepository.findById(id).orElseThrow();

        double totalPrice = orderDetails
            .stream()
            .mapToDouble(detail -> detail.getPrice() * detail.getQuantity())
            .sum();

        Order order = new Order(null, LocalDateTime.now(), customer, orderDetails, totalPrice, true);

        Order savedOrder = orderRepository.save(order);

        return savedOrder.toApiResponse();
    }
}
