package dis.extraordinaria.backend.controller;

import dis.extraordinaria.backend.model.DataModel;
import dis.extraordinaria.backend.service.DataModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class DataModelController {
    @Autowired
    private DataModelService dataModelService;

    @GetMapping
    public List<DataModel> getAllUsers() throws IOException {
        return dataModelService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<DataModel> getUserById(@PathVariable Long id) throws IOException {
        return dataModelService.getUserById(id);
    }

    @PostMapping
    public DataModel createUser(@RequestBody DataModel user) throws IOException {
        return dataModelService.saveUser(user);
    }

    @PutMapping("/{id}")
    public DataModel updateUser(@PathVariable Long id, @RequestBody DataModel user) throws IOException {
        user.setId(id);
        return dataModelService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws IOException {
        dataModelService.deleteUser(id);
    }
}
