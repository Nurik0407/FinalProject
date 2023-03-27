package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.StopListRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.stopList.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.impl
 * 18.03.2023
 **/
@Service
@Transactional
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    public StopListServiceImpl(StopListRepository stopListRepository, MenuItemRepository menuItemRepository) {
        this.stopListRepository = stopListRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public SimpleResponse save(StopListRequest request) {

        MenuItem menuItem = menuItemRepository.findById(request.menuItemId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("MenuItem with id: %s not found", request.menuItemId())));

        if (request.date().isBefore(LocalDate.now()) && !request.date().isEqual(LocalDate.now())) {
            throw new BadRequestException("Date must be in the future");
        }

        boolean exists = stopListRepository.existsByDateAndMenuItem_Name(request.date(), menuItem.getName());
        if (exists) {
            throw new AlreadyExistException(String.format("The MenuItem named %s has already been added to the stop list on this date", menuItem.getName()));
        }

        StopList stopList = StopList.builder()
                .reason(request.reason())
                .date(request.date())
                .menuItem(menuItem)
                .build();
        stopListRepository.save(stopList);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem named %s successfully added to stop list", menuItem.getName()))
                .build();
    }

    @Override
    public List<StopListResponse> findAll() {
        return stopListRepository.findAllStopList();
    }

    @Override
    public StopListResponse findById(Long id) {
        return stopListRepository.findStopListById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("StopList with id %s not found!", id)));
    }

    @Override
    public SimpleResponse update(Long id, StopListRequest request) {

        StopList stopList = stopListRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("StopList with id: %s not found!")));

        boolean exists = stopListRepository.existsByMenuItem_NameAndDateAndIdNot(stopList.getMenuItem().getName(), request.date(), id);
        if (exists) {
            throw new AlreadyExistException(String.format("The MenuItem named %s has already been added to the stop list on this date", stopList.getMenuItem().getName()));
        }

        stopList.setReason(request.reason());
        stopList.setDate(request.date());
        stopListRepository.save(stopList);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("StopList with id: %s successfully updated", id))
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!stopListRepository.existsById(id)) {
            throw new NotFoundException(
                    String.format("StopList with id %s not found!", id));
        }

        stopListRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("StopList with id: %s successfully deleted", id))
                .build();
    }
}
