package peaksoft.service;

import peaksoft.dto.requests.ChequeRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.cheque.ChequeResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 18.03.2023
 **/
public interface ChequeService {
    List<ChequeResponse> findAll();

    SimpleResponse save(ChequeRequest request);

    ChequeResponse findById(Long id);

    SimpleResponse update(Long id,ChequeRequest request);
    SimpleResponse delete(Long id);

    SimpleResponse totalSum(Long id,LocalDate date);

    SimpleResponse avg(LocalDate date);
}
