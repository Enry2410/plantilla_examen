package dis.extraordinaria.backend.service;

import dis.extraordinaria.backend.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataModelService {
    @Autowired
    private final Gson gson = new Gson();
    String json_path="src/main/resources/data.json";
    public List<DataModel> getAllUsers() throws IOException {
        return readFromJson(json_path);
    }

    public Optional<DataModel> getUserById(Long id) throws IOException {
        return getAllUsers().stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public DataModel saveUser(DataModel user) throws IOException {
        List<DataModel> users = getAllUsers();
        if (user.getId() == null) {
            user.setId(users.stream().mapToLong(DataModel::getId).max().orElse(0L) + 1);
        }
        users = users.stream().filter(u -> !u.getId().equals(user.getId())).collect(Collectors.toList());
        users.add(user);
        writeToJson(users, json_path);
        return user;
    }

    public void deleteUser(Long id) throws IOException {
        List<DataModel> users = null;
        try {
            users = getAllUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        users = users.stream().filter(user -> !user.getId().equals(id)).collect(Collectors.toList());
        try {
            writeToJson(users, json_path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DataModel> readFromJson(String filePath) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(filePath);
        Type userListType = new TypeToken<List<DataModel>>(){}.getType();
        List<DataModel> dataModel = gson.fromJson(reader, userListType);
        reader.close();
        return dataModel;
    }

    private void writeToJson(List<DataModel> users, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        gson.toJson(users, writer);
        writer.flush();
        writer.close();
    }
}
