package com.workintech.s17d2.rest;

import com.workintech.s17d2.dto.DeveloperResponse;
import com.workintech.s17d2.model.DeveloperFactory;
import com.workintech.s17d2.tax.DeveloperTax;
import com.workintech.s17d2.tax.Taxable;
import com.workintech.s17d2.utils.Constants;
import com.workintech.s17d2.validation.DeveloperValidation;
import jakarta.annotation.PostConstruct;
import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.Experience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path = "/developers")
public class DeveloperController {
    private Map<Integer, Developer> developers;
    private Taxable taxable;

    @Autowired
    public DeveloperController(DeveloperTax taxable) {
        this.taxable = taxable;
    }
    @PostConstruct
    public void loadAll(){

        this.developers = new HashMap<>();
        developers.put(1,new Developer(1,"dd", 24000.0,Experience.JUNIOR));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperResponse save(@RequestBody Developer developer) {
        Developer createdDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        if (Objects.nonNull(createdDeveloper))
            developers.put(createdDeveloper.getId(), createdDeveloper);
        return new DeveloperResponse(createdDeveloper, Constants.SUCCEED_MSG, HttpStatus.OK.value());
    }

    @GetMapping
    public List<Developer> getAll() {
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public DeveloperResponse getById(@PathVariable("id") Integer id) {
        if (DeveloperValidation.isDeveloperExist(this.developers, id))
            return new DeveloperResponse(this.developers.get(id), Constants.SUCCEED_MSG, HttpStatus.OK.value());

        return new DeveloperResponse(null, "developer does not exist", HttpStatus.NOT_FOUND.value());
    }

    @PutMapping("/{id}")
    public DeveloperResponse update(@PathVariable("id") Integer id, @RequestBody Developer developer) {
        if (!DeveloperValidation.isDeveloperExist(this.developers, id))
            return new DeveloperResponse(null, "developer already not exist id= " + id, HttpStatus.NOT_FOUND.value());
        developer.setId(id);
        Developer updatedDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        this.developers.put(updatedDeveloper.getId(), updatedDeveloper);
        return new DeveloperResponse(updatedDeveloper, Constants.SUCCEED_MSG, HttpStatus.OK.value());
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse delete(@PathVariable("id") Integer id) {
        Developer removedDeveloper = this.developers.get(id);
        this.developers.remove(id);
        return new DeveloperResponse(removedDeveloper, Constants.SUCCEED_MSG, HttpStatus.OK.value());
    }

}
