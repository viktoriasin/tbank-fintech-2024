package org.tbank.spring.locations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tbank.spring.aop.Timed;
import org.tbank.spring.common.DataSource;

import java.util.List;

@Timed
@RestController
@RequestMapping(value = "/api/v1/locations")
public class LocationsController {

    @Autowired
    private DataSource<Integer, Location> dataSource;

    @GetMapping("")
    public List<Location> getLocations() {
        return dataSource.getAll();
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable(value = "id") Integer id) {
        return dataSource.get(id);
    }

    @PostMapping("")
    public void postLocation(
            @RequestParam String slug,
            @RequestParam String name
    ) {
        dataSource.put(new Location(slug, name));
    }

    @PutMapping("/{id}")
    public void putLocation(
            @PathVariable(value = "id") Integer id,
            @RequestParam(required = false) String slug,
            @RequestParam(required = false) String name
    ) {
        dataSource.update(id, location -> {
            if (slug != null) location.setSlug(slug);
            if (name != null) location.setName(name);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable(value = "id") Integer id) {
        dataSource.delete(id);
    }
}
