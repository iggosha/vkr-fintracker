package ru.golovkov.fintracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.fintracker.dto.MoneyFlowDto;
import ru.golovkov.fintracker.dto.parsed.ParsedAccountDto;
import ru.golovkov.fintracker.dto.parsed.ParsedClientDto;
import ru.golovkov.fintracker.dto.parsed.ParsedEntitiesDto;
import ru.golovkov.fintracker.dto.parsed.ParsedMoneyFlowDto;
import ru.golovkov.fintracker.exception.ResourceNotFoundException;
import ru.golovkov.fintracker.mapper.MoneyFlowMapper;
import ru.golovkov.fintracker.model.Account;
import ru.golovkov.fintracker.model.Category;
import ru.golovkov.fintracker.model.Client;
import ru.golovkov.fintracker.model.MoneyFlow;
import ru.golovkov.fintracker.repository.AccountRepository;
import ru.golovkov.fintracker.repository.CategoryRepository;
import ru.golovkov.fintracker.repository.ClientRepository;
import ru.golovkov.fintracker.repository.MoneyFlowRepository;
import ru.golovkov.fintracker.service.MoneyFlowService;
import ru.golovkov.fintracker.util.FinparserClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class MoneyFlowServiceImpl implements MoneyFlowService {

    private final FinparserClient finparserClient;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    private final MoneyFlowRepository repository;
    private final MoneyFlowMapper mapper;

    @Override
    public MoneyFlowDto create(MoneyFlowDto dto) {
        return null;
    }

    @Override
    public List<MoneyFlowDto> createAll(List<MoneyFlowDto> dtos) {
        return List.of();
    }

    @Override
    public void createFromFile(MultipartFile sheetFile) {
        ParsedEntitiesDto parsedEntities = finparserClient.getParsedEntities(sheetFile);

        Client client = findOrSaveClient(parsedEntities.getClient());
        Account account = findOrSaveAccount(parsedEntities.getAccount(), client);
        Map<String, Category> categoryMap = getCategoryMap();
        Set<String> categoryNames = new HashSet<>(categoryMap.keySet());

        List<MoneyFlow> moneyFlows = new ArrayList<>();
        for (ParsedMoneyFlowDto parsedMoneyFlow : parsedEntities.getMoneyFlows()) {
            MoneyFlow moneyFlow = mapper.mapToEntityFromParsed(parsedMoneyFlow);
            String categoryName = parsedMoneyFlow.getCategoryName();
            Category category = findOrSaveCategory(categoryName, categoryNames, categoryMap);
            moneyFlow.setCategory(category);
            moneyFlow.setAccount(account);
            moneyFlows.add(moneyFlow);
        }
        repository.saveAll(moneyFlows);
    }

    private Client findOrSaveClient(ParsedClientDto client) {
        return clientRepository.findByName(client.getName())
                .orElseGet(() -> {
                    Client newClient = new Client();
                    newClient.setName(client.getName());
                    return clientRepository.save(newClient);
                });
    }

    private Account findOrSaveAccount(ParsedAccountDto account, Client client) {
        return accountRepository
                .findById(account.getId())
                .orElseGet(() -> {
                    Account newAccount = new Account();
                    newAccount.setId(account.getId());
                    newAccount.setType(account.getType());
                    newAccount.setCreationDate(account.getCreationDate());
                    newAccount.setClient(client);
                    return accountRepository.save(newAccount);
                });
    }

    private Category findOrSaveCategory(String categoryName, Set<String> categoryNames, Map<String, Category> categoryMap) {
        if (!categoryNames.contains(categoryName)) {
            Category category = new Category();
            category.setName(categoryName);
            category = categoryRepository.save(category);
            categoryMap.put(categoryName, category);
            categoryNames.add(categoryName);
            return category;
        } else {
            return categoryMap.get(categoryName);
        }
    }

    private Map<String, Category> getCategoryMap() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, Category> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getName(), category);
        }
        return categoryMap;
    }

    @Override
    public MoneyFlowDto getById(String id) {
        return mapper.mapToDtoFromEntity(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Money flow with id " + id + " was not found")
        ));
    }

    @Override
    public List<MoneyFlowDto> getAll(Pageable pageable) {
        Page<MoneyFlow> moneyFlowsPage = repository.findAll(pageable);
        return mapper.mapToDtosFromEntities(moneyFlowsPage.getContent());
    }

    @Override
    public MoneyFlowDto updateById(String id, MoneyFlowDto dto) {
        return null;
    }

    @Override
    public void deleteById(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Money flow with id " + id + " was not found");
        }
    }

    @Override
    public void deleteAllByIds(List<String> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
