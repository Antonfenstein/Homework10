package dto;

public class CourierCreationDto {

        int id;
        String login;
        String password;
        String name;

    public CourierCreationDto(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }
}
