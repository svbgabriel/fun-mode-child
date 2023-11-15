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

import java.util.List;
import java.util.Optional;

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
        Optional<Order> orderOptional = orderRepository.findByIdAndCustomerId(id, customerId);

        if (orderOptional.isPresent()) {
            return orderOptional.get().toApiResponse();
        }

        throw new OrderNotFoundException("Order not found");
    }

    public void updateStatus(String id, boolean status, String customerId) {
        Order order = orderRepository
            .findByIdAndCustomerId(id, customerId)
            .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setActive(status);
        orderRepository.save(order);
    }

    public List<OrderResponse> getOrdersByUserId(String customerId) {
        return orderRepository.findByCustomerId(customerId)
            .stream().map(Order::toApiResponse)
            .toList();
    }

    public OrderResponse createOrder(OrderRequest request, String id) {
        List<OrderDetail> orderDetails = request.details()
            .stream()
            .map(detail -> {
                Product product = productRepository.findById(detail.productId()).orElseThrow();
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(detail.quantity());
                orderDetail.setPrice(product.getPrice());
                return orderDetail;
            })
            .toList();

        Customer customer = customerRepository.findById(id).orElseThrow();

        double totalPrice = orderDetails
            .stream()
            .mapToDouble(detail -> detail.getPrice() * detail.getQuantity())
            .sum();

        Order order = new Order();
        order.setActive(true);
        order.setDetails(orderDetails);
        order.setCustomer(customer);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        return savedOrder.toApiResponse();
    }
}
