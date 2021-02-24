package az.ibar.customerservice.service.impl;

import az.ibar.customerservice.client.NotificationServiceClient;
import az.ibar.customerservice.client.model.MailSendDto;
import az.ibar.customerservice.error.CustomerIsExistException;
import az.ibar.customerservice.error.CustomerNotFoundException;
import az.ibar.customerservice.mapper.CustomerMapper;
import az.ibar.customerservice.model.dto.CustomerDto;
import az.ibar.customerservice.model.dto.CustomerRegisterDto;
import az.ibar.customerservice.model.entity.CustomerEntity;
import az.ibar.customerservice.repository.CustomerRepository;
import az.ibar.customerservice.service.CustomerService;
import az.ibar.customerservice.util.AES;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static az.ibar.customerservice.util.CustomerUtil.ACTIVATION_LINK;
import static az.ibar.customerservice.util.CustomerUtil.REGISTRATION_KEY;
import static az.ibar.customerservice.util.CustomerUtil.REGISTRATION_SUBJECT;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final NotificationServiceClient notificationServiceClient;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto createCustomer(CustomerRegisterDto dto) {
        Optional<CustomerEntity> entity = customerRepository.findByEmail(dto.getEmail());
        log.info("entity: {}", entity);
        if (entity.isEmpty()) {
            CustomerEntity customer = customerRepository.save(customerMapper.toCustomerEntity(dto));

            String key = AES.encrypt(dto.getEmail(), REGISTRATION_KEY);
            key = Optional.ofNullable(key)
                    .map(k -> URLEncoder.encode(k, StandardCharsets.UTF_8))
                    .orElseThrow(RuntimeException::new);

            final String url = ACTIVATION_LINK + key;
            final String message = String.format("Click to activate: %s", url);

            notificationServiceClient.send(MailSendDto.builder()
                    .message(message)
                    .subject(REGISTRATION_SUBJECT)
                    .toMail(dto.getEmail())
                    .build());

            return customerMapper.toCustomerDto(customer);
        } else {
            throw new CustomerIsExistException();
        }
    }

    @SneakyThrows
    @Override
    public void activateCustomer(String key) {
        log.info("key:{}", key);
        key = URLDecoder.decode(key, StandardCharsets.UTF_8.name());
        log.info("key:{}", key);

        String email = AES.decrypt(key, REGISTRATION_KEY);
        log.info("email:{}", email);

        var customerEntity = customerRepository.findByEmail(email)
                .map(entity -> {
                    entity.setActivated(Boolean.TRUE);
                    return entity;
                })
                .orElseThrow(CustomerNotFoundException::new);

        customerRepository.save(customerEntity);
    }

    /**
     * Will be implemented by adding to check this is active user or not
     *
     * @param id
     * @return
     */
    @Override
    public CustomerDto getCustomer(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
//        if (customer.isActivated())
//            return customerMapper.toCustomerDto(customer);
//        else throw new CustomerNotFoundException();
        return customerMapper.toCustomerDto(customerEntity);
    }
}