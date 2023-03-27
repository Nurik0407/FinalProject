package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.ChequeRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.cheque.ChequeResponse;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 18.03.2023
 **/
@RestController
@RequestMapping("/api/cheques")
public class ChequeAPI {
   private final ChequeService chequeService;

    public ChequeAPI(ChequeService chequeService) {
        this.chequeService = chequeService;
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid ChequeRequest request){
        return  chequeService.save(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/{id}")
    public ChequeResponse findById(@PathVariable Long id){
        return chequeService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,@RequestBody @Valid ChequeRequest request ){
        return chequeService.update(id,request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return chequeService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping
    public List<ChequeResponse> findAll(){
        return chequeService.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/total/{id}")
    public SimpleResponse totalSum(@PathVariable Long id,
                                   @RequestParam(required = false) LocalDate date){
        return chequeService.totalSum(id,Objects.requireNonNullElseGet(date,LocalDate::now));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/avg")
    public SimpleResponse avg(@RequestParam(required = false) LocalDate date){
        return chequeService.avg(Objects.requireNonNullElseGet(date, LocalDate::now));
    }


}
