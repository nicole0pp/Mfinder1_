package com.mfinder.app.service.impl;

import com.mfinder.app.domain.Client;
import com.mfinder.app.domain.User;
import com.mfinder.app.repository.ClientRepository;
import com.mfinder.app.repository.UserRepository;
import com.mfinder.app.service.ClientService;
import com.mfinder.app.service.dto.ClientDTO;
import com.mfinder.app.service.dto.UserDTO;
import com.mfinder.app.service.mapper.ClientMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final UserRepository userRepository;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO) {
        log.debug("Request to update Client : {}", clientDTO);
        Client client = clientMapper.toEntity(clientDTO);
        // no save call needed as we have no fields that can be updated
        return clientMapper.toDto(client);
    }

    @Override
    public Optional<ClientDTO> partialUpdate(ClientDTO clientDTO) {
        log.debug("Request to partially update Client : {}", clientDTO);

        return clientRepository
            .findById(clientDTO.getId())
            .map(existingClient -> {
                clientMapper.partialUpdate(existingClient, clientDTO);

                return existingClient;
            })
            // .map(clientRepository::save)
            .map(clientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable).map(clientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDTO> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id).map(clientMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        Client client = clientRepository.findById(id).get();
        clientRepository.deleteById(id);
    }

    public void getIdUser(Long userId) {
        //WTF
        User user = userRepository.findById(userId).get();
        Client client = clientRepository.findById(userId).get();
        // clientRepository.deleteByUserId(userId);
        // Long idUser = client.getUser().getId();

        // client= clientRepository.findByd(client.getUser(idUser));
    }
}
